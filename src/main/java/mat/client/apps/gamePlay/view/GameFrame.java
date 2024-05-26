package mat.client.apps.gamePlay.view;

import mat.client.apps.gamePlay.model.GameFrameListener;
import mat.client.apps.gamePlay.view.boardPanel.model.BoardListener;
import mat.client.apps.gamePlay.view.boardPanel.view.BoardPanel;
import mat.client.controller.Quiter;
import mat.model.authentification.AuthToken;
import mat.model.game.Board;
import mat.model.game.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

public class GameFrame extends JFrame {

    private final AuthToken authToken;

    private GameState gameState;
    private Board myBoard;
    private Board enemyBoard;

    private BoardPanel myBoardPanel;
    private BoardPanel enemyBoardPanel;
    private ButtonPanel buttonPanel;
    private NamePanel namePanel;

    private Timer timer;

    private final GameFrameListener gameFrameListener;

    public GameFrame(AuthToken authToken, GameState gameState, Socket socket){
        this.authToken = authToken;
        this.gameState = gameState;
        gameFrameListener  = new GameFrameListener(this, socket);
        resetBoards();
        setGraphics();
        buttonPanel.setTime(gameState.getUserInt(authToken));
        timer = new Timer(100, e -> timeUpdate());
        timer.start();
        myBoardPanel.setShipDraw(true);
        enemyBoardPanel.setShipDraw(false);
    }

    private void timeUpdate() {
        gameFrameListener.listenUpdate();
    }


    public void resetBoards(){
        myBoard = gameState.getBoard(authToken);
        enemyBoard = gameState.getEnemyBoard(authToken);
    }

    private static final int panelSize = BoardPanel.boardGraphicalDimension;

    private void setGraphics() {
        this.setSize(panelSize + panelSize + 65 , 50 + panelSize + 200);
        this.setTitle("BattleShip");
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                dispose();
                Quiter quiter = new Quiter(authToken);
                quiter.quit();
            }
        });

        this.namePanel = new NamePanel(gameState.getMyName(authToken), gameState.getEnemyName(authToken));
        namePanel.setBounds(0,0, namePanel.getWidth(), namePanel.getHeight());
        this.add(namePanel);


        myBoardPanel = new BoardPanel(myBoard);
        myBoardPanel.setBounds(0,50, myBoardPanel.getWidth(), myBoardPanel.getHeight());
        this.add(myBoardPanel);
        myBoardPanel.setListener((x, y) -> {

        });

        enemyBoardPanel = new BoardPanel(enemyBoard);
        enemyBoardPanel.setBounds(panelSize +50, 50, panelSize, panelSize);
        this.add(enemyBoardPanel);
        enemyBoardPanel.setListener((x, y) -> {
            if (gameState.getTurn() > 0) {
                if (gameState.getTurn() % 2 == gameState.getUserInt(authToken) % 2) {
                    gameFrameListener.listenBomb(x, y);
                }
            }
        });

        buttonPanel = new ButtonPanel();
        this.add(buttonPanel);
        buttonPanel.setBounds(0, 50+ panelSize + 50, buttonPanel.getWidth(), buttonPanel.getHeight());
        buttonPanel.setListeners(new ButtonPanelListener() {
            //leftButtonText is ready
            @Override
            public void leftButtonPressed() {
                if (gameState.getTurn() == 0) {
                    if (!gameState.isEnemyReady(authToken)) {
                        buttonPanel.disableButtons();
                        buttonPanel.setLeftButtonText("Waiting for the opponent...");
                    }
                    gameFrameListener.listenReadyButton();
                }
            }

            //rightButtonText is alternate board
            @Override
            public void rightButtonPressed() {
                gameFrameListener.listenAlternateButton();
            }
        });
    }

    public GameState getGameState() {
        return gameState;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public BoardPanel getEnemyBoardPanel() {
        return enemyBoardPanel;
    }

    public BoardPanel getMyBoardPanel() {
        return myBoardPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public Timer getTimer() {
        return timer;
    }

    public NamePanel getNamePanel() {
        return namePanel;
    }
}
