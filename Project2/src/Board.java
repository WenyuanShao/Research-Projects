/**
 * Created by shaowenyuan on 20/02/2018.
 */
public class Board {
    public int[][] board;
    public int size;
    public int m;
    public final int[] score = {
            10,
            100,
            1000,
            10000,
            100000
    };

    public Board() {

    }

    public Board(int size, int m) {
        this.board = new int[size][size];
        this.size = size;
        this.m = m;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public boolean isFull() {
        int len = this.board.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if(this.board[i][j] != 0) return false;
            }
        }
        return true;
    }

    public void move(int row, int col, int turn) {
        System.out.println("row: "+row +"     col: "+col);
        if (this.board[row][col] != 0) {
            System.exit(0);
        } else {
            this.board[row][col] = turn;
        }
    }



}
