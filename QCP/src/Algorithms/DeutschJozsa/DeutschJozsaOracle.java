package Algorithms.DeutschJozsa;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Operator;
import Core.QRegister;

/**
 * Deutsch-Jozsa-Oracle class, representing the Uf operator (the black box).
 */
public class DeutschJozsaOracle implements Operator {

    /**
     * Number of control qubits (n-1) plus 1 target qubit
     */
    int n;
    /**
     * Specifies whether the function is constant or balanced. true: constant
     * function false: balanced function
     */
    boolean constant;

    /**
     * Constructor for a specific number n of qubits. It has to be decided
     * whether the oracle should be constant or balanced.
     *
     * @param n Number of qubits (n-1 control, 1 target)
     * @param constant Constant or balanced function
     */
    public DeutschJozsaOracle(int n, boolean constant) {
        this.n = n;
        this.constant = constant;
    }

    /**
     * The method where the Uf operator is applied to a given quantum register,
     * the changed quantum register is returned.
     *
     * @param qReg The input quantum register
     * @return The register after the operator has been applied.
     * @throws BitOutOfBoundsException
     */
    @Override
    public QRegister apply(QRegister qReg) throws BitOutOfBoundsException {

        //New register, the input register is discarded.
        QRegister output = new QRegister(qReg.getBitCount());

        //The result of the function evaluation
        int oracleResult;

        /*
         * All bases are evaluated. We do not evaluate the target qubit and
         * hence two bases are evaluated at the same time.
         */
        for (int i = 0; i < qReg.getBaseCount(); i += 2) {

            /*
             * The actual function f(00100..001) is evaluated, in decimal
             * representation. We do not evaluate the target qubit, that is why
             * the number is divided by two.
             */
            oracleResult = askClassicalOracle(i / 2);
            
            /*
             * Same control qubit bases with different target qubit states get 
             * either changed or stay the same.
             * Example:
             * a|1010> and b|1011>
             * if f(101) = 0 => 0 xor 0 = 1 and 0 xor 1 = 1
             * coefficients stay the same
             * if f(101) = 1 => 1 xor 0 = 1 and 1 xor 1 = 0
             * a and b get changed
             */
            if (oracleResult == 0) {
                output.setAmplitude(i, qReg.getAmplitude(i));
                output.setAmplitude(i + 1, qReg.getAmplitude(i + 1));
            } //else if oracleResult == 1
            else {
                output.setAmplitude(i, qReg.getAmplitude(i + 1));
                output.setAmplitude(i + 1, qReg.getAmplitude(i));
            }
        }

        return output;
    }

    /**
     * The actual oracle-function. Returns 0 or 1 for all inputs, or exactly 0
     * half the time and exactly 1 half the time.
     *
     * @param number Decimal number representing the binary basis, not including
     * the control qubit.
     * @return The value of f(number), i.e. 0 or 1.
     */
    public int askClassicalOracle(int number) {
        int result;
        if (constant) {
            return 0;
        } else if (number < ((1 << n) / 2 / 2)) {
            result = 0;
        } else {
            result = 1;
        }

        return result;

    }

    /**
     * Another example for the oracle function, there a many possibilities for
     * balanced functions.
     *
     * @param The number that is evaluated, in decimal notation. f(number)
     * @return The value of f(number), i.e. 0 or 1.
     */
    public int askClassicalOracle2(int number) {
        int result;
        if (constant) {
            return 1;
        } else if (number / 2 * 2 == number) {
            result = 1;
        } else {
            result = 0;
        }
        return result;

    }

    /**
     * Not needed in this operator.
     */
    @Override
    public Operator apply(Operator Op) {
        throw new UnsupportedOperationException("Not applicable");
    }
}
