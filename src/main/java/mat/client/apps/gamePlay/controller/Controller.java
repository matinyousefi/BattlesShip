package mat.client.apps.gamePlay.controller;

import mat.model.authentification.AuthToken;
import mat.model.game.Cell;
import mat.model.game.GameState;
import mat.model.networkEvents.gamePlay.AlternateBoard;
import mat.model.networkEvents.gamePlay.BombRequest;
import mat.model.networkEvents.gamePlay.Ready;
import mat.model.networkEvents.gamePlay.UpdateRequest;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {

    private final Socket socket;
    private final AuthToken authToken;
    private final ControllerListener listener;
//    private final Thread updateThread;

    public Controller(Socket socket, AuthToken authToken, ControllerListener listener) {
        this.socket = socket;
        this.authToken = authToken;
        this.listener = listener;
//        this.updateThread = new Thread(){
//            @Override
//            public void run() {
//                try{
//                    Object object;
//                    do {
//                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//                        object = objectInputStream.readObject();
//                        listener.listen((GameState) object);
//                    } while (((GameState) object).getTurn() != -1);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        };
//        updateThread.start();
    }



    public void bomb(int x, int y) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new BombRequest(authToken, new Cell(x, y)));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            listener.listen((GameState) object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setReady() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new Ready(authToken));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            listener.listen((GameState) object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void alternate() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new AlternateBoard(authToken));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            listener.listen((GameState) object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateRequest() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new UpdateRequest(authToken));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            listener.listen((GameState) object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
