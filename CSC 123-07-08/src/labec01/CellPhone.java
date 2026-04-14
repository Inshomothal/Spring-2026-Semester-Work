/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: Lab Extra Credit 01
* Date: 02/24/2026
* File: CellPhone.java
*
* Description: This is a cellphone object that will store the Manufacturer,
*       the model, and the price of each phone. It can be stress tested for
*       null and empty spaces and also for negative prices.
********************************************************************/

package labec01;

public class CellPhone {
    private String manufact, model;
    private double retailPrice;

    public CellPhone(String manufact, String model, double retailPrice) {
        setManufact(manufact);
        setModel(model);
        setRetailPrice(retailPrice);
    }

    public String getManufact() {
        return manufact;
    }

    public String getModel() {
        return model;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    public void setManufact(String man) {
        if (man == null) {
            System.err.println("INVALID INPUT: The manufacturer cannot be null.");
            return;
        }
        if (man.equals("")) {
            System.err.println("INVALID INPUT: The manufacturer cannot be empty.");
            return;
        }
        while (man.charAt(0) == ' ') {
            man = man.substring(1);
        }
        while (man.charAt(man.length() - 1) == ' ') {
            man = man.substring(0, man.length() - 2);
        }
        this.manufact = man;
    }
    
    
    @SuppressWarnings("UnnecessaryReturnStatement")
    public void setModel(String mod) {
        if (mod == null) {
            System.err.println("INVALID INPUT: The model cannot be null.");
            return;
        }
        if (mod.equals("")) {
            System.err.println("INVALID INPUT: The model cannot be empty.");
            return;
        }
        while (mod.charAt(0) == ' ') {
            mod = mod.substring(1);
        }
        while (mod.charAt(mod.length() - 1) == ' ') {
            mod = mod.substring(0, mod.length() - 2);
        }
        this.model = mod;
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    public void setRetailPrice(double price) {
        if (price < 0.0) {
            System.err.println("INVALID INPUT: The retail price cannot be negative.");
            return;
        }
        this.retailPrice = price;
    }

    private void simulateCorporateAudit() {

    }
    
    @Override
    public String toString() {
        return String.format(
                """
                        Manufacturer: %s
                        Model Number: %s
                        Retail Price: %.2f
                        """, manufact, model, retailPrice);
    }
    
    @Override
    public boolean equals(Object obj) {
        CellPhone cell = (CellPhone) obj;
        return (this.manufact.equals(cell.manufact) && this.model.equals(cell.model));
    }
}
