package Core;

import Core.Exceptions.DataValueException;
import Core.Exceptions.NormalizationException;
import Core.Exceptions.VectorLengthException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import java.util.Random;

/**
 * This is the basic data structure to contain a quantum register.
 *
 * Individual qubits are not important compared to the composite, due to
 * entanglement.
 */
public class QRegister {

    /**
     * The random number generator used to measure a register.
     */
    static Random rng = new Random(System.currentTimeMillis());
    /**
     * The number of qubits in the register.
     */
    private int bits;
    /**
     * The ComplexVector representing the amplitudes in each base of the
     * register.
     *
     * The order of bases is unimportant as long as it is consistent.
     */
    private ComplexVector amplitudes;

    /**
     * Constructs a QRegister object from a number of qubits.
     *
     * @param Bits Number of qubits requested.
     */
    public QRegister(int Bits) {
        this.bits = Bits;

        this.amplitudes = new ComplexVector(1 << Bits);
    }

    /**
     * Constructs a QRegister object from a set of amplitudes.
     *
     * This constructor calculates the number of qubits from the amplitudes
     * vector. The log base 2 of the vector length will result in the number of
     * qubits, since the number of bases is 2^bits.
     *
     * @param Amplitudes A ComplexVector containing all amplitudes of a
     * register.
     */
    public QRegister(ComplexVector Amplitudes) {
        //Calculate number of bits in register, log_2(amplitudes).
        this.bits = (int) (Math.log(Amplitudes.length()) / Math.log(2.0));

        this.amplitudes = Amplitudes;
    }

    /**
     * Constructs a QRegister from a previous QRegister and a ComplexVector.
     *
     * Simply copies over the number of bits from old register and assigns a new
     * ComplexVector.
     *
     * @param Register Derived QRegister before transform.
     * @param Amplitudes Transformed ComplexVector.
     */
    public QRegister(QRegister Register, ComplexVector Amplitudes) {
        this.bits = Register.bits;
        this.amplitudes = Amplitudes;
    }

    /**
     * Constructs a QRegister from a number of requested bits and a classical
     * integer.
     *
     * @param Bits Number of bits requested.
     * @param Data Value to set QRegister to.
     * @throws DataValueException Thrown if Bits ability to represent Value is
     * exceeded.
     */
    public QRegister(int Bits, int Data) throws DataValueException {
        //Test that classical register could hold value.        
        if ((1 << Bits) < Data) {
            throw new DataValueException(Bits, Data);
        }

        //Set number of bits in register.
        this.bits = Bits;

        //Initialize complex array to fill with correct values.
        Complex[] amps = new Complex[1 << Bits];

        for (int i = 0; i < 1 << Bits; i++) {
            amps[i] = new Complex(0.0f, 0.0f);
        }

        //Fill in classical data
        amps[Data] = new Complex(1.0f, 0.0f);

        //Set ComplexVector and normalize
        this.amplitudes = new ComplexVector(amps);

        try {
            amplitudes.normalize();
        } catch (VectorLengthException E) {
            E.printStackTrace();
        }
    }

    /**
     * @param Base The Base |...> to get the amplitude of.
     * @return
     */
    public Complex getAmplitude(int Base) {
        return amplitudes.getComponent(Base);
    }

    /**
     * @param Base The Base |...> to set the amplitude of.
     * @param Amplitude Amplitude to be set for Base.
     */
    public void setAmplitude(int Base, Complex Amplitude) {
        this.amplitudes.setComponent(Amplitude, Base);
    }

    /**
     * Amplitudes of bases in register.
     *
     * @return Returns the ComplexVector representing the QRegister.
     */
    public ComplexVector getComplexVector() {
        return this.amplitudes;
    }

    /**
     * Number of qubits in register.
     *
     * @return Returns the number of bits in the QRegister.
     */
    public int getBitCount() {
        return this.bits;
    }

    /**
     * Number of bases in register.
     *
     * is 2^getBitCount()
     *
     * @return Returns the number of bases in the QRegister. (2^Bits)
     */
    public int getBaseCount() {
        return 1 << this.bits;
    }

    /**
     * Measures a value from the QRegister. This collaspes the wavefunction
     * represented into a discrete classical piece of information.
     *
     * @return Measurement of the QRegister.
     * @throws NormalizationException ComplexVector is not normalized.
     */
    public int measure() throws NormalizationException, VectorLengthException {
        //Normalize the QRegister

        amplitudes.normalize();

        //Generate random number
        double psi = rng.nextDouble();

        //Add amplitudes until greater than random number
        double theta = 0.0;

        for (int base = 0; base < this.amplitudes.length(); base++) {
            double amp = this.amplitudes.getComponent(base).magnitude();
            amp *= amp;

            //Test which base is set by random number.
            if (psi <= theta + amp) {
                //Collapse wavefunction so that further measurements would result in same measurement.
                amplitudes.clear();
                amplitudes.setComponent(new Complex(1.0f), base);

                //Return classical result
                return base;
            } else {
                theta += amp;
            }
        }

        throw new NormalizationException();
    }

    /**
     * A human readable representation of the QRegister object.
     *
     * @return String containing the number of bits and ComplexVector of the
     * QRegister.
     */
    public String toString() {
        String output = "Bits: " + bits + " Data: " + this.amplitudes.toString();

        return output;
    }

    /**
     * Combines two QRegisters to form a larger QRegister using tensor product between two vectors
     * @param Register Register this register is being combined with
     * @return Larger register representing the combination of the two registers
     */
    public QRegister combine(QRegister Register) {
        return new QRegister(this.getComplexVector().tensorKetProduct(Register.getComplexVector()));
    }

    /**
     * This methods measures one bit and then collapses all states that oppose
     * the measurement.
     *
     * @param bit, the position of the qubit to be measured (0,...,n-1)
     * @return 0 or 1, the state of the qubit.
     * @throws NormalizationException
     * @throws VectorLengthException
     */
    public int measure(int bit) throws NormalizationException, VectorLengthException {

        //Generate random number
        double psi = rng.nextDouble();
        amplitudes.normalize();
        //Add coefficients for all states where the qubit is zero
        double coefficientSum = 0;
        for (int j = 0; j < (1 << (bits - 1 - bit)); j++) {
            for (int k = 0; k < (1 << bit); k++) {
                double amp = this.amplitudes.getComponent(j * (1 << (bit + 1)) + k).magnitude();
                coefficientSum += amp * amp;
            }
        }

        //Check if 0 or 1;
        int state = 1;
        if (coefficientSum >= psi) {
            state = 0;
        }

        //Kill all other states, that oppose the measurement
        if (state == 1) {
            for (int j = 0; j < (1 << (bits - 1 - bit)); j++) {
                for (int k = 0; k < (1 << bit); k++) {
                    this.setAmplitude(j * (1 << (bit + 1)) + k, new Complex(0, 0));
                }
            }

        } else {
            for (int j = 0; j < (1 << (bits - 1 - bit)); j++) {
                for (int k = 0; k < (1 << bit); k++) {
                    this.setAmplitude(j * (1 << (bit + 1)) + k + (1 << bit), new Complex(0, 0));
                }
            }
        }

        //Normalize register
        amplitudes.normalize();

        //return measurement
        return state;

    }

    /**
     * Measures an array of adjacent qubits.
     *
     * @param start, the first qubit to be measured |0010(end)10...01(start)01>
     * @param end, the last qubit to be measured
     * @return The measured state, as a decimal number.
     * @throws NormalizationException
     * @throws VectorLengthException
     */
    public int measure(int start, int end) throws NormalizationException, VectorLengthException {
        String binary = "";
        for (int i = start; i <= end; i++) {
            binary = measure(i) + binary;
        }
        return Integer.parseInt(binary, 2);
    }
}
