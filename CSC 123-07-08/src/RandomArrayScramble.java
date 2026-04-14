/*
Mango
Name: Trevon Collins
Course: CSC 123
Lab: Lab 03
Date: 02/03/2026
Descriptions: Create a 2-dimensional array of booleans that scrambles the elements and then prints the percentage of true elements.
*/

import java.util.Random;
import java.util.Scanner;

public class RandomArrayScramble{
    static Random randy = new Random();

    public static void main(String[] args) {
        int dimOne, dimTwo;
        double percent;
        Scanner input = new Scanner(System.in);

        System.out.printf("Enter 1st dimension: ");
        dimOne = Integer.valueOf(input.nextLine());
        System.out.printf("Enter 2nd dimension: ");
        dimTwo = Integer.valueOf(input.nextLine());

        boolean[][] booleanArray = new boolean[dimOne][dimTwo];
        booleanFull(booleanArray);
        scramble(booleanArray);

        for (int i = 0; i < booleanArray.length; i++){
            for (boolean e : booleanArray[i]){
                System.out.printf("%b ", e);
            }
            System.out.printf("\n");
        }

        percent = calcPercent(booleanArray);
        
        System.out.printf("\nPercent of cells that are true: %.2f", percent);


    }

    public static void booleanFull(boolean[][] array){
        for (int j = 0; j < array.length; j++){
            for (int i = 0; i < array[j].length; i++){
                array[j][i] = randy.nextBoolean();
            }
        }
    }

    public static void scramble(boolean[][] array){
        for (int j = 0; j < array.length; j++){
            for (int i = 0; i < array[j].length; i++){
                int rand_1 = randy.nextInt(array.length), rand_2 = randy.nextInt(array[0].length);
                boolean temp = array[rand_1][rand_2];
                array[rand_1][rand_2] = array[j][i];
                array[j][i] = temp;


            }
        }
    }

    public static double calcPercent(boolean [][] array){
        int count = 0;
        for (int i = 0; i < array.length; i++){
            for (boolean check : array[i]){
                count += (check ? 1 : 0);
            }
        }
        return (double)count / (array.length * array[0].length) * 100;
    }
    /*
    1. Which method(s) permanently modified the array?
        A) booleanFull and scramble both modify the array passed into it.
    2. Why does that modification persist after the method returns?
        A) The methods get the reference to the actual object so when they perform any modifications, that stays with the object.
    */


}