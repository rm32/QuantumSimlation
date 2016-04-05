package Operators.CCNot;

import Core.Exceptions.BitOutOfBoundsException;
import Core.QRegister;
import Operators.CNot.CNotCompositeOperator;
import Operators.CNot.CNotOperator;
import Operators.CV.CVBitOperator;
import Operators.CV.CVOperator;
import Operators.Hadamard.HadamardBitOperator;
import Operators.Hadamard.HadamardOperator;

/**
 * This class implements the CCNotOperator as a composite of a universal set of
 * operators.
 */
public class CCNotCompositeOperator extends CCNotOperator {

    /**
     * Buffered hadamard operator
     */
    HadamardOperator hadamard;
    /**
     * Buffered controlled-V operator 1
     */
    CVOperator cv;
    /**
     * Buffered controlled-V operator 2
     */
    CVOperator cvs;
    /**
     * Buffered CNotCompositeOperator
     */
    CNotOperator cnot;

    /**
     * Constructs a CCNotCompositeOperator with two control bits and one target bit.
     *
     * @param C1Bit
     * @param C2Bit
     * @param Target
     */
    public CCNotCompositeOperator(int C1Bit, int C2Bit, int Target) throws BitOutOfBoundsException {
        super(C1Bit, C2Bit, Target);

        //Construct other composite gates or universal gates
        hadamard = new HadamardBitOperator(Target);
        cv = new CVBitOperator(C2Bit, Target);
        cvs = new CVBitOperator(C1Bit, Target);
        cnot = new CNotCompositeOperator(C1Bit, C2Bit);
    }

    /**
     * Applies the CCNotOperator to the given register.
     * 
     * Uses a composite of hadamard, c-V and CNot operators.
     * 
     * @param Register
     * @return
     * @throws BitOutOfBoundsException
     */
    public QRegister apply(QRegister Register) throws Throwable {
        //Apply initial hadamard
        QRegister temp = hadamard.apply(Register);

        //Apply first cV gate
        temp = cv.apply(temp);

        //Apply first cnot to controls
        temp = cnot.apply(temp);

        //Apply V-dagger (3 sequential cV gates)
        temp = cv.apply(temp);
        temp = cv.apply(temp);
        temp = cv.apply(temp);

        //Apply second cnot to controls
        temp = cnot.apply(temp);

        //Apply last cV
        temp = cvs.apply(temp);

        //Apply final hadamard
        return hadamard.apply(temp);
    }
}
