/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: Lab04
* Date: 02/19/2026
* File: CheckingAccount.java
*
* Description: Code base for CheckingAccount class. This will
* allow basic account creations with implicit account IDs only.
********************************************************************/
package checkingaccount;

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

    public void deposit(double d){
        if (d != (double)((int)(d * 100) / 100)){
            System.out.println("Whole pennies only");
            return;
        }
        accountBalance += d;
    }

    public void processCheck(double d){
        if (accountBalance < 1000) {
            accountBalance -= 0.15;
        }

        if (d != (double)((int)(d * 100) / 100)){
            System.out.println("Whole pennies only");
            return;
        }
        accountBalance -= d;
    }

    public void setAccountName(String name){
        accountName = name;
    }
    
    public String getAccountName(){return accountName;}

    public void setAccountNumber(String number){
        if (number.length() > 8){
            System.out.println("The number should not be more than 8 digits!!!");
            return;
        }
        
        boolean allDigits = true;
        for (int i = 0; i < number.length(); i++){
            if (!Character.isDigit(number.charAt(i))){
                allDigits = false;
            }
        }
        if (!allDigits){
            System.out.println("Only numbers");
            return;
        }

        accountNumber = number;
    }

    public String getAccountNumber() {return accountNumber;}

    public void setAccountBalance(double amount){
        
        int temp = (int)(amount * 100);
        if (amount != (double)(temp / 100.0)){
            System.out.println("Whole pennies only!! ");
            return;
        }
        accountBalance = amount;
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
