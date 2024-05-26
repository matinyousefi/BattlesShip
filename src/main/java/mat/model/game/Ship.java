package mat.model.game;

import java.io.Serializable;
import java.util.HashSet;

public class Ship implements Serializable {

    private final Cell cell1;
    private final Cell cell2;

    private boolean destroyed = false;

    public Ship(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    public boolean hit(Cell cell){
        if(cell.getX() == cell1.getX() &&
           cell.getX() == cell2.getX()){
            return (cell.getY() - cell1.getY()) * (cell.getY() - cell2.getY()) <= 0;
        } else if (cell.getY() == cell1.getY() &&
                   cell.getY() == cell2.getY()) {
            return (cell.getX() - cell1.getX()) * (cell.getX() - cell2.getX()) <= 0;
        }
        else return false;
    }

    public HashSet<Cell> getCells(){
        HashSet<Cell> cells = new HashSet<>();
        if(cell1.getX() == cell2.getX()){
            for (int i = Math.min(cell1.getY(), cell2.getY()); i <= Math.max(cell1.getY(), cell2.getY()) ; i++) {
                Cell cell = new Cell(cell1.getX(), i);
                cells.add(cell);
            }
        } else if(cell1.getY() == cell2.getY()){
            for (int i = Math.min(cell1.getX(), cell2.getX()); i <= Math.max(cell1.getX(), cell2.getX()) ; i++) {
                Cell cell = new Cell(i, cell1.getY());
                cells.add(cell);
            }
        }
        return cells;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
