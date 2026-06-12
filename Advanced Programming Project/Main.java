import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;

public class Main extends JFrame {

    private int robotRow = 1, robotCol = 1;
    private int moves = 0, score = 0, rescued = 0;

    private boolean survivor1Alive = true;
    private boolean survivor2Alive = true;
    private boolean survivor3Alive = true;
    private boolean survivor4Alive = true;
    private boolean survivor5Alive = true;

    private JPanel gridPanel;
    private JLabel movesLabel, scoreLabel, rescuedLabel;

    private ArrayList<int[]> currentPath = new ArrayList<>();

    private int[] leaderboard = {450, 320, 600, 280, 500};

    public Main() {
        setTitle("Rescue Robot Pathfinder");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(16, 24, 32));

        JLabel title = new JLabel("Mission: Rescue all survivors!", SwingConstants.CENTER);
        title.setForeground(Color.GREEN);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(10, 10));
        drawGrid();

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(22, 34, 46));
        leftPanel.setPreferredSize(new Dimension(220, 600));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel infoTitle = new JLabel("Game Info");
        infoTitle.setForeground(Color.CYAN);
        infoTitle.setFont(new Font("Arial", Font.BOLD, 20));

        rescuedLabel = new JLabel("Rescued: 0 / 5");
        scoreLabel = new JLabel("Score: 0");
        movesLabel = new JLabel("Moves: 0");

        leftPanel.add(infoTitle);
        leftPanel.add(Box.createVerticalStrut(20));
        addInfoLabel(leftPanel, rescuedLabel);
        addInfoLabel(leftPanel, scoreLabel);
        addInfoLabel(leftPanel, movesLabel);
        addInfoLabel(leftPanel, new JLabel("Difficulty: Medium"));

        JPanel rightPanel = makeSidePanel(
                "Legend",
                "R = Robot",
                "S = Survivor",
                "X = Obstacle",
                "* = BFS Path",
                "Algorithm: BFS + Recursion",
                "Sorting: Bubble Sort"
        );

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(16, 24, 32));

        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        JButton startAIButton = new JButton("Start BFS AI");
        JButton scanButton = new JButton("Scan Area");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton resetButton = new JButton("Reset");

        upButton.addActionListener(e -> moveRobot(-1, 0));
        downButton.addActionListener(e -> moveRobot(1, 0));
        leftButton.addActionListener(e -> moveRobot(0, -1));
        rightButton.addActionListener(e -> moveRobot(0, 1));
        startAIButton.addActionListener(e -> startBFSAI());
        scanButton.addActionListener(e -> scanArea());
        saveButton.addActionListener(e -> saveGame());
        loadButton.addActionListener(e -> loadGame());
        leaderboardButton.addActionListener(e -> showLeaderboard());
        resetButton.addActionListener(e -> resetGame());

        bottomPanel.add(upButton);
        bottomPanel.add(downButton);
        bottomPanel.add(leftButton);
        bottomPanel.add(rightButton);
        bottomPanel.add(startAIButton);
        bottomPanel.add(scanButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(leaderboardButton);
        bottomPanel.add(resetButton);

        add(leftPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void startBFSAI() {
        javax.swing.Timer timer = new javax.swing.Timer(350, null);

        timer.addActionListener(e -> {
            if (rescued == 5) {
                timer.stop();
                return;
            }

            currentPath = findPathToNearestSurvivor();

            if (currentPath.size() <= 1) {
                timer.stop();
                return;
            }

            int[] nextStep = currentPath.get(1);
            moveRobotTo(nextStep[0], nextStep[1]);
        });

        timer.start();
    }

    private ArrayList<int[]> findPathToNearestSurvivor() {
        boolean[][] visited = new boolean[10][10];
        int[][] parentRow = new int[10][10];
        int[][] parentCol = new int[10][10];

        for (int r = 0; r < 10; r++) {
            Arrays.fill(parentRow[r], -1);
            Arrays.fill(parentCol[r], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{robotRow, robotCol});
        visited[robotRow][robotCol] = true;

        int[] found = null;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] current = queue.remove();
            int row = current[0];
            int col = current[1];

            if (isSurvivor(row, col)) {
                found = current;
                break;
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (isValidBFSCell(newRow, newCol, visited)) {
                    visited[newRow][newCol] = true;
                    parentRow[newRow][newCol] = row;
                    parentCol[newRow][newCol] = col;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        if (found == null) return new ArrayList<>();

        ArrayList<int[]> path = new ArrayList<>();
        int r = found[0];
        int c = found[1];

        while (r != -1 && c != -1) {
            path.add(0, new int[]{r, c});
            int previousRow = parentRow[r][c];
            int previousCol = parentCol[r][c];
            r = previousRow;
            c = previousCol;
        }

        return path;
    }

    private boolean isValidBFSCell(int row, int col, boolean[][] visited) {
        return row >= 0 && row < 10 && col >= 0 && col < 10
                && !visited[row][col]
                && !isObstacle(row, col);
    }

    private void scanArea() {
        boolean[][] visited = new boolean[10][10];
        int count = recursiveScan(robotRow, robotCol, visited);

        JOptionPane.showMessageDialog(
                this,
                "Recursive Scan Complete!\nReachable safe cells: " + count
        );
    }

    private int recursiveScan(int row, int col, boolean[][] visited) {
        if (row < 0 || row >= 10 || col < 0 || col >= 10) return 0;
        if (visited[row][col]) return 0;
        if (isObstacle(row, col)) return 0;

        visited[row][col] = true;

        return 1
                + recursiveScan(row - 1, col, visited)
                + recursiveScan(row + 1, col, visited)
                + recursiveScan(row, col - 1, visited)
                + recursiveScan(row, col + 1, visited);
    }

    private void showLeaderboard() {
        int[] scores = leaderboard.clone();

        for (int i = 0; i < scores.length - 1; i++) {
            for (int j = 0; j < scores.length - i - 1; j++) {
                if (scores[j] < scores[j + 1]) {
                    int temp = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = temp;
                }
            }
        }

        String result = "Leaderboard - Bubble Sort\n\n";

        for (int i = 0; i < scores.length; i++) {
            result += (i + 1) + ". " + scores[i] + "\n";
        }

        JOptionPane.showMessageDialog(this, result);
    }

    private void moveRobotTo(int newRow, int newCol) {
        robotRow = newRow;
        robotCol = newCol;
        moves++;

        checkRescue();
        updateLabels();
        drawGrid();
    }

    private void moveRobot(int rowChange, int colChange) {
        int newRow = robotRow + rowChange;
        int newCol = robotCol + colChange;

        if (newRow < 0 || newRow >= 10 || newCol < 0 || newCol >= 10) return;
        if (isObstacle(newRow, newCol)) return;

        currentPath.clear();
        moveRobotTo(newRow, newCol);
    }

    private void drawGrid() {
        gridPanel.removeAll();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton cell = new JButton();
                cell.setFont(new Font("Arial", Font.BOLD, 18));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (row == robotRow && col == robotCol) {
                    cell.setText("R");
                    cell.setBackground(new Color(45, 140, 255));
                } else if (isObstacle(row, col)) {
                    cell.setText("X");
                    cell.setBackground(new Color(200, 50, 50));
                } else if (isSurvivor(row, col)) {
                    cell.setText("S");
                    cell.setBackground(new Color(255, 210, 31));
                } else if (isInCurrentPath(row, col)) {
                    cell.setText("*");
                    cell.setBackground(new Color(255, 230, 60));
                } else {
                    cell.setBackground(new Color(140, 207, 95));
                }

                gridPanel.add(cell);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private boolean isInCurrentPath(int row, int col) {
        for (int[] step : currentPath) {
            if (step[0] == row && step[1] == col) return true;
        }
        return false;
    }

    private void checkRescue() {
        if (robotRow == 1 && robotCol == 8 && survivor1Alive) {
            survivor1Alive = false;
            rescued++;
            score += 100;
        }
        if (robotRow == 8 && robotCol == 2 && survivor2Alive) {
            survivor2Alive = false;
            rescued++;
            score += 100;
        }
        if (robotRow == 2 && robotCol == 9 && survivor3Alive) {
            survivor3Alive = false;
            rescued++;
            score += 100;
        }
        if (robotRow == 7 && robotCol == 1 && survivor4Alive) {
            survivor4Alive = false;
            rescued++;
            score += 100;
        }
        if (robotRow == 9 && robotCol == 9 && survivor5Alive) {
            survivor5Alive = false;
            rescued++;
            score += 100;
        }

        if (rescued == 5) {
            leaderboard[leaderboard.length - 1] = score;
            JOptionPane.showMessageDialog(this, "Mission Complete!\nFinal Score: " + score);
        }
    }

    private boolean isObstacle(int row, int col) {
        return (row == 0 && col == 3)
                || (row == 1 && col == 5)
                || (row == 2 && col == 5)
                || (row == 3 && col == 2)
                || (row == 4 && col == 4)
                || (row == 4 && col == 7)
                || (row == 5 && col == 1)
                || (row == 6 && col == 7)
                || (row == 7 && col == 5)
                || (row == 8 && col == 8)
                || (row == 9 && col == 4);
    }

    private boolean isSurvivor(int row, int col) {
        return (row == 1 && col == 8 && survivor1Alive)
                || (row == 8 && col == 2 && survivor2Alive)
                || (row == 2 && col == 9 && survivor3Alive)
                || (row == 7 && col == 1 && survivor4Alive)
                || (row == 9 && col == 9 && survivor5Alive);
    }

    private void saveGame() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("save.txt"));

            writer.println(robotRow);
            writer.println(robotCol);
            writer.println(moves);
            writer.println(score);
            writer.println(rescued);
            writer.println(survivor1Alive);
            writer.println(survivor2Alive);
            writer.println(survivor3Alive);
            writer.println(survivor4Alive);
            writer.println(survivor5Alive);

            writer.close();
            JOptionPane.showMessageDialog(this, "Game saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving game.");
        }
    }

    private void loadGame() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("save.txt"));

            robotRow = Integer.parseInt(reader.readLine());
            robotCol = Integer.parseInt(reader.readLine());
            moves = Integer.parseInt(reader.readLine());
            score = Integer.parseInt(reader.readLine());
            rescued = Integer.parseInt(reader.readLine());

            survivor1Alive = Boolean.parseBoolean(reader.readLine());
            survivor2Alive = Boolean.parseBoolean(reader.readLine());
            survivor3Alive = Boolean.parseBoolean(reader.readLine());
            survivor4Alive = Boolean.parseBoolean(reader.readLine());
            survivor5Alive = Boolean.parseBoolean(reader.readLine());

            reader.close();

            currentPath.clear();
            updateLabels();
            drawGrid();

            JOptionPane.showMessageDialog(this, "Game loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No saved game found.");
        }
    }

    private void resetGame() {
        robotRow = 1;
        robotCol = 1;
        moves = 0;
        score = 0;
        rescued = 0;
        currentPath.clear();

        survivor1Alive = true;
        survivor2Alive = true;
        survivor3Alive = true;
        survivor4Alive = true;
        survivor5Alive = true;

        updateLabels();
        drawGrid();
    }

    private void updateLabels() {
        movesLabel.setText("Moves: " + moves);
        scoreLabel.setText("Score: " + score);
        rescuedLabel.setText("Rescued: " + rescued + " / 5");
    }

    private void addInfoLabel(JPanel panel, JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(label);
        panel.add(Box.createVerticalStrut(15));
    }

    private JPanel makeSidePanel(String title, String... lines) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(22, 34, 46));
        panel.setPreferredSize(new Dimension(220, 600));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        for (String line : lines) {
            JLabel label = new JLabel(line);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(label);
            panel.add(Box.createVerticalStrut(15));
        }

        return panel;
    }

    public static void main(String[] args) {
        new Main();
    }
}