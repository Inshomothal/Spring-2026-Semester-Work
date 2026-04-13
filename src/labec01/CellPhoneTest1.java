/********************************************************************
 * Name: Malcolm McCullough
 * Course: CSC 123 – Programming and Problem Solving II
 * Lab: CellPhone Class Tester
 * Date:
 * File: CellPhoneTest1.java
 *
 * Description:
 * This program tests the CellPhone class.
 *
 * The program:
 * - Prompts the user for manufacturer, model, and retail price
 * - Creates a CellPhone object using constructor arguments
 * - Displays object data using getter methods
 * - Modifies object fields using setter methods
 * - Prints object data using toString
 * - Tests object equality using the equals method
 *
 * This file demonstrates object creation, method invocation,
 * user input handling, and object comparison.
 ********************************************************************/
package labec01;

import java.util.Scanner;

public class CellPhoneTest1 {
    public static void main(String[] args) {
        String testMan; // To hold a manufacturer
        String testMod; // To hold a model number
        double testPrice; // To hold a price
        String dummyMoveToNextLine;

        // Create a Scanner object for keyboard input.
        Scanner keyboard = new Scanner(System.in);

        // Get the manufacturer name.
        System.out.print("Enter the manufacturer: ");
        testMan = keyboard.nextLine();

        // Get the model number.
        System.out.print("Enter the model number: ");
        testMod = keyboard.nextLine();

        // Get the retail price.
        System.out.print("Enter the retail price: ");
        testPrice = keyboard.nextDouble();
        dummyMoveToNextLine = keyboard.nextLine(); // move to next line

        // Create an instance of the CellPhone class,
        // passing the data that was entered as arguments
        // to the constructor.
        CellPhone phone = new CellPhone(testMan, testMod, testPrice);

        // Get the data from the phone and display it.

        System.out.printf("\n%s\n", "Here is the data that you provided: ");
        System.out.printf("Manufacturer: %s\n", phone.getManufact());
        System.out.printf("Model number: %s\n", phone.getModel());
        System.out.printf("Retail price: $%.2f\n", phone.getRetailPrice());

        // Reset the manufacture, model and price
        // Get and set manufactturer
        System.out.printf("%s\n", "Enter the manufacturer: ");
        testMan = keyboard.nextLine();
        phone.setManufact(testMan);

        // Get and set the model number.
        System.out.printf("%s\n", "Enter the model number: ");
        testMod = keyboard.nextLine();
        phone.setModel(testMod);

        // Get and set the retail price.
        System.out.printf("%s\n", "Enter the retail price: ");
        testPrice = keyboard.nextDouble();
        phone.setRetailPrice(testPrice);

        System.out.printf("\n%s", phone);

        CellPhone phone2 = new CellPhone(testMan, testMod, testPrice);
        boolean b1 = phone.equals(phone2);
        System.out.printf("It is %b that phone and phone2 are equal\n", b1);

        CellPhone phone3 = new CellPhone("Verizon", "Apple XS", 1234.56);
        b1 = phone.equals(phone3);
        System.out.printf("It is %b that phone and phone3 are equal\n", b1);
    }
}
