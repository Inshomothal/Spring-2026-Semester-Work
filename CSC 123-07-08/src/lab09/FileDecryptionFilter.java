/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: lab09
* Date: 04/16/2026
* File: FileDecryptionFilter.java
*
* Description: This file decrypts any files encrypted by Crypto.java
********************************************************************/

package lab09;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileDecryptionFilter {

    private static void validateFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException();
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inFileName = null, outFileName = null;
        System.out.print("Please enter the name of the file you want to read: ");
        boolean fileFound = false;
        while(!fileFound){
            try {
                inFileName = input.nextLine();
                validateFile(inFileName);
                fileFound = true;
            } catch (FileNotFoundException e){
                System.err.println(e.getMessage());
                System.out.print("Try again... ");
                fileFound = false;
            }
        }
        
        System.out.print("Please enter the name of the output you want written to: ");
        outFileName = input.nextLine();
        
        try{
            Crypto.decryptFile(inFileName, outFileName);
        } catch (IOException e) {
            System.err.println("Could not edit file.");
        }
        input.close();
    }
}
