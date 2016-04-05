package Operators.QFT;

import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.Math.DenseMatrix;
import Core.Math.Matrix;
import Core.Operator;
import Core.QRegister;
import Operators.CompositeOperator;

/**
 * Class that generates the Quantum Fourier transform in matrix format and has
 * methods to apply it to a QRegister, and other operators
 */
public class QFTMatrix implements Operator {

    private int MatrixSize;
    
    private DenseMatrix QFT;

    /**
     * Constructor for QFT Matrix
     *
     * @param numberofbits Number of qubits Quantum Fourier transform is being
     * generated for
     * @throws Exception
     */
    public QFTMatrix(int numberofbits) throws Exception {
        MatrixSize = (int) Math.pow(2, numberofbits);
        QFT = new DenseMatrix(MatrixSize);
        //Finding factor in front of QFT
        float Scalarfactor = (float) (1.0f / Math.sqrt(MatrixSize));
        //Calculating value of omega
        Complex omega = new Complex(2.0 * Math.PI / Math.pow(2, numberofbits));
        //Iterating through to fill up matrix
        for (int i = 0; i < MatrixSize; i++) {
            for (int j = 0; j < MatrixSize; j++) {
                QFT.setElement(i, j, omega.power(i * j));
            }
        }
        QFT = QFT.multiply(Scalarfactor);
    }

    /**
     * Method to apply gate to a QRegister
     *
     * @param Q QRegister gate is being applied to
     * @return QRegister after gate has been applied
     */
    public QRegister apply(QRegister Q) {
        ComplexVector out = Q.getComplexVector();
        out = QFT.multiply(out);
        return new QRegister(out);
    }

    /**
     * Method to apply gate to another gate/system of gates
     *
     * @param op Gate/network Quantum Fourier transform is being applied to
     * @return
     */
    public Operator apply(Operator op) {
        return new CompositeOperator(this, op);
    }

    /**
     * Method to obtain matrix representing the quantum Fourier transform
     *
     * @return Matrix representing quantum Fourier transform
     */
    public Matrix getMatrix() {
        return QFT;
    }
}
