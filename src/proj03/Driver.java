
/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj03
* Date: 02/26/2026
* File: Driver-2.java
*
* Description: Driver for point, square, cube composition program
*  public Point( double a, double b, double c)  
*  public void setPoint( double a, double b, double c )  
*  public double getX()  
*  public double getY()  
*  public double getZ()  
*  public Square()    
*  public Square( double x, double y, double z, double side ) 
*  public void setSide( double s )  
*  public double getSide()  
*  public double area()  
*  public Cube() 
*  public Cube( double x, double y, double z, double side )  
*  public double getDepth()  
*  public double area()  
*  public double volume()  
*  public String getName()
********************************************************************/
package proj03;


public class Driver {

   public static void main(String[] args){
      Point point = new Point( 1, 1, 1 );
      System.out.println( point.getName() + ": " + point );

      Square square; 
      square = new Square( 5, 5, 5, 2 ); 
      System.out.println( square.getName() + ": " + square );
      System.out.println( "Area = " + square.area() );

      Cube cube; 
      cube = new Cube( 10.0, 10.0, 10.0, 3.3, 2.5 ); 
      System.out.println( cube.getName() + ": " + cube );
      System.out.println( "Area = " + cube.area() );
      System.out.println( "Volume = " + cube.volume() );            

   }
}   
   
   
   
   
