/**
 * Created by shaowenyuan on 2018/4/22.
 */
public class test {

    public static double        discount = 0.9;
    public static double        reward = -0.01;
    public static double[]      noise = {0.65, 0.15, 0.15, 0.05};

    public static void main(String[] args) {

        MDP mdp = new MDP(discount, noise, reward);
        double[][] temp = mdp.solve();

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                if (temp[i][j] == Integer.MIN_VALUE)
                    System.out.print("-"+" ");
                else
                    System.out.print(temp[i][j] + " ");
            }
            System.out.println();
        }
    }
}
