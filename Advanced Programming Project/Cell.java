public class Cell {
    private int row;
    private int col;
    private String type;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = "EMPTY";
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isObstacle() {
        return type.equals("OBSTACLE");
    }

    public boolean isSurvivor() {
        return type.equals("SURVIVOR");
    }

    public boolean isEmpty() {
        return type.equals("EMPTY");
    }
}