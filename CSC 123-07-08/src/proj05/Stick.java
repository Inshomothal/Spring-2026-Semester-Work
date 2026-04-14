/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: Stick.java
*
* Description:  This is a basic weapon that a player starts with. Deals
*               d4 damage.
********************************************************************/
package proj05;

public class Stick implements Weapon{
    private Die die;

    public Stick(){
        this.die = new Die(4);
    }
    public String getName(){
        return "Stick";
    }

    public int rollDamage(){
        return die.roll();
    }

    public void display(){}
}
