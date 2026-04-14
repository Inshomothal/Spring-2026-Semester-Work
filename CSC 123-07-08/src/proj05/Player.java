/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: Player.java
*
* Description:  The main avater for the player. Stores health, weapons
                and name. Game ends if player's health reaches 0.
********************************************************************/

package proj05;

public class Player {
    private String name;
    private int health;
    private Weapon leftHand, rightHand;

    public Player (String _name, int _health){
        this.name = _name;
        this.health = _health;

    }

    public String getName(){}

    public int getHealth(){}

    public Weapon getLeftHand(){}

    public Weapon getRightHand(){}

    public void setLeftHand(weapon _w){}

    public void setRightHand(weapon _w){}

    public void recordDamage (int _amount){}

    public String toString(){}
