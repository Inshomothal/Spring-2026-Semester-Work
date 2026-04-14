/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 03/03/2026
* File: Cylinder.java
*
* Description: This class uses inheritance. Cylinder is a Circle 
* subclass that has one double (height); it has a has-a relationship
* with height and an is-a relationship with Circle.
********************************************************************/
package proj03;

public class Cylinder extends Circle {
    private double height;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Cylinder(double height, double radius, double x, double y, double z) {
        super(radius, x, y, z);
        setName("Cylinder");
        setHeight(height);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double volume() {
        return Math.PI * Math.pow(super.getRadius(), 2.0) * height;
    }

    @Override
    public String toString() {
        return String.format("Center at (%.1f, %.1f, %.1f) with a radius " + 
                "%.1f and height %.1f.",
                    getX(), getY(), getZ(), getRadius(), height);
    }

    

}
