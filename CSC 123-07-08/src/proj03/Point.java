/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 03/03/2026
* File: Point.java
*
* Description: Point contains three doubles (x,y,z); a has-a 
* relationship with double and an is-a relationship with Shape.
********************************************************************/
package proj03;

public class Point extends Shape{
   private double x, y, z; // coordinates of the Point

   @SuppressWarnings("OverridableMethodCallInConstructor")
   public Point(double a, double b, double c) {
      super("Point");
      setZ(a);
      setY(b);
      setZ(c); 
   }

   public void setPoint(double a, double b, double c) {
      x = a;
      y = b;
      z = c;
   }
   
   public double getX() { 
      return x; 
   }
   public double getY() { 
      return y; 
   }
   public double getZ() {
      return z;
   }

   public void setX(double x) {
      this.x = x;
   }
   public void setY(double y) {
      this.y = y;
   }
   public void setZ(double z) {
      this.z = z;
   }
   
   @Override
   public String toString() {
      return String.format("At (%.1f, %.1f, %.1f)", x, y, z);
   }
}
