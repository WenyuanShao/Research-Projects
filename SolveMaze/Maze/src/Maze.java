/**
 * Created by shaowenyuan on 25/01/2018.
 */
public class Maze {

    public static int[] start;
    public static int[] end;
    public static int count = 10000;
    public static int[][] maze;


    /*public static void main(String[] args) {
        ReadMaze readMaze = new ReadMaze();
        maze = readMaze.getMaze();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }

        //int start[] = {1, 71};
        //int end[] = {5, 79};
        //System.out.println(check(1,75,maze,37,77));
    }*/

    private static void check (int start_x, int start_y,int[][] maze, int end_x, int end_y) {
        if (start_x == end_x && start_y == end_y) {
            System.out.println("asasdasdasd");
            System.exit(1);
        }
        if (canMove(start_x, start_y, start_x, start_y+1, maze)) {
            maze[start_x][start_y] = -1;
            check(start_x,start_y+1,maze,end_x,end_y);
            //maze[start_x][start_y] = 0;
        }
        if (canMove(start_x, start_y, start_x, start_y-1, maze)) {
            maze[start_x][start_y] = -1;
            check(start_x,start_y-1,maze,end_x,end_y);
            //maze[start_x][start_y] = 0;
        }
        if (canMove(start_x, start_y, start_x-1, start_y, maze)) {
            maze[start_x][start_y] = -1;
            check(start_x-1,start_y,maze,end_x,end_y);
            //maze[start_x][start_y] = 0;
        }if (canMove(start_x+1, start_y, start_x, start_y, maze)) {
            maze[start_x][start_y] = -1;
            check(start_x + 1, start_y, maze, end_x, end_y);
            //maze[start_x][start_y] = 0;
        }
        return;
    }

    private static boolean canMove (int i, int j, int x, int y, int[][] maze) {
        if (x < 0 || y < 0 || x >= 81 || y>= 81) {
            return false;
        }
        if (maze[x][y] == 1) {
            return false;
        }
        if (maze[x][y] == -1) {
            return false;
        }
        return true;
    }

}
