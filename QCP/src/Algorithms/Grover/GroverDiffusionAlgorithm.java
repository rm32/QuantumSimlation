package Algorithms.Grover;

import Core.AlgorithmOutput;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;
import Core.Math.Complex;
import Core.Math.DenseMatrix;
import Core.QRegister;

/**
 * Class Implementing Grover Diffusion Matrix 
 */
public class GroverDiffusionAlgorithm extends GroverAlgorithm {
    /**
     * This is the diffusion matrix used for Grovers algorithm.
     */
    DenseMatrix diffusion;

    /**
     * Initiates the class taking an Oracle as its constructor.
     *
     * @param Oracle
     */
    public GroverDiffusionAlgorithm( GroverOracle Oracle ) {
        super( Oracle );

        //Construct diffusion matrix
        diffusion = new DenseMatrix(oracle.getBaseCount());

        int bases = oracle.getBaseCount();

        Complex elements = new Complex(2.0f / bases, 0);
        Complex elementsDiagonal = new Complex(2 / bases - 1, 0);

        //if we had an intelligent matrix implementation we could save a ton of memory here...
        for (int row = 0; row < bases; row++) {
            for (int column = 0; column < bases; column++) {
                if (row == column) {
                    diffusion.setElement(row, column, elementsDiagonal);
                } else {
                    diffusion.setElement(row, column, elements);
                }
            }
        }
    }

    /**
     * Executes grovers algorithm with given oracle.
     * 
     * This implementation stores the diffusion matrix as a DenseMatrix and then multiplies it every step.
     *
     * @return Returns GroverOutput containing final QRegister.
     */
    @Override
    public AlgorithmOutput run() {        
        
        //Large N
        int recursions = (int)Math.floor( Math.PI / 4.0f * Math.sqrt( oracle.getBaseCount() ) );
        System.out.println( "Total number of Steps: " + recursions );

        //Selection of hadamard or trick
        //QRegister superposition = getSuperpositionHadamard();
        QRegister superposition = getSuperpositionFast();
        
        //Comment this out to save memory.
        GroverDisplay display = new GroverDisplay( superposition.getComplexVector(), oracle.getAnswerRegister().getComplexVector() );
        
        //Iterate
        for (int step = 0; step < recursions; step++) {
            System.out.println( "Step: " + step );
            
            //Apply oracle
            for (int base = 0; base < oracle.getBaseCount(); base++) {
                if (oracle.recognize(base)) {
                    superposition.setAmplitude(base, superposition.getAmplitude(base).multiply( new Complex(-1.0f, 0.0f) ) );
                }
            }
            
            //Apply diffusion matrix
            QRegister rotated = new QRegister( diffusion.multiply(superposition.getComplexVector()) );
            
            //Add to display
            display.vectors.add( rotated.getComplexVector() );
            display.repaint();
            
            //Set up for next iteration
            superposition = rotated;
        }
        
        int measure = 0;
        
        try 
        {
             measure = superposition.measure();
        } 
        catch (NormalizationException ex) 
        {
            System.exit( 1 );
        } 
        catch (VectorLengthException ex) 
        {
            System.exit( 2 );
        }
        
        if( oracle.recognize( measure ) != true)
        {
            return this.run();
        }
        
        return new GroverOutput( measure );
    }
}
 