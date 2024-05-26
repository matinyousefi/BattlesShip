package mat.client.controller;

import mat.client.config.ConfigAgent;
import mat.client.config.SocketConfig;

import java.io.IOException;
import java.net.Socket;

public class SocketBuilder {

    private Socket socket = null;

    public SocketBuilder(){
        try {
            SocketConfig socketConfig = new ConfigAgent().getSocketConfig();
            this.socket = new Socket(socketConfig.get("host"), Integer.parseInt(socketConfig.get("port")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
