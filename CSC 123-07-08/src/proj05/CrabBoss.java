/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: CrabBoss.java
*
* Description:  The object that will manage the capabilities of a crab
*               boss.
********************************************************************/
package proj05;

public class CrabBoss {
    private int health;
    private Die leftClaw, rightClaw;
    private String legendaryName;
    private String[] name = { "Daudulus", "Harriot", "Garus", "Entrand" };
    private String[] legend = { "Destroyer", "Gluttonous", "Hangry", "Shyguy" };

    public CrabBoss(int _health) {
        this.health = _health;
        leftClaw = new Die(4);
        rightClaw = new LoadedDie(6, 6, 0.5);
        legendaryName = crabLegendIsBorn();
    }

    public int getHealth() {
        return health;
    }
    
    public Die getLeftClaw() {
        return leftClaw;
    }
    
    public Die getRightClaw() {
        return rightClaw;
    }
    
    public void recordDamage(int _amount) {
        if (this.health < _amount) {
            this.health = 0;
        } else {
            this.health -= _amount;
        }
    }
    
    public String toString() {
        return this.legendaryName;
    }

    private String crabLegendIsBorn() {
        return name[(int)(Math.round(Math.random() * (name.length - 1)))] + " the " + legend[(int)Math.round((Math.random() * (legend.length - 1)))];
    }

    public String getName() {
        return legendaryName;
    }
}
