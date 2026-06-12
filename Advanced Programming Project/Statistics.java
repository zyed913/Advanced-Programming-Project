public class Statistics {
    private int moves;
    private int score;
    private int rescued;

    public Statistics() {
        moves = 0;
        score = 0;
        rescued = 0;
    }

    public void addMove() {
        moves++;
    }

    public void rescueSurvivor() {
        rescued++;
        score += 100;
    }

    public int getMoves() {
        return moves;
    }

    public int getScore() {
        return score;
    }

    public int getRescued() {
        return rescued;
    }

    public void reset() {
        moves = 0;
        score = 0;
        rescued = 0;
    }
}