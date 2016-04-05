package Algorithms.Grover;

import Core.AlgorithmOutput;

/**
 * Class gives Grover Output
 */
public class GroverOutput implements AlgorithmOutput {

    /**
     * The final answer by a GroverAlgorithm class.
     */
    int answer;

    /**
     *
     * @param Answer The answer measured.
     */
    GroverOutput(int Answer) {
        answer = Answer;
    }

    /**
     * Prints out the resultant QRegister
     *
     * @return Resultant QRegister printed out as a String
     */
    @Override
    public String toString() {
        return "" + answer;
    }
}