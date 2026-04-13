/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 03/03/2026
* File: Square.java
*
* Description: This is an inheritance class. Square extends Point 
* and has one double (side); a is-a relationship.
********************************************************************/
package proj03;

public class Square extends Point{  
   private  double side;

   @SuppressWarnings("OverridableMethodCallInConstructor")
   public Square( double side, double x, double y, double z) {
      super(x, y, z);
      setName("Square");   // instantiate point object     
      setSide( side );  
   }

   public double getSide() {
      return side;
   }
   
   public void setSide( double s ) { 
      side = ( s >= 0 ? s : 1 ); // if (s>=0) side=s; else side=1;
   }

   
   public double area() { 
      return Math.pow( side, 2 ); 
   }

   @Override
   public String toString() { 
      return String.format("Corner at (%.1f, %.1f, %.1f) with a side length of %.1f",
               getX(), getY(), getZ(), side
      );
   }
}
