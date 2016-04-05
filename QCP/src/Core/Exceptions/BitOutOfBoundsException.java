package Core.Exceptions;

/**
 * Exception that gets thrown when the bit you want to act on is out of the
 * range of bits in the register.
 */
public class BitOutOfBoundsException extends Throwable {

    /**
     * Returns a detailed message about the exception
     *
     * @param msg
     */
    public BitOutOfBoundsException(String msg) {
        super(msg);
    }

    /**
     * Exception for general use
     */
    public BitOutOfBoundsException() {
        super();
    }
}
