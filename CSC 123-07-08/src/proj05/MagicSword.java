/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: MagicSword.java
*
* Description:  A magic sword that is stronger than a regular sword
*               that deals d6 damage. Has a higher chance of reaching
*               max damage.
********************************************************************/

package proj05;

public class MagicSword implements Weapon{
    private Die die;

    public MagicSword(){
        this.die = new LoadedDie(6, 6, 0.5);
    }
    public String getName(){
        return "Magic Sword";
    }

    public int rollDamage(){
        return die.roll();
    }
    
    public void display(){
        System.out.println("""
                         /\
                        /**\
                        ||||
                        ||||
                        ||||
                        ||||
                        /==\
                         ][
                        _][_
                You found a MagicSword!
                """);
    }
}
