/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: lab06
* Date: 03/19/2026
* File: Die.java
*
* Description: Die is the core file that will generate a random side of a die
* allowing the game to commence.
********************************************************************/

package lab06;

import java.util.Random;

public class Die {
    private int numSides;
    private Random random;

    /**
     * Creates a die with 6 sides and a persistent seed for rolls.
     */
    public Die() {
        this(6, new Random());
    }

    /**
     * Creates a die with a specified {@code int} number of sides and
     * a persistent seed for rolls.
     * 
     * @param numSides The number of sides for the die
     */
    public Die(int numSides) {
        this(numSides, new Random());
    }

    /**
     * Creates a die with a specified {@code int} number of sides and
     * a {@code Random} object provided as a parameter.
     * 
     * @param numSides The number of sides the die has
     * @param random The random object with a persistent seed for rolls
     */
    public Die(int numSides, Random random) {
        if (numSides < 1) {
            this.numSides = 6;
            System.err.println("Error: Number of sides must be at least 1. Using default of 6.");
        } else {
            this.numSides = numSides;
        }

        if (random == null) {
            this.random = new Random();
        } else {
            this.random = random;
        }
    }

    public int roll() {
        return random.nextInt(numSides) + 1; // nextInt(n) gives 0 to n - 1, so +1 shifts it to 1..n in an uniform discrete distribution
                                             // using a provided Random lets test code control the sequence
    }
    
    public int getNumSides() {
        return numSides;
    }

    public void setNumSides(int numSides) {
        if (numSides < 1) {
            this.numSides = 6;
            System.err.println("Error: Number of sides must be at least 1. Using default of 6.");
        } else {
            this.numSides = numSides;
        }
    }
    
    @Override
    public String toString() {
        return "Die[sides=" + numSides + "]";
    }

    /*
    * AI Review Summary:
    * 1. Class is mostly well made and decently defensive
    * 2. Error handling is a bit surprising and noisy
    * 3. Random random should be validated as well
    * 4. Field mutability could be reconsidered since Random random
    *    doesn't change after initialization.
    * 5. Comments could be cleaner and grammar checked
    * Student Response:
    *    I've implemented the validation that it suggested. I didn't think
    *  about a case where null is entered so this was a valuable bit of
    *  advice. The mutability and noisiness is unnecessary to change to me
    *  because our program's small and should be fine with the error report
    *  inside Die. I also like the way I made my comments so I'll keep that.
    */
    
    /*
    * Trace example:
    * 
    * roll() works by running the nextInt(int) method. Let's say
    * numSides is 8, when you use roll() nextInt(numSides) it may
    * generate the highest result 7, so roll() would output 8. 
    * If it generates 0, roll() would output 1.
     */
}
