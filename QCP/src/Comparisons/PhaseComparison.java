package Comparisons;

import Core.QRegister;
import Operators.Phase.*;

public class PhaseComparison
{
    public static void main( String[] args )
    {
        try
        {
            QRegister initial = new QRegister( 2, 3 );
            
            System.out.println( "Initial:" );
            System.out.println( initial );
            
            float shift = (float)Math.PI/4.0f;
            
            PhaseOperator bitOp = new PhaseBitOperator( 0, shift );
            PhaseOperator matrixOp = new PhaseMatrixOperator( 0, shift );
            
            QRegister bitRegister = bitOp.apply( initial );
            System.out.println( "Bit: " );
            System.out.println( bitRegister);
            
            QRegister matrixRegister = matrixOp.apply( initial );
            System.out.println( "Matrix: " );
            System.out.println( matrixRegister );
        }
        catch( Throwable E )
        {
            E.printStackTrace();           
        }
    }
}
