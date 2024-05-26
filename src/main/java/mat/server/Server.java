package mat.server;

import mat.model.networkEvents.AuthenticatedNetworkEvent;
import mat.model.networkEvents.QuitRequest;
import mat.model.networkEvents.authentification.EnterEvent;
import mat.model.networkEvents.GameRequest;
import mat.model.networkEvents.authentification.RegisterEvent;
import mat.model.networkEvents.scoreBoard.SBRequest;
import mat.model.networkEvents.stats.StatsRequest;
import mat.server.config.ServerSocketConfig;
import mat.server.threads.*;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {

    private final GamePoolThread gamePoolThread = new GamePoolThread(){
        {
            start();
        }
    };


    @Override
    public void run() {
        try {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(Integer.parseInt(
                        (String) (new ServerSocketConfig()).get("ip")
                ));
            } catch (Exception exception) {
                exception.printStackTrace();
                serverSocket = new ServerSocket(8000);
            }
            while(true){
                Socket socket = serverSocket.accept();
                socket.getInputStream().mark(1);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object object = objectInputStream.readObject();
                if (object instanceof RegisterEvent registerEvent) {
                    RegisterThread registerThread = new RegisterThread(socket, registerEvent);
                    registerThread.start();
                } else if(object instanceof EnterEvent enterEvent){
                    EnterThread eventThread = new EnterThread(socket, enterEvent);
                    eventThread.start();
                }
                AuthenticatedNetworkEvent authenticatedNetworkEvent = (AuthenticatedNetworkEvent) object;
                if (authenticatedNetworkEvent.getAuthToken() == null) {
                    continue;
                }
                if(object instanceof StatsRequest statsRequest){
                    StatsThread statsThread = new StatsThread(socket, statsRequest);
                    statsThread.start();
                } else if (object instanceof GameRequest gameRequest) {
                    gamePoolThread.request(socket, gameRequest);
                } else if (object instanceof QuitRequest quitEvent) {
                    new QuitThread(quitEvent).start();
                } else if (object instanceof SBRequest) {
                    new SBThread(socket).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
