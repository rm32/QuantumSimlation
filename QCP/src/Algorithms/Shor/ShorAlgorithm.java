package Algorithms.Shor;

import Classical.Algorithms.EuclidianAlgorithm;
import Core.Algorithm;
import Core.Exceptions.NegativeIndexException;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;
import Core.Math.Complex;
import Core.Math.IsOdd;
import Core.Math.Matrix;
import Core.QRegister;
import Operators.QFT.QFTMatrix;
import Operators.ShorFunction.ShorFunction;
import java.math.BigInteger;
import java.util.Random;

/**
 * Implementation of Shor's Algorithm
 */
public class ShorAlgorithm implements Algorithm {

    private int n, m, L, period;
    private BigInteger bigm, bign, bigperiod;

    /**
     * Constructor to obtain data required to run Shor's
     *
     * @param numberToFactor Number being factored by Shor's Algorithm
     */
    public ShorAlgorithm(int numberToFactor) {
        n = numberToFactor;
        bign = BigInteger.valueOf(n);

    }

    /**
     * Method that runs the algorithm
     *
     * @return Output of Shor's Algorithm, can be converted to human readable
     * string using ShorOutput class
     * @throws NormalizationException
     * @throws Exception
     * @throws VectorLengthException
     * @throws NegativeIndexException
     */
    public ShorOutput run() throws NormalizationException, Exception, VectorLengthException, NegativeIndexException {
        ShorOutput out = null;
        //Generating random value for m
        Random rand = new Random();
        m = rand.nextInt(n - 2) + 2;
        //Testing if relitively prime to N
        EuclidianAlgorithm test = new EuclidianAlgorithm(m, n);
        int needsToBeOne = test.runAlgorithm();
        if (needsToBeOne != 1) {
            System.out.println("m and n are not relitively prime, running again");
            out = this.run();
        } else {
            bigm = BigInteger.valueOf(m);

            System.out.println("Quantum section started.");
            //Doing quantum part of algorithm
            int quantumout = this.quantumPart();
            System.out.println("Quantum Part done");
            //Running through continued fraction algorithm
            double epsilon = (double) quantumout / Math.pow(2, L);
            int aZero = (int) (epsilon);
            int qZero = 1;
            double epsilonZero = epsilon - aZero;
            int a1 = (int) (1.0 / epsilonZero);
            int q1 = a1;
            //Ints to store previous values 

            int qNMinus1 = q1;
            int qUtil = 1;
            int qNMinus2 = qZero;
            // datatypes to store current values
            int an = a1;
            double epsilonN = (1.0 / epsilonZero) - a1;
            int qn = q1;

            //Iterating through testing the values of q to see if they are the period
            while (epsilonN != 0) {
                an = (int) (1.0 / epsilonN);
                qUtil = qn;
                qn = an * qNMinus1 + qNMinus2;

                qNMinus2 = qNMinus1;
                qNMinus1 = qUtil;
                //System.out.println(qn + " qn     " + qNMinus1 + " qNMinus1 " + qNMinus2 + " qNMinus2    " + epsilonN + " epsilonN  " + an + "  an");
                epsilonN = (1.0 / epsilonN) - an;

                if (epsilonN < 1.0e-10) {

                    epsilonN = 0;
                }
                BigInteger bigqn = BigInteger.valueOf(qn);


                if (bigm.modPow(bigqn, bign).longValue() == 1) {
                    // Below Needs to happen as if it is zero will go into if loop below and say fail when in reality it works
                    epsilonN = 1;
                    break;
                }
                if (epsilonN == 0) {

                    break;

                }
            }

            if (epsilonN == 0) {

                System.out.println("Failed to find Period as epsilonN became 0");
                out = this.run();
            } //if the loop fails run again to redo the quantum part euclid step not nessecary but is fast
            //if succesful in obtaining the period
            else {
                period = qn;
                System.out.println("Testing if period is even");
                //testing if the period is odd, if it is have to start again
                if (IsOdd.isOdd(period)) {
                    System.out.println("Period is not even, running again");
                    out = this.run();
                } //period is even
                else {

                    System.out.println("Now testing if m^period/2±1 mod(n) =0");
                    BigInteger testone = bigm.pow(period / 2).add(BigInteger.valueOf(1));
                    BigInteger testother = bigm.pow(period / 2).subtract(BigInteger.valueOf(1));
                    // now testing if either m^P/2±1 = 0 Mod N as then other test can be anything, if either is 0 have to start again
                    if (testone.mod(bign).longValue() == 0 || testother.mod(bign).longValue() == 0) {
                        System.out.println("m^P/2±1 = 0 Mod N So the other can be any value, running again");
                        out = this.run();
                    } //Now factoring using the euclidean algorithm to get a factor and finding the other via division
                    else {


                        BigInteger factorone = testother.gcd(bign);
                        BigInteger factortwo = bign.divide(factorone);
                        out = new ShorOutput((int) factorone.longValue(), (int) factortwo.longValue());

                    }
                }


            }
        }
        return out;

    }

    /**
     * Method to return N the number being factored
     *
     * @return Number being factored N
     */
    public int getN() {
        return n;
    }

    /**
     * Quantum Part of Algorithm
     *
     * @return Output from Quantum Part
     * @throws NormalizationException
     * @throws Exception
     * @throws VectorLengthException
     * @throws NegativeIndexException
     */
    private int quantumPart() throws NormalizationException, Exception, VectorLengthException, NegativeIndexException {
        L = this.getL();
        //Setting up QRegisters to correct Size
        QRegister QRegOne = new QRegister(L);
        QRegOne.setAmplitude(0, new Complex(1, 0));
        QRegister QRegTwo = new QRegister((int) Math.ceil(Math.log(n) / Math.log(2)));
        QRegTwo.setAmplitude(1, new Complex(1, 0));
        //Creating the Fourier transform Matrix
        QFTMatrix FourierTransform = new QFTMatrix(L);
        //Applying it to the first QRegister
        QRegOne = FourierTransform.apply(QRegOne);
        //Generating the function that applies the transformation |x>|1> -> |x>|m^x Mod N>
        ShorFunction unitaryOperator = new ShorFunction(QRegOne, m, n);
        //Applying this to the system, QRegisters entangled so now represented by a single QRegister
        QRegister Register = unitaryOperator.apply(QRegTwo);
        Matrix Identity = Matrix.identityMatrix(QRegTwo.getBaseCount());
        //Generating QFT acting on first QRegister for the new Register representing whole system
        Matrix QFTOnFirstRegister = FourierTransform.getMatrix().tensorProduct(Identity);
        //QFT acting on first register
        Register = new QRegister(QFTOnFirstRegister.multiply(Register.getComplexVector()));
        //Measureing the first QRegister Bit by Bit to obtain the output
        int out = Register.measure(Register.getBitCount() - L, Register.getBitCount() - 1);

        return out;
    }

    /**
     * Method to obtain value for L, the number of Qubits in the first register
     *
     * @return Number of Qubits in first QRegister
     * @throws NegativeIndexException
     */
    public int getL() throws NegativeIndexException {

        double q = n * n * Math.random() + n * n;
        int out = (int) (Math.log(q) / Math.log(2));
        return out;
    }
}
