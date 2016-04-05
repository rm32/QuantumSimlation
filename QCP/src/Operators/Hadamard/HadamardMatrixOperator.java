package Operators.Hadamard;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.Math.DenseMatrix;
import Core.Math.Matrix;
import Core.QRegister;

/**
 * Matrix multiplication implementation of Hadamard gate.
 * 
 */
public class HadamardMatrixOperator extends HadamardOperator {

    /**
     * Matrix implementation of Hadamard gate.
     */
    private Matrix hadamard = new DenseMatrix(2, new Complex[]{new Complex(1.0f / (float) Math.sqrt(2)), new Complex(1.0f / (float) Math.sqrt(2)),
                new Complex(1.0f / (float) Math.sqrt(2)), new Complex(-1.0f / (float) Math.sqrt(2))});

    /**
     * Constructor for a specific qubit index
     * @param Bit index of target qubit
     * @throws BitOutOfBoundsException
     */
    public HadamardMatrixOperator(int Bit) throws BitOutOfBoundsException {
        super(Bit);

        if (Bit > 0) {
            Matrix identity = Matrix.identityMatrix(1 << Bit);
            hadamard = hadamard.tensorProduct(identity);
        }
    }

    /**
     * Applies Hadamard gate to a quantum register
     * @param Register
     * @return
     * @throws BitOutOfBoundsException
     */
    public QRegister apply(QRegister Register) throws BitOutOfBoundsException {
        //Check for size constraint
        if (Register.getBitCount() <= this.bit && this.bit >= 0) {
            throw new BitOutOfBoundsException();
        }

        //Generate Hadamard transform for specific register
        Matrix transform = Matrix.identityMatrix(1 << (Register.getBitCount() - 1 - bit));
        transform = transform.tensorProduct(hadamard);

        ComplexVector initial = Register.getComplexVector();
        ComplexVector termin = transform.multiply(initial);

        return new QRegister(termin);
    }
}
