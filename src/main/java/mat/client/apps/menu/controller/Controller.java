package mat.client.apps.menu.controller;

import mat.client.apps.gamePlay.view.GameFrame;
import mat.client.apps.menu.view.MenuFrame;
import mat.client.apps.scoreBoard.view.ScoreBoardPage;
import mat.client.apps.stats.view.StatsFrame;
import mat.client.controller.SocketBuilder;
import mat.model.authentification.AuthToken;
import mat.model.game.GameState;
import mat.model.networkEvents.GameRequest;
import mat.model.networkEvents.stats.StatsRequest;
import mat.model.networkEvents.stats.StatsResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {
    private final AuthToken authToken;
    private final MenuFrame menuFrame;

    public Controller(AuthToken authToken, MenuFrame menuFrame) {
        this.authToken = authToken;
        this.menuFrame = menuFrame;
    }

    public void stats() {
        try {
            menuFrame.dispose();
            StatsRequest statsRequest = new StatsRequest(authToken);
            Socket socket = new SocketBuilder().getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(statsRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            StatsResponse statsResponse = (StatsResponse) object;
            objectInputStream.close();
            new StatsFrame(authToken, statsResponse);
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    public void newGame() {
        try {
            menuFrame.setNewGameToWaiting();
            GameRequest gameRequest = new GameRequest(authToken);
            Socket socket = new SocketBuilder().getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameRequest);
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        GameState gameState = (GameState) objectInputStream.readObject();
                        new GameFrame(authToken, gameState, socket);
                        menuFrame.dispose();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            };
            thread.start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void watchGame() {
    }

    public void scoreBoard() {
        menuFrame.dispose();
        new ScoreBoardPage(authToken);
    }

}
