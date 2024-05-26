package mat.client.apps.login.model;

import mat.client.controller.ControllerException;
import mat.client.apps.login.view.LoginFrame;

import java.awt.event.ActionListener;

public class ButtonPanelListener {

    private final LoginFrame loginFrame;

    private final ActionListener registerButtonActionListener;
    private final ActionListener enterButtonActionListener;


    public ButtonPanelListener(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.registerButtonActionListener = e -> registerButtonActionListener();
        this.enterButtonActionListener = e -> enterButtonActionListener();
    }

    private void enterButtonActionListener(){
        UserPassEvent userPassEvent = new UserPassEvent(
                false, loginFrame.getInputPanel().getUsername(), loginFrame.getInputPanel().getPassword()
        );
        try {
            loginFrame.getListener().listen(userPassEvent);
            dispose();
        } catch (ControllerException e){
            loginFrame.getOutputPanel().error(e.getMessage());
        }
    }

    private void registerButtonActionListener(){
        UserPassEvent userPassEvent = new UserPassEvent(
                true, loginFrame.getInputPanel().getUsername(), loginFrame.getInputPanel().getPassword()
        );
        try {
            loginFrame.getListener().listen(userPassEvent);
            dispose();
        } catch (ControllerException e){
            loginFrame.getOutputPanel().error(e.getMessage());
        }
    }

    private void dispose() {
        loginFrame.dispose();
    }

    public ActionListener getEnterButtonActionListener() {
        return enterButtonActionListener;
    }

    public ActionListener getRegisterButtonActionListener() {
        return registerButtonActionListener;
    }


    private static boolean isNumber(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}
