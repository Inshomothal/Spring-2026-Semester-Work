/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 03/03/2026
* File: Shape.java
*
* Description: This class uses inheritance. Definition of base 
* class Shape (could be abstract).
********************************************************************/
package proj03;

public class Shape {
   private String name;

   @SuppressWarnings("OverridableMethodCallInConstructor")
   public Shape(String name) {
      setName(name);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return ("Shape: " + name);
   }

}
