//package mat.client.apps.watchGame.view;
//
//import mat.client.apps.menu.view.MatButton;
//import mat.client.apps.menu.view.MenuFrame;
//import mat.client.apps.scoreBoard.model.ScoreBoardPageListener;
//import mat.client.apps.watchGame.model.WatchGameListener;
//import mat.client.controller.Quiter;
//import mat.model.authentification.AuthToken;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//public class WatchGamePage extends JFrame {
//
//    WatchGameListener listener;
//    Timer timer;
//
//    public WatchGamePage(AuthToken authToken){
//
//        listener = new WatchGameListener(authToken);
//        timer = new Timer(100, e -> {
//            update();
//        }){
//            {start();}
//        };
//
//        this.setTitle("BattleShip");
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                dispose();
//                Quiter quiter = new Quiter(authToken);
//                quiter.quit();
//            }
//        });
//        this.setResizable(false);
//        this.setVisible(true);
//        this.setLayout(new BorderLayout());
//        this.setBackground(Color.white);
//        this.setLocationRelativeTo(null);
//        JPanel backPanel = new JPanel();
//        backPanel.setPreferredSize(new Dimension(600, 60));
//        this.add(backPanel, BorderLayout.NORTH);
//        backPanel.setVisible(true);
//        MatButton backButton = new MatButton();
//        backButton.setText("Back");
//        backButton.setPreferredSize(new Dimension(100,50));
//        backButton.setVisible(true);
//        backButton.addActionListener(e -> {
//            this.dispose();
//            new MenuFrame(authToken);
//        });
//        backPanel.setBackground(Color.white);
//        backPanel.add(backButton);
//        JPanel panel = new JPanel();
//        panel.setPreferredSize(new Dimension(600, 600));
//        panel.setBackground(Color.white);
//        panel.add(label);
//        this.add(panel, BorderLayout.SOUTH);
//        this.add(backPanel, BorderLayout.NORTH);
//        this.pack();
//        this.setLocationRelativeTo(null);
//    }
//
//    private void update() {
//    }
//}
