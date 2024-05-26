package mat.client.apps.gamePlay.view;

import mat.client.apps.gamePlay.view.boardPanel.view.BoardPanel;
import mat.client.apps.menu.view.MatButton;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {


    private static final int panelSize = BoardPanel.boardGraphicalDimension;

    private final MatButton leftButton;
    private final MatButton rightButton;
    private final JLabel timeLabel;

    public ButtonPanel(){
        setLayout(null);
        setPreferredSize(new Dimension(panelSize*2+50, 90));
        setSize(new Dimension(panelSize*2+50, panelSize*2+50));

        leftButton = new MatButton();
        this.leftButton.setText("Ready");
        this.leftButton.setBounds(20, 0, panelSize - 40, 70);
        add(this.leftButton);

        timeLabel = new JLabel();
        timeLabel.setText("0");
        timeLabel.setForeground(Color.red);
        timeLabel.setBounds(panelSize - 10, 0, 70, 70);
        timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timeLabel);

        rightButton = new MatButton();
        this.rightButton.setText("Alternate Board (3)");
        this.rightButton.setBounds(panelSize + 50 + 20, 0, panelSize - 40, 70);
        add(this.rightButton);
    }

    public void setListeners(ButtonPanelListener buttonPanelListener) {
        leftButton.addActionListener(e -> buttonPanelListener.leftButtonPressed());
        rightButton.addActionListener(e -> buttonPanelListener.rightButtonPressed());
    }

    public void setRightButtonText(String s){
        rightButton.setText(s);
    }

    public void setLeftButtonText(String s){
        leftButton.setText(s);
    }

    public void disableButtons() {
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
    }

    public void enableButtons() {
        leftButton.setEnabled(true);
        rightButton.setEnabled(true);
    }

    public void setTime(int i){
        timeLabel.setText(String.valueOf(i));
    }

    public void disableMidLabel() {
        timeLabel.setVisible(false);
    }
}
