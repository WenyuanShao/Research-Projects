/**
 * Created by shaowenyuan on 27/02/2018.
 */
public class pointScore {
    public int row;
    public int col;
    public double score;

    public pointScore(int row, int col, double score) {
        this.row = row;
        this.col = col;
        this.score = score;
    }

    public pointScore() {

    }

    public double getScore() {
        return this.score;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
