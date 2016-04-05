package Operators.Phase;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.QRegister;

/**
 * A PhaseBitOperator is a bit manipulation implementation of PhaseOperator.
 */
public class PhaseBitOperator extends PhaseOperator {

    /**
     * The shift to apply.
     */
    private Complex shift;

    /**
     * Constructs a PhaseOperator as with a bit manipulation implementation.
     *
     * @param Bit Bit location in given Register to apply to.
     * @param Shift Phase shift to be applied.
     */
    public PhaseBitOperator(int Bit, double Shift) {
        //Initialize PhaseOperator with bit location and phase shift
        super(Bit);

        //Generate shift coefficent
        this.shift = new Complex(Math.cos(Shift), Math.sin(Shift));
    }

    /**
     * Applies the PhaseOperator to the given Register with a bit manipulation.
     *
     * @param Register The QRegister to which the Operator will be applied.
     * @return Returns the transformed QRegister.
     */
    public QRegister apply(QRegister Register) throws BitOutOfBoundsException {
        //Check for size constraint
        if (Register.getBitCount() <= this.bit) {
            throw new BitOutOfBoundsException();
        }

        //Generate new ComplexVector
        ComplexVector initial = Register.getComplexVector();
        ComplexVector termin = new ComplexVector(Register.getBaseCount());

        for (int base = 0; base < Register.getBaseCount(); base++) {
            int a = (1 << this.bit) & base;

            if (a > 0) {
                termin.setComponent(initial.getComponent(base).multiply(this.shift), base);
            } else {
                //Amplitude for base is unchanged
                termin.setComponent(initial.getComponent(base), base);
            }
        }

        //Return new QRegister
        return new QRegister(Register, termin);
    }
}
