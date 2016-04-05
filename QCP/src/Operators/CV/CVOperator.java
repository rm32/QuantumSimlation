package Operators.CV;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Operator;
import Core.QRegister;
import Operators.CompositeOperator;

/**
 * Defines the CVOperator as a controlled-V operator.
 */
public abstract class CVOperator implements Operator {

    /**
     * The control bit location.
     */
    protected int control;
    /**
     * The target bit location.
     */
    protected int target;

    /**
     * Constructs a CVOperator from a control and target bit.
     * 
     * @param Control Control bit location.
     * @param Target Target bit location.
     */
    public CVOperator(int Control, int Target) throws BitOutOfBoundsException {
        //Check whether control or target are outside lower bound
        if (Control < 0 || Target < 0) {
            throw new BitOutOfBoundsException();
        }

        //Set control & target
        this.control = Control;
        this.target = Target;
    }

    /**
     * Combine this operator and another into a CompositeOperator
     * 
     * @param Op Other operator to act on.
     * @return CompositeOperator linked to both Operator.
     */
    public Operator apply(Operator Op) {
        return new CompositeOperator(this, Op);
    }

    /**
     * Declaration of application to register.
     * 
     * @param Register Register to be applied to.
     * @return Transformed register.
     * @throws BitOutOfBoundsException
     */
    public abstract QRegister apply(QRegister Register) throws Throwable;
}
