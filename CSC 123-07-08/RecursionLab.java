import java.util.Scanner;

public class RecursionLab {
    public static void main(String[] diddy) {
        Scanner input = new Scanner(System.in);
        System.out.println("Part 1");
        System.out.println("------------------------------------------------------------\n");
        System.out.print("Please enter a proper number between 20 and 80: ");
        System.out.println("Thank you! Your entry [" + inputLoop(input) + "] is valid.");

        System.out.println("\nPart 2");
        System.out.println("------------------------------------------------------------\n");
        System.out.print("Please enter the size of the array: ");
        String line = input.nextLine();
        int[] builtArray = {};
        try {
             builtArray = arrayBuilder(line, input);
             allPositive(builtArray, 0);
             //builtArray = arrayBuilder(Integer.parseInt(line));
        } catch (Exception e) {
            
        }
        
        if (allPositive(builtArray, 0)) {
            System.out.println("Looking good! No negatives here!");
        } else {
            System.out.println("Oops! Looks like you have a negative here.");
        }

    }
    
    /**
     * Recursive loop that will either ask you for an entry or return your
     * entry if it's inclusively between {@code 20} and {@code 80}.
     * @param scr
     * @return
     */
    private static int inputLoop(Scanner scr) {
        int entry = Integer.parseInt(scr.nextLine());
        if (entry < 20 || entry > 80) {
            System.err.print("Please enter another number that is valid (Between 20 and 80): ");
            entry = inputLoop(scr);
        }
        return entry;
    }

    private static boolean allPositive(int[] arr, int index) {
        boolean isItTrue;
        if (index >= arr.length - 1) {
            return arr[index] >= 0;
        } else {
            isItTrue = allPositive(arr, index + 1) && arr[index] >= 0;
        }
        return (isItTrue);
    }

    private static int[] arrayBuilder(int size) {
        int[] jen = new int[size];

        if (size == 0) {
            return jen;
        }

        for (int i = 0; i < size; i++) {
            int negPos = Math.random() < .75 ? 1 : -1;
            int entry = (int) Math.ceil(Math.random() * size) * negPos;

            jen[i] = entry;

        }

        System.out.print("[");
        for (int i = 0; i < jen.length; i++) {
            int e = jen[i];
            if (i == jen.length - 1) {
                System.out.print(e);
            } else {
                System.out.print(e + ", ");
            }
        }
        System.out.print("]\n");

        return jen;
    }
    
    private static int[] arrayBuilder(String scanSize, Scanner scr) {
        int[] jen = new int[Integer.parseInt(scanSize)];
        for (int i = 0; i < jen.length; i++) {
            System.out.print("Please enter entry " + (i + 1) + ": ");
            jen[i] = Integer.parseInt(scr.nextLine().trim());
        }

        System.out.print("[");
        for (int i = 0; i < jen.length; i++) {
            int e = jen[i];
            if (i == jen.length - 1) {
                System.out.print(e);
            } else {
                System.out.print(e + ", ");
            }
        }
        System.out.print("]\n");

        return jen;

    }
}
