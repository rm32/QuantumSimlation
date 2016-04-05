package Examples;

import Algorithms.Grover.GroverOptAlgorithm;
import Algorithms.Grover.GroverOracle;
import Algorithms.Grover.GroverOutput;

/**
 * This is a simple class to demonstrate Grover's algorithm.
 */
public class GroverExample {

    public static void main(String[] args) throws Throwable {
        //Construct answer and GroverOracle
        int bits = 12;
        int answer = (int) (Math.random() * ((1 << bits) - 1));

        System.out.println("Looking for: " + answer);

        GroverOracle oracle = new GroverOracle(bits, answer);

        //Let GroverAlgorithm run
        GroverOptAlgorithm algorithm = new GroverOptAlgorithm(oracle);
        GroverOutput output = algorithm.run();

        //Read final answer
        System.out.println("Collapsed to: " + output.toString());
    }
}
