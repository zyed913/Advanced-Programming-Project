import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PathFinder {

    public ArrayList<int[]> bfs(int startRow, int startCol, int targetRow, int targetCol, Grid grid) {
        boolean[][] visited = new boolean[10][10];
        int[][] parentRow = new int[10][10];
        int[][] parentCol = new int[10][10];

        for (int row = 0; row < 10; row++) {
            Arrays.fill(parentRow[row], -1);
            Arrays.fill(parentCol[row], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        while (!queue.isEmpty()) {
            int[] current = queue.remove();
            int row = current[0];
            int col = current[1];

            if (row == targetRow && col == targetCol) {
                return buildPath(parentRow, parentCol, targetRow, targetCol);
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (grid.isInsideGrid(newRow, newCol)
                        && !visited[newRow][newCol]
                        && !grid.isObstacle(newRow, newCol)) {

                    visited[newRow][newCol] = true;
                    parentRow[newRow][newCol] = row;
                    parentCol[newRow][newCol] = col;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        return new ArrayList<>();
    }

    private ArrayList<int[]> buildPath(int[][] parentRow, int[][] parentCol, int row, int col) {
        ArrayList<int[]> path = new ArrayList<>();

        while (row != -1 && col != -1) {
            path.add(0, new int[]{row, col});

            int previousRow = parentRow[row][col];
            int previousCol = parentCol[row][col];

            row = previousRow;
            col = previousCol;
        }

        return path;
    }
}