package Core.Exceptions;

/**
 * Exception used when 2 vectors do not have the same umber of components, used
 * in dot product methods for example
 */
public class VectorLengthException extends Throwable {

    /**
     * @param msg Detailed message given.
     */
    public VectorLengthException(String msg) {
        super(msg);
    }

    /**
     * General use exception
     */
    public VectorLengthException() {
        super();
    }
}
