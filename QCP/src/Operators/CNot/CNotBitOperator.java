package Operators.CNot;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.QRegister;

/**
 * Bit manipulation implementation of CNot.
 *
 */
public class CNotBitOperator extends CNotOperator {

    /**
     * Construct gate from a control bit and target bit.
     * @param Control Control bit location.
     * @param Target Target bit location.
     * @throws BitOutOfBoundsException
     */
    public CNotBitOperator(int Control, int Target) throws BitOutOfBoundsException {
        super(Control, Target);

        if (control < 0 || target < 0) {
            throw new BitOutOfBoundsException();
        }
    }

    /**
     * Execute cnot on a given QRegister.
     * @param reg QRegister to apply gate to
     * @return a QRegister that is cnotted
     */
    public QRegister apply(QRegister reg) {
        Complex[] amps = reg.getComplexVector().getArray();
        Complex[] out = new Complex[amps.length];

        int controlShifted = 1 << control;
        int targetShifted = 1 << target;

        for (int j = 0; j < amps.length; j++) {
            //check value of control bit with AND
            if ((controlShifted & j) > 0) {
                //if control bit is 1, swap target bit with XOR
                int l = j ^ targetShifted;
                out[l] = amps[j];
            } else {
                //leave other bits unchanged
                out[j] = amps[j];
            }
        }

        return new QRegister(new ComplexVector(out));
    }
}
