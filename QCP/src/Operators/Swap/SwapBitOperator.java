package Operators.Swap;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.ComplexVector;
import Core.QRegister;

/**
 * The SwapOperator implemented using bit manipulation.
 */
public class SwapBitOperator extends SwapOperator {

    /**
     * The two bit locations shifted in the right classical location.
     */
    int tb1;
    int tb2;

    /**
     * Constructs a SwapOperator as a bit manipulation implementation.
     *
     * @param Target1 First qubit
     * @param Target2 Second qubit
     */
    public SwapBitOperator(int Target1, int Target2) throws BitOutOfBoundsException {
        //Construct super class
        super(Target1, Target2);

        //Initialize shifted locations
        this.tb1 = 1 << Target1;
        this.tb2 = 1 << Target2;
    }

    /**
     * Applies the SwapOperator to a QRegister via a bit manipulation
     * implementation. The swap is performed by looping through all bases and
     * swapping those that are different from each other.
     *
     * <br>For example, <br>|00> -> |00> <br>|01> -> |10> <br>|10> -> |01>
     * <br>|11> -> |11>
     *
     * @param Register Register to be applied to.
     * @return Transformed register.
     * @throws BitOutOfBoundsException
     */
    public QRegister apply(QRegister Register) throws BitOutOfBoundsException {
        //Size check
        if (Register.getBitCount() <= this.target1 || Register.getBitCount() <= this.target2) {
            throw new BitOutOfBoundsException();
        }

        //Transform
        ComplexVector initial = Register.getComplexVector();
        ComplexVector termin = new ComplexVector(Register.getBaseCount());

        for (int base = 0; base < Register.getBaseCount(); base++) {
            //Filter componenents
            int f1 = base & this.tb1;
            int f2 = base & this.tb2;

            //Compare components
            if (f1 == 0 && f2 > 0) {
                termin.setComponent(initial.getComponent(base), base ^ (tb1 | tb2));
            } else if (f1 > 0 && f2 == 0) {
                termin.setComponent(initial.getComponent(base), base ^ (tb1 | tb2));
            } else {
                termin.setComponent(initial.getComponent(base), base);
            }
        }

        return new QRegister(Register, termin);
    }
}
