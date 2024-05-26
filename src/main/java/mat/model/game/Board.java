package mat.model.game;

import mat.client.config.ConfigAgent;
import mat.client.config.GameConfig;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class Board implements Serializable {

    private final HashSet<Ship> ships;
    private final HashSet<Cell> bombs = new HashSet<>();

    public Board(@NotNull HashSet<Ship> ships) {
        this.ships = ships;
    }

    public HashSet<Ship> getShips() {
        return ships;
    }

    public HashSet<Cell> getBombs() {
        return bombs;
    }

    public static Board generate(){

        interface Inter{
            int get(String key);
        }

        Inter gameConfig = new Inter() {
            final GameConfig interGameConfig = new ConfigAgent().getGameConfig();
            @Override
            public int get(String key) {
                return Integer.parseInt(interGameConfig.get(key));
            }
        };

        final int dimension = gameConfig.get("dimension");
        final int typeCount = gameConfig.get("typeCount");

        Random random = new Random();

        HashSet<Ship> ships = new HashSet<>();

        for (int i = 1; i <= typeCount; i++) {
            final int thisTypeCount = gameConfig.get("type" + i + "Count");
            int typeLength = gameConfig.get("type" + i + "Length");
            int loopCounter = thisTypeCount;
            while (loopCounter > 0) {
                int horizontalVertical = random.nextInt(2);
                int x;
                int y;
                Ship ship;
                if (horizontalVertical == 0){//Horizontal

                    x = random.nextInt(dimension - typeLength) + 1;
                    y = random.nextInt(dimension) + 1;

                    ship = new Ship(
                            new Cell(x, y), new Cell(x + typeLength - 1, y)
                    );

                } else {//Vertical

                    x = random.nextInt(dimension) + 1;
                    y = random.nextInt(dimension - typeLength) + 1;

                    ship = new Ship(
                            new Cell(x, y), new Cell(x, y + typeLength - 1)
                    );

                }

                boolean flag = false;
                for (Ship shipIt :
                        ships) {
                    for (Cell cell :
                            ship.getCells()) {
                        for (Cell cellIt :
                                shipIt.getCells()) {
                            if (cell.equals(cellIt)
                            || cell.equals(new Cell(cellIt.getX(), cellIt.getY() + 1))
                            || cell.equals(new Cell(cellIt.getX(), cellIt.getY() - 1))
                            || cell.equals(new Cell(cellIt.getX() + 1, cellIt.getY()))
                            || cell.equals(new Cell(cellIt.getX() - 1, cellIt.getY()))
                            || cell.equals(new Cell(cellIt.getX() + 1, cellIt.getY() + 1))
                            || cell.equals(new Cell(cellIt.getX() - 1, cellIt.getY() + 1))
                            || cell.equals(new Cell(cellIt.getX() + 1, cellIt.getY() - 1))
                            || cell.equals(new Cell(cellIt.getX() - 1, cellIt.getY() - 1))) {
                                flag = true;
                                break;
                            }
                            if (cell.equals(new Cell(cellIt.getX(), cellIt.getY() + 1))) {
                            }
                        }
                    }
                }
                if(flag) continue;

                ships.add(ship);
                loopCounter--;
            }
        }

        return new Board(ships);
    }

    @Override
    public Board clone() {
        Board board = new Board(this.ships);
        board.getBombs().addAll(this.bombs);
        return board;
    }


    public static class RepeatedBombException extends Exception{}

    public static class CellOutOfRangeException extends Exception{}

    public void bomb(Cell cell) throws RepeatedBombException, CellOutOfRangeException{
        if (cell.getX() > 0 && cell.getX() <= new ConfigAgent().getGameConfig().getInt("dimension")
         && cell.getY() > 0 && cell.getY() <= new ConfigAgent().getGameConfig().getInt("dimension")) {
            for (Cell bomb :
                    bombs) {
                if (bomb.equals(cell)) {
                    throw new RepeatedBombException();
                }
            }
            bombs.add(cell);
        } else {
            throw new CellOutOfRangeException();
        }
    }
}
