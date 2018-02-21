/**
 * Created by shaowenyuan on 24/01/2018.
 */
public class TicTacToe {
    public static char[][] board;
    public static char currentMark;

    public TicTacToe() {
        board = new char[3][3];
        currentMark = 'X';
        initialBoard();
    }

    public void initialBoard() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    /*public void printboard() {
        System.out.println("*****************");
        for (int i = 0; i < 3; i++) {
            System.out.print("|   ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+"|   ");
            }
            System.out.println();
        }
        System.out.println("*****************");
    }*/

    public boolean isBoardFull() {
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j =0; j < 3; j++) {
                if (board[i][j] == '-') {
                    isFull = false;
                }
            }
        }
        return isFull;
    }

    /*private boolean check (char x1, char x2, char x3) {
        return (x1 != '-' && x1 == x2 && x2 == x3);
    }

    private boolean CoulumnWin() {
        for (int i = 0; i < 3; i++) {
            if (check(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        return false;
    }

    private boolean RowWin() {
        for (int i = 0; i < 3; i++) {
            if (check(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean DiagonalWin() {
        return  ((check(board[0][0],board[1][1],board[2][2])) ||
                (check(board[0][2],board[1][1],board[2][0])));
    }*/

    public boolean placemark(int row, int column) {
        if (row >= 0 && row < 3) {
            if (column >= 0 && column < 3) {
                if (board[row][column] == '-') {
                    board[row][column] = currentMark;
                    return true;
                }
            }
        }
        return false;
    }
}
