package mat.test;

import mat.model.game.Board;
import mat.model.game.Ship;

public class BoardGenerateTest {
    public static void main(String[] args) {
        Board board = Board.generate();
        for (Ship ship:
                board.getShips()) {
            System.out.println( "("+ship.getCell1().getX()+","+ship.getCell1().getY()+")" +
                    "    "+
                    "("+ship.getCell2().getX()+","+ship.getCell2().getY()+")" );
        }
    }
}
