package mat.test;

import mat.client.apps.gamePlay.view.boardPanel.view.BoardPanel;
import mat.model.game.Board;

import javax.swing.*;

public class BoardPanelTest {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        BoardPanel boardPanel = new BoardPanel(Board.generate());
        jFrame.setSize(boardPanel.getSize());
        jFrame.add(boardPanel);
        jFrame.setVisible(true);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
    }
}
