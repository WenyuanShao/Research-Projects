/**
 * Created by shaowenyuan on 27/02/2018.
 */
public class Heuristic {
    private final double BONUS = 1.5;
    public static final double SCORE_WIN = 999999;
    private final double GAMMA = 1.5;

    public Heuristic () {

    }

    public double HeuristicValue (int[][] board, int row, int col, int m) {

        int len = board.length;

        int l = 0;
        int r = 0;
        int u = 0;
        int d = 0;
        int ul = 0;
        int ur = 0;
        int dl = 0;
        int dr = 0;

        while (col - l > 0 && l < m - 1) {
            l++;
        }
        while (col + r < len - 1 && r < m - 1) {
            r++;
        }
        while (row - u > 0 && u < m - 1) {
            u++;
        }
        while (row + d < len - 1 && d < m - 1) {
            d++;
        }
        while (row - ul > 0 && col - ul > 0 && ul < m - 1) {
            ul++;
        }
        while (row - ur > 0 && col + ur < len - 1 && ur < m - 1) {
            ur++;
        }
        while (row + dl < len - 1 && col - dl > 0 && dl < m - 1) {
            dl++;
        }
        while (row + dr < len - 1 && col + dr < len - 1 && dr < m - 1) {
            dr++;
        }
        double selfScore = evaPoint(r,l,u,d,ul,ur,dl,dr,row,col,board,1,m);
        double enemyScore = evaPoint(r,l,u,d,ul,ur,dl,dr,row,col,board,-1,m);
        double score = selfScore- enemyScore * GAMMA;
        return score;
    }

    public double evaPoint(int r, int l, int u, int d, int ul, int ur, int dl, int dr,
                           int row, int col,
                           int[][] board, int turn, int m) {
        int len = board.length;
        double[] score = new double[m];
        //row
        for (int i = -l; i <= r+1-m; i++) {
            int count = 0;
            int sum = 0;
            int MaxCount = 0;
            for (int k = 0; k < m; k++) {
                if (board[row][i + k + col] == 0) {
                    MaxCount = Math.max(count, MaxCount);
                    count = 0;
                } else if (board[row][i + k + col] == turn) {
                    sum++;
                    count++;
                } else {
                    sum = 0;
                    i += k;
                    break;
                }
            }
            MaxCount = Math.max(MaxCount,count);
            if (sum == m) {
                return SCORE_WIN;
            }
            score[sum] += m + BONUS * MaxCount;
        }


        //column
        for (int i = -u; i < d-m+1; i++) {
            int count = 0;
            int sum = 0;
            int MaxCount = 0;
            for (int k = 0; k < m; k++) {
                if (board[i + k + row][col] == 0) {
                    MaxCount = Math.max(count, MaxCount);
                    count = 0;
                } else if (board[i + k + row][col] == turn) {
                    sum++;
                    count++;
                } else {
                    sum = 0;
                    i += k;
                    break;
                }
            }
            MaxCount = Math.max(MaxCount,count);
            if (sum == m) {
                return SCORE_WIN;
            }
            score[sum] += m + BONUS * MaxCount;
        }

        //diagnal(\)
        for (int i = -ul; i <= dr - m + 1; i++) {
            int sum = 0;
            int count  = 0;
            int MaxCount = 0;
            for (int k = 0; k < m; k++) {
                if (board[row + i + k][col + i + k] == 0) {
                    MaxCount = Math.max(count, MaxCount);
                    count = 0;
                } else if (board[row + i + k][col + i + k] == turn) {
                    sum ++;
                    count++;
                } else {
                    sum = 0;
                    i += k;
                    break;
                }
            }
            MaxCount = Math.max(MaxCount, count);
            if (sum == m) {
                return SCORE_WIN;
            }
            score[sum] += m + BONUS * MaxCount;
        }
        // opposite (/)
        for (int i = -dl; i <= ur - m + 1; i++) {
            int sum = 0;
            int count = 0;
            int MaxCount = 0;
            for (int k = 0; k < m; k++) {
                if (board[row - i - k][col + k + i] == 0) {
                    MaxCount = Math.max(count, MaxCount);
                    count = 0;
                } else if (board[row - i - k][col + i + k] == turn) {
                    sum ++;
                    count++;
                } else {
                    sum = 0;
                    i += k;
                    break;
                }
            }
            MaxCount = Math.max(MaxCount, count);
            if (sum == m)   return SCORE_WIN;
            score[sum] += m + BONUS * MaxCount;
        }

        double res = 0;
        for (int i = 0; i < score.length; i++) {
            res += i * score[i];
        }
        return res;
    }
}
