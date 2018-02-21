/**
 * Created by shaowenyuan on 20/02/2018.
 */
public class Board {
    public int[][] board;
    public int size;
    public int m;
    //public int player;

    public Board() {

    }

    public Board(int size, int m) {
        this.board = new int[size][size];
        this.size  = size;
        this.m = m;
        //this.player = player;
    }

    public int[][] getBoard() {
        return this.board;
    }

    /*public int getPlayer() {
        return this.player;
    }*/

    public void move (int row, int col, int player) {
        if (player == 1) {
            this.board[row][col] = 'X';
        } else {
            this.board[row][col] = 'O';
        }
    }
}
