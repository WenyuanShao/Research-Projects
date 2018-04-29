package GridWorld;

/**
 * Created by shaowenyuan on 2018/4/22.
 */
public class test {

    public static double        discount = 0.9;
    public static double        reward = -0.01;
    public static double[]      noise = {0.65, 0.15, 0.05, 0.15};

    public static void main(String[] args) {

        MDP mdp = new MDP(discount, noise, reward);
        System.out.println("Constants: ");
        System.out.println("discount: " + mdp.discount +"    noise: {"+ mdp.noise[0] + ", " + mdp.noise[1] +", " + mdp.noise[2] +", "+
        mdp.noise[3] +"}      reward: " + mdp.reward);
        double[][] temp = mdp.MDPIteration();
        mdp.printState(temp);
    }
}
