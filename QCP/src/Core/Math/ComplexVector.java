package Core.Math;

import Core.Exceptions.VectorLengthException;

/**
 * <p>Complex Vector class to perform operations involving vectors which can
 * have complex components </p>
 */
public class ComplexVector {

    /**
     * Complex Vector represented by an array of Complex
     * numbers(,<var>amplitudes</amplitudes> where each element of the array is
     * a component of the vector in a user defined basis
     *
     */
    //array storing the magnitudes of each component of the vector
    private Complex[] amplitudes;

    /**
     * Constructs a vector with a number of components set by
     * <var>noofcomps</var> all the components bar the first are set to zero
     *
     * @param noofcomps Number of components the vector has
     */
    //creating a unnormalized complex vector with n components with the first being one and the rest being 0
    public ComplexVector(int noOfComps) {
        //initializing the array
        amplitudes = new Complex[noOfComps];

        for (int i = 0; i < noOfComps; i++) {
            amplitudes[i] = new Complex(0, 0);
        }
    }

    /**
     * Constructs a vector from an array containing the components of the vector
     *
     * @param components array containing the components of the vector
     */
    public ComplexVector(Complex[] components) {
        //Setting the value for amplitudes to the array provided 
        amplitudes = components;
    }

    /**
     * Constructs a complex vector from a complex vector (shallow copy)
     *
     * @param Vector Copy of the original complex vector
     */
    public ComplexVector(ComplexVector Vector) {
        amplitudes = Vector.getArray();
    }

    /**
     * Returns a component of the vector
     *
     * @param i component of vector you want to get
     * @return the component of the vector
     */
    public Complex getComponent(int i) {
        return amplitudes[i];
    }

    /**
     * Sets the vector to a component of the users choice
     *
     * @param c value that component is going to be changed to
     * @param i index of component that is being changed
     */
    public void setComponent(Complex complexSet, int indexSet) {
        amplitudes[indexSet] = complexSet;
    }

    /**
     * Returns the number of components the vector has
     *
     * @return numberOfComponents the number of components
     */
    public int numberOfComponents() {
        //Number of components is equal to the number of elements in the array
        return amplitudes.length;
    }

    /**
     * Adds two vectors together to obtain a new vector
     *
     * @param q Vector being added
     * @return Sum of the vectors
     * @throws VectorLengthException throws this if the vectors are not of the
     * same length
     */
    public ComplexVector add(ComplexVector q) throws VectorLengthException {
        //initializing a complex vector for the sum of the two vectors
        ComplexVector sum = new ComplexVector(amplitudes.length);
        if (amplitudes.length != q.numberOfComponents()) {
            //throwing exception if the vectors dont have the same number of components 
            throw new VectorLengthException();
        } else {
            for (int i = 0; i < amplitudes.length; i++) {
                //iterating through the loop and adding each corresponding component of the vectors together
                sum.amplitudes[i] = amplitudes[i].add(q.amplitudes[i]);
            }

        }
        return sum;
    }

    /**
     * Calculates the dot product of two vectors
     *
     * @param q Vector being dot producted with
     * @return Dot product of the two vectors
     * @throws VectorLengthException Throws this if the vectors are not of the
     * same length
     */
    public Complex dotprod(ComplexVector q) throws VectorLengthException {
        //Initializing a complex number to represent the dot product of the two vectors
        Complex dot = new Complex();
        if (amplitudes.length != q.numberOfComponents()) {
            //throwing an exception if the two vectors dont have the same number of components
            throw new VectorLengthException();
        } else {
            /*
             * iterating over the complex vectors to obtain the product of the
             * corresponding components and then summing them up, A.B =
             * A0*B0+A1*B1....AN-1*BN-1
             */

            for (int i = 0; i < amplitudes.length; i++) {
                dot = dot.add(amplitudes[i].multiply(q.amplitudes[i]));
            }
        }
        return dot;

    }

    /**
     * Method to obtain an array containing all of the components of the vector
     *
     * @return Array containing all the components of the complex vector
     */
    public Complex[] getArray() {
        //returning the array containing the amplitudes of the complex vector
        return amplitudes;
    }

    /**
     * Returns the magnitude of the vector
     *
     * @return Magnitude of the vector
     */
    /*
     * Calculating the magnitude of the complex vector by its conjuagte, the
     * magnitude method then square roots the dot product to obtain the
     * magnitude value as a float
     */
    public float magnitude() throws VectorLengthException {
        float out = this.dotprod(this.conjugate()).magnitude();
        return out;
    }

    /**
     * C
     * Calculates the outer (Tensor) product between two vectors
     *
     * @param q Vector being outer producted with
     * @return Tensor product of the two vectors
     * @throws VectorLengthException DenseMatrix only takes square matricies,
     * hence the vector lengths must be the same (This is only used for Grovers
     * algorithm so which uses vectors of the same order so this will not impede
     * the simulation in ant way.
     */
//Calculates the outer (TENSOR) product between two vectors
    public DenseMatrix outerProduct(ComplexVector q) throws VectorLengthException {
        DenseMatrix output = new DenseMatrix(amplitudes.length);
        if (amplitudes.length != q.amplitudes.length) {
            /*
             * Throwing a vector length exception if the vectors are not the
             * same size such that the matrix produced would not be a squre
             * matrix, this is not mathematically required to produce a square
             * matrix, but the class DenseMatrix can only generate square
             * matricies and also the situation in which the outer product is
             * used (grovers algorithm) requires that the vectors be of the same
             * order
             */
            throw new VectorLengthException();
        } else {
            //filling up the matrix with the with the elements of the tensor product of the two vectors
            for (int i = 0; i < amplitudes.length; i++) {
                for (int j = 0; j < amplitudes.length; j++) {
                    output.setElement(i, j, amplitudes[i].multiply(q.amplitudes[j]));
                }
            }
        }
        return output;
    }

    /**
     * Multiplies the vector by a scalar
     *
     * @param p Scalar that is multiplying vector
     * @return Vector multiplied by Scalar
     */
    public ComplexVector scalarMultiply(Complex p) {
        //initialzing the new complex vector
        ComplexVector output = new ComplexVector(amplitudes.length);
        for (int i = 0; i < amplitudes.length; i++) {
            //setting each component of the new vector to the product of the old vector with the scalar
            output.amplitudes[i] = p.multiply(amplitudes[i]);
        }
        return output;
    }

    /**
     * Converts the vector so it can be read in the terminal
     *
     * @return Vector in string format
     */
    public String toString() {
        /*
         * String output = "{"; for(int i=0;i<amplitudes.length;i++){ output =
         * output+amplitudes[i]+" "; }
         */

        String output = "";

        for (int i = 0; i < amplitudes.length; i++) {
            //Convert to binary representation
            String base = "|";

            int bits = (int) (Math.log(amplitudes.length) / Math.log(2));

            for (int t = (bits - 1); t >= 0; t--) {
                int filter = 1 << t;

                int value = ((i & filter) > 0) ? 1 : 0;

                base += value;
            }

            base += ">";

            output += "(" + amplitudes[i] + ")" + base + " ";
        }

        return output;
    }

    /**
     * Gives the number of components in the vector
     *
     * @return integer value for the number of the components in the vector
     */
    public int length() {
        return amplitudes.length;
    }

    /**
     * Normalizes the vector so has a magnitude of 1
     *
     *
     */
    public void normalize() throws VectorLengthException {
        //finds the magnitude of the vector
        float mag = this.magnitude();
        //multiplies the vector by 1/magnitude to give a vector with megnitude 1
        this.scale(new Complex((float) Math.sqrt(1.0f / mag), 0));

    }

    /**
     * Returns the complex conjugate of the vector
     *
     * @return Complex Conjugate of the vector
     */
//obtains the complex conjugate of the current vector
    public ComplexVector conjugate() {
        //initializing the conjugate vector
        Complex[] output = new Complex[this.length()];
        for (int i = 0; i < this.length(); i++) {
            //iterating through and obtaining the conjugate of each element
            output[i] = amplitudes[i].conjugate();
        }
        ComplexVector out = new ComplexVector(output);
        return out;
    }

    /**
     * Private method used in the normalization method
     *
     * @param p Scalar multiplying the vector
     */
    private void scale(Complex p) {
        for (int i = 0; i < amplitudes.length; i++) {
            //basically applys the scalar multiply method to the current vector 
            amplitudes[i] = p.multiply(amplitudes[i]);
        }

    }

    /**
     *Sets all components of the vector to 0
     */
    public void clear() {
        for (int idx = 0; idx < this.length(); idx++) {
            this.setComponent(new Complex(0, 0), idx);
        }
    }

    /**
     * Calculates the tensor product between two kets |x>|y>
     * @param V The vector being tensor producted with
     * @return The tensor product of the two vectors 
     */
    public ComplexVector tensorKetProduct(ComplexVector V) {
        ComplexVector output = new ComplexVector(this.length() * V.length());
        for (int i = 0; i < this.length(); i++) {
            for (int j = 0; j < V.length(); j++) {
                output.setComponent(this.getComponent(i).multiply(V.getComponent(j)), (V.length() * i) + j);
                //System.out.println(this.getComponent(i).multiply(V.getComponent(j))+" comp "+ ((V.length()*i) + j)+"  index");
            }
        }
        return output;
    }
}
