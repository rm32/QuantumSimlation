package Classical.Algorithms;

import java.math.BigInteger;

/**
 * Euclidian Algorithm For BigIntegers
 */
public class BigEuclidianAlgorithm {

    /**
     * Numbers used to run algorithm
     */
    private BigInteger larger, smaller;

    /**
     * Constructor for the algorithm
     *
     * @param larger Number in the expression gcd(x,y);
     * @param smaller number in the expression gcd(x,y)
     */
    public BigEuclidianAlgorithm(BigInteger larger, BigInteger smaller) {
        this.larger = larger;
        this.smaller = smaller;
    }

    /**
     * Method that runs the Algorithm
     *
     * @return Greatest common divisor of the two numbers
     */
    public BigInteger runAlgorithm() {
        while (!larger.equals(smaller)) {
            BigInteger test = larger.subtract(smaller);
            if (test.signum() > 0) {
                larger = test;
            }

            if (test.signum() < 0) {
                smaller = smaller.subtract(larger);
            }
        }
        return larger;
    }
}
