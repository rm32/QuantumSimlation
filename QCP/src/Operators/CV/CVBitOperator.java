package Operators.CV;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.QRegister;

/**
 * Bit manipulation implementation of CV gate.
 */
public class CVBitOperator extends CVOperator {

    /**
     * Constructs a bit manipulation implementation of a controlled-V operator.
     *
     * @param Control Control bit.
     * @param Target Target bit.
     */
    public CVBitOperator(int Control, int Target) throws BitOutOfBoundsException {
        super(Control, Target);
    }

    /**
     * Applies the controlled-V operator to the given register.
     *
     * @param Register Register to be transformed.
     * @return Transformed register.
     * @throws BitOutOfBoundsException
     */
    public QRegister apply(QRegister Register) throws BitOutOfBoundsException {
        //Check size constraint
        if (Register.getBitCount() <= this.control && this.control >= 0) {
            throw new BitOutOfBoundsException();
        }
        if (Register.getBitCount() <= this.target && this.target >= 0) {
            throw new BitOutOfBoundsException();
        }

        //Initialize registers
        ComplexVector initial = Register.getComplexVector();
        ComplexVector termin = new ComplexVector(Register.getBaseCount());

        //Iterate through basis
        for (int base = 0; base < Register.getBaseCount(); base++) {
            //Check if control bit is set
            int ac = base & (1 << this.control);

            if (ac > 0) {
                //Check if target is set
                int at = base & (1 << this.target);

                if (at > 0) {
                    //Apply the imaginary to target
                    termin.setComponent(initial.getComponent(base).multiply(new Complex(0.0f, 1.0f)), base);
                } else {
                    //Amplitude for base is unchanged
                    termin.setComponent(initial.getComponent(base), base);
                }
            } else {
                //Amplitude for base is unchanged
                termin.setComponent(initial.getComponent(base), base);
            }
        }

        //Return transformed register
        return new QRegister(Register, termin);
    }
}