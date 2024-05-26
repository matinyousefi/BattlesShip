package mat.client.apps.login.view;

import javax.swing.*;

public class OutputPanel extends JPanel {
    private JLabel errorLabel;


    OutputPanel(){
        setLayout(null);
        setSize(220, 40);
        errorLabel = new JLabel();
        errorLabel.setText("Error");
        errorLabel.setVisible(false);
        errorLabel.setBounds(10,10, 210, 20);
        add(errorLabel);
    }


    public void error(String message) {
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }
}
