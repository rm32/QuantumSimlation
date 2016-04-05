package Operators.Phase;

import Core.Operator;
import Operators.CompositeOperator;

/**
 * Implementation of the phase shift gate.
 */
public abstract class PhaseOperator implements Operator {

    /**
     * Index of qubit acted on
     */
    protected int bit;

    /**
     * Constructor for a specific qubit index
     * @param Bit index of target qubit
     */
    public PhaseOperator(int Bit) {
        this.bit = Bit;
    }

    /**
     * Applies the PhaseOperator to the other operator through a
     * CompositeOperator.
     *
     * @param Op The Operator that is being applied to.
     * @return Returns a new CompositeOperator.
     */
    public Operator apply(Operator Op) {
        return new CompositeOperator(this, Op);
    }
}
