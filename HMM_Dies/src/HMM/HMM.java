package HMM;

/**
 * Created by shaowenyuan on 2018/4/30.
 */
public class HMM {

    private double[] initalPick;
    private double[][] transitionProb;
    private double[][] emissionProb;

    public HMM() {
        this.initalPick     = new double[] {1.0 / 3, 1.0 / 3, 1.0 / 3};
        this.transitionProb = new double[][] {
                {1.0 / 2,   1.0 / 8,   1.0 / 8},
                {1.0 / 8,   1.0 / 2,   1.0 / 8},
                {1.0 / 8,   1.0 / 8,   1.0 / 2}
        };
        this.emissionProb   = new double[][] {
                {0.6,   0.2,   0.2},
                {0.2,   0.6,   0.2},
                {0.2,   0.2,   0.6}
        };
    }

    public int[] evaluate(String inputSeq) {
        if (inputSeq.equals("") || inputSeq.length() == 0) {
            System.out.println("No input!");
            return null;
        }
        String[] tempS = inputSeq.split(", ");
        int[] ObservationSeq  = new int[tempS.length];
        for (int i = 0; i < ObservationSeq.length; i++) {
            ObservationSeq[i] = Integer.parseInt(tempS[i]);
        }
        int[] dieSeq = new int[ObservationSeq.length];
        // all of the possible sequence in a specific place.
        int[][] possibleSeq = new int[ObservationSeq.length][transitionProb.length];
        double[][] proMatrix = new double[ObservationSeq.length][transitionProb.length];

        proMatrix[0][0] = emissionProb[0][ObservationSeq[0]-1] * initalPick[0];
        proMatrix[0][1] = emissionProb[1][ObservationSeq[0]-1] * initalPick[1];
        proMatrix[0][2] = emissionProb[2][ObservationSeq[0]-1] * initalPick[2];

        possibleSeq[0][0] = 0;
        possibleSeq[0][1] = 0;
        possibleSeq[0][2] = 0;

        for (int i = 1; i < ObservationSeq.length; i++) {
            // for each choice in the same row
            for (int j = 0; j < transitionProb.length; j++) {
                int die = -1;
                double maxProb = 0;
                for (int k = 0; k < transitionProb.length; k++) {
                    double temp = proMatrix[i-1][k] *
                            transitionProb[k][j] *
                            emissionProb[j][ObservationSeq[i]-1];
                    if (temp > maxProb) {
                        maxProb = temp;
                        die = k;
                    }
                }
                proMatrix[i][j]   = maxProb;
                possibleSeq[i][j] = die;
            }
        }
        //printSeq(possibleSeq);
        //printSeq(proMatrix);

        dieSeq[dieSeq.length-1] = getMax(proMatrix, proMatrix.length-1)+1;
        for (int i = dieSeq.length-2; i>= 0; i--) {
            dieSeq[i] = possibleSeq[i+1][dieSeq[i+1]-1]+1;
        }
        System.out.println("Most possible dies sequence: ");
        printSeq(dieSeq);
        //System.out.println(proMatrix[dieSeq.length-1][dieSeq[dieSeq.length-1]-1]);
        return null;
    }

    private void printSeq (int[][] Matrix) {
        for (int i = 0; i < Matrix.length; i++) {
            for (int j = 0; j < Matrix[0].length; j++) {
                System.out.printf("%10d", Matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("*********************************");
    }

    private void printSeq (double[][] Matrix) {
        for (int i = 0; i < Matrix.length; i++) {
            for (int j = 0; j < Matrix[0].length; j++) {
                System.out.printf("%10.6f", Matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("*********************************");
    }

    private int getMax (double[][] proMatrix, int row) {
        double[] temp = proMatrix[row];
        int col = 0;
        double MaxProb = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] > MaxProb) {
                MaxProb = temp[i];
                col = i;
            }
        }
        return  col;
    }

    private void printSeq(int[] input) {
        for (int i = 0; i < input.length; i++) {
            if (i != input.length -1) {
                System.out.print(input[i] + ", ");
            } else {
                System.out.print(input[i]);
            }
        }
        System.out.println();
    }
}

class test {
    public static void main (String[] args) {
        String[] input = {
                "1, 2, 3, 2, 1, 3, 2, 1, 3, 2, 3",
                "1, 2, 1, 1, 1, 2, 3, 3, 1, 2, 3, 3, 1, 3, 3",
                "3, 2, 3, 1, 1, 3",
                "3, 2, 3, 1, 1, 2, 3",
                "1, 2, 1, 1, 2, 1",
                "3, 3, 3, 1, 2, 3, 2, 2, 2, 2, 1"
        };
        HMM hmm = new HMM();
        for (int i = 0; i < input.length; i++) {
            System.out.println("Observation sequence: "+(i+1));
            System.out.println(input[i]);
            hmm.evaluate(input[i]);
            System.out.println("************************************************");
        }
    }
}
