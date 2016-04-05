package Comparisons;

import Core.QRegister;
import Operators.Hadamard.HadamardBitOperator;
import Operators.Hadamard.HadamardMatrixOperator;
import Operators.Hadamard.HadamardOperator;

public class HadamardComparison
{
    public static void main( String[] args )
    {
        try
        {
            QRegister initial = new QRegister( 2, 0 );
            
            System.out.println( "Initial:" );
            System.out.println( initial );
            
            HadamardOperator bitOp = new HadamardBitOperator( 0 );
            HadamardOperator matrixOp = new HadamardMatrixOperator( 0 );
            
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
