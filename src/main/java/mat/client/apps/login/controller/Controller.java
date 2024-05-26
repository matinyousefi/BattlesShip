package mat.client.apps.login.controller;

import mat.client.apps.menu.view.MenuFrame;
import mat.client.controller.ControllerException;
import mat.client.controller.SocketBuilder;
import mat.model.authentification.AuthToken;
import mat.model.networkEvents.authentification.EnterEvent;
import mat.model.networkEvents.authentification.RegisterEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {
    public void register(String username, char[] password) throws ControllerException {
        try {
            RegisterEvent registerEvent = new RegisterEvent(username, password, null);
            Socket socket = new SocketBuilder().getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(registerEvent);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            if(object instanceof ControllerException){
                throw (ControllerException) object;
            } else if(object instanceof AuthToken){
                new MenuFrame((AuthToken) object);
            }
        } catch (IOException | ClassNotFoundException ioException){
            ioException.printStackTrace();
        }
    }

    public void enter(String username, char[] password) throws ControllerException {
        try {
            EnterEvent registerEvent = new EnterEvent(username, password, null);
            Socket socket = new SocketBuilder().getSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(registerEvent);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            if(object instanceof ControllerException){
                throw (ControllerException) object;
            } else if (object instanceof AuthToken) {
                new MenuFrame((AuthToken) object);
            }
        } catch (IOException | ClassNotFoundException ioException){
            ioException.printStackTrace();
        }
    }


}
