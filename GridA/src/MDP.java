import java.util.DoubleSummaryStatistics;

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
    private int[][]         direction = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

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

    public double[][] solve () {

        double[][] check = new double[row][col];
        double[][] res = new double[row][col];

        int count = 0;
        copy(grid, check);
        copy(check, res);
        while (count < 200) {
        //while (!isSame(check, res) || count == 0) {
            copy(res, check);
            North = DP(0, check);
            East  = DP(1, check);
            South = DP(2, check);
            West  = DP(3, check);

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    double temp1 = Math.max(North[i][j], East[i][j]);
                    double temp2 = Math.max(West[i][j], South[i][j]);
                    res[i][j] = Math.max(temp1,temp2);
                }
            }
            count++;
        }
        System.out.println("count: " + count);
        return res;
    }

    public double[][] DP (int dir, double[][] input) {
        double main_val;
        double noise_val1;
        double noise_val2;
        double noise_val3;

        double[][] temp  = new double[row][col];
        copy(input,temp);
        //copy(grid, temp);
        //double[][] check = new double[row][col];
        //while (!isSame(check, temp)) {
            //copy(temp,check);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (!grid_string[i][j].equals("0")) {
                        continue;
                    }

                    //get main direction val;
                    main_val = getNext(dir, i, j, temp);

                    //get two noise direction val;
                    noise_val1 = getNext((dir+1) % 4, i, j, temp);

                    noise_val2 = getNext((dir+3) % 4, i, j, temp);

                    noise_val3 = getNext((dir+2) % 4, i, j, temp);

                    //result
                    temp[i][j] = (noise[0] * main_val + noise[1] * noise_val1 + noise[2] * noise_val2 + noise[3] * noise_val3) * discount + reward;

                }
            }
            //count++;
        //}
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
}
