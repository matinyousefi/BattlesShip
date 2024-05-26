package mat.client.apps.login.model;

import mat.client.apps.login.controller.Controller;
import mat.client.apps.login.view.LoginFrame;
import mat.client.controller.ControllerException;
import mat.client.listeners.Listener;

public class LoginFrameListener implements Listener<UserPassEvent> {

    private final Controller controller = new Controller();

    @Override
    public void listen(UserPassEvent userPassEvent) throws ControllerException {
        if(userPassEvent.isRegister()){
            controller.register(userPassEvent.getUsername(), userPassEvent.getPassword());
        } else {
            controller.enter(userPassEvent.getUsername(), userPassEvent.getPassword());
        }
    }
}
