package Algorithms.Grover;

import Core.Exceptions.DataValueException;
import Core.QRegister;

/**
 * Class implementing the oracle for grover.
 */
public class GroverOracle {

    /**
     * The integer index that is considered the answer.
     */
    int answer;
    /**
     * The number of bits required to represent the answer.
     *
     * This is used for the size of the QRegister, instead of a pure 32 bit java
     * integer. (That would take alot of memory!)
     */
    int bitcount;

    /**
     * Constructs and Oracle with just an answer.
     *
     * Computes the minimum size for the answer to be contained in QRegister.
     *
     * @param Answer The Answer index to recognize.
     */
    public GroverOracle(int Answer) {
        this.answer = Answer;

        //Calculate register size
        int highest = 0;

        //walk through bitwise to check for highest 1-bit.
        for (int idx = 0; idx < 32; idx++) {
            int dx = answer & (1 << idx);

            if (dx > 0) {
                highest = idx + 1;
            }
        }

        this.bitcount = highest;

        System.out.println("Bits: " + this.bitcount);
    }

    /**
     * Constructs an Oracle with a number of bits to use and an answer.
     *
     * @param Bits The bit count to use. Helpful to force it to use a greater
     * than required amount.
     * @param Answer The Answer index to recognize.
     * @throws DataValueException Thrown if the Answer cannot be represented in
     * that number of bits.
     */
    public GroverOracle(int Bits, int Answer) throws DataValueException {
        this.answer = Answer;
        this.bitcount = Bits;

        //Test that classical register could hold value.        
        if ((1 << Bits) < Answer) {
            throw new DataValueException(Bits, Answer);
        }
    }

    /**
     * Gets the bit count of the QRegister.
     *
     * @return The number of bits to represent the answer.
     */
    public int getBitCount() {
        return this.bitcount;
    }

    /**
     * Gets the base count of the QRegister
     *
     * @return The base count of the QRegister (2^bits).
     */
    public int getBaseCount() {
        return 1 << this.bitcount;
    }

    /**
     * This method returns the answer as a QRegister.
     *
     * This is only used for plotting not for Grover itself.
     *
     * @return The QRegister holding the answer as classical data.
     */
    public QRegister getAnswerRegister() {
        try {
            return new QRegister(this.bitcount, this.answer);
        } catch (DataValueException E) {
            System.out.println("This should never happen... AnswerVector problem");

            return null;
        }
    }

    /**
     * This is the main function of the Oracle. It recognizes whether the base
     * is the answer or not.
     *
     * @param Base The trial base.
     * @return True if the current base is our answer, otherwise returns false.
     */
    public boolean recognize(int Base) {
        // if the current base matches the required base
        if (Base == answer) {
            return true;
        } else {
            // current base does not match given base so oracle is false 
            return false;
        }
    }
}
