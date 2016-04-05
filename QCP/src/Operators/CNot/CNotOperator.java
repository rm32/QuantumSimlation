package Operators.CNot;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Operator;
import Core.QRegister;
import Operators.CompositeOperator;

/**
 * Abstract class implementing the CNot gate.
 *
 */
public abstract class CNotOperator implements Operator {
   
   /**
    *  Index of control qubit
    */
    protected int control;
    
    /**
     * Index of target qubit 
     */
    protected int target;

    /**
     * Constructor for specific target and control qubit
     * @param control Index of control qubit
     * @param target Index of target qubit
     */
    public CNotOperator(int control, int target) {
        this.control = control;
        this.target = target;
    }

    /**
     * Method to act with the CNot gate on a gate to produce a composite gate
     * @param Op The operator acted on
     * @return The new composite operator
     */
    public Operator apply(Operator op) {
        return new CompositeOperator(this, op);
    }

    /**
     * Method to act with the CNot gate on a quantum register
     * @param register The quantum register acted upon
     * @return The changed quantum register
     * @throws BitOutOfBoundsException
     */
    public abstract QRegister apply(QRegister register) throws Throwable;
}