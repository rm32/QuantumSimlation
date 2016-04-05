package Algorithms.DeutschJozsa;

import Core.AlgorithmOutput;

/**
 * An object of this class contains the result of the Deutsch-Jozsa function
 * evaluation, stored as 0 or 1. It is returned by the DeutschJozsaAlgorithm
 * class after running the algorithm.
 */
public class DeutschJozsaOutput implements AlgorithmOutput {

    /**
     * The integer representing the result: 0: constant >0: balanced
     */
    private int result;

    /**
     * Constructor, needs the result, i.e. 0 or 1.
     *
     * @param result
     */
    public DeutschJozsaOutput(int result) {
        this.result = result;
    }

    /**
     * Returns the result explaining what it means.
     *
     * @return The result as a string.
     */
    @Override
    public String toString() {
        String output;
        if (result == 0) {
            output = "The function is constant! Measurement: " + result;
        } else {
            output = "The function is balanced! Measurement: " + result;
        }
        return output;

    }

    /**
     * Returns the result as an integer without explanation
     *
     * @return The result as a integer
     */
    public int getResult() {
        return result;
    }
}
