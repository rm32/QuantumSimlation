package Core;

/**
 * This is the interface for an algorithm implementation.
 */
public interface Algorithm {

    /**
     * The actual execution of the algorithm.
     *
     * @return AlgorithmOutput is an interface implemented by every algorithm to
     * output its own type of data.
     */
    AlgorithmOutput run() throws Throwable;
}