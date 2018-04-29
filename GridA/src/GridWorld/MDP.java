package GridWorld;

/**
 * Created by shaowenyuan on 2018/4/22.
 */
public class MDP {

    public int              row;
    public int              col;
    public double           discount;
    public double[]         noise;
    public double           reward;
    public String[][]       grid_string;
    public double[][]       grid;
    public double[][]       North;
    public double[][]       South;
    public double[][]       West;
    public double[][]       East;
    private int[][]         direction = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public MDP (double discount, double[] noise, double reward) {
        getVal getVal = new getVal();

        grid_string     = getVal.getGrid();
        this.row        = grid_string.length;
        this.col        = grid_string[0].length;
        this.grid       = new double[row][col];
        this.discount   = discount;
        this.noise      = noise;
        this.reward     = reward;
        this.North      = new double[row][col];
        this.South      = new double[row][col];
        this.West       = new double[row][col];
        this.East       = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid_string[i][j].equals("-")) {
                    grid[i][j] = Integer.MIN_VALUE;
                } else {
                    grid[i][j] = Double.valueOf(grid_string[i][j]);
                }
            }
        }
    }

    public double[][] getNorth() {
        return North;
    }

    public double[][] getSouth() {
        return South;
    }

    public double[][] getWest() {
        return West;
    }

    public double[][] getEast() {
        return East;
    }

    public double[][] MDPIteration () {
        int iteration = 0;
        double [][] input = new double[row][col];
        double [][] pre   = new double[row][col];
        copy(grid, pre);
        copy(grid, input);
        while (!isSame(input, pre) || iteration == 0) {
            copy(input, pre);
            North = DP(0,input);
            //printState(North);
            East  = DP(1,input);
            //printState(East);
            West  = DP(2,input);
            //printState(West);
            South = DP(3,input);
            //printState(South);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    double temp1 = Math.max(North[i][j], East[i][j]);
                    double temp2 = Math.max(West[i][j], South[i][j]);

                    input[i][j] = Math.max(temp1, temp2);
                }
            }
            iteration++;
        }
        System.out.println("After " + iteration + " times of iteration.");
        return input;
    }

    public double[][] DP (int dir, double[][] input) {
        double main_val = 0;
        double[][] temp  = new double[row][col];
        copy(grid,temp);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!grid_string[i][j].equals("0")) {
                    continue;
                }
                for (int d = 0; d < 4; d++) {
                    main_val = getNext((dir+d) % 4, i, j, input);
                    temp[i][j] += noise[d] * (main_val * discount + reward);
                }
            }
        }
        return temp;
    }

    public void copy(double[][] a, double[][] b) {
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i].clone();
        }
    }

    public boolean isSame(double[][] a, double[][]b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    public double getNext(int dir, int r, int c, double[][] temp) {
        double res = 0;

        int temp_r = r + direction[dir][0];
        int temp_c = c + direction[dir][1];

        if (temp_c < 0 || temp_c >= col || temp_r < 0 || temp_r >= row || grid_string[temp_r][temp_c].equals("-")) {
            return temp[r][c];
        } else
            return temp[temp_r][temp_c];
    }

    public void printState (double[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state [i][j] == Integer.MIN_VALUE) {
                    System.out.printf("%10s", "-");
                } else {
                    System.out.printf("%10.2f", state[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("*************************************************************");
    }
}
