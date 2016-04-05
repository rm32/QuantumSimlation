package Comparisons;

import Core.QRegister;
import Operators.CNot.CNotBitOperator;
import Operators.CNot.CNotCompositeOperator;
import Operators.CNot.CNotMatrixOperator;
import Operators.CNot.CNotOperator;

public class CNotComparison
{
    public static void main( String[] args )
    {
        try
        {
            QRegister initial = new QRegister( 2, 3 );
            
            System.out.println( "Initial:" );
            System.out.println( initial );
            System.out.println();
            
            CNotOperator bitOp = new CNotBitOperator( 0, 1 );
            CNotOperator matrixOp = new CNotMatrixOperator( 0, 1 );
            CNotOperator comOp = new CNotCompositeOperator( 0, 1 );
            
            QRegister matrixRegister = matrixOp.apply( initial );
            System.out.println( "Matrix: " );
            System.out.println( matrixRegister );
             
            QRegister bitRegister = bitOp.apply( initial );
            System.out.println( "Bit: " );
            System.out.println( bitRegister);
            
            QRegister comRegister = comOp.apply( initial );
            System.out.println( "Composite: " );
            System.out.println( comRegister );
        }
        catch( Throwable E )
        {
            E.printStackTrace();
        }
    }
}
