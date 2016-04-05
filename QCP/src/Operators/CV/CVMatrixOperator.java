package Operators.CV;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.Math.DenseMatrix;
import Core.Math.Matrix;
import Core.QRegister;
import Operators.Swap.SwapBitOperator;
import Operators.Swap.SwapOperator;

/**
 * Matrix multiplication implementation of CV gate.
 * 
 */
public class CVMatrixOperator extends CVOperator {

    /**
     * cv is the DenseMatrix representing the controlled-V operator in matrix
     * form.
     *
     * The control & target are not important either order works, because only
     * |11> is non-identity.
     */
    private static DenseMatrix cv = new DenseMatrix(4, new Complex[]{new Complex(1, 0), new Complex(), new Complex(), new Complex(),
                                                       new Complex(), new Complex(1, 0), new Complex(), new Complex(),
                                                       new Complex(), new Complex(), new Complex(1, 0), new Complex(),
                                                       new Complex(), new Complex(), new Complex(), new Complex(0, 1)});

    /**
     * Constructs a matrix representation of a CVOperator.
     * 
     * @param Control
     * @param Target
     */
    public CVMatrixOperator(int Control, int Target) throws BitOutOfBoundsException {
        super(Control, Target);
    }

    /**
     * Applies the CVOperator as a matrix multiplication to the register.
     * 
     * @param Register Register to be transformed.
     * @return Transformed register.
     * @throws BitOutOfBoundsException
     */
    public QRegister apply(QRegister Register) throws Throwable {
        //Check size
        if (Register.getBitCount() < this.control || Register.getBitCount() < this.target) {
            throw new BitOutOfBoundsException();
        }

        //Swap them around
        if (control != 0) {
            SwapOperator swapControl = new SwapBitOperator(0, control);

            Register = swapControl.apply(Register);
        }
        if (target != 1) {
            SwapOperator swapTarget = new SwapBitOperator(1, target);

            Register = swapTarget.apply(Register);
        }

        //Apply gate
        Matrix transform = Matrix.identityMatrix(1 << (Register.getBitCount() - 2));
        transform = transform.tensorProduct(cv);

        ComplexVector initial = Register.getComplexVector();
        ComplexVector termin = transform.multiply(initial);

        QRegister fin = new QRegister(termin);

        //Swap back
        if (control != 0) {
            SwapOperator swapControl = new SwapBitOperator(0, control);

            fin = swapControl.apply(fin);
        }
        if (target != 1) {
            SwapOperator swapTarget = new SwapBitOperator(1, target);

            fin = swapTarget.apply(fin);
        }

        //Return Register
        return fin;
    }
}
