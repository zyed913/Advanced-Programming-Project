public class Grid {
    private Cell[][] cells;
    private final int SIZE = 10;

    public Grid() {
        cells = new Cell[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getSize() {
        return SIZE;
    }

    public boolean isInsideGrid(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public void setObstacle(int row, int col) {
        if (isInsideGrid(row, col)) {
            cells[row][col].setType("OBSTACLE");
        }
    }

    public void setSurvivor(int row, int col) {
        if (isInsideGrid(row, col)) {
            cells[row][col].setType("SURVIVOR");
        }
    }

    public void clearCell(int row, int col) {
        if (isInsideGrid(row, col)) {
            cells[row][col].setType("EMPTY");
        }
    }

    public boolean isObstacle(int row, int col) {
        return isInsideGrid(row, col) && cells[row][col].isObstacle();
    }
}