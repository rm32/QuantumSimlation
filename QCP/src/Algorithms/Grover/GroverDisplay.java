package Algorithms.Grover;

import Core.Math.Complex;
import Core.Math.ComplexVector;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * Outputs Grover as a GUI display
 *
 * does not behave the same for small qubits.
 */
public class GroverDisplay extends JPanel {

    /**
     * Basis vectors constructed through Gram Schmidt.
     */
    ComplexVector e1, e2;
    /**
     * JFrame reference.
     */
    JFrame frame;
    /**
     * Container for all vectors that are to be drawn.
     */
    public ArrayList<ComplexVector> vectors = new ArrayList<ComplexVector>();

    /**
     * Constructs a display from two vectors in n-complex space.
     *
     * @param E1 First basis vector used for Gram Schmidt.
     * @param E2 Second basis vector used for Gram Schmidt.
     */
    public GroverDisplay(ComplexVector E1, ComplexVector E2) {
        //Set e1 basis
        e1 = E1;

        try {
            //Gram schmidt algorithm
            //Constructs orthonormal basis from superposition and answer vectors

            Complex dotvu = E1.dotprod(E2);
            Complex dotuu = E1.dotprod(E1);

            Complex coj = dotuu.conjugate();

            dotvu = dotvu.multiply(coj);
            dotuu = dotuu.multiply(coj);

            Complex proj = dotvu.scalarmultiply(1.0f / dotuu.getReal());

            e2 = E2.add(E1.scalarMultiply(proj).scalarMultiply(new Complex(-1.0f, 0.0f)));

            e1.normalize();
            e2.normalize();
        } catch (Throwable E) {
            System.out.println("Gram Schmidt failed.");
        }

        //Set frame
        frame = new JFrame("Grover Display");
        frame.setSize(800, 800);
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    /**
     * Overrides JPanels paint method to draw the diagram.
     */
    public void paintComponent(Graphics G) {
        super.paintComponent(G);

        Graphics2D g2 = (Graphics2D) G;

        //Draw axis
        g2.setColor(Color.BLACK);

        g2.setStroke(new BasicStroke(3));

        g2.drawLine(400, 50, 400, 750);
        g2.drawLine(50, 400, 750, 400);

        //Draw labels
        g2.drawString("|A>", 390, 25);
        g2.drawString("|S>", 725, 390);

        g2.drawString("Recursion: " + vectors.size(), 50, 50);

        //Draw vectors
        g2.setStroke(new BasicStroke(2));

        for (int idx = 0; idx < vectors.size(); idx++) {
            ComplexVector vec = vectors.get(idx);

            try {
                //Project vector into basis...
                Complex e1c = e1.dotprod(vec);
                Complex e2c = e2.dotprod(vec);

                //Color appropriately
                if (idx + 1 < vectors.size()) {
                    g2.setColor(Color.getHSBColor(0.2f + 0.57f * (float) idx / (float) vectors.size(), 1.0f, 1.0f));
                } else {
                    g2.setColor(Color.RED);
                }

                //Draw vector
                g2.drawLine(400, 400, 400 + (int) (e1c.magnitude() * 350), 400 - (int) (e2c.magnitude() * 350));
            } catch (Throwable E) {
                //Do nothing, just ignore...
            }
        }
    }
}