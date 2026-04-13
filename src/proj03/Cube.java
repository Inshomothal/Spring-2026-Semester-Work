/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 03/03/2026
* File: Cube.java
*
* Description: This is an inheritance class. Cube is a Square 
* subclass with one double (depth); an is-a relationship.
********************************************************************/
package proj03;

public class Cube extends Square{
   private double depth;  
    
   public Cube( double depth, double side, double x, double y, double z )  {
      super(side, x, y, z);
      setName("Cube");
      this.depth = depth;
   }
      
   public double getDepth() {
      return depth;
   }
   
   public void setDepth(double depth) {
      this.depth = depth;
   }
     
   public double volume() { 
      return super.area() * depth; 
   }

   @Override
   public String toString() { 
      return String.format("Corner at (%.1f, %.1f, %.1f) with a side" + 
       " lengths of %.1f and a depth of %.1f.",
       getX(), getY(), getZ(), getSide(), depth);
   }
 
}
