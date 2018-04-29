package GridWorld;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by shaowenyuan on 2018/4/22.
 */
public class getVal {

    private String filename = "/Users/shaowenyuan/Desktop/gridA.csv";
    public String[][] grid;

    public getVal() {

    }

    public String[][] getGrid() {
        get();
        return this.grid;
    }

    public void get() {

        int n = 0;

        try {
            Scanner scanner = new Scanner(new FileInputStream(filename));
            ArrayList<String[]> temp = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] table = line.split(",");

                n = table.length;
                temp.add(table);
            }
            grid = new String[temp.size()][n];
            for (int i = 0; i < temp.size(); i++) {
                grid[i] = temp.get(i);
            }

            /*for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    System.out.print(grid[i][j]+" ");
                }
                System.out.println();
            }
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
