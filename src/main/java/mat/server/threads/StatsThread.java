package mat.server.threads;

import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import mat.model.networkEvents.stats.StatsRequest;
import mat.model.networkEvents.stats.StatsResponse;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StatsThread extends MyThread {

    private final Socket socket;
    private final StatsRequest statsRequest;

    public StatsThread(Socket socket, StatsRequest statsRequest) {
        this.socket = socket;
        this.statsRequest = statsRequest;
    }

    @Override
    public void run() {
        try {
            AuthToken authToken = statsRequest.getAuthToken();
            UserDB userDB = new UserDB();
            User user = userDB.get(authToken);
            assert user != null;
            StatsResponse statsResponse = new StatsResponse(user.getUsername(), user.getWinCount(), user.getLostCount(), user.getScore());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(statsResponse);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
