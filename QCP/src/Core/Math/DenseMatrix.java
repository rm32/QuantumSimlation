package Core.Math;

/**
 * Matrix extention where all elements of the matrix are stored.
 */
public class DenseMatrix extends Matrix {

    /**
     * The two-dimensional array containing the values
     */
    private Complex values[][];
    /**
     * The number of zero elements
     */
    private int nonZeroElementCount = 0;

    /**
     * Constructor that makes a zero matrix of specified order.
     *
     * @param n
     */
    public DenseMatrix(int n) {
        setOrder(n);
        values = new Complex[n][n];

        //Initialize all elements to 0.
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                values[x][y] = new Complex(0.0, 0.0);
            }
        }
    }

    /**
     * Constructor that makes a zero matrix of specified order and with values
     * from an array.
     *
     * The values array should be a concatenated list of all the rows of the
     * desired matrix. i.e. {row1elem1, row1elem2,..., row1elemN, row2elem1,
     * row2elem2,..., rowN,elemN} It is assumed that user gives enough values to
     * fill the whole matrix.
     *
     * @param ord matrix order
     * @param vals values to put in matrix
     */
    public DenseMatrix(int ord, Complex[] vals) {
        setOrder(ord);
        values = new Complex[getOrder()][getOrder()];
        for (int row = 0; row < getOrder(); row++) {
            for (int col = 0; col < getOrder(); col++) {
                int elem = row * getOrder() + col;
                setElement(row, col, vals[elem]);
            }
        }
    }

    /**
     * Constructor that creates a matrix from a 2D array of complex elements
     *
     * @param val 2D array of complex elements representing the matrix
     */
    public DenseMatrix(Complex[][] val) {
        values = val;
        setOrder(val.length);
    }

    @Override
    public int getNonZeroElmentCount() {
        return nonZeroElementCount;
    }

    public Complex getElement(int row, int column) {
        return values[row][column];
    }

    public void setElement(int row, int column, Complex z) {
        //adjust the counter for number of non-zero elements in the matrix
        try {
            if (getElement(row, column).magnitude() == 0 && z.magnitude() != 0) {
                nonZeroElementCount++;
            } else if (getElement(row, column).magnitude() != 0 && z.magnitude() == 0) {
                nonZeroElementCount--;
            }
        } catch (NullPointerException e) {
            //null element is treated as a zero element
            if (z.magnitude() != 0) {
                nonZeroElementCount++;
            }
        }

        values[row][column] = z;
    }

    /**
     * Convert matrix to string.
     *
     * @return String out
     */
    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < getOrder(); i++) {
            for (int j = 0; j < getOrder(); j++) {
                out = out + values[i][j] + "  ";
            }
            out = out + "\n";
        }
        return out;
    }

    /**
     * Multiplies matrix by a scalar
     *
     * @param scalar The scalar to be multiplied by
     * @return The resulting matrix
     */
    public DenseMatrix multiply(Complex scalar) {
        DenseMatrix out = new DenseMatrix(values.length);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                out.setElement(i, j, values[i][j].multiply(scalar));
            }
        }
        return out;
    }

    /**
     * Multiplies matrix by a scalar
     *
     * @param scalar The scalar to be multiplied by
     * @return The resulting matrix
     */
    public DenseMatrix multiply(float scalar) {
        DenseMatrix out = new DenseMatrix(values.length);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                out.setElement(i, j, values[i][j].scalarmultiply(scalar));
            }
        }
        return out;
    }
}
