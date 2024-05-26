package mat.client.apps.menu.view;

import javax.swing.*;
import java.awt.*;

public class MatButton extends JButton {
    public MatButton(){
        this.setFocusable(false);
        this.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        this.setBackground(new Color(218, 206, 246));
        this.setBorder(BorderFactory.createLineBorder(new Color(10, 36, 68), 3, true));
        this.setPreferredSize(new Dimension(400, 100));
    }
}