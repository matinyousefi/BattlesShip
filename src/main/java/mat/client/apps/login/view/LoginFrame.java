package mat.client.apps.login.view;

import mat.client.apps.login.model.ButtonPanelListener;
import mat.client.apps.login.model.LoginFrameListener;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final LoginFrameListener listener = new LoginFrameListener();

    private InputPanel inputPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;

    public LoginFrame(){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(230, 190);
        setBackground(Color.BLACK);
        setVisible(true);
        setResizable(false);
        buttonPanel = new ButtonPanel();
        buttonPanel.setListener(new ButtonPanelListener(this));
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();
        add(outputPanel);
        add(inputPanel);
        add(buttonPanel);
        outputPanel.setLocation(0,0);
        inputPanel.setLocation(0, outputPanel.getHeight());
        buttonPanel.setLocation(0,outputPanel.getHeight() + inputPanel.getHeight());
    }

    public InputPanel getInputPanel() {
        return inputPanel;
    }

    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    public LoginFrameListener getListener() {
        return listener;
    }
}
