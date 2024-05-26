package mat.server.threads;

import mat.model.authentification.User;
import mat.model.networkEvents.QuitRequest;


public class QuitThread extends Thread {

    private final QuitRequest quitEvent;

    public QuitThread(QuitRequest quitEvent) {
        this.quitEvent = quitEvent;
    }

    @Override
    public void run() {
        User user = new UserDB().get(quitEvent.getAuthToken());
        assert user != null;
        user.setAuthToken(null);
        new UserDB().update(user);
    }
}
