import java.io.*;

public class SaveManager {

    public void save(int robotRow, int robotCol, int moves, int score, int rescued) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("save.txt"));

            writer.println(robotRow);
            writer.println(robotCol);
            writer.println(moves);
            writer.println(score);
            writer.println(rescued);

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving game.");
        }
    }
}