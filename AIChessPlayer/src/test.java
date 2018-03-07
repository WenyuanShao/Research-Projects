/**
 * Created by shaowenyuan on 04/03/2018.
 */

public class test {

    public static final int HUMAN_TURN = -1;
    public static final int AI_TURN = 1;
    public static final int SIZE = 4;
    public static final int M = 4;
    public static final int WINNING_SCORE = 999999;
    public static final int DEPTH = 6;

    public static void AIvsAI (){
        Board board = new Board(SIZE,M);
        AIPlayer aiPlayer1 = new AIPlayer(AI_TURN);
        board.board[1][1] = -1;
        int turn = 1;
        while(true) {
            //AIPlayer 1
            //double[] move1 = aiPlayer1.minimaxHelper2(board.getBoard(), DEPTH, AI_TURN, M, WINNING_SCORE);
            double[] move1 = aiPlayer1.play(board.getBoard(),DEPTH,Integer.MIN_VALUE,Integer.MAX_VALUE,AI_TURN,M);
            System.out.println("turn: AI1    "+move1[0] + "     " + move1[1] + "    " + move1[2]);
            board.move((int) move1[1], (int) move1[2], AI_TURN);
            printLog(aiPlayer1);
            board.printBoard();
            aiPlayer1.reSet();
            if (board.isFull())    return;

            //AIPlayer 2
            //double[] move2 = aiPlayer2.minimaxHelper2(board.getBoard(), DEPTH, -AI_TURN, M, WINNING_SCORE);
            double[] move2 = aiPlayer1.play(board.getBoard(),DEPTH,Integer.MIN_VALUE,Integer.MAX_VALUE,-AI_TURN,M);
            System.out.println("turn: AI2    "+move2[0] + "     " + move2[1] + "    " + move2[2]);
            board.move((int) move2[1], (int) move2[2], -AI_TURN);
            printLog(aiPlayer1);
            board.printBoard();
            aiPlayer1.reSet();
            if (board.isFull())    return;
        }
    }

    public static void AIvsHuman () {
        Board board = new Board(SIZE, M);
        HumanPlayer humanPlayer = new HumanPlayer(HUMAN_TURN);
        AIPlayer aiPlayer = new AIPlayer(AI_TURN);
        int turn = 1;

        while (!board.isFull()) {

            //Human Move
            int[] hMove = humanPlayer.getMove();
            board.move(hMove[0], hMove[1], humanPlayer.getTurn());

            //AIMove
            //double[] res = aiPlayer.minimaxHelper2(board.getBoard(), DEPTH, turn, M, WINNING_SCORE);
            double[] res = aiPlayer.play(board.getBoard(),DEPTH,Integer.MIN_VALUE,Integer.MAX_VALUE,AI_TURN,M);
            board.move((int) res[1], (int) res[2], turn);

            //Print Log
            System.out.println(res[0] + "     " + res[1] + "    " + res[2]);
            printLog(aiPlayer);

            //Print Board
            board.printBoard();

            //Reset factor;
            aiPlayer.reSet();
        }
        return;
    }

    public static void printLog(AIPlayer aiPlayer) {
        //Print reward message;

        System.out.println("leaf: " + aiPlayer.leaf);
        System.out.println("recursion: " + aiPlayer.aaa);
        System.out.println("cut: " + aiPlayer.cut);
        System.out.println("test:" + aiPlayer.test);

        return;
    }

    public static void main (String[] args) {
        //AIvsHuman();
        AIvsAI();
    }
}
