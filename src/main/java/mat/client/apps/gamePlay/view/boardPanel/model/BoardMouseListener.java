package mat.client.apps.gamePlay.view.boardPanel.model;

import mat.client.apps.gamePlay.view.boardPanel.view.BoardPanel;
import mat.client.config.ConfigAgent;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class BoardMouseListener implements MouseInputListener {

    private final static int cellGraphicalDimension = new ConfigAgent().getGameConfig().getInt("cellGraphicalDimension");


    private final BoardPanel boardPanel;

    public BoardMouseListener(BoardPanel boardPanel){
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            boardPanel.getListener().listen(
                    (Math.floorDiv(e.getX(), cellGraphicalDimension) + 1),
                    (Math.floorDiv(e.getY(), cellGraphicalDimension) + 1)
            );
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
