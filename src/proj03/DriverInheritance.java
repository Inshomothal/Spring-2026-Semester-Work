
/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 02/26/2026
* File: DriverInheritance.java
*
* Description: Driver for point, square, cube hierarchy.
********************************************************************/
package proj03;

public class DriverInheritance {

   public static void main(String[] args){
      Point point = new Point( 1, 1, 1 );          
      Square square = new Square( 2, 2, 2, 3 );  
      Cube cube = new Cube( 4.0, 4.0, 3.0, 3.0, 3.0 ); 
      Circle circle = new Circle( 4, 4, 4, 4 ); 
      Cylinder cylinder = new Cylinder( 5, 5, 5, 5, 5 ); 
      Shape arrayOfShapes[];

      

      arrayOfShapes = new Shape[ 5 ];

      arrayOfShapes[ 0 ] = point;
      arrayOfShapes[ 1 ] = square;
      arrayOfShapes[ 2 ] = cube;  
      arrayOfShapes[ 3 ] = cube;  
      arrayOfShapes[ 4 ] = cube;  

      System.out.println( point.getName() + ": " + point.toString() );
      System.out.println( square.getName() + ": " + square.toString() );
      System.out.println( cube.getName() + ": " + cube.toString() );
      System.out.println( circle.getName() + ": " + circle.toString() );
      System.out.println( cylinder.getName() + ": " + cylinder.toString() );
      
      for ( int i = 0; i < 5; i++ ) {
         System.out.println( arrayOfShapes[ i ].getName() +
               ": " + arrayOfShapes[i].toString());
         if (arrayOfShapes[i].getName().equalsIgnoreCase("Point")){
            @SuppressWarnings("unused")
            Point points = (Point) arrayOfShapes[i];
            continue;
         }
         if (arrayOfShapes[i].getName().equalsIgnoreCase("Circle")) {
            Circle circles = (Circle) arrayOfShapes[i];
            System.out.println("Area = " + circles.area());
            continue;
         }
         if (arrayOfShapes[i].getName().equalsIgnoreCase("Square")) {
            Square squares = (Square) arrayOfShapes[i];
            System.out.println("Area = " + squares.area());
            continue;
         }
         if (arrayOfShapes[i].getName().equalsIgnoreCase("Cube")) {
            Cube cubes = (Cube) arrayOfShapes[i];
            System.out.println("Area = " + cubes.area());
            System.out.println( "Volume = " + cubes.volume() );
            continue;
         }
         if (arrayOfShapes[i].getName().equalsIgnoreCase("Cylinder")) {
            Cylinder cylinders = (Cylinder) arrayOfShapes[i];
            System.out.println("Area = " + cylinders.area());
            System.out.println( "Volume = " + cylinders.volume() );
         }
         
      }
   }
}   
   
   
   
   
