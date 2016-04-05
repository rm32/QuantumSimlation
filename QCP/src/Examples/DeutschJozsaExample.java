package Examples;

import Algorithms.DeutschJozsa.DeutschJozsaAlgorithm;
import Algorithms.DeutschJozsa.DeutschJozsaOracle;
import Core.Exceptions.BitOutOfBoundsException;
import Core.Exceptions.DataValueException;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;

/**
 * This is a simple class to demonstrate the Deutsch-Jozsa algorithm.
 */
public class DeutschJozsaExample {

    public static void main(String[] args) throws VectorLengthException, BitOutOfBoundsException, NormalizationException, DataValueException {

        /*
         * Test No 1
         */
        
        //Number of qubits: n-1 control qubits, one 1 target qubit
        int n = 10;
        //Creating a "black box" oracle of size n, the function is constant.
        DeutschJozsaOracle oracle = new DeutschJozsaOracle(n, true);
        //Creating the algorithm class, using size n and the created oracle.
        DeutschJozsaAlgorithm alg = new DeutschJozsaAlgorithm(n, oracle);
        //The algorithm is run and the result printed.
        System.out.println(alg.run().toString());
        
        /*
         * Test No 2
         */
        
        //Number of qubits: n-1 control qubits, one 1 target qubit
        n = 6;
        //Creating a "black box" oracle of size n, the function is balanced.
        oracle = new DeutschJozsaOracle(n, false);
        //Creating the algorithm class, using size n and the created oracle.
        alg = new DeutschJozsaAlgorithm(n, oracle);
        //The algorithm is run and the result printed.
        System.out.println(alg.run().toString());
    }
    
}
