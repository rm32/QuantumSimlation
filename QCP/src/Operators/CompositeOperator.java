package Operators;

import Core.Operator;
import Core.QRegister;

/**
 * The CompositeOperator is an implementation of Operator which allows for
 * multiple operators to be combined into a single Operator.
 *
 * When applying this Operator to a QRegister it will simply call the
 * corresponding Operators apply methods.
 *
 */
public class CompositeOperator implements Operator {

    /**
     * The operator that gets applied first
     */
    private Operator left;
    /**
     * The operator that gets applied last
     */
    private Operator right;

    /**
     *
     * @param Left
     * @param Right
     */
    public CompositeOperator(Operator Left, Operator Right) {
        this.left = Left;
        this.right = Right;
    }

    /**
     * Apply the CompositeOperator on a QRegister.
     *
     * @param QReg The QRegister to be applied to.
     * @return Returns a transform QRegister.
     */
    public QRegister apply(QRegister Register) throws Throwable {
        QRegister leftOut = this.left.apply(Register);

        return this.right.apply(leftOut);
    }

    /**
     * Apply the CompositeOperator on another Operator creating another
     * CompositeOperator.
     *
     * @param Op Operator to be applied to.
     * @return Returns a CompositeOperator combining both Operators.
     */
    public Operator apply(Operator Op) {
        return new CompositeOperator(this, Op);
    }
}
