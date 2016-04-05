package Algorithms.DeutschJozsa;

import Core.Algorithm;
import Core.AlgorithmOutput;
import Core.Exceptions.BitOutOfBoundsException;
import Core.Exceptions.DataValueException;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;
import Core.QRegister;
import Operators.Hadamard.HadamardBitOperator;

/**
 * Simulation of the Deutsch-Jozsa algorithm, for any number of qubits. The
 * oracle is in an independent class and can thus be changed to any possible
 * function.
 */
public class DeutschJozsaAlgorithm implements Algorithm {

    /**
     * The register containing the control qubits and the target qubit. The
     * first qubit (with index 0) is the target qubit.
     */
    QRegister register;
    
    /**
     * The size of the register, n-1 control qubits plus 1 target qubit.
     */
    int n;
    
    /**
     * The Uf (oracle) operator.
     */
    DeutschJozsaOracle oracle;

    /**
     * Constructor for the Algorithm, needs a specific oracle and size of
     * quantum register. The oracle should be for the same number as qubits as
     * the algorithm, i.e. n.
     *
     * @param n the number of control qubits plus target qubit.
     * @param oracle The oracle, Uf
     */
    public DeutschJozsaAlgorithm(int n, DeutschJozsaOracle oracle) {
        this.n = n;
        this.oracle = oracle;
    }

    /**
     * New register in state |000...0001> is created.
     */
    private void initializeRegister(int n) {
        try {
            register = new QRegister(n, 1);
        } catch (DataValueException ex) {
            System.out.println("Error initialzing");
        }
    }

    /**
     * Main method, runs the algorithm.
     *
     * @return Returns the result, i.e. whether the function in the oracle is
     * constant or balanced.
     */
    @Override
    public AlgorithmOutput run() {
        //Initializes the register, so the algorithm can be run again.
        this.initializeRegister(n);

        HadamardBitOperator h;

        //Applying the first set of Hadamard gates to all qubits
        for (int i = 0; i < n; i++) {
            h = new HadamardBitOperator(i);
            try {
                register = h.apply(this.register);
            } catch (BitOutOfBoundsException ex) {
                System.out.println("Error applying the first set of Hadamard gates!");
            }
        }

        //Appling the oracle function Uf
        try {
            register = oracle.apply(this.register);
        } catch (BitOutOfBoundsException ex) {
            System.out.println("Error applying the oracle-function!");
        }

        //Applying the second set of qubits to all but the control qubit (the first qubit)
        for (int i = 1; i < n; i++) {
            try {
                h = new HadamardBitOperator(i);
                register = h.apply(this.register);
            } catch (BitOutOfBoundsException ex) {
                System.out.println("Error applying the second set of Hadamard gates");
            }
        }

        /*
         * The register (all qubits apart from the control qubit, the first one)
         * is measured. 0 means the function is constant, any other number means
         * the function is balanced
         */
        int result = 0;
        try {
            result = register.measure(1, register.getBitCount() - 1);
        } catch (NormalizationException ex) {
            System.out.println("Error measuring!");
        } catch (VectorLengthException ex) {
            System.out.println("Error measuring!");
        }

        //The result is returned using an DeutschJoszOutput object.
        return new DeutschJozsaOutput(result);

    }
}