package mat.server.threads;

import mat.client.controller.ControllerException;
import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import mat.model.networkEvents.authentification.EnterEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class EnterThread extends MyThread {
    private final Socket socket;
    private final EnterEvent enterEvent;

    public EnterThread(Socket socket, EnterEvent enterEvent) {
        this.socket = socket;
        this.enterEvent = enterEvent;
    }


    @Override
    public void run() {
        try {
            UserDB userDB = new UserDB();
            if (!userDB.exists(enterEvent.getUsername())) {
                throw new ControllerException("Username not found.");
            }
            User user = userDB.get(enterEvent.getUsername());
            assert user != null;
            if (!Arrays.equals(user.getPassword(), enterEvent.getPassword())) {
                throw new ControllerException("Wrong Password.");
            }
            if (userDB.isOnline(user)) {
                throw new ControllerException("Already online.");
            }
            AuthToken authToken = userDB.makeAuthToken();
            userDB.addAuthToken(user, authToken);
            user.setAuthToken(authToken);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(user.getAuthToken());
        } catch (ControllerException controllerException) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(controllerException);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
