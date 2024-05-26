package mat.client.apps.scoreBoard.controller;

import mat.client.controller.SocketBuilder;
import mat.model.authentification.AuthToken;
import mat.model.networkEvents.scoreBoard.SBRequest;
import mat.model.networkEvents.scoreBoard.SBResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.TreeMap;

public class Controller {


    private final AuthToken authToken;

    public Controller(AuthToken authToken) {
        this.authToken = authToken;
    }

    public HashMap<String, Integer> getScores() {
        try {
            Socket socket = new SocketBuilder().getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new SBRequest(authToken));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            socket.close();
            return ((SBResponse) object).getScores();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
