package Core.Math;

import Core.Exceptions.NegativeIndexException;

/**
 * Class that raises a integer to a positive integer
 */
public class Power {

    /**
     * Static method to calculate base^exp
     *
     * @param base number being raised to a power
     * @param exp power number being raised by
     * @return number raised to the power
     * @throws NegativeIndexException Does this as method cannot calculate
     * numbers raised to negative powers
     */
    public static int pow(int base, int exp) throws NegativeIndexException {
        int out = 1;
        if (exp < 0) {
            throw new NegativeIndexException();
        }

        if (exp == 0) {
            out = 1;
        } else {
            int i = exp;
            while (i > 0) {
                out = out * base;
                i = i - 1;
            }
        }
        return out;
    }
}
