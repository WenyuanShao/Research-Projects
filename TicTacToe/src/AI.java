import java.util.Random;
import java.util.Scanner;

/**
 * Created by shaowenyuan on 24/01/2018.
 */
public class AI {
    public static char[][] board;
    public static final char[] mark = {'X','O'};
    public static final int[][] random = {
            {0,0},
            {0,1},
            {0,2},
            {1,0},
            {1,1},
            {1,2},
            {2,0},
            {2,1},
            {2,2}
    };
    public static final int[][] weightedChoice = {
            {0,0},
            {0,2},
            {2,0},
            {2,2},
            {0,1},
            {1,0},
            {1,2},
            {2,1}
    };

    public static void main(String[] args) {

        board = new char[3][3];
        int step = 0;
        initialboard(board);
        int ID  = 0;

        randomStep(ID);
        ID = 1-ID;

        while (!isFull()) {
            oneStep(ID);
            ID = 1-ID;
        }
        System.out.println();
        System.out.println("The result is:");
        print(board);
        if (win(board)) {
            System.out.println("Not Draw!");
        } else {
            System.out.println("Draw!");
        }
    }

    public static boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void randomStep(int ID) {
        System.out.println("this is a randomly picked step");
        Random rnd = new Random();
        int n = rnd.nextInt(6);
        drop(mark[ID],random[n][0],random[n][1]);
    }

    public static void oneStep(int ID) {
        if (win(board)) {
            return;
        }
        System.out.println("role:"+mark[ID]);
        if (isSecond()) {
            other(ID);
        } else if (attack(ID)) {
        } else if (defend(ID)) {
        } else if (first(ID)) {
        } else {
            other(ID);
        }
    }

    public static boolean isSecond() {
        int judge1 = 0;
        int judge2 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != '-') judge1++;
            }
        }
        if (judge1 != 1) {
            return false;
        } else {
            if (board[0][0] != '-') judge2++;
            if (board[2][0] != '-') judge2++;
            if (board[0][2] != '-') judge2++;
            if (board[2][2] != '-') judge2++;
            if (judge2 == 1) return true;
            else return false;
        }
    }

    public static void print(char[][] board){
        System.out.println("*****************");
        for (int i = 0; i < 3; i++) {
            System.out.print("|   ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+"|   ");
            }
            System.out.println();
        }
        System.out.println("*****************");
    }

    public static boolean first(int ID) {
        Character player = mark[ID];
        if (board[1][1] == '-') {
            drop(player,1,1);
            return true;
        } else {
            return false;
        }
    }

    public static void other(int ID) {
        Character player = mark[ID];
        //Character player2 = mark[1-ID];
        /*if ((board[0][0] == board[2][2] && board[0][0] == player2) ||
                (board[0][2] == board[2][0] && board[0][2] == player2)) {
            if (board[1][0] == '-'){
                drop(player,1,0);
                return;
            } else if (board[0][1] == '-'){
                drop(player,0,1);
                return;
            } else if (board[1][2] == '-'){
                drop(player,1,2);
                return;
            } else if (board[2][1] == '-'){
                drop(player,2,1);
                return;
            }
        } else if((board[0][1] == player2 || board[1][2] == player2) && board[0][2] == '-') {
            drop(player,0,2);
            return;
        } else if((board[1][0] == player2 || board[2][1] == player2) && board[2][0] == '-') {
            drop(player,2,0);
            return;
        } else if(board[0][0] == '-') {
            drop(player,0,0);
            return;
        } else if(board[0][2] == '-') {
            drop(player,0,2);
            return;
        } else if(board[2][0] == '-') {
            drop(player,2,0);
            return;
        } else if(board[2][2] == '-') {
            drop(player,2,2);
            return;
        } else if(board[0][1] == '-') {
            drop(player,0,1);
            return;
        } else if(board[1][0] == '-') {
            drop(player,1,0);
            return;
        } else if(board[1][2] == '-') {
            drop(player,1,2);
            return;
        } else {
            drop(player,2,1);
            return;
        }*/

        for (int i = 0; i < 8; i++) {
            int row = weightedChoice[i][0];
            int col = weightedChoice[i][1];
            if (board[row][col] == '-') {
                drop(player,row,col);
                return;
            }
        }
    }

    public static boolean attack(int ID) {
        return judge(mark[ID],ID);
    }

    public static boolean defend(int ID) {
        return judge(mark[1-ID],ID);
    }

    public static void initialboard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0 ; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public static  boolean judge (Character p, int ID) {
        Character player = mark[ID];
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == p && board[i][2] == '-') {
                drop(player,i,2);
                return true;
            } else if (board[i][0] == board[i][2] && board[i][0] == p && board[i][1] == '-') {
                drop(player,i,1);
                return true;
            } else if (board[i][1] == board[i][2] && board[i][1] == p && board[i][0] == '-') {
                drop(player,i,0);
                return true;
            } else if (board[0][i] == board[1][i] && board[0][i] == p && board[2][i] == '-') {
                drop(player,2,i);
                return true;
            } else if (board[0][i] == board[2][i] && board[0][i] == p && board[1][i] == '-') {
                drop(player,1,i);
                return true;
            } else if (board[1][i] == board[2][i] && board[1][i] == p && board[0][i] == '-') {
                drop(player,0,i);
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[0][0] == p && board[2][2] == '-') {
            drop(player,2,2);
            return true;
        } else if (board[0][0] == board[2][2] && board[0][0] == p && board[1][1] == '-') {
            drop(player,1,1);
            return true;
        } else if (board[1][1] == board[2][2] && board[1][1] == p && board[0][0] == '-') {
            drop(player,0,0);
            return true;
        } else {
            return false;
        }
    }

    private static void drop(Character player, int row, int col) {
        System.out.println("Drop: " + row +" "+col);
        System.out.println("*****************");
       board[row][col] = player;
    }

    private static boolean ColumnWin(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (check(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        return false;
    }

    private static boolean RowWin(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (check(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        return false;
}

    private static boolean DiagonalWin(char[][] board) {
        return  ((check(board[0][0],board[1][1],board[2][2])) ||
                (check(board[0][2],board[1][1],board[2][0])));
    }

    private static boolean check (char x1, char x2, char x3) {
        return (x1 != '-' && x1 == x2 && x2 == x3);
    }

    private static boolean win(char[][] board) {
        return RowWin(board) || ColumnWin(board) || DiagonalWin(board);
    }
}
