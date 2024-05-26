package mat.client.apps.stats.view;

import mat.client.apps.menu.view.MatButton;
import mat.client.apps.menu.view.MenuFrame;
import mat.client.controller.Quiter;
import mat.model.authentification.AuthToken;
import mat.model.networkEvents.stats.StatsResponse;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StatsFrame extends JFrame {

    public StatsFrame(@NotNull AuthToken authToken, @NotNull StatsResponse statsResponse) {
        this.setTitle("BattleShip");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Quiter quiter = new Quiter(authToken);
                quiter.quit();
            }
        });
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        this.setLocationRelativeTo(null);
        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(600, 60));
        this.add(backPanel, BorderLayout.NORTH);
        backPanel.setVisible(true);
        MatButton backButton = new MatButton();
        backButton.setText("Back");
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.setVisible(true);
        backButton.addActionListener(e -> {
            this.dispose();
            new MenuFrame(authToken);
        });
        backPanel.setBackground(Color.white);
        backPanel.add(backButton);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setBackground(Color.white);

        panel.add(new JLabel(){
            {
                setFont(new Font("Times New Roman", Font.PLAIN, 30));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<html>");
                stringBuilder.append("Username:  ").append(statsResponse.getUsername()).append("<br/>");
                stringBuilder.append("Lost:  ").append(statsResponse.getLostCount()).append("<br/>");
                stringBuilder.append("Won:   ").append(statsResponse.getWinCount()).append("<br/>");
                stringBuilder.append("Score: ").append(statsResponse.getScore()).append("<br/>");
                stringBuilder.append("<html>");
                setText(stringBuilder.toString());
                setForeground(Color.black);
            }
        });
        this.add(panel, BorderLayout.SOUTH);
        this.add(backPanel, BorderLayout.NORTH);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
