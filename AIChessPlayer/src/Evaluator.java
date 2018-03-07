/**
 * Created by shaowenyuan on 26/02/2018.
 */
public class Evaluator {

    private int m;

    private final double BONUS = 1.5;
    public static final double SCORE_WIN = 999999;

    public Evaluator(int m) {
        this.m = m;
    }

    public double evaBoard (int[][] board, int player) {
        double[] score = new double[m];
        //double[] enemy = new double[m];

        int len = board.length;

        //row
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - m + 1; j++) {
                int count = 0;
                int sum = 0;
                int MaxCount = 0;
                for (int k = 0; k < m; k++) {
                    if (board[i][j + k] == 0) {
                        MaxCount = Math.max(count, MaxCount);
                        count = 0;
                    } else if (board[i][j + k] == player) {
                        sum++;
                        count++;
                    } else {
                        sum = 0;
                        j += k;
                        break;
                    }
                }
                MaxCount = Math.max(MaxCount,count);
                if (sum == m) {
                    return SCORE_WIN;
                }
                score[sum] += m + BONUS * MaxCount;
            }
        }

        //column
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - m + 1; j++) {
                int count = 0;
                int sum = 0;
                int MaxCount = 0;
                for (int k = 0; k < m; k++) {
                    if (board[j + k][i] == 0) {
                        MaxCount = Math.max(count, MaxCount);
                        count = 0;
                    } else if (board[j + k][i] == player) {
                        sum++;
                        count++;
                    } else {
                        sum = 0;
                        j += k;
                        break;
                    }
                }
                MaxCount = Math.max(MaxCount,count);
                if (sum == m) {
                    return SCORE_WIN;
                }
                score[sum] += m + BONUS * MaxCount;
            }
        }

        //diagnal(\)
        int row = len - m;
        int col = 0;
        while (col < len - m) {
            int i = row;
            int j = col;
            while (i < len - m + 1 && j < len - m + 1) {
                int sum = 0;
                int count  = 0;
                int MaxCount = 0;
                for (int k = 0; k < m; k++) {
                    if (board[i+k][j+k] == 0) {
                        MaxCount = Math.max(count, MaxCount);
                        count = 0;
                    } else if (board[i+k][j+k] == player) {
                        sum ++;
                        count++;
                    } else {
                        sum = 0;
                        j += k;
                        i += k;
                        break;
                    }
                }
                MaxCount = Math.max(MaxCount, count);
                if (sum == m) {
                    return SCORE_WIN;
                }
                score[sum] += m + BONUS * MaxCount;
                i++;j++;
            }
            if (row > 0) {
                row --;
            } else{
                col ++;
            }
        }

        //opposite (/)
        row = m - 1;
        col = 0;
        while (col < len - m) {
            int i = row;
            int j = col;
            while (i >= m - 1 && j < len - m + 1) {
                int sum = 0;
                int count = 0;
                int MaxCount = 0;
                for (int k = 0; k < m; k++) {
                    if (board[i - k][j + k] == 0) {
                        MaxCount = Math.max(count, MaxCount);
                        count = 0;
                    } else if (board[i - k][j + k] == player) {
                        sum ++;
                        count++;
                    } else {
                        sum = 0;
                        i -= k;
                        j += k;
                        break;
                    }
                }
                MaxCount = Math.max(MaxCount, count);
                if (sum == m)   return SCORE_WIN;
                score[sum] += m + BONUS * MaxCount;
                i--;
                j++;
            }
            if (row < len - 1)  row++;
            else col++;
        }


        double res = 0;
        for (int i = 1; i < score.length; i++) {
            res += score[i] * i;
        }
        return res;
    }

    /*public static void main (String[] args) {
        Evaluator e1 = new Evaluator(4);
        int[][] b = new int[4][4];
        b[1][1] = -1;
        b[1][0] = 1;
        b[0][0] = -1;
        b[2][2] = 1;
        b[0][2] = -1;
        b[3][3] = 1;
        b[2][1] = -1;
        b[2][0] = 1;
        b[3][0] = -1;
        b[0][1] = 1;
        b[1][2] = -1;
        /*double prevScore = e1.evaluateWholeBoard(b);
        System.out.println(prevScore);
        b[7][7] = 1;*/
    //    double score = e1.evaBoard(b,1);
    //    System.out.println(score);
    // }
}
