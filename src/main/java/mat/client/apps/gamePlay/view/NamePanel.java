package mat.client.apps.gamePlay.view;

import mat.client.apps.gamePlay.view.boardPanel.view.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class NamePanel extends JPanel {

    private static final int panelSize = BoardPanel.boardGraphicalDimension;

    JLabel leftLabel = new JLabel();
    JLabel rightLabel = new JLabel();

    public NamePanel(String left, String right){
        setLayout(null);
        setPreferredSize(new Dimension(panelSize*2+50, 50));
        setSize(getPreferredSize());


        add(leftLabel);
        leftLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        leftLabel.setBounds(20, 0, panelSize - 40, 50);
        leftLabel.setText(left);
        leftLabel.setVerticalAlignment(SwingConstants.CENTER);
        leftLabel.setHorizontalAlignment(SwingConstants.CENTER);


        rightLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        rightLabel.setBounds(panelSize + 50 + 20, 0, panelSize - 40, 50);
        rightLabel.setText(right);
        rightLabel.setVerticalAlignment(SwingConstants.CENTER);
        rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(rightLabel);
    }

    public void setLeftLabelColor(Color color){
        leftLabel.setForeground(color);
    }

    public void setRightLabel(Color color){
        rightLabel.setForeground(color);
    }
}
