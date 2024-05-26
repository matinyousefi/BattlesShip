package mat.client.apps.login.view;

import mat.client.config.ConfigAgent;
import mat.client.config.LoginFrameConfig;

import javax.swing.*;

public class InputPanel extends JPanel {

    private JTextField usernameField;
    private JLabel usernameLabel;

    private JPasswordField passwordField;
    private JLabel passwordLabel;

    InputPanel(){
        final LoginFrameConfig config = new ConfigAgent().getLoginFrameConfig();

        setLayout(null);
        setSize(220, 70);

        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameLabel.setBounds(10,10, 100, 20);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(110,10, 100, 20);
        add(usernameField);

        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordLabel.setBounds(10, 40, 100, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 40, 100, 20);
        add(passwordField);
    }

    public String getUsername(){
        return usernameField.getText();
    }

    public char[] getPassword(){
        return passwordField.getPassword();
    }
}
