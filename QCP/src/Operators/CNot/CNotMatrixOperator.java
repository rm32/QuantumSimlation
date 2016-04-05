package Operators.CNot;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Math.Complex;
import Core.Math.DenseMatrix;
import Core.Math.Matrix;
import Core.QRegister;

/**
 * Matrix multiplication implementation of the CNot gate. This implementation is
 * very inefficient but not too complicated. The inefficiency is mainly because
 * of the swapping, if target and control qubits are non-adjacent. The swap
 * 4-by-4 matrix is only used within this method, and not in a separate operator
 * class, as matrix multiplication swapping is fairly inefficient and is just
 * used in this example class.
 */
public class CNotMatrixOperator extends CNotOperator {

    /**
     * The standard 4-by-4 swap Matrix
     */
    private Matrix swap;
    /**
     * Size of quantum register that the C-NOT gate is applied to. Gets updated
     * for every new register.
     */
    private int n;

    /*
     * The standard 4-by-4 CNOT matrix.
     */
    private Matrix cnot;
    
    /**
     * Constructor of the C-NOT gate, for a specific control and target qubit
     *
     * @param control Index of control qubit
     * @param target Index of target qubit
     */
    public CNotMatrixOperator(int control, int target) {
        super(control, target);
        //Exception if target and control qubit are the same
        if (control == target) {
            try {
                throw new Exception();
            } catch (Exception ex) {
            }
        }
        //Creating CNOT matrix
        cnot = (Matrix) DenseMatrix.identityMatrix(4);
        cnot.setElement(2, 2, new Complex(0, 0));
        cnot.setElement(3, 3, new Complex(0, 0));
        cnot.setElement(2, 3, new Complex(1, 0));
        cnot.setElement(3, 2, new Complex(1, 0));
        //Creating SWAP matrix
        swap = (Matrix) DenseMatrix.identityMatrix(4);
        swap.setElement(1, 1, new Complex(0, 0));
        swap.setElement(1, 2, new Complex(1, 0));
        swap.setElement(2, 1, new Complex(1, 0));
        swap.setElement(2, 2, new Complex(0, 0));
        this.control = control;
        this.target = target;
    }

    /**
     * This method applies the C-NOT gate to a quantum register
     *
     * @param register The quantum register that the gate is applied to
     */
    public QRegister apply(QRegister register) {
        this.n = register.getBitCount();
        //Checking that control and target qubit are in range of register
        if (control >= n || target >= n) {
            try {
                throw new BitOutOfBoundsException("Control or target bit out of range");
            } catch (BitOutOfBoundsException ex) {
            }
        }
        Matrix operatorMatrix;
        //No swapping is needed, the CNOT gate is directly applied
        if (control - target == 1) {
            operatorMatrix = applyCNOT();
        } /*
         * The qubits need to be swapped first. Three matrices: A*B*C C:
         * moveTogether(), making the control and target qubits adjacent B:
         * applyCNOT(), applying the CNOT gate on adjacent control and qubit
         * gate A: moveApart(), moving control and/or target qubit to their old
         * positon
         */ else {
            operatorMatrix = moveApart().multiply(applyCNOT().multiply(moveTogether()));
        }
        //The quantum register is multiplied by the final matrix D = A*B*C
        return new QRegister(operatorMatrix.multiply(register.getComplexVector()));

    }

    /**
     * Applies the C-NOT gate after the qubits have been moved so that they are
     * adjacent.
     *
     * @return
     */
    private Matrix applyCNOT() {
        int x;
        if (control < target) {
            x = target;
        } else {
            x = control;
        }
        Matrix upper = DenseMatrix.identityMatrix(1 << (n - 1 - x));
        Matrix lower = DenseMatrix.identityMatrix(1 << (x - 1));
        return (upper.tensorProduct(cnot)).tensorProduct(lower);
    }

    /**
     * Moves the qubits so they are adjacent.
     *
     * @return The register with adjacent control and target qubits
     */
    private Matrix moveTogether() {
        Matrix upper;
        Matrix lower;
        Matrix swapOne;
        Matrix shiftMatrix = DenseMatrix.identityMatrix(1 << n);
        int j;
        int k;
        if (control < target) {
            j = control;
            k = target - 1;
        } else {
            j = target;
            k = control - 2;
        }

        for (int i = j; i <= k; i++) {
            //System.out.println(i);
            upper = DenseMatrix.identityMatrix(1 << (n - 2 - i));
            lower = DenseMatrix.identityMatrix(1 << i);
            swapOne = ((upper).tensorProduct(swap)).tensorProduct(lower);
            shiftMatrix = swapOne.multiply(shiftMatrix);
        }

        return shiftMatrix;
    }

    /**
     * Moves the qubits back to their original position after the C-NOT gate was
     * applied.
     *
     * @return The register in the previous order
     */
    private Matrix moveApart() {
        Matrix upper;
        Matrix lower;
        Matrix swapOne;
        Matrix shiftMatrix = DenseMatrix.identityMatrix((int) Math.pow(2, n));
        int j;
        int k;
        if (control < target) {
            j = target - 1;
            k = control;
        } else {
            j = control - 2;
            k = target;
        }
        for (int i = j; i >= k; i--) {
            //System.out.println(i);
            upper = DenseMatrix.identityMatrix(1 << n - 2 - i);
            lower = DenseMatrix.identityMatrix(1 << i);
            swapOne = ((upper).tensorProduct(swap)).tensorProduct(lower);
            shiftMatrix = swapOne.multiply(shiftMatrix);
        }

        return shiftMatrix;
    }
}
