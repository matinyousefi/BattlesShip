package mat.client.apps.login.view;

import mat.client.apps.login.model.ButtonPanelListener;

import javax.swing.*;


public class ButtonPanel extends JPanel {

    private ButtonPanelListener listener;

    private JButton enterButton;
    private JButton registerButton;

    ButtonPanel() {
        setSize(220, 40);
        setLayout(null);

        enterButton = new JButton();
        enterButton.setText("Enter");
        enterButton.setBounds(10,10, 95, 20);

        registerButton = new JButton();
        registerButton.setText("Register");
        registerButton.setBounds(110,10, 95, 20);

        this.add(enterButton);
        this.add(registerButton);
    }

    public void setListener(ButtonPanelListener listener) {
        this.listener = listener;
        enterButton.addActionListener(listener.getEnterButtonActionListener());
        registerButton.addActionListener(listener.getRegisterButtonActionListener());
    }

}
