package Operators.CNot;

import Core.Exceptions.BitOutOfBoundsException;
import Core.QRegister;
import Operators.CV.CVBitOperator;
import Operators.CV.CVOperator;
import Operators.Hadamard.HadamardBitOperator;
import Operators.Hadamard.HadamardOperator;

/**
 * A CNotOperator implementation using a universal set.
 */
public class CNotCompositeOperator extends CNotOperator {

    /**
     * Buffered Hadamard operator
     */
    private HadamardOperator hadamard;
    /**
     * Buffered controlled-V operator
     */
    private CVOperator v;

    /**
     * Constructs a CNotCompositeOperator with one control and one target bit.
     * 
     * Buffers all needed operators.
     * 
     * @param Control
     * @param Target
     */
    public CNotCompositeOperator(int Control, int Target) throws BitOutOfBoundsException {
        super(Control, Target);

        //Construct both operators required to form CNotOperator
        hadamard = new HadamardBitOperator(Target);
        v = new CVBitOperator(Control, Target);
    }

    /**
     * Applies the operator to the given register by applying buffered operators
     * in sequence.
     * 
     * @param Register
     * @return
     * @throws BitOutOfBoundsException
     */
    public QRegister apply(QRegister Register) throws Throwable {
        //Apply first Hadamard operator
        QRegister temp = hadamard.apply(Register);

        //Apply cV operator twice
        temp = v.apply(temp);
        temp = v.apply(temp);

        //Apply final hadamard
        return hadamard.apply(temp);
    }
}
