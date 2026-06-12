public class GameManager {
    private Grid grid;
    private Robot robot;
    private Statistics statistics;
    private PathFinder pathFinder;
    private SaveManager saveManager;

    public GameManager() {
        grid = new Grid();
        robot = new Robot(1, 1);
        statistics = new Statistics();
        pathFinder = new PathFinder();
        saveManager = new SaveManager();
    }

    public Grid getGrid() {
        return grid;
    }

    public Robot getRobot() {
        return robot;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }
}