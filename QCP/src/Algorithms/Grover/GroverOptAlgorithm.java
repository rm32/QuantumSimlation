package Algorithms.Grover;

import Core.Exceptions.BitOutOfBoundsException;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;
import Core.Math.Complex;
import Core.QRegister;
import Operators.Hadamard.HadamardBitOperator;
import Operators.Hadamard.HadamardOperator;

/**
 * Runs Grovers Algorithm base-wise (using Hadamard gates)
 */
public class GroverOptAlgorithm extends GroverAlgorithm {

    /**
     * initialises Grovers Algorithm base-wise (using Hadamard gates)
     *
     * @param Oracle
     */
    public GroverOptAlgorithm(GroverOracle Oracle) {
        super(Oracle);
    }

    /**
     * This implementation applies the Composite of the diffusion matrix
     * (H,cV,cV,H) to the the QRegister as a diffusion operator.
     *
     * @return
     */
    @Override
    public GroverOutput run() {
        //Calculate number of steps to take
        int recursions = (int) Math.floor(Math.PI / 4.0f * Math.sqrt(oracle.getBaseCount()));
        System.out.println("Steps: " + recursions);

        //Create initial QRegister
        QRegister initial = getSuperpositionFast();

        //Comment this out to save memory.
        GroverDisplay display = new GroverDisplay(initial.getComplexVector(), oracle.getAnswerRegister().getComplexVector());

        //Start to iterator through steps
        for (int step = 0; step < recursions; step++) {
            System.out.println("Step: " + step);

            //Apply Oracle
            for (int base = 0; base < oracle.getBaseCount(); base++) {
                if (oracle.recognize(base)) {
                    initial.setAmplitude(base, initial.getAmplitude(base).multiply(new Complex(-1.0f, 0.0f)));
                }
            }

            //Apply hadamard to entire register
            for (int bit = 0; bit < oracle.getBitCount(); bit++) {
                HadamardOperator op = new HadamardBitOperator(bit);

                try {
                    initial = op.apply(initial);
                } catch (BitOutOfBoundsException E) {
                    //Never happens due to oracle.
                } catch (Throwable E) {
                }
            }

            //Apply I_|0>
            initial.setAmplitude(0, initial.getAmplitude(0).multiply(new Complex(-1.0f, 0.0f)));

            //Apply hadamard to entire register
            for (int bit = 0; bit < oracle.getBitCount(); bit++) {
                HadamardOperator op = new HadamardBitOperator(bit);

                try {
                    initial = op.apply(initial);
                } catch (BitOutOfBoundsException E) {
                    //Never happens due to oracle.
                } catch (Throwable E) {
                }
            }

            display.vectors.add(initial.getComplexVector());
            display.repaint();
        }

        int measure = 0;

        try {
            measure = initial.measure();
        } catch (NormalizationException ex) {
            System.exit(1);
        } catch (VectorLengthException ex) {
            System.exit(2);
        }


        if (oracle.recognize(measure) != true) {
            return this.run();
        }

        return new GroverOutput(measure);
    }
}
