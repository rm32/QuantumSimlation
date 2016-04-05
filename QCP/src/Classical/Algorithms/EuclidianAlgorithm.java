package Classical.Algorithms;

/**
 * Class that finds the Greatest common divisor of two numbers using Euclidian
 * Algorithm
 */
public class EuclidianAlgorithm {

    /**
     * Numbers used to run algorithm
     */
    int large, small;

    /**
     * Constructor for Algorithm
     *
     * @param larger number in the expression gcd(x,y)
     * @param smaller number in the expression gcd(x,y)
     */
    public EuclidianAlgorithm(int larger, int smaller) {
        large = larger;
        small = smaller;
    }

    /**
     * Method to run Algorithm
     *
     * @return Greatest Common divisor of the two numbers
     */
    public int runAlgorithm() {
        //int i =0;

        //Running through EuclidsAlgorithm
        while (large != small) {

            if (large > small) {
                large = large - small;
            }

            if (large < small) {
                small = small - large;
            }
            // i=i+1;
            //Added in a failsafe so if in shors are unlucky and get 0 can break out rather than iterating for ever
            /*
             * if(i>1000){ System.out.println("Euclid Failed"); large =1; small
             * =1; break; }
             *
             */
        }
        return large;
    }
}
