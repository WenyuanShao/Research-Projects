import java.util.Stack;

/**
 * Created by shaowenyuan on 28/01/2018.
 */
public class Solve {
    public int[][] maze = new ReadMaze().getMaze();
    public int count;

    public boolean step (int x, int y) {
        count ++;
        if (maze[x][y] == 3) {
            System.out.println("I am here");
            System.out.println(x+ "       " +y);
            return true;
        }

        if (maze[x][y] == 1 || maze[x][y] == -1) {
            return false;
        }
        maze[x][y] = -1;
        boolean result;

        result = step(x,y+1);
        if (result) {
            return true;
        }
        result = step(x,y-1);
        if (result) {
            return true;
        }
        result = step(x+1,y);
        if (result) {
            return true;
        }
        result = step(x-1,y);
        if (result) {
            return true;
        }

        maze[x][y] = 0;
        return false;
    }

    public void solve(int x, int y ) {
        if (step(x,y)) {
            //maze[x][y] = 2;
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public static void main(String[] args) {
        Solve s = new Solve();
        s.maze[1][75] = 3;
        s.solve(8,79);
        System.out.println(s.count);
    }
}
