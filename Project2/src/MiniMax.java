import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

/**
 * Created by shaowenyuan on 20/02/2018.
 */
public class MiniMax {

    private final int MAX_NUMBER = 1000000;
    private final double GAMMA = 1.5;
    public int aaa = 0;
    public int cut = 0;
    public int leaf = 0;
    public int test = 0;

    public MiniMax() {
        aaa = 0;
        cut = 0;
    }
/*
    public int[] getPosition(int[][] board, int m , int turn) {
        List<pointScore> list = getpoints(board,m,turn);
        int row = -1;
        int col = -1;
        double score = Integer.MIN_VALUE;

        for(int k = 0; k <list.size(); k++) {
            int i = list.get(k).getRow();
            int j = list.get(k).getCol();
            board[i][j] = turn;
            double temp = play(board,4,Integer.MIN_VALUE,Integer.MAX_VALUE,turn,m);
            board[i][j] = 0;
            if (temp > score) {
                row = i;
                col = j;
                score = temp;
            }
        }
        int[] res = new int[2];
        res[0] = row;
        res[1] = col;
        return res;
    }

    public double play (int[][] board, int depth, double alpha, double beta, int turn, int m) {

        double temp = 0;

        if (depth == 0) {
            Evaluator evaluator = new Evaluator(m);
            double player = evaluator.evaBoard(board,1);
            double enemy = evaluator.evaBoard(board, -1);
            temp = player - (enemy * GAMMA);
            return temp;
        }

        if (turn == 1) {
            List<pointScore> list = getpoints(board,m,turn);

            aaa++;

            int searchSpace = list.size();
            double best = Integer.MIN_VALUE;

            for (int k = 0; k < searchSpace; k++) {
                int row = list.get(k).getRow();
                int col = list.get(k).getCol();


                board[row][col] = turn;
                best = Math.max(play(board,depth-1,alpha,beta,-turn,m),best);
                board[row][col] = 0;
                alpha = Math.max(best,alpha);
                if (beta <= alpha) {
                    cut++;
                    break;
                }
            }
            return best;
        } else {
            List<pointScore> list = getpoints(board,m,turn);

            aaa++;

            int searchSpace = list.size();
            double best = Integer.MAX_VALUE;
            for (int k = 0; k < searchSpace; k++) {
                int row = list.get(k).getRow();
                int col = list.get(k).getCol();

                board[row][col] = turn;

                best = Math.max(play(board,depth-1,alpha,best,-turn,m),best);
                board[row][col] = 0;
                beta = Math.min(best,beta);
                if (beta <= alpha) {
                    cut++;
                    break;
                }
            }
            return best;
        }
    }
*/

    public double[] minimaxHelper2 (int[][] board, int depth, int turn , int m, double alpha_beta) {
        double [] temp = new double[3];
        aaa++;
        if (depth == 0) {
            leaf++;
            Evaluator evaluator = new Evaluator(m);
            double player = evaluator.evaBoard(board, 1);
            double enemy = evaluator.evaBoard(board, -1);
            temp[0] = player - (enemy * GAMMA);
            temp[1] = -1;
            temp[2] = -1;
            return temp;
        }

        double[] rewardStep = {-turn * MAX_NUMBER, -1, -1};
        double tempReward = rewardStep[0];
        double[] tempchoice = {-1, -1};

        List<pointScore> list = getpoints(board,m,turn);

        for (pointScore point : list) {
            int row = point.getRow();
            int col = point.getCol();

            if(!checkNeighbour(board,row,col)) {
                break;
            }
            board[row][col] = turn;

            rewardStep = minimaxHelper2(board, depth-1, -turn, m, tempReward);
            board[row][col] = 0;

            if (turn * rewardStep[0] > turn * tempReward) {
                tempReward = rewardStep[0];
                tempchoice[0] = row;
                tempchoice[1] = col;
            }

            if (turn * alpha_beta <= turn * tempReward) {
                cut++;
                break;
            }
        }
        rewardStep[0] = tempReward;
        rewardStep[1] = tempchoice[0];
        rewardStep[2] = tempchoice[1];
        return rewardStep;
    }

    public void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) {
                    System.out.print("X ");
                }
                if (board[i][j] == 1) {
                    System.out.print("O ");
                }
                if (board[i][j] == 0) {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }

    public double[] minimaxHelper (int[][] board, int depth, int turn, int m, double alpha_beta) {
        aaa++;
        double[] temp = new double[3];
        if (depth == 0) {
            leaf++;
            Evaluator evaluator = new Evaluator(m);
            double player = evaluator.evaBoard(board, 1);
            double enemy = evaluator.evaBoard(board, -1);
            temp[0] = player - (enemy * GAMMA);
            temp[1] = -1;
            temp[2] = -1;
            return temp;
        }

        double[] rewardStep = {-turn * MAX_NUMBER, -1, -1};
        double tempReward = rewardStep[0];
        double[] tempchoice = {-1, -1};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0 && checkNeighbour(board,i,j)) {
                    //System.out.println("row: "+i+"  col:" + j);
                    board[i][j] = turn;
                    rewardStep = minimaxHelper(board, depth-1, -turn, m, tempReward);
                    board[i][j] = 0;

                    if (turn * rewardStep[0] > turn * tempReward) {
                        tempReward = rewardStep[0];
                        tempchoice[0] = i;
                        tempchoice[1] = j;
                    }

                    if (turn * alpha_beta <= turn * tempReward) {
                        cut++;
                        board[i][j] = 0;
                        break;
                    }
                }
            }
        }
        rewardStep[0] = tempReward;
        rewardStep[1] = tempchoice[0];
        rewardStep[2] = tempchoice[1];
        return rewardStep;
    }

    public boolean checkNeighbour(int[][] board, int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row+i < 0 || row+i > board.length-1 || col+j < 0 || col+j > board.length-1)
                    continue;
                if (board[row+i][col+j] != 0)
                    return true;
            }
        }
        return false;
    }
    /*
    public int evaluation (int[][] board, int m, int player) {
        //return 0;
        int[] count = new int[5];

        for (int i = 0; i < board.length; i++) {
            int j = 0;
            int horizon = 0;
            int vertical = 0;
            while (j < board.length) {
                if (board[i][j] == player) {
                    horizon++;
                    if (horizon >= m)   return score[4];
                }
                else {
                    if (horizon - m + 4 >= 0) {
                        count[horizon - m + 4]++;
                    }
                    horizon = 0;
                }
                if (board[j][i] == player) {
                    vertical++;
                    if (vertical >= m)  return score[4];
                }
                else {
                    if (vertical - m + 4 >= 0) {
                        count[vertical - m + 4]++;
                    }

                    vertical = 0;
                }
                j++;
            }
            if (horizon - m + 4 >= 0) {
                count[horizon - m + 4]++;
            }
            if (vertical - m + 4 >= 0) {
                count[vertical - m + 4]++;
            }
        }

        //diagnal
        for (int k = m-1; k <= 2*board.length - m - 1; k++) {
            int diagnal = 0;
            int opposite = 0;
            for (int j = 0; j < board.length; j++) {
                int dia = k - j;
                int opp = k - board.length + j;
                if (dia >= 0 && dia <= board.length - 1){
                    if (board[dia][j] == player) {
                        diagnal++;
                        if (diagnal >= m) return score[4];
                    }
                    else {
                        if (diagnal >= m - 4) {
                            count[diagnal - m + 4]++;
                        }
                        diagnal = 0;
                    }
                }
                if (opp >= 0 && opp <= board.length - 1){
                    if (board[opp][j] == player) {
                        opposite++;
                        if (opposite >= m) return score[4];
                    }
                    else {
                        if (opposite >= m -4) {
                            count[opposite - m + 4]++;
                        }
                        opposite = 0;
                    }
                }
            }
            if (diagnal - m + 4 >= 0) {
                count[diagnal - m + 4]++;
            }
            if (opposite - m + 4 >= 0) {
                count[opposite - m +4]++;
            }
        }
        int res = 0;
        //calculate
        for (int i = 0; i < count.length; i++) {
            res += count[i] * score[i];
        }
        return res;
    }
    */
    /*
    public int evaluation2 (int[][] board, int m, int player) {
        //return 0;
        int[] count = new int[5];

        for (int i = 0; i < board.length; i++) {
            int j = 0;
            int horizon = 0;
            int vertical = 0;
            int len_hor = 0;
            int len_ver = 0;
            boolean flag_hor = true;
            boolean flag_ver = true;
            while (j < board.length) {
                if (board[i][j] != -player) {
                    len_hor++;
                    if (board[i][j] == player) {
                        horizon++;
                        if (horizon >= m) return score[4];
                    }
                }
                else {
                    if (horizon - m + 4 >= 0 && len_hor >= m) {
                        count[horizon - m + 4]++;
                    }
                    horizon = 0;
                    len_hor = 0;
                }
                if (board[j][i] != -player) {
                    len_ver++;
                    if (board[j][i] == player) {
                        vertical++;
                        if (vertical >= m)  return score[4];
                    }
                }
                else {
                    if (vertical - m + 4 >= 0 && len_ver >= m) {
                        count[vertical - m + 4]++;
                    }

                    vertical = 0;
                    len_ver = 0;
                }
                j++;
            }
            if (horizon - m + 4 >= 0 && len_hor >= m) {
                count[horizon - m + 4]++;
            }
            if (vertical - m + 4 >= 0 && len_ver >= m) {
                count[vertical - m + 4]++;
            }
        }

        //diagnal
        for (int k = m-1; k <= 2*board.length - m - 1; k++) {
            int diagnal = 0;
            int len_dia = 0;
            int opposite = 0;
            int len_opp = 0;
            for (int j = 0; j < board.length; j++) {
                int dia = k - j;
                int opp = k - board.length + j;
                if (dia >= 0 && dia <= board.length - 1){
                    if (board[dia][j] != -player) {
                        len_dia++;
                        if (board[dia][j] == player) {
                            diagnal++;
                            if (diagnal >= m)   return score[4];
                        }
                    }
                    else {
                        if (diagnal >= m - 4 && len_dia >= m) {
                            count[diagnal - m + 4]++;
                        }
                        diagnal = 0;
                        len_dia = 0;
                    }
                }
                if (opp >= 0 && opp <= board.length - 1){
                    if (board[opp][j] != -player) {
                        len_opp++;
                        if (board[opp][j] == player) {
                            opposite++;
                            if (opposite >= m)  return score[4];
                        }
                    }
                    else {
                        if (opposite >= m -4 && len_opp >= m) {
                            count[opposite - m + 4]++;
                        }
                        opposite = 0;
                        len_opp = 0;
                    }
                }
            }
            if (diagnal - m + 4 >= 0 && len_dia >= m) {
                count[diagnal - m + 4]++;
            }
            if (opposite - m + 4 >= 0 && len_opp >= m) {
                count[opposite - m +4]++;
            }
        }
        int res = 0;
        //calculate
        for (int i = 0; i < count.length; i++) {
            res += count[i] * score[i];
        }
        return res;
    }
    */

    public List<pointScore> getpoints (int[][] board, int m, int turn) {
        int len = board.length;
        Heuristic heuristic = new Heuristic();
        Evaluator evaluator = new Evaluator(m);
        List<pointScore> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] == 0 && checkNeighbour(board,i,j)) {
                    int row = i;
                    int col = j;
                    board[i][j] = turn;
                    double score = heuristic.HeuristicValue(board,row,col,m,turn);
                    //double score = evaluator.evaBoard(board,turn) - GAMMA * evaluator.evaBoard(board,-turn);
                    board[i][j] = 0;
                    res.add(new pointScore(row,col,score));
                }
            }
        }
        if (turn == 1) {
            Collections.sort(res, Comparator.comparingDouble(pointScore::getScore).reversed());
        } else {
            Collections.sort(res, Comparator.comparingDouble(pointScore::getScore));
        }
        return res;
    }
}


