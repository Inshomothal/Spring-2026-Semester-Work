/********************************************************************
 * Name: Trevon Collins
 * Course: CSC 123
 * Lab: Lab05
 * Date: 02/27/2026
 * File: CheckingAccount.java
 *
 * Description: Code base for CheckingAccount class. This will
 * allow simple account creations with user-made account IDs only.
 * This class will also allow simple transfers through checks and deposits.
********************************************************************/
package lab05;

public class CheckingAccount {
    private String accountName, accountNumber;
    private double accountBalance;

    public CheckingAccount(String accountNumber, String accountHolder, double balance){
        if(accountHolder.contains(" ")){
            this.accountName = accountHolder;
        } else {
            System.out.printf("Account Name has to have a first and a last name(%s)\n", accountHolder);
            this.accountName = "UNKNOWN";
        }

        if(accountNumber.length()==8){
            this.accountNumber = accountNumber;
        } else {
            System.out.printf("Account number must be 8 digits (%s)\n", accountNumber);
            this.accountNumber = "-1";
            this.accountName = "BAD ACCOUNTNUMBER";
        }

        if (!isValidAmount(balance)) {
            System.out.printf("Can not deposit an amount with factions of a penny (%f)\n", balance);
            this.accountBalance = 0;
            this.accountName = "BAD BALANCE";
        } else if (isValidAmount(balance) && balance <= 0) {
            System.out.printf("Can not deposit a negative amount (%.2f)\n", balance);
            this.accountBalance = 0;
            this.accountName = "BAD BALANCE";
        }else {
            this.accountBalance = balance;
        }
            


    }

    public CheckingAccount(){
        accountName = "EMPTY NAME";
        accountNumber = "00000000";
        accountBalance = 0;
    }

    public void depositMoney(double amount) {
        if (!isValidAmount(amount)) {
            System.err.println("Whole pennies only");
            return;
        }

        if (amount < 0.0) {
            System.err.println("Negative deposits are invalid.");
            return;
        }
        accountBalance += amount;
    }
    
    @SuppressWarnings("UnnecessaryReturnStatement")
    public void withdrawMoney(double amount) {
        if (amount < 0.0) {
            System.err.println("Negative deposits are invalid.");
            return;
        }
        if (!isValidAmount(amount)) {
            System.err.println("Whole pennies only.");
            return;
        }
        if ((accountBalance - amount) < 0.0) {
            System.err.println("Insufficient funds.");
            return;
        }
        accountBalance -= amount;
        
    }

    public void processCheck(double amount){

        if (!isValidAmount(amount)) {
            System.err.println("Whole pennies only");
            return;
        }
        if (amount < 0.0) {
            System.err.println("Negative deposits are invalid.");
            return;
        }
        if (accountBalance < 1000) {
            accountBalance -= 0.15;
        }

        accountBalance -= amount;
    }

    public void setAccountName(String name){
        if (name.contains(" ")) {
            this.accountName = name;
            return;
        }
        System.err.println("Account must have at least a first and last name.");
    }
    
    public String getAccountName(){return accountName;}

    public void setAccountNumber(String number) {
        boolean allDigits = true;
        for (int i = 0; i < number.length(); i++){
            if (!Character.isDigit(number.charAt(i))){
                allDigits = false;
            }
        }
        if (number.length() == 8 && allDigits) {
            this.accountNumber = number;
            return;
        }
        
        if (number.length() > 8){
            System.err.println("The number should not be more than 8 digits!!!");
        }
        
        if (!allDigits){
            System.err.println("Only numbers");
        }
    }

    public String getAccountNumber() {return accountNumber;}

    public void setAccountBalance(double amount){
        
        if (isValidAmount(amount) && amount > 0.0) {
            this.accountBalance = amount;
            return;
        }
        
        if (!isValidAmount(amount)) {
            System.err.println("Whole pennies only!! ");
        }
        
        if (amount < 0.0) {
            System.err.println("Setting negative balances is invalid.");
        }
    }
    public double getAccountBalance(){return accountBalance;}

    @Override
    public String toString() {
        return String.format("Account: %s     Owner: %s     Balance: %.2f\n", 
                                accountNumber,
                                accountName,
                                accountBalance);
    }

    private boolean isValidAmount(double amount){
        int pennies = (int)(amount * 100);
        double normalized = (double)(pennies / 100.0);
        return (amount == normalized);
    }
}
