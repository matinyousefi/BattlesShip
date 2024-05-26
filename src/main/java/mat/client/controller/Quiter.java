package mat.client.controller;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.QuitRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Quiter {

    private final AuthToken authToken;

    public Quiter(AuthToken authToken) {
        this.authToken = authToken;
        try {
            Socket socket = new SocketBuilder().getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new QuitRequest(authToken));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void quit() {
        System.exit(1);
    }
}
