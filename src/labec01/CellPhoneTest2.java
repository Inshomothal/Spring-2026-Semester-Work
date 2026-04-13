/********************************************************************
 * Name: Malcolm McCullough
 * Course: CSC 123 – Programming and Problem Solving II
 * Lab: CellPhone Class Tester (Dank Edition)
 * Date:
 * File: CellPhoneTest2.java
 *
 * Description:
 * This program stress-tests the CellPhone class while maintaining
 * questionable emotional stability. Demonstrates constructor usage,
 * getters, setters, toString, and equals — but with vibes.
 ********************************************************************/
package labec01;

public class CellPhoneTest2 {

    public static void main(String[] args) {

        System.out.println("=== Wireless Solutions Inventory Audit ===");
        System.out.println("Running diagnostics before the phones gain sentience...\n");

        // Create phones
        CellPhone phone1 = new CellPhone("Nokia", "Brick 3310", 49.99);
        CellPhone phone2 = new CellPhone("Apple", "iPhone Overpriced Pro Max Ultra", 1499.99);
        CellPhone phone3 = new CellPhone("Samsung", "Galaxy Probably Explodes", 899.99);

        // Display phones
        System.out.println("Inventory initialized:\n");
        System.out.println(phone1);
        System.out.println(phone2);
        System.out.println(phone3);

        // Testing setters
        System.out.println("Updating price because inflation chose violence...");
        phone1.setRetailPrice(59.99);

        System.out.println("Rebranding Galaxy to something less lawsuit-y...");
        phone3.setModel("Galaxy Safe-ish Edition");

        System.out.println("\nUpdated Inventory:\n");
        System.out.println(phone1);
        System.out.println(phone3);

        // Testing equals
        System.out.println("Creating a suspiciously identical clone of phone1...");
        CellPhone phoneClone = new CellPhone("Nokia", "Brick 3310", 59.99);

        boolean result1 = phone1.equals(phoneClone);
        System.out.println("Is phone1 equal to phoneClone? " + result1);

        boolean result2 = phone1.equals(phone2);
        System.out.println("Is phone1 equal to phone2? " + result2);

        // Getter tests
        System.out.println("\nExtracting data manually because trust issues:");
        System.out.println("Manufacturer: " + phone2.getManufact());
        System.out.println("Model: " + phone2.getModel());
        System.out.printf("Retail Price: $%.2f\n", phone2.getRetailPrice());

        System.out.println("\nInventory audit complete.");
        System.out.println("If this compiled, we ship it.");
    }
}

