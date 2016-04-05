package Algorithms.Shor;

import Core.AlgorithmOutput;

/**
 * Class to produce formatted output of Shor's Algorithm
 */
public class ShorOutput implements AlgorithmOutput {

    /**
     * The resulting factors
     */
    private int fact1, fact2;

    /**
     * Constructor for output of Shor's Algorithm
     *
     * @param factor1 The first Factor
     * @param factor2 The second Factor
     */
    public ShorOutput(int factor1, int factor2) {
        fact1 = factor1;
        fact2 = factor2;

    }

    /**
     *
     * @return String containing the number factored and it's factors
     */
    public String toString() {
        return "The prime factors of " + (fact1 * fact2) + " are " + fact1 + " and " + fact2;
    }

    /**
     * Returns factors in an int Array
     *
     * @return int array containing the two factors
     */
    public int[] getFactors() {
        int[] out = new int[2];
        out[0] = fact1;
        out[1] = fact2;
        return out;
    }
}
