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
        this.leftHand = new Stick();
        this.rightHand = new Stick();

    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public Weapon getLeftHand() {
        return leftHand;
    }

    public Weapon getRightHand() {
        return rightHand;
    }

    public void setLeftHand(Weapon _w) {
        this.leftHand = _w;
    }

    public void setRightHand(Weapon _w) {
        this.rightHand = _w;
    }

    public void recordDamage(int _amount) {
        if (this.health < _amount) {
            this.health = 0;
        } else {
            this.health -= _amount;
        }
    }

    public String toString() {
        return this.name;
    }
}
