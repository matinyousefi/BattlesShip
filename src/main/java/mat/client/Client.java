package mat.client;

import mat.client.apps.login.view.LoginFrame;

import java.net.Socket;

public class Client extends Thread {
    @Override
    public void run() {
        try {
            new LoginFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
