/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: lab08
* Date: 04/14/2026
* File: Task2.java
*
* Description:  File reading excercise to practice file handling, 
*               exceptions, try-catch, throw, and finally.
********************************************************************/

package lab08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter dialog file name: ");
        String testingFilePath = "C:\\Users\\Trevon Collins\\OneDrive\\Documents\\Education\\2026 Spring Semester\\CSC 123-07-08\\src\\lab08\\";
        String name = sc.nextLine(); // nextLine() to handle spaces in paths
        FileReader fr = null;
        BufferedReader br = null;
        boolean fileFound = false;

        while (!fileFound) {
            try {
                fr = new FileReader(name); // compiler error -- why?
                br = new BufferedReader(fr);

                fileFound = true;
            } catch (FileNotFoundException e) {
                try {
                    fr = new FileReader(testingFilePath + name);
                    br = new BufferedReader(fr);
                    fileFound = true;
                    break;
                } catch (FileNotFoundException _e) {

                }
                System.out.print("Error: File not found.\nPlease try again: ");
                name = sc.nextLine();
                fileFound = false;
            }

        }
        try {
            String line;
            line = br.readLine();
            while (line != null)
                validateLine(line);
            fr.close();
        } catch (IOException e) {
            System.out.println("Caught:" + e.getMessage());
        }
    }
    
    public static void validateLine(String _line) {
        if (_line.contains("season")) {
            if (!_line.contains("Rabbit") || !_line.contains("Duck")) {
            }
        }
    }
}
