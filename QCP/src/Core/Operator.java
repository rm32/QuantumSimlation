package Core;

import Core.Exceptions.BitOutOfBoundsException;

/**
 * This is the interface for a linear operator on a register.
 */
public interface Operator {

    /**
     * This method declares that a linear operator can be applied to a register.
     *
     * @param QReg QRegister to which the Operator should be applied.
     * @return QRegister with applied Operator.
     */
    QRegister apply(QRegister QReg) throws BitOutOfBoundsException, Throwable;

    /**
     * This method declares that a linear operator can be applied to another
     * operator.
     *
     * @param Op Operator to which the Operator should be applied.
     * @return CompositeOperator of both operators.
     */
    Operator apply(Operator Op);
}
