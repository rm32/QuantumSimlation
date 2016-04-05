package Core.Math;

/**
 * <p>Complex number class to add, multiply and conjugate complex numbers</p>
 */
public class Complex {

    /**
     * Complex number represented by two
     * <code>float</code>s with <var>real</var> representing the real part and
     * <var>imaginary</var> representing the imaginary part
     */
    private float real, imaginary;

    /**
     * Constructs a complex number with coefficients 0 for both the real and
     * imaginary parts
     *
     */
    public Complex() {
        real = 0.0f;
        imaginary = 0.0f;
    }

    
    /**
     * Constructs a complex number which sets the real part to the float constructor
     * and 0 for the imaginary part
     *
     * @param Real real part of the complex number
     */
    public Complex(float Real) {
        real = Real;
        imaginary = 0.0f;
    }

    /**
     * Constructs a complex number represented by <var>Real</var> and
     * <var>Imaginary</var> where <var>Real</var> is the real part and
     * <var>Imaginary</var> is the imaginary part taking two
     * <code>float</code>s as its argument
     *
     * @param Real real part of number
     * @param Imaginary complex part of number
     */
    public Complex(float Real, float Imaginary) {
        real = Real;
        imaginary = Imaginary;
    }

    /**
     * Creates a complex number in which the real and imaginary parts are floats
     * having taken two doubles as its constructor. 
     *
     * @param Real real part of number
     * @param Imaginary imaginary part of number
     */
    public Complex(double Real, double Imaginary) {
        real = (float) Real;
        imaginary = (float) Imaginary;
    }

    /**
     * Constructs a complex number from Eulers formula e^ix = cos(x)+isin(x)
     *
     * @param index The x in the equation above the index of e
     */
    public Complex(double index) {
        real = (float) Math.cos(index);
        imaginary = (float) Math.sin(index);
    }

    /**
     * Conjugates a complex number such that it's imaginary term sign is
     * reversed
     *
     * @return Complex
     */
    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    /**
     * Adds two Complex numbers
     *
     * @param q Complex number to add to
     * @return Complex Sum of the two numbers
     */
    public Complex add(Complex q) {
        float sumreal = real + q.real;
        float sumimaginary = imaginary + q.imaginary;

        return new Complex(sumreal, sumimaginary);
    }

    /**
     * Subtracts two Complex numbers
     *
     * @param q Complex number to subtract
     * @return Complex complex number -q
     */
    public Complex subtract(Complex q) {
        float diffreal = real - q.real;
        float diffimaginary = imaginary - q.imaginary;

        return new Complex(diffreal, diffimaginary);
    }

    /**
     * Multiplies two complex numbers together
     *
     * @param q Number being multiplied with
     * @return Complex product of the two numbers
     */
    public Complex multiply(Complex q) {
        float realreal = real * q.real;
        //Imaginary*Imaginary will have minus sign due to i*i
        float imaginaryimaginary = -imaginary * q.imaginary;
        float real1imaginary2 = real * q.imaginary;
        float imaginary1real2 = imaginary * q.real;
        return new Complex(realreal + imaginaryimaginary, real1imaginary2 + imaginary1real2);
    }

    /**
     * Returns the complex number in a string format
     *
     * @return String complex number in string format
     */
    @Override
    public String toString() {
        return (real + "+" + imaginary + "i");
    }

    /**
     * Returns the magnitude of the complex number
     *
     * @return float Magnitude of complex number
     */
    public float magnitude() {
        float mag = (float) Math.sqrt(Math.pow((double) real, 2) + Math.pow((double) imaginary, 2));
        return mag;
    }

    /**
     * Multiplies the complex number by a real scalar value
     *
     * @param a real number multiplying complex one
     * @return product of real can complex number
     */
    public Complex scalarmultiply(float a) {
        return new Complex(real * a, imaginary * a);
    }

    /**
     * gets the real part of the complex number
     * 
     * @return the real part of the complex number
     */
    public float getReal() {
        return this.real;
    }

    /**
     * gets the imaginary part of the complex number 
     * 
     * @return imaginary part of the complex number
     */
    public float getImaginary() {
        return this.imaginary;
    }

    //raises matrix to power
    /**
     * 
     * @param power
     * @return Complex number which is raised to the power specified. 
     * @throws Exception 
     */
    public Complex power(int power) throws Exception {
        Complex out = null;
        if (power < 0) {
            throw new Exception("Sorry only positive powers");
        }

        if (power == 0) {
            out = new Complex(1, 0);
        } else {
            out = this;
            for (int i = 1; i < power; i++) {
                out = out.multiply(this);
            }

        }
        return out;
    }
}
