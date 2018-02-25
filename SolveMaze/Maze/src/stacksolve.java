import java.util.Stack;

/**
 * Created by shaowenyuan on 30/01/2018.
 */
public class stacksolve {

    public int[][] maze;
    public Stack<Position> stack;
    public boolean visited[][];
    public final int[][] move = {
            {0,1},
            {1,0},
            {0,-1},
            {-1,0},
    };
    public int EXIT_ROW;
    public int EXIT_COL;


    public void setEXIT(int EXIT_ROW, int EXIT_COL) {
        this.EXIT_ROW = EXIT_ROW;
        this.EXIT_COL = EXIT_COL;
    }

    public void init() {
        maze = new ReadMaze().getMaze();
        stack = new Stack<>();
        visited = new boolean[81][81];
    }

    public boolean checkbound (int row, int col) {
        boolean rowBound = (row >= 0 && row < 81);
        boolean colBound = (col >= 0 && col < 81);
        boolean res = rowBound && colBound;
        return res;
    }

    public void printMatrix () {
        Position temp;
        while (!stack.isEmpty())  {
            temp = stack.pop();
            maze[temp.row][temp.col] = 3;
        }
    }

    public boolean solve(int i, int j) {

        int row, col, dir;
        int nextRow, nextCol;

        stack.push(new Position(i,j,0));
        visited[i][j] = true;
        boolean found = false;
        Position temp = new Position(0,0,0);

        while (!stack.isEmpty() && !found) {

            try {
                temp = stack.pop();
                row = temp.row;
                col = temp.col;
                dir = temp.dir;

                while (dir < 4 && !found) {

                    nextRow = row + move[dir][0];
                    nextCol = col + move[dir][1];
                    if (nextRow == EXIT_ROW && nextCol == EXIT_COL) {

                        found = true;
                        temp = new Position(row, col, ++dir);
                        stack.push(temp);
                        temp = new Position(EXIT_ROW,EXIT_COL,1);
                        stack.push(temp);
                    } else if (checkbound(nextRow,nextCol) && maze[nextRow][nextCol] == 0
                            && visited[nextRow][nextCol] == false) {
                        visited[nextRow][nextCol] = true;
                        temp = new Position(row, col, ++dir);
                        stack.push(temp);
                        row = nextRow;
                        col = nextCol;
                        dir = 0;
                    } else {
                        ++dir;
                    }
                }
            } catch (Exception e) {
                System.out.println("Stack is empty");
                e.printStackTrace();
            }
            visited[EXIT_ROW][EXIT_ROW] = true;
        }

        if (found) {
            return true;
        } else {
            return false;
        }
    }
}

class Position {
    public int row;
    public int col;
    public int dir;

    public Position() {

    }
    public Position(int row, int col, int dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }
}

class test {
    public static void main (String[] args) {
        stacksolve ss = new stacksolve();
        ss.init();
        ss.setEXIT(8,79);

        System.out.println(ss.maze[8][79]+ "    " + ss.maze[1][75]);
        boolean res = ss.solve(1 ,75);
        System.out.println(res);
        /*ss.printMatrix();
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                System.out.print(ss.maze[i][j]);
            }
            System.out.println();
        }*/
        Position temp = new Position();
        if (!ss.stack.isEmpty()) {
            temp = ss.stack.pop();
            System.out.print("("+temp.row+", "+temp.col+")");
            while (!ss.stack.isEmpty()) {
                temp = ss.stack.pop();
                System.out.print(" --> ("+temp.row+", "+temp.col+")");
            }
        }
    }
}
