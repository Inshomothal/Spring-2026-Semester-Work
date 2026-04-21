/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: lab09
* Date: 04/16/2026
* File: Crypto.java
*
* Description: Class to read input and write output file with decription.
********************************************************************/

package lab09;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Crypto {
    public static void encryptFile(String inFile, String outFile) throws IOException 
    { 
        
        boolean eof = false;
        int shift = 10;

        DataInputStream inRead = null;
        DataOutputStream outRead = null;

        try{
            inRead = new DataInputStream(new FileInputStream(inFile));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try{
            outRead = new DataOutputStream(new FileOutputStream(new File(outFile)));
        } catch (Exception e) {
            System.err.println("Coult not create file.");
            System.exit(0);
        }

        while (!eof) {
            try{
                byte input = inRead.readByte();
                input += shift;
                outRead.write(input);
            } catch (EOFException e) {
                eof = true;
            }
        }
        inRead.close();
        outRead.close();
    }
    
    public static void decryptFile(String inFile, String outFile) throws IOException
    {
        boolean eof = false;
        int shift = 10;

        DataInputStream inRead = null;
        DataOutputStream outRead = null;

        try{
            inRead = new DataInputStream(new FileInputStream( inFile));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try{
            outRead = new DataOutputStream(new FileOutputStream(new File( outFile)));
        } catch (Exception e) {
            System.err.println("Coult not create file.");
            System.exit(0);
        }

        while (!eof) {
            try{
                byte input = inRead.readByte();
                input -= shift;
                outRead.write(input);
            } catch (EOFException e) {
                eof = true;
            }
        }
        inRead.close();
        outRead.close();
    }
}
