package Operators.CCNot;

import Core.Operator;
import Core.QRegister;
import Operators.CompositeOperator;

/**
 * The CCNotOperator inverts the target bit if both control bits are 'true'.
 */
public abstract class CCNotOperator implements Operator {

    /**
     * Holds the bit location of the first control bit.
     */
    protected int c1bit;
    /**
     * Holds the bit location of the second control bit.
     */
    protected int c2bit;
    /**
     * Holds the bit location of the target bit.
     */
    protected int target;

    /**
     *
     * @param C1Bit The first control bit in the QRegister.
     * @param C2Bit The second control bit in the QRegister.
     * @param Target The target bit in the QRegister.
     */
    public CCNotOperator(int C1Bit, int C2Bit, int Target) {
        this.c1bit = C1Bit;
        this.c2bit = C2Bit;
        this.target = Target;
    }

    /**
     * Apply the Operator to a QRegister object.
     *
     * @param Register The QRegister object to be transformed.
     * @return The transformed QRegister object.
     */
    public abstract QRegister apply(QRegister Register) throws Throwable;

    /**
     * Apply the Operator to another Operator object.
     *
     * @param Op The Operator object to be transformed.
     * @return The transformed Operator, as a CompositeOperator.
     */
    public Operator apply(Operator Op) {
        return new CompositeOperator(this, Op);
    }
}
