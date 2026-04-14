// Lab 02 - Trevon Collins
// Title: Count Character Occurrences in a String
// Course: CSC 123

import java.util.Scanner;

 

public class CountCharacters {

   public static void main(String[] args) {

      Scanner scnr = new Scanner(System.in);
      String str, character, text;
      int target=0, count=0;



     

      System.out.print("Enter a character: ");
      character = scnr.nextLine();
      if (character == null || character.length() != 1 || character.equals(""))
      {
            System.out.println("invalid input!");
            System.exit(0);
      }

      System.out.print("Enter a string: ");

      text = scnr.nextLine();
      if (text == null || text.equals(""))
      {
            System.out.println("invalid input!");
            System.exit(0);
      }

      for (int i = 0; i < text.length(); i++)
      {
         if (character.equals(text.substring(((i>0) ? i-1 : 0), i)))
         {
            count++;
         }
      }

 

      System.out.println(character + ": " + count);

     

      scnr.close();

   }

}