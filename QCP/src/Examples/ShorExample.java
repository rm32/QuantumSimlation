package Examples;

import Algorithms.Shor.ShorAlgorithm;
import Algorithms.Shor.ShorOutput;
import Core.Exceptions.NegativeIndexException;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;

/**
 *
 * This is a simple class to demonstrate Shor's algorithm.
 */
public class ShorExample {
    
    public static void main(String[] args) throws Exception, NormalizationException, VectorLengthException, NegativeIndexException {
       //Number to be factored.
       int number = 15;
       System.out.println("The number being factored is "+number);
       ShorAlgorithm test = new ShorAlgorithm(number);
       ShorOutput out = test.run();
       System.out.println(out);
    }

}
