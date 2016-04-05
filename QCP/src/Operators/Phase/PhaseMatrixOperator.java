package Operators.Phase;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.Math.DenseMatrix;
import Core.Math.Matrix;
import Core.QRegister;

/**
 * A PhaseMatrixOperator is a matrix implementation of PhaseOperator.
 */
public class PhaseMatrixOperator extends PhaseOperator {

    /**
     * The two-by-two phase shift matrix
     */
    private DenseMatrix matrix;

    /**
     * Constructs a PhaseOperator as with a Matrix implementation.
     *
     * @param Bit Bit location in given Register to apply to.
     * @param Shift Phase shift to be applied.
     */
    public PhaseMatrixOperator(int Bit, double Shift) {
        //Initialize PhaseOperator with bit location and phase shift
        super(Bit);

        //Generate matrix representation of operator for specific phase shift
        this.matrix = new DenseMatrix(2);
        this.matrix.setElement(0, 0, new Complex(1.0, 0.0));
        this.matrix.setElement(1, 1, new Complex(Math.cos(Shift), Math.sin(Shift)));
    }

    /**
     * Applies the PhaseOperator to the given Register with a matrix
     * multiplication.
     *
     * @param Register The QRegister to which the Operator will be applied.
     * @return Returns the transformed QRegister.
     */
    public QRegister apply(QRegister Register) throws BitOutOfBoundsException {
        //Check for size constraint
        if (Register.getBitCount() <= this.bit) {
            throw new BitOutOfBoundsException();
        }

        //Generate transform for given Register size.
        //TODO: Might be faster to do the end cases outside the loop and then not have to check for null...
        Matrix transform = null;

        for (int bitIdx = Register.getBitCount() - 1; bitIdx >= 0; bitIdx--) {
            if (bitIdx == this.bit) {
                //Apply shift for given bitIdx
                if (transform == null) {
                    //bitIdx == bit as MSB
                    transform = this.matrix;
                } else {
                    transform = transform.tensorProduct(this.matrix);
                }
            } else {
                //Apply identity for given bitIdx
                if (transform == null) {
                    //TODO: SparseMatricies
                    transform = DenseMatrix.identityMatrix(2);
                } else {
                    //TODO: SparseMatricies
                    transform = transform.tensorProduct(DenseMatrix.identityMatrix(2));
                }
            }
        }

        //Apply generated matrix to register
        ComplexVector amplitudes = Register.getComplexVector();

        ComplexVector transformed = transform.multiply(amplitudes);

        //Return new QRegister
        return new QRegister(Register, transformed);
    }
}
