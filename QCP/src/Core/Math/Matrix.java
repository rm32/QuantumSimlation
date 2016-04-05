package Core.Math;

/**
 * An abstract class for matrix algebra.
 */
public abstract class Matrix {

    /**
     * Order of the matrix
     */
    private int order;

    /**
     * Returns specified matrix element.
     *
     * @param row
     * @param column
     * @return matrix element at (row, column)
     */
    public abstract Complex getElement(int row, int column);

    /**
     * Sets matrix element at (row, column) to z.
     *
     * @param row
     * @param column
     * @param z complex number to put at (row, column)
     */
    public abstract void setElement(int row, int column, Complex z);

    /**
     * Returns number of non-zero elements in matrix.
     *
     * @return number of non-zero elements in matrix
     */
    public abstract int getNonZeroElmentCount();

    /**
     * Returns string representation of matrix.
     *
     * @return string representation of matrix
     */
    @Override
    public abstract String toString();

    /**
     * Decides and returns zero matrix of most memory efficient type.
     *
     * Multiplies the non zero element count in m1 with that in m2 to estimate
     * non zero entry count in resulting matrix. If the product is more than
     * half of total element count in matrix, return a dense matrix. Otherwise
     * return a sparse matrix.
     *
     * @param m1
     * @param m2
     * @return zero matrix of most memory efficient type
     */
    public Matrix makeMultiplyResult(Matrix m1, Matrix m2) {
        int resultNonZeroElementCount = m1.getNonZeroElmentCount() * m2.getNonZeroElmentCount();
        int resultOrder = m1.getOrder();

        if (resultNonZeroElementCount > (resultOrder * resultOrder / 2)) {
            return new DenseMatrix(resultOrder);
        } else {
            return new SparseMatrix(resultOrder);
        }
    }

    /**
     * Decides and returns zero matrix of most memory efficient type.
     *
     * Multiplies the non zero element count in m1 with that in m2 to estimate
     * non zero entry count in resulting matrix. If the product is more than
     * half of total element count in matrix, return a dense matrix. Otherwise
     * return a sparse matrix. The resulting matrix of a tensor product is of
     * dimensions m1.order x m2.order.
     *
     * @param m1
     * @param m2
     * @return zero matrix of most memory efficient type
     */
    public Matrix makeTensorResult(Matrix m1, Matrix m2) {
        int resultNonZeroElementCount = m1.getNonZeroElmentCount() * m2.getNonZeroElmentCount();
        int resultOrder = m1.getOrder() * m2.getOrder();

        if (resultNonZeroElementCount > (resultOrder * resultOrder / 2)) {
            return new DenseMatrix(resultOrder);
        } else {
            return new SparseMatrix(resultOrder);
        }
    }

    /**
     * Returns matrix order.
     *
     * @return matrix order
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets matrix order.
     *
     * @param n order
     */
    public void setOrder(int n) {
        order = n;
    }

    /**
     * Multiplies matrix by a vector.
     *
     * @param v vector
     * @return matrix * v
     */
    public ComplexVector multiply(ComplexVector v) {
        ComplexVector result = new ComplexVector(order);
        for (int i = 0; i < order; i++) {
            Complex z = new Complex();
            for (int j = 0; j < order; j++) {
                z = z.add(getElement(i, j).multiply(v.getComponent(j)));
            }
            result.setComponent(z, i);
        }

        return result;
    }

    /**
     * Multiplies matrix by another matrix.
     *
     * Use: A.multiply(B) = AB
     *
     * @param m
     * @return matrix * m
     */
    public Matrix multiply(Matrix m) {
        Matrix result = makeMultiplyResult(this, m);
        //Matrix result = new SparseMatrix(order);

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                Complex z = new Complex();
                for (int r = 0; r < order; r++) {
                    z = z.add(getElement(i, r).multiply(m.getElement(r, j)));
                }
                result.setElement(i, j, z);
            }
        }
        return result;
    }

    /**
     * Performs matrix tensor product.
     *
     * Use: A.tensorProduct(B) = AxB
     *
     * @param m
     * @return matrix x m
     */
    public Matrix tensorProduct(Matrix m) {
        Matrix result = makeTensorResult(this, m);

        //i and j cycle through this matrix; k and l cycle through m matrix
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                for (int k = 0; k < m.order; k++) {
                    for (int l = 0; l < m.order; l++) {
                        //here each "sub-matrix" is calculated and put into the correct part of the
                        //full matrix by shifting the entries by a factor of this matrix's dimension [m.order*i+k][m.order*j+l]
                        result.setElement(m.order * i + k, m.order * j + l, getElement(i, j).multiply(m.getElement(k, l)));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Static method for creating an identity matrix of any dimensions.
     *
     * @param d the desired dimension of the identity matrix
     * @return identity matrix of dimension d
     */
    public static Matrix identityMatrix(int d) {
        Matrix id = new SparseMatrix(d);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i == j) {
                    id.setElement(i, j, new Complex(1, 0));
                } else {
                    id.setElement(i, j, new Complex());
                }
            }
        }
        return id;
    }
}