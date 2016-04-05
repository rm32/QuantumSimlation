package Operators.Hadamard;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.QRegister;

/**
 * Class representing the Hadamard acting on a certain qubit using the bit
 * manipulation method
 */
public class HadamardBitOperator extends HadamardOperator {

    /**
     * Buffered binary representation of the shift
     */
    private int shift;

    /**
     * Constructor creating the Hadamard acting on a certain Qubit
     *
     * @param Bit Qubit the Hadamard gate is acting on
     */
    public HadamardBitOperator(int Bit) {
        super(Bit);

        //Getting the shifted value of used in the bit twiddling formula by shifiting 1  by bitacting on bits to the left
        this.shift = 1 << Bit;
    }

    /**
     * Method to act with the gate on a certain qubit in a Qregister
     *
     * @param Q Qregister being acted on
     * @return Qregister after it has been acted on by the gate
     */
    public QRegister apply(QRegister Register) throws BitOutOfBoundsException {
        //Check for size constraint
        if (Register.getBitCount() <= this.bit && this.bit >= 0) {
            throw new BitOutOfBoundsException();
        }

        //Declare registers
        ComplexVector initial = Register.getComplexVector();
        ComplexVector termin = new ComplexVector(Register.getBaseCount());

        //MOVE: Buffer Hadamard values
        Complex onesqrt = new Complex(1.0f / (float) Math.sqrt(2), 0.0f);
        Complex negsqrt = new Complex(-1.0f / (float) Math.sqrt(2), 0.0f);

        //Iterate basis
        for (int base = 0; base < Register.getBaseCount(); base++) {
            //Conjugate basis according to Hadamard
            int l = base ^ shift;

            //Set amplitudes according to Hadamard
            if (base < l) {
                termin.setComponent(termin.getComponent(base).add(initial.getComponent(base).multiply(onesqrt)), base);
                termin.setComponent(termin.getComponent(l).add(initial.getComponent(base).multiply(onesqrt)), l);
            } else //base >= l
            {
                termin.setComponent(termin.getComponent(base).add(initial.getComponent(base).multiply(negsqrt)), base);
                termin.setComponent(termin.getComponent(l).add(initial.getComponent(base).multiply(onesqrt)), l);
            }
        }

        //Return transformed register
        return new QRegister(termin);
    }
}
