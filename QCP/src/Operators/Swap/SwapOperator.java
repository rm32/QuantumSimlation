package Operators.Swap;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Operator;
import Operators.CompositeOperator;

/**
 * The SwapOperator swaps two qubits in a register.
 */
public abstract class SwapOperator implements Operator {

    /**
     * The two qubits to be swapped.
     */
    int target1;
    int target2;

    /**
     * Abstract constructor to initialise basic requirement SwapOperator
     * independent of implementation.
     *
     * @param Target1 First qubit
     * @param Target2 Second qubit
     */
    public SwapOperator(int Target1, int Target2) throws BitOutOfBoundsException {
        //Check whether Target1 & Target2 are valid in lower bound
        if (Target1 < 0 || Target2 < 0) {
            throw new BitOutOfBoundsException();
        }

        //Set them
        this.target1 = Target1;
        this.target2 = Target2;
    }

    /**
     * Applies SwapOperator to other Operator.
     *
     * @param Op Other operator.
     * @return CompositeOperator created from both operators.
     */
    @Override
    public Operator apply(Operator Op) {
        return new CompositeOperator(this, Op);
    }
}
