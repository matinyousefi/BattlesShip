package mat.server.threads;

import mat.client.controller.ControllerException;
import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import mat.model.networkEvents.authentification.RegisterEvent;

import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;

public class RegisterThread extends Thread {

    private final Socket socket;
    private final RegisterEvent registerEvent;

    public RegisterThread(Socket socket, RegisterEvent registerEvent) {
        this.socket = socket;
        this.registerEvent = registerEvent;
    }

    @Override
    public void run() {
        try{
            UserDB userDB = new UserDB();
            if (userDB.exists(registerEvent.getUsername())) {
                throw new ControllerException("Username is taken.");
            }
            User user = new User(registerEvent.getUsername(), registerEvent.getPassword());
            AuthToken authToken = userDB.makeAuthToken();
            userDB.addAuthToken(user, authToken);
            user.setAuthToken(authToken);
            userDB.add(user);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(authToken);
        } catch (ControllerException controllerException) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(controllerException);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
