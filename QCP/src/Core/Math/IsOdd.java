package Core.Math;

/**
 * Class to test if a number is odd
 */
public class IsOdd {

    /**
     * Static method testing if number is odd
     *
     * @param number Number being tested if it's odd
     * @return boolean true if it is odd false otherwise
     */
    public static boolean isOdd(int number) {
        boolean out = true;
        if ((number & 1) == 0) {
            out = false;
        }

        return out;
    }
}
