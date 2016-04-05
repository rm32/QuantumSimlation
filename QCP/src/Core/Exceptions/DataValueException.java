package Core.Exceptions;

/**
 * This exception is thrown when a data value is passed to a QRegister
 * constructor that can not be classically represented by the number of bits
 * set.
 */
public class DataValueException extends Throwable {

    private int bits;
    private int value;

    /**
     * @param Bits Number of bits requested from QRegister.
     * @param Value Value passed to QRegister, which can not be represented in
     * the requested number of bits.
     */
    public DataValueException(int Bits, int Value) {
        super();

        this.bits = Bits;
        this.value = Value;
    }

    /**
     * @param Bits Number of bits requested from QRegister.
     * @param ValueValue passed to QRegister, which can not be represented in
     * the requested number of bits.
     * @param Message Detailed message given.
     */
    public DataValueException(int Bits, int Value, String Message) {
        super(Message);

        this.bits = Bits;
        this.value = Value;
    }
}
