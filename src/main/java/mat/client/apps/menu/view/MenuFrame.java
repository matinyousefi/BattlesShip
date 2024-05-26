package mat.client.apps.menu.view;

import mat.client.apps.menu.model.MenuFrameListener;
import mat.client.controller.Quiter;
import mat.model.authentification.AuthToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuFrame extends JFrame {
    private final MenuFrameListener menuFrameListener;

    private final MatButton newGameButton;
    private final MatButton statsButton;
    private final MatButton watchGameButton;
    private final MatButton scoreboardButton;

    public MenuFrame(AuthToken authToken) {
        this.setSize(600,800);
        this.setTitle("BattleShip");
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.menuFrameListener = new MenuFrameListener(authToken, this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Quiter quiter = new Quiter(authToken);
                quiter.quit();
            }
        });

        int x = 190;

        statsButton = new MatButton();
        this.add(statsButton);
        statsButton.addActionListener(menuFrameListener.getStatsListener());
        statsButton.setText("My Status");
        statsButton.setBounds(x, 150, 200, 100);

        newGameButton = new MatButton();
        this.add(newGameButton);
        newGameButton.addActionListener(menuFrameListener.getNewGameListener());
        newGameButton.setText("New Game");
        newGameButton.setBounds(x, 260, 200, 100);

        watchGameButton = new MatButton();
        this.add(watchGameButton);
        watchGameButton.addActionListener(menuFrameListener.getWatchGameListener());
        watchGameButton.setText("Watch Game");
        watchGameButton.setBounds(x, 370, 200, 100);

        scoreboardButton = new MatButton();
        this.add(scoreboardButton);
        scoreboardButton.addActionListener(menuFrameListener.getScoreBoardListener());
        scoreboardButton.setText("Scoreboard");
        scoreboardButton.setBounds(x, 480, 200, 100);
    }

    public void setNewGameToWaiting() {
        newGameButton.setText("Waiting...");
        watchGameButton.setEnabled(false);
        statsButton.setEnabled(false);
        newGameButton.setEnabled(false);
        scoreboardButton.setEnabled(false);
    }
}
