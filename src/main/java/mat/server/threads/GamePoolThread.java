package mat.server.threads;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.GameRequest;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class GamePoolThread extends MyThread{

    private final ArrayList<AuthToken> gameRequests = new ArrayList<>();
    private final ArrayList<Socket> sockets = new ArrayList<>();
    private final HashSet<GameThread> gameThreads = new HashSet<>();

    public ArrayList<AuthToken> getGameRequests() {
        return gameRequests;
    }

    public ArrayList<Socket> getSockets() {
        return sockets;
    }

    @Override
    public void run() {
        while(true){
            try {
                synchronized (gameRequests){
                    gameRequests.wait();
                    while(gameRequests.size() > 1) {

                        AuthToken authToken2 = gameRequests.remove(0);
                        Socket socket2 = sockets.remove(0);

                        AuthToken authToken1 = gameRequests.remove(0);
                        Socket socket1 = sockets.remove(0);

                        GameThread gameThread = new GameThread(authToken1, socket1, authToken2, socket2);
                        gameThread.start();

                        synchronized (gameThreads) {
                            gameThreads.add(gameThread);
                        }

                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void request(Socket socket, GameRequest gameRequest) {
        synchronized (gameRequests) {
            gameRequests.add(gameRequest.getAuthToken());
            sockets.add(socket);
            gameRequests.notifyAll();
        }
    }
}
