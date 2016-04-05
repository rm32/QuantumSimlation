package Core.Exceptions;

/**
 * This exception is thrown by the QRegister when being measured with a
 * ComplexVector which is not normalised (magnitude != 1).
 */
public class NormalizationException extends Throwable {

    public NormalizationException() {
        super();
    }

    /**
     * @param Message Detailed message given.
     */
    public NormalizationException(String Message) {
        super(Message);
    }
}
