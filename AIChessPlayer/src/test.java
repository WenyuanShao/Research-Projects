/**
 * Created by shaowenyuan on 04/03/2018.
 */

public class test {

    public static final int HUMAN_TURN = -1;
    public static final int AI_TURN = 1;

    public static void main (String[] args) {
        Board board = new Board(12,6);
        HumanPlayer humanPlayer = new HumanPlayer(HUMAN_TURN);
        AIPlayer aiPlayer = new AIPlayer(AI_TURN);
        int turn = 1;

        while (true) {

            //Human Move
            int[] hMove = humanPlayer.getMove();
            board.move(hMove[0], hMove[1], humanPlayer.getTurn());

            //AIMove
            double[] res = aiPlayer.minimaxHelper2(board.getBoard(),6, turn, 6,100000);
            board.move((int)res[1],(int)res[2],turn);

            //Print reward message;
            System.out.println(res[0]+"     "+res[1]+"    "+res[2]);

            System.out.println("leaf: "+ aiPlayer.leaf);
            System.out.println("recursion: "+ aiPlayer.aaa);
            System.out.println("cut: "+ aiPlayer.cut);
            System.out.println("test:" + aiPlayer.test);

            //Print Board
            board.printBoard();

            //Reset factor;
            aiPlayer.reSet();
        }
    }
}
