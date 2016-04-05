package Comparisons;

import Core.QRegister;
import Operators.CV.CVBitOperator;
import Operators.CV.CVMatrixOperator;
import Operators.CV.CVOperator;

public class CVComparison
{
    public static void main( String[] args )
    {
        try
        {
            QRegister initial = new QRegister( 2, 3 );
            
            System.out.println( "Initial:" );
            System.out.println( initial );
            
            CVOperator bitOp = new CVBitOperator( 0, 1 );
            CVOperator matrixOp = new CVMatrixOperator( 0, 1 );
            
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
