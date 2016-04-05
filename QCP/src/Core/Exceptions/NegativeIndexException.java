package Core.Exceptions;

/**
 * Exception for the power method thrown if index is smaller than zero as method
 * cannot evaluate negative powers
 */
public class NegativeIndexException extends Throwable {

    public NegativeIndexException(String msg) {
        super(msg);
    }

    /**
     * Exception for general use
     */
    public NegativeIndexException() {
        super();
    }
}
