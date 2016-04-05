package Core.Math;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Matrix extention where only non-zero magnitude elements of the matrix are
 * stored.
 *
 */
public class SparseMatrix extends Matrix {

    Map<Integer, Complex> values = new HashMap<Integer, Complex>();

    /**
     * Constructor that makes a zero matrix of specified order.
     *
     * @param order
     */
    public SparseMatrix(int order) {
        setOrder(order);
    }

    /**
     * Constructor that makes a zero matrix of specified order and with values
     * from an array.
     *
     * The values array should be a concatonated list of all the rows of the
     * desired matrix. i.e. {row1elem1, row1elem2,..., row1elemN, row2elem1,
     * row2elem2,..., rowN,elemN} It is assumed that user gives enough values to
     * fill the whole matrix.
     *
     * @param order matrix order
     * @param vals values to put in matrix
     */
    public SparseMatrix(int order, Complex[] vals) {
        setOrder(order);
        for (int i = 0; i < vals.length; i++) {
            int column = i % getOrder();
            int row = (i - column) / getOrder();
            setElement(row, column, vals[i]);
        }
    }

    @Override
    public int getNonZeroElmentCount() {
        return values.size();
    }

    /**
     * 
     * @param row
     * @param column
     * @return 
     */
    public Complex getElement(int row, int column) {
        int element = row * getOrder() + column;

        if (values.containsKey(element)) {
            return values.get(element);
        } else {
            return new Complex(0, 0);
        }
    }

    /**
     * 
     * @param row
     * @param column
     * @param z 
     */
    public void setElement(int row, int column, Complex z) {
        int element = row * getOrder() + column;
        if (z.magnitude() != 0) {
            values.put(element, z);
        } else {
            values.remove(element);
        }
    }

    /**
     * Returns a string representation of the matrix. Indexing is from 1, not 0.
     *
     * @return string representation of matrix
     */
    public String toString() {
        //Get Map in Set interface to get key and value
        Set s = values.entrySet();

        //Move next key and value of Map by iterator
        Iterator it = s.iterator();

        String out = "";
        while (it.hasNext()) {
            // key=value separator this by Map.Entry to get key and value
            Map.Entry m = (Map.Entry) it.next();
            int key = (Integer) m.getKey();
            Complex value = (Complex) m.getValue();

            int column = key % getOrder();
            int row = (key - column) / getOrder();


            out = out + "([" + (row + 1) + ", " + (column + 1) + "] " + value + ")\n";
        }
        return out;
    }
}
