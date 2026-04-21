/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: CrabBattleGame.java
*
* Description:  The program that will manage the systems of the game.
*               How will the players fair?  
********************************************************************/

package proj05;

import java.util.Scanner;

public class CrabBattleGame {
    Player player;
    CrabBoss crab;
    Scanner keyboard;

    public CrabBattleGame() {
        keyboard = new Scanner(System.in);
        System.out.print("Please give the player a name: ");

        this.player = new Player(keyboard.nextLine(), 50);
        this.crab = new CrabBoss(50);
    }
    
    public void play() {
        boolean gameContinue = true;
        while (gameContinue) {
            displayMenu();

            if (player.getHealth() == 0) playerLoses();
            if (crab.getHealth() <= 0) playerWins();
            
            System.out.println("""
                    Go again?
                        1. Yes!
                        2. No
                    """);
            int choice = Integer.parseInt(keyboard.nextLine());

            if (choice == 1) {
                crab = new CrabBoss(50);
                player = new Player(player.getName(), 50);
            }
            gameContinue = (choice == 1);
        }
        
        System.out.println("Thanks for playing! Goodbye!");
    }

    private void displayMenu() {
        int option;
        boolean trueNumber = false;

        showStatus();
        System.out.print("""

            With unrelenting courage you...
                1. Strike!
                2. Search for a weapon
                3. Quit

            What would you like to do?""" + " ");
        while (!trueNumber) {
            try {
                option = Integer.parseInt(keyboard.nextLine());
                System.out.println("");
                trueNumber = true;
            } catch (NumberFormatException eN) {
                System.err.println("Please enter a valid number");
                continue;
            }

            switch (option) {
                case 1 -> playerAttack();
                case 2 -> searchForWeapons();
                case 3 -> {
                }
                default -> {
                    System.err.println("Please pick a proper choice.");
                    trueNumber = false;
                }
            }
        }


    }

    private void playerAttack() {
        int lDam, rDam;

        lDam = player.getLeftHand().rollDamage();
        rDam = player.getRightHand().rollDamage();
        
        System.out.printf("You swing your %s and %s at %s, dealing %d.\n\n"
                                , player.getLeftHand().getName()
                                , player.getRightHand().getName()
                                , crab.getName()
                                , lDam + rDam
                                );
        
        crab.recordDamage(lDam);
        crab.recordDamage(rDam);
        
        if(isGameOver()) return;
        crabAttack();
    }

    private void crabAttack() {
        int lClaw, rClaw;

        lClaw = crab.getLeftClaw().roll();
        rClaw = crab.getRightClaw().roll();
        
        System.out.printf("Gurgle! Gurgle!! %s strikes you for %d\n\n!!"
                                , crab.getName()
                                , lClaw + rClaw
                                );

        player.recordDamage(lClaw);
        player.recordDamage(rClaw);
        
        if(isGameOver()) return;
        displayMenu();
    }

    private void searchForWeapons() {
        String  lh = "Left Hand: " + player.getLeftHand().getName(),
                rh = "Right Hand: " + player.getRightHand().getName(),
                ld = "1-" + player.getLeftHand().damage(),
                rd = "1-" + player.getRightHand().damage();
        int option;
        boolean trueChoice = false;

        System.out.printf("""
                %-20s %20s
                %-20s %20s

                Which hand would you like to replace?
                    1. Left Hand
                    2. Right Hand
                    3. Both
                """
                , lh, rh
                , ld, rd);
        while (!trueChoice) {
            try {
                option = Integer.parseInt(keyboard.nextLine());
                System.out.println("");
                trueChoice = true;
            } catch (NumberFormatException eN) {
                System.err.println("Please enter a valid number");
                continue;
            }

            switch (option) {
                case 1 -> replaceLeftHand();
                case 2 -> replaceRightHand();
                case 3 -> {
                    replaceLeftHand();
                    replaceRightHand();
                }
                default -> {
                    System.err.println("Please pick a proper choice.");
                    trueChoice = false;
                }
            }
        }
        displayMenu();
    }

    private void replaceLeftHand() {
        System.out.println("You search for a weapon for your left hand...");
        player.setLeftHand(randomWeapon());
        player.getLeftHand().display();
    }

    private void replaceRightHand() {
        System.out.println("You search for a weapon for your right hand...");
        player.setRightHand(randomWeapon());
        player.getRightHand().display();
    }

    private Weapon randomWeapon() {
        int weapon = (int) Math.round(Math.random() * 5);

        return switch (weapon) {
            case 3, 4 -> new Sword();
            case 5 -> new MagicSword();
            default -> new Stick();
        };
    }
    
    private void showStatus() {
        System.out.printf("""
                %s
                %-20s %20s
                %-20s %20s
                
                
                %-20s %20s
                %-20s %20s
                %s\n"""
                , formatCrabName()
                , "Crab Left Claw", "Crab Right Claw"
                , "1-" + crab.getLeftClaw().getNumSides(), "1-" + crab.getRightClaw().getNumSides()
                , player.getLeftHand().getName(), player.getRightHand().getName()
                , "1-" + player.getLeftHand().damage(), "1-" + player.getRightHand().damage()
                , formatPlayerName());
    }

    private boolean isGameOver() {
        return player.getHealth() == 0 || crab.getHealth() <= 0;
    }

    private String formatCrabName() {
        StringBuilder formattedCrabName;
        int buffer, middle;

        formattedCrabName = new StringBuilder(crab.getName() + ": " + crab.getHealth());
        middle = 41 - formattedCrabName.length();
        buffer = middle / 2;
        for (int i = 0; i < buffer; i++) {
            formattedCrabName.insert(0, " ");
            formattedCrabName.insert(formattedCrabName.length(), " ");

        }

        return formattedCrabName.toString();
    }

    private String formatPlayerName() {
        StringBuilder formattingPlayerName;
        int buffer, middle;

        formattingPlayerName = new StringBuilder(player.getName() + ": " + player.getHealth());
        middle = 41 - formattingPlayerName.length();
        buffer = middle / 2;
        for (int i = 0; i < buffer; i++) {
            formattingPlayerName.insert(0, " ");
            formattingPlayerName.insert(formattingPlayerName.length(), " ");

        }

        return formattingPlayerName.toString();
    }

    private void playerWins() {
        System.out.printf("""
                %s raises their %s and %s in triumph!
                %s is slain! Another Victory!!!

                Well done, adventurer!
                """
                , player.getName()
                , player.getLeftHand().getName()
                , player.getRightHand().getName()
                , crab.getName());
    }

    private void playerLoses() {
        System.out.printf("""
                %s raises its claws and triumph!
                Another meal is secured...

                Better luck next time...for the next adventurer, of course...
                """, crab.getName());
    }

}
