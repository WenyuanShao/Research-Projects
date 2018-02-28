import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by shaowenyuan on 20/02/2018.
 */
public class MiniMax {

    private final int MAX_NUMBER = 1000000;
    private final double GAMMA = 1.5;
    private final int[] score = {
            10,
            100,
            1000,
            10000,
            100000
    };
    public int aaa = 0;
    public int cut = 0;
    public int test = 0;

    public MiniMax() {
        aaa = 0;
        cut = 0;
    }

    public double[] minimaxHelper2 (int[][] board, int depth, int turn , int m, double alpha_beta) {
        double [] temp = new double[3];
        aaa++;
        if (depth == 0) {
            Evaluator evaluator = new Evaluator(m);
            //int player = evaluation(board,m,1);
            //int enemy = evaluation(board,m,-1)*2;
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
        //if (turn == 1) {
            List<pointScore> list = getpoints(board,m,turn);
            int searchBreadth = list.size();
            for (int i = 0; i < searchBreadth; i++) {
                int row = list.get(i).getRow();
                int col = list.get(i).getCol();
                board[row][col] = turn;

                rewardStep = minimaxHelper2(board, depth-1, -turn, m, tempReward);
                board[row][col] = 0;

                if (turn * alpha_beta < turn * tempReward) {
                    cut++;
                    continue;
                }
                if (turn * rewardStep[0] > turn * tempReward) {
                    tempReward = rewardStep[0];
                    tempchoice[0] = row;
                    tempchoice[1] = col;
                }
            }
            rewardStep[0] = tempReward;
            rewardStep[1] = tempchoice[0];
            rewardStep[2] = tempchoice[1];
            return rewardStep;
        //}
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
            Evaluator evaluator = new Evaluator(m);
            //int player = evaluation(board,m,1);
            //int enemy = evaluation(board,m,-1)*2;
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
                    board[i][j] = turn;
                    rewardStep = minimaxHelper(board, depth-1, -turn, m, tempReward);

                    if (turn * alpha_beta < turn * tempReward) {
                        cut++;
                        board[i][j] = 0;
                        break;
                    }
                    if (turn * rewardStep[0] > turn * tempReward) {
                        tempReward = rewardStep[0];
                        tempchoice[0] = i;
                        tempchoice[1] = j;
                    }
                    board[i][j] = 0;

                    /*for (int p = 0; p < board.length; p++) {
                        for (int q = 0; q < board.length; q++) {
                            System.out.print(board[p][q]+" ");
                        }
                        System.out.println();
                    }*/
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
        List<pointScore> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] == 0 && checkNeighbour(board,i,j)) {
                    int row = i;
                    int col = j;
                    board[i][j] = turn;
                    double score = heuristic.HeuristicValue(board,row,col,m);
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

class test {
    public static void main (String[] args) {
        Board board = new Board(17,5);
        MiniMax miniMax = new MiniMax();
        board.board[7][7] = -1;
        /*board.board[7][6] = 1;
        board.board[6][7] = -1;
        board.board[6][5] = 1;
        board.board[5][6] = -1;
        board.board[5][4] = 1;
        board.board[8][7] = -1;*/
        //board.board[2][4] = 1;
        int turn = 1;
        int it = 0;
        while (!board.isFull()){
            double[] res = miniMax.minimaxHelper2(board.getBoard(),4,1, 5, 100000);
            System.out.println(res[0]+"     "+res[1]+"    "+res[2]);
            System.out.println("recursion: "+miniMax.aaa);
            System.out.println("cut: "+miniMax.cut);
            board.move((int)res[1],(int)res[2],turn);
            turn = -turn;
            for (int i = 0; i < board.board.length; i++) {
                for (int j = 0; j < board.board.length; j++) {
                    if (board.board[i][j] == -1) {
                        System.out.print("X ");
                    }
                    if (board.board[i][j] == 1) {
                        System.out.print("O ");
                    }
                    if (board.board[i][j] == 0) {
                        System.out.print("_ ");
                    }
                }
                System.out.println();
            }
            it++;
            System.out.println("---------------------------------------------");
        }
    }
}
