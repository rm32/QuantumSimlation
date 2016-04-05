package Core;

/**
 * This is the interface for an algorithms output container.
 */
public interface AlgorithmOutput {

    /**
     * AlgorithmOutput must be able to be human readable.
     *
     * This is a basic requirement. Further accessors to the data can be
     * implemented in the derived classes.
     *
     * @return String representing result from algorithm.
     */
    @Override
    String toString();
}
