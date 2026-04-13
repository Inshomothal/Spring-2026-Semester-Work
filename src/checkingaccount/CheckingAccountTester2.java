package checkingaccount;

import java.util.Scanner;

public class CheckingAccountTester2 {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        CheckingAccount[] accounts = new CheckingAccount[6];

        accounts[0] = new CheckingAccount();
        accounts[0].setAccountName("John Doe");
        accounts[0].setAccountNumber("00000123");
        accounts[0].setAccountBalance(123.00);
        System.out.println("Account one is " + accounts[0]);

        accounts[1] = new CheckingAccount("00000124", "Jane Doe", 12.3);
        System.out.println("Account two is " + accounts[1]);

        System.out.println("Creating a account with a bad name (no space)");
        accounts[2] = new CheckingAccount("00000125", "BADNAMENOSPACE", 1234.50);
        System.out.println("Account two is " + accounts[2]);

        System.out.println("Creating a account with an invalid balace (-1)");
        accounts[3] = new CheckingAccount("00000126", "BAD BALANCE", -1);
        System.out.println("Account two is " + accounts[3]);

        System.out.println("Creating a account with an invalid balace (0.001)");
        accounts[4] = new CheckingAccount("00000127", "BAD BALANCE", 0.001);
        System.out.println("Account two is " + accounts[4]);

        System.out.println("Creating a account with an invalid Account Number");
        accounts[5] = new CheckingAccount("420", "BAD ACCOUNTNUMBER", 123);
        System.out.println("Account two is " + accounts[5]);
        System.out.println("Account three is " + accounts[2]);
    }
}
