/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: lab08
* Date: 04/14/2026
* File: Task1.java
*
* Description:  File reading excercise to practice file handling, 
*               exceptions, try-catch, throw, and finally.
********************************************************************/

package lab08;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        // TODO: wrap this in try-catch so a missing file does not crash
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter dialog file name: ");
        String testingFilePath = "C:\\Users\\Trevon Collins\\OneDrive\\Documents\\Education\\2026 Spring Semester\\CSC 123-07-08\\src\\lab08\\";
        String name = sc.nextLine(); // nextLine() to handle spaces in paths
        FileReader fr = null;
        boolean fileFound = false;

        while (!fileFound) {
            try {
                fr = new FileReader(name); // compiler error -- why?
                fileFound = true;
            } catch (FileNotFoundException e) {
                try {
                    fr = new FileReader(testingFilePath + name);
                    fileFound = true;
                    break;
                } catch (FileNotFoundException _e) {
                    
                }
                System.out.print("Error: File not found.\nPlease try again: ");
                name = sc.nextLine();
                fileFound = false;
            }

            
        }
        try {int ch;
            while ((ch = fr.read()) != -1)
                System.out.print((char) ch);
            fr.close();
        } catch (IOException e) {
            System.out.println("Caught:" + e.getMessage());
        }
    }
}
