package mat.client.apps.gamePlay.view.boardPanel.view;

import mat.client.apps.gamePlay.view.boardPanel.model.BoardListener;
import mat.client.apps.gamePlay.view.boardPanel.model.BoardMouseListener;
import mat.client.config.ConfigAgent;
import mat.model.game.Board;
import mat.model.game.Cell;
import mat.model.game.Ship;

import javax.swing.*;
import java.awt.*;


public class BoardPanel extends JPanel {

    private final static int dim = new ConfigAgent().getGameConfig().getInt("dimension");
    private final static int cellGraphicalDimension = new ConfigAgent().getGameConfig().getInt("cellGraphicalDimension");
    public final static int boardGraphicalDimension = dim * cellGraphicalDimension;

    private BoardListener listener;
    private Board board;
    private boolean shipDraw;

    public BoardPanel(Board board) {
        this.board = board;
        setLayout(null);
        setPreferredSize(new Dimension(boardGraphicalDimension, boardGraphicalDimension));
        setSize(new Dimension(boardGraphicalDimension, boardGraphicalDimension));
        addMouseListener(new BoardMouseListener(this));
    }

    public void setShipDraw(boolean shipDraw) {
        this.shipDraw = shipDraw;
    }

    public Board getBoard() {
        return board;
    }

    public BoardListener getListener() {
        return listener;
    }

    public void setListener(BoardListener listener) {
        this.listener = listener;
    }

    @Override
    public void paint(Graphics graphics2D) {
        paintBackground(graphics2D);
        paintBombs(graphics2D);
        if(shipDraw) {
            paintShips(graphics2D);
        }
    }

    private void paintBombs(Graphics graphics2D) {
        for (Cell cell :
                board.getBombs()) {
            Color color = new Color(80, 80, 80);
            for (Ship ship :
                    board.getShips()) {
                if (ship.hit(cell)) {
                    color = new Color(193, 18, 18);
                }
            }
            graphics2D.setColor(color);
            int i = cell.getX() - 1;
            int j = cell.getY() - 1;
            graphics2D.fillRect(i * cellGraphicalDimension, j * cellGraphicalDimension, cellGraphicalDimension, cellGraphicalDimension);
        }
    }

    private void paintShips(Graphics graphics2D) {

        graphics2D.setColor(Color.black);

        for (Ship ship :
                board.getShips()) {

            Cell cell1 = ship.getCell1();
            int x1 = (cell1.getX()-1) * cellGraphicalDimension + cellGraphicalDimension / 3;
            int y1 = (cell1.getY()-1) * cellGraphicalDimension + cellGraphicalDimension / 3;

            Cell cell2 = ship.getCell2();
            int x2 = (cell2.getX()-1) * cellGraphicalDimension + 2 * cellGraphicalDimension / 3;
            int y2 = (cell2.getY()-1) * cellGraphicalDimension + 2 * cellGraphicalDimension / 3;

            graphics2D.fillRect(x1, y1, x2 - x1, y2 - y1);

        }

    }

    private void paintBackground(Graphics graphics2D){
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                Color color;
                if ((i + j) % 2 == 0) {
                    color = Color.white;
                } else {
                    color = new Color(29, 193, 0, 255);
                }
                graphics2D.setColor(color);
                graphics2D.fillRect(i * cellGraphicalDimension, j * cellGraphicalDimension, cellGraphicalDimension, cellGraphicalDimension);
            }
        }
    }

    public void updateBoard(Board board) {
        this.board = board;
        repaint();
    }
}
