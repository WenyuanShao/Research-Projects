import java.io.*;

/**
 * Created by shaowenyuan on 25/01/2018.
 */
public class ReadMaze {

    public static String[][] maze;

    public ReadMaze() {
        String filename = "/Users/shaowenyuan/Desktop/maze.txt";
        maze = read(filename);
    }

    public static int[][] getMaze() {
        int [][] res = new int[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                res[i][j] = Integer.parseInt(maze[i][j]);
            }
        }
        return res;
    }

    /*public static void main (String[] args) {
        String filename = "/Users/shaowenyuan/Desktop/maze.txt";
        String[][] maze = read(filename);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }*/

    public static String[][] read (String filename) {
        File file = new File(filename);
        String[][] maze = new String[81][81];

        //String encoding = "UTF-8";
        if (file.isFile()) {
            BufferedReader bufferedReader = null;
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();

                int index = 0;

                while (line != null) {
                    maze[index] = line.split(" ");
                    //System.out.println(line);
                    line = bufferedReader.readLine();
                    index++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileReader.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return maze;
    }
}
