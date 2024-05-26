package mat.server.threads;

import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import mat.model.game.Board;
import mat.model.game.Cell;
import mat.model.game.GameState;
import mat.model.game.Ship;
import mat.model.networkEvents.gamePlay.AlternateBoard;
import mat.model.networkEvents.gamePlay.BombRequest;
import mat.model.networkEvents.gamePlay.Ready;
import mat.model.networkEvents.gamePlay.UpdateRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class GameThread extends Thread {

    private final AuthToken authToken1;
    private final Socket socket1;
    private final AuthToken authToken2;
    private final Socket socket2;
    private final UserDB userDB = new UserDB();
    protected GameState gameState;

    public GameThread(AuthToken authToken1, Socket socket1, AuthToken authToken2, Socket socket2) {
        this.authToken1 = authToken1;
        this.socket1 = socket1;
        this.authToken2 = authToken2;
        this.socket2 = socket2;
    }

    @Override
    public void run() {
        gameState = new GameState(userDB.get(authToken1), userDB.get(authToken2));
        SoloGameThread thread1 = new SoloGameThread(socket1, authToken1);
        SoloGameThread thread2 = new SoloGameThread(socket2, authToken2);
        EndGameListener endGameListener = new EndGameListener() {
            @Override
            public void listenEnd(AuthToken authToken) {
                User user1 = Objects.requireNonNull(userDB.get(authToken1));
                User user2 = Objects.requireNonNull(userDB.get(authToken2));
                if(authToken.equals(authToken1)){
                    user1.won();
                    user2.lost();
                } else if (authToken.equals(authToken2)) {
                    user2.won();
                    user1.lost();
                } else throw new RuntimeException("Invalid AuthToken");
                userDB.update(user1);
                userDB.update(user2);
            }
        };
        thread1.setEndGameListener(endGameListener);
        thread2.setEndGameListener(endGameListener);
        thread1.start();
        thread2.start();
    }

    private interface EndGameListener{
        void listenEnd(AuthToken authToken);
    }

    public class SoloGameThread extends Thread {

        private final Socket socket;
        private final AuthToken authToken;
        private EndGameListener endGameListener;

        SoloGameThread(Socket socket, AuthToken authToken){
            this.socket = socket;
            this.authToken = authToken;
        }

        public void setEndGameListener(EndGameListener endGameListener) {
            this.endGameListener = endGameListener;
        }

        public void writeGameState(){
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(gameState);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                writeGameState();
                while (true) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();
                    if (object instanceof AlternateBoard && gameState.getTurn() == 0) {
                        if(gameState.getTurn()==0) {
                            if (gameState.getAlternationLeft(authToken) > 0) {
                                gameState.setBoard(authToken, Board.generate());
                                gameState.reduceAlternationLeft(authToken);
                                if (gameState.getAlternationLeft(authToken) == 0) {
                                    gameState.setReady(authToken);
                                }
                                checkForStart();
                            }
                        }
                    } else if (object instanceof Ready) {
                        if(gameState.getTurn()==0) {
                            gameState.setReady(authToken);
                            checkForStart();
                        }
                    } else if (object instanceof BombRequest bombRequest) {
                        if (gameState.getTurn() % 2 == gameState.getUserInt(authToken) % 2) {
                            try {
                                gameState.getEnemyBoard(authToken).bomb(bombRequest.getCell());
                                checkForShipExplosion(bombRequest.getCell());
                                boolean hit = false;
                                for (Ship ship :
                                        gameState.getEnemyBoard(authToken).getShips()) {
                                    if (ship.hit(bombRequest.getCell())) {
                                        hit = true;
                                        break;
                                    }
                                }
                                if (!hit) {
                                    gameState.nextTurn();
                                } else {
                                    checkForEnd();
                                }
                            } catch (Board.RepeatedBombException ignored) {

                            } catch (Board.CellOutOfRangeException exception) {
                                exception.printStackTrace();
                            }
                        }
                    } else if (object instanceof UpdateRequest){
                        if (gameState.getTurn() == 0) {
                            checkForStart();
                        }
                    }
                    writeGameState();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void checkForEnd() {
            boolean allShipsDestroyed = true;
            for (Ship ship :
                    gameState.getEnemyBoard(authToken).getShips()) {
                if(!ship.isDestroyed()){
                    allShipsDestroyed = false;
                    break;
                }
            }
            if(allShipsDestroyed){
                gameState.setTurn(-1);
                endGameListener.listenEnd(authToken);
            }
        }

        private void checkForShipExplosion(Cell attacked) {
            for (Ship ship :
                    gameState.getEnemyBoard(authToken).getShips()) {
                if (ship.hit(attacked)) {
                    boolean allCellsHit = true;
                    for (Cell cell :
                            ship.getCells()) {
                        boolean thisCellHit = false;
                        for (Cell cell2 :
                                gameState.getEnemyBoard(authToken).getBombs()) {
                            if (cell2.equals(cell)) {
                                thisCellHit = true;
                                break;
                            }
                        }
                        if (!thisCellHit) {
                            allCellsHit = false;
                            break;
                        }
                    }
                    if(allCellsHit){
                        ship.setDestroyed(true);
                        for (Cell cell :
                                ship.getCells()) {
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() - 1, cell.getY())
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() + 1, cell.getY())
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX(), cell.getY() - 1)
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() , cell.getY() + 1)
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() - 1, cell.getY() - 1)
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() + 1, cell.getY() + 1)
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() + 1, cell.getY() - 1)
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                            try {
                                gameState.getEnemyBoard(authToken).bomb(
                                        new Cell(cell.getX() - 1 , cell.getY() + 1)
                                );
                            } catch (Board.CellOutOfRangeException | Board.RepeatedBombException ignored){

                            }
                        }
                    }
                }
            }
        }

        private void checkForStart() {
            if(gameState.bothReady()){
                gameState.nextTurn();
            }
        }
    }
}
