import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by shaowenyuan on 20/02/2018.
 */
public class MiniMax {

    private final int MAX_NUMBER = 1000000;
    private final int[] score = {10,100,1000,10000,100000};
    public int aaa = 0;
    public int cut = 0;

    public MiniMax() {
        aaa = 0;
        cut = 0;
    }

    public int[] minimaxHelper (int[][] board, int depth, int turn, int m, int alpha_beta) {
        aaa++;
        int[] temp = new int[3];
        if (depth == 0) {
            int player = evaluation(board,m,1);
            int enemy = evaluation(board,m,-1);
            temp[0] = player-enemy;
            temp[1] = -1;
            temp[2] = -1;
            return temp;
        }

        int[] rewardStep = {-turn * MAX_NUMBER, -1, -1};
        int tempReward = rewardStep[0];
        int[] tempchoice = {-1, -1};

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

    /*public int evaluationW (int[][] board, int m, int player) {
        int[] count = new int[5];
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

            }
        }
    }*/

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

}

class test {
    public static void main (String[] args) {
        Board board = new Board(17,5);
        MiniMax miniMax = new MiniMax();
        board.board[7][7] = -1;
        /*board.board[2][2] = 1;
        board.board[3][1] = -1;
        board.board[2][1] = 1;
        board.board[4][3] = -1;
        board.board[2][3] = 1;
        board.board[2][0] = -1;*/
        //board.board[2][4] = 1;
        //System.out.println(miniMax.evaluation(board.board,4));
        int turn = 1;
        int it = 0;
        while (!board.isFull()){
            int[] res = miniMax.minimaxHelper(board.getBoard(),4,1, 5, 100000);
            System.out.println(res[1]+"    "+res[2]);
            System.out.println("recursion: "+miniMax.aaa);
            System.out.println("cut: "+miniMax.cut);
            board.move(res[1],res[2],turn);
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
