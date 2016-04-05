package Operators.Hadamard;

import Core.Operator;
import Operators.CompositeOperator;

/**
 * Class implementing the Hadamard gate for one qubit
 */
public abstract class HadamardOperator implements Operator {

    /**
     * Index of qubit acting on
     */
    protected int bit;

    /**
     * Constructor of the operator for specific qubit index
     * @param Bit Index of target qubit
     */
    public HadamardOperator(int Bit) {
        this.bit = Bit;
    }

    /**
     * Method to act with the Hadamard on a gate to produce a composite gate
     *
     * @param Op Gate being acted on with this gate
     * @return CompositeOperator made up of the Hadamard and Op
     */
    public Operator apply(Operator Op) {
        return new CompositeOperator(this, Op);
    }
}
