package Operators.ShorFunction;
import Core.Exceptions.NegativeIndexException;
import Core.Math.Complex;
import Core.Math.ComplexVector;
import Core.Math.Modulo;
import Core.Math.Power;
import Core.Operator;
import Core.QRegister;
import Operators.CompositeOperator;

/**
 *Class that represents the function that applies the transform |x>|1> -> |x>|m^x mod N>
 */
public class ShorFunction implements Operator {
  /**
   * @param args The first QRegister |x>
   * @param funcValues The second QRegister |m^x Mod N>
   * @param m the value m in the expression m^x Mod N
   * @param n the value N in the expression m^x Mod N
   */
    private QRegister args,funcValues;
   private int m,n;
   /**
    * Constructor for the gate
    * @param arguments the QRegister |x> in |x>|1> -> |x>|m^x Mod N>
    * @param m the integer m in the expression above
    * @param n the integer N in the expression above
    */
   public ShorFunction(QRegister arguments,int m, int n) {
       args = arguments;
       this.m = m;
       this.n = n;
   }
   /**
    * Method that applies the gate to the System, in order to still be consistent with operator interface only acts on second QRegister but in reality acts on whole system
    * @param functionValues The Second QRegister that holds the values of the function
    * @return QRegister representing entire state |x>|m^x Mod N>
    * @throws NegativeIndexException 
    */
   public QRegister apply(QRegister functionValues) throws NegativeIndexException{
       this.funcValues = functionValues;
       ComplexVector funcVector =  funcValues.getComplexVector();
       
       ComplexVector argvector = args.getComplexVector();
       //Combining QRegisters to obtain QRegister representing the state |x>|m^x Mod N>
       ComplexVector reg = argvector.tensorKetProduct(funcVector);
       ComplexVector regout = new ComplexVector(reg.length());
       //Bit twiddling to obtain state of second QRegister
       for (int i=0; i<argvector.length();i++){
           Modulo funcValue = new Modulo(Power.pow(m,i),n);
           long val = funcValue.getValue();
           regout.setComponent(new Complex(1,0),(int)val+(i*funcVector.length()));
          // System.out.println(regout);
       }
       QRegister out = new QRegister(regout);
       return out;   
       
   }
   /**
    * Method to apply Gate to Gate or system of gates
    * @param Op Gate/System of gates this gate is being applied to
    * @return  System of gates after this gate has been added
    */
   public Operator apply(Operator Op){
       return new CompositeOperator(this,Op);
   }
}
