package Core.Math;

/**
 * Class that calculates the modulo of a number with respect to another number 
 */
public class Modulo {
    long val,mod;
    /**
     * Constructor for modulo object
     * @param value value of x in expression x Mod N
     * @param modulus value of N in expression x Mod N
     */
    public Modulo(long value, long modulus){
        val = value;
        mod = modulus;
    }
    /**
     * Method to calculate x modulo N
     * @return x Mod N
     */
    public long getValue(){
        return (val%mod+mod)%mod;
    }

}
