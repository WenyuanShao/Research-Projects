/**
 * Created by shaowenyuan on 20/02/2018.
 */
public class MiniMax {

    private final int MAX_NUMBER = 1000000;

    public MiniMax() {

    }

    public int[] minimaxHelper (int[][] board, int depth, int turn/*, int alpha_beta*/) {
        int[] temp = new int[3];
        if (depth == 0) {
            temp[0] = evaluation(board);
            temp[1] = -1;
            temp[2] = -1;
            return temp;
        }

        int[] rewardStep = {-turn * MAX_NUMBER, -1, -1};
        int tempReward = rewardStep[0];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = turn;
                    rewardStep = minimaxHelper(board, -turn, depth-1/*, rewardStep[0]*/);
                }

                /*if (turn * alpha_beta < turn * rewardStep[0]) break;*/
                if (turn * rewardStep[0] > turn * tempReward) {
                    tempReward = rewardStep[0];
                    rewardStep[1] = i;
                    rewardStep[2] = j;
                }
                board[i][j] = 0;
            }
        }

        return rewardStep;
    }

    public int evaluation (int[][] board, int m) {
        int[] count = new int[5];
        int turn = 1;

        for (int i = 0; i < board.length; i++) {
            int j = 0;
            int horizon = 0;
            int vertical = 0;
            while (j < board.length) {
                if (board[i][j] == 1)   horizon++;
                else {
                    if (horizon - m + 4 >= 0) {
                        count[horizon - m + 4]++;
                    }
                    horizon = 0;
                }
                if (board[i][j] == -1)  vertical++;
                else {
                    if (vertical - m + 4 >= 0) {
                        count[vertical - m + 4]++;
                    }
                    vertical = 0;
                }
            }
        }

        //diagnal
        for (int k = m-1; k <= 2*board.length - m; k++) {
            int diagnal = 0;
            int opposite = 0;
            for (int j = 0; j <= k; j++) {
                int dia = k - j;
                int opp = k - board.length + j;
                if (board[dia][j] == 1) diagnal++;
                else {
                    if (diagnal >= m - 4) {
                        count[diagnal - m + 4]++;
                    }
                    diagnal = 0;
                }
                if (board[opp][j] == -1) opposite++;
                else {
                    if (opposite >= m -4) {
                        count[opposite - m + 4]++;
                    }
                    opposite = 0;
                }
            }
        }

        //calculate
    }

}

class test {
    public static void main (String[] args) {
        Board board = new Board(5,3);
        MiniMax miniMax = new MiniMax();
        int[] res = miniMax.minimaxHelper(board.getBoard(),5,1);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }
}
