package mat.client.apps.gamePlay.model;

import mat.client.apps.gamePlay.controller.Controller;
import mat.client.apps.gamePlay.controller.ControllerListener;
import mat.client.apps.gamePlay.view.GameFrame;
import mat.client.apps.menu.view.MenuFrame;
import mat.model.authentification.AuthToken;
import mat.model.game.GameState;

import java.awt.*;
import java.net.Socket;

public class GameFrameListener {

    private final Controller controller;
    private final GameFrame gameFrame;
    private final AuthToken authToken;

    public GameFrameListener(GameFrame gameFrame, Socket socket) {
        this.authToken = gameFrame.getAuthToken();
        this.gameFrame = gameFrame;
        controller = new Controller(socket, gameFrame.getAuthToken(), this::updateGameFrame);
    }

    public void updateGameFrame(GameState gameState){
        gameFrame.setGameState(gameState);
        gameFrame.resetBoards();
        gameFrame.getEnemyBoardPanel().updateBoard(gameFrame.getEnemyBoard());
        gameFrame.getMyBoardPanel().updateBoard(gameFrame.getMyBoard());
        if (gameState.getTurn() == 0) {
            gameFrame.getButtonPanel().setRightButtonText("Alternate Board ("+gameState.getAlternationLeft(authToken)+")");
            if (gameState.getAlternationLeft(authToken) == 0 || gameState.isReady(authToken)) {
                gameFrame.getButtonPanel().disableButtons();
                gameFrame.getButtonPanel().setLeftButtonText("Waiting for the opponent...");
            }
            gameFrame.getButtonPanel().setTime(gameState.getBuckleUpTime(authToken));
            if (gameState.getUserInt(authToken)==1){
                gameFrame.getNamePanel().setLeftLabelColor(new Color(32, 141, 7));
                gameFrame.getNamePanel().setRightLabel(Color.red);
            } else {
                gameFrame.getNamePanel().setLeftLabelColor(Color.red);
                gameFrame.getNamePanel().setRightLabel(new Color(32, 141, 7));
            }
        }
        if (gameState.getTurn() == 1 || gameState.getTurn() == 2) {
            gameFrame.getButtonPanel().disableMidLabel();
        }
        if (gameState.getTurn() >= 1) {
            gameFrame.getButtonPanel().setLeftButtonText(String.valueOf(gameState.getTurnTime(authToken)));
            gameFrame.getButtonPanel().setRightButtonText(String.valueOf(gameState.getEnemyTurnTime(authToken)));
            gameFrame.getButtonPanel().enableButtons();
            if (gameState.getUserInt(authToken) % 2 == gameState.getTurn() % 2) {
                gameFrame.getNamePanel().setLeftLabelColor(new Color(32, 141, 7));
                gameFrame.getNamePanel().setRightLabel(Color.red);
            } else {
                gameFrame.getNamePanel().setLeftLabelColor(Color.red);
                gameFrame.getNamePanel().setRightLabel(new Color(32, 141, 7));
            }
        }
        if (gameState.getTurn() == -1) {
            gameFrame.getTimer().stop();
            gameFrame.dispose();
            new MenuFrame(authToken);
        }
    }

    public void listenBomb(int x, int y) {
        controller.bomb(x, y);
    }

    public void listenReadyButton() {
        controller.setReady();
    }

    public void listenAlternateButton() {
        controller.alternate();
    }

    public void listenUpdate() {
        controller.updateRequest();
    }
}
