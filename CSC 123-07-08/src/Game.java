/*
Mango
Name: Trevon Collins
Course: CSC 123
Lab: Lab 03
Date: 02/03/2026
Descriptions: Is the space where the game runs and creates instances of Xs and Os.
*/

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        char p = 'X';
        XsAndOs XsOs = new XsAndOs();
        int r, c;

        while (!(XsOs.isWinner('X') || XsOs.isWinner('O') || XsOs.isFull())) {
            XsOs.displayBoard();
            System.out.print("'" + p + "', choose your location (row  column): ");
            r = getInput(keyboard);
            c = getInput(keyboard);

            while (XsOs.isValid(r, c) == false || XsOs.playerAt(r, c) != ' ') {
                if (XsOs.isValid(r, c) == false)
                    System.out.println("That is not a valid location. Try again.");
                else if (XsOs.playerAt(r, c) != ' ')
                    System.out.println("That location is already full. Try again.");

                System.out.print("Choose your location (row, column): ");
                r = getInput(keyboard);
                c = getInput(keyboard);
            }
            System.out.println("");
            XsOs.playMove(p, r, c);

            if (p == 'X') p = 'O';
            else p = 'X';
        }

        XsOs.displayBoard();

        if (XsOs.isWinner('X')) System.out.println("X is the winner!");
        if (XsOs.isWinner('O')) System.out.println("O is the winner!");
        if (XsOs.isCat())
            System.out.println("It is a cat's game (a cat can not catch his own tail).");
    }



    private static int getInput(Scanner keyboard){
        int number = 0;
        do {
            while (!keyboard.hasNextInt()) {
                System.out.println("That's not a number between 0 and 2!");
                keyboard.next(); // this is important!
            }
            number = keyboard.nextInt();
            if( number < 0 || number > 2 )
                System.out.println("That's not a number between 0 and 2!");
        }while ( number < 0 || number > 2 );
        return number;
    }
}
