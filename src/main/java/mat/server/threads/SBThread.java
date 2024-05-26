package mat.server.threads;

import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import mat.model.networkEvents.scoreBoard.SBRequest;
import mat.model.networkEvents.scoreBoard.SBResponse;
import mat.model.networkEvents.stats.StatsResponse;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.TreeMap;

public class SBThread extends Thread {
    private final Socket socket;
    public SBThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            UserDB userDB = new UserDB();
            HashMap<String, Integer> hashMap = new HashMap<>();
            for (User user :
                    userDB.getAll()) {
                hashMap.put( user.getUsername(), user.getScore());
            }
            SBResponse statsResponse = new SBResponse(hashMap);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(statsResponse);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
