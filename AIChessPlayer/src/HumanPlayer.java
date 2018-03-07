import java.util.Scanner;

/**
 * Created by shaowenyuan on 06/03/2018.
 */
public class HumanPlayer {

    public int turn;

    public HumanPlayer() {

    }

    public HumanPlayer(int turn) {
        this.turn = turn;
    }

    public int[] getMove () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the move!");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        return new int[]{row,col};
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
