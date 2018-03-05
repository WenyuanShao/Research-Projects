/**
 * Created by shaowenyuan on 04/03/2018.
 */

public class test {
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
            double[] res = miniMax.minimaxHelper2(board.getBoard(),6,turn, 5, 100000);

            System.out.println(res[0]+"     "+res[1]+"    "+res[2]);
            System.out.println("turn: "+turn);
            System.out.println("leaf: "+miniMax.leaf);
            System.out.println("recursion: "+miniMax.aaa);
            System.out.println("cut: "+miniMax.cut);
            System.out.println("test:" +miniMax.test);
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
