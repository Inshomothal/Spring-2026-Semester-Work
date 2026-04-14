/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: Sword.java
*
* Description:  A weapon stronger than a stick. This weapon deals d6
*               damage instead of a d4
********************************************************************/

package proj05;

public class Sword implements Weapon{
    private Die die;

    public Sword(){
        this.die = new Die(6);
    }
    public String getName(){
        return "Sword";
    }

    public int rollDamage(){
        return die.roll();
    }
    
    public void display(){
        System.out.println("""
                        /\
                       /  \
                       ||||
                       ||||
                       ||||
                       ||||
                       /==\
                        ][
                        ][
                You found a Sword!
                """);
    }
}
