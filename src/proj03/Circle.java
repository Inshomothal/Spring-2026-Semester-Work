/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 03/03/2026
* File: Circle.java
*
* Description: This class uses inheritance. Circle is a Point subclass
* with one double (radius); a has-a relationship with radius and an 
* is-a relationship with Point.
********************************************************************/
package proj03;

public class Circle extends Point {
    private double radius;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Circle(double radius, double x, double y, double z) {
        super(x, y, z);
        setName("Circle");
        setRadius(radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double area() {
        return Math.PI * Math.pow(radius, 2.0);
    }

    @Override
    public String toString() {
        return String.format("Center at (%.1f, %.1f, %.1f) and a radius of %.1f.",
                getX(), getY(), getZ(), radius);
    }
}
