package Algorithms.Grover;

import Core.Algorithm;
import Core.AlgorithmOutput;
import Core.Exceptions.BitOutOfBoundsException;
import Core.Exceptions.DataValueException;
import Core.Exceptions.VectorLengthException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.QRegister;
import Operators.Hadamard.HadamardBitOperator;
import Operators.Hadamard.HadamardOperator;

/**
 * Class Implementing Grover Algorithm
 */
public abstract class GroverAlgorithm implements Algorithm {

    /**
     * The Oracle that was given at the initialization.
     */
    GroverOracle oracle;

    /**
     * Initiates the class taking an Oracle as its constructor.
     *
     * @param Oracle Black box that knows if f(x) = 1
     */
    public GroverAlgorithm(GroverOracle Oracle) {
        oracle = Oracle;
    }

    /**
     * Constructs a superposition QRegister of the required size from the
     * Oracle.
     *
     * This implementation uses this libraries native HadamardOperator to
     * construct the QRegister.
     *
     * @return Grovers initial step QRegister.
     */
    protected QRegister getSuperpositionHadamard() {
        try {
            QRegister register = new QRegister(oracle.getBitCount(), 0);

            //Apply hadamard to every bit
            for (int bitIdx = 0; bitIdx < oracle.getBitCount(); bitIdx++) {
                HadamardOperator operator = new HadamardBitOperator(bitIdx);

                register = operator.apply(register);
            }

            return register;
        } catch (BitOutOfBoundsException E) {
            //Do nothing, never happens... bit count never exceeded.
            //This is not possible since it is coming from the Oracle.
        } catch (DataValueException E) {
            //Zero is always representable
        } catch (Throwable E) {
            //Shouldnto happen
        }

        return null;
    }

    /**
     * Constructs a superposition QRegister of the required size from the
     * Oracle.
     *
     * This implementation is quick because we already know what the QRegister
     * needs to be when we want a superposition of all bits.
     *
     * @return Grovers initial step QRegister.
     */
    protected QRegister getSuperpositionFast() {
        //Initial vector of (1,...,1)
        Complex[] vector = new Complex[oracle.getBaseCount()];

        for (int i = 0; i < oracle.getBaseCount(); i++) {
            vector[i] = new Complex(1.0f, 0.0f);
        }

        //Multiply by inverse of sqrt to normalize
        ComplexVector v = new ComplexVector(vector);
        v = v.scalarMultiply(new Complex(1.0f / (float) Math.sqrt(oracle.getBaseCount()), 0.0f));
        try {
            v.normalize();
        } catch (VectorLengthException E) {
            //This should never happen
            //Doesnot really matter whether its normalized.
        }

        QRegister reg = new QRegister(v);
        return reg;
    }

    /**
     *
     * @return the output of Grover Algorithm
     */
    public abstract AlgorithmOutput run();
}
