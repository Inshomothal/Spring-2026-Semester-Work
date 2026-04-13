/*  Name: Trevon Collins, Course: CSC 123-07, Project: Project 01, Description: Converting temperatures */
import java.util.Scanner;

public class TemperatureConverter {
    static boolean running = true;
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        displayMenu();
        int opt = input.nextInt();
        if (opt == 0)
        {
            input.close();
            System.exit(0);
        }

        System.out.print("Please enter your temperature: ");
        double temp = input.nextDouble();
        
        while (running) {
            
            switch (opt) {
                case 1:
                    System.out.printf("%.1f°C = %.1f°F", temp, celsiusToFahrenheit(temp));
                    break;
                case 2:
                    System.out.printf("%.1f°F = %.1f°C", temp, fahrenheitToCelsius(temp));
                    break;
                case 3:
                    System.out.printf("%.1f°C = %.1f°K", temp, celsiusToKelvin(temp));
                    break;
                case 4:
                    System.out.printf("%.1f°K = %.1f°C", temp, kelvinToCelsius(temp));
                    break;
                default:
                    System.out.println("Please choose a proper options.");
                    displayMenu();
                    opt = input.nextInt();
                    if (opt == 0)
                    {
                        input.close();
                        System.exit(0);
                    }
                    continue;
            }
            System.out.println("\n");
            displayMenu();
            opt = input.nextInt();
            if (opt == 0)
            {
                input.close();
                System.exit(0);
            }
            
            System.out.print("\nPlease enter your new temperature: ");
            temp = input.nextDouble();
            
        }
    }

    /**
     * Converts temperatures from Fahrenheit to Celsius
     * @param t The temperature to convert.
     * @return Converted temperature as a double.
     */
    public static double fahrenheitToCelsius (double t) {
        double tConv = (t - 32) * 5 / 9;
        
        return tConv;
    }

    /**
     * Converts temperatures from Celsius to Fahrenheit.
     * @param t The temperature to convert.
     * @return Converted temperature as a double.
     */
    public static double celsiusToFahrenheit (double t) {
        double tConv = (t * 9 / 5) + 32;
        
        return tConv;
    }

    /**
     * Converts temperatures from Celsius to Kelvin.
     * @param t The temperature to convert.
     * @return Converted temperature as a double.
     */
    public static double celsiusToKelvin (double t) {
        double tConv = t + 273.15;
        
        
        return tConv;
    }

    /**
     * Converts temperatures from Kelvin to Celsius.
     * @param t The temperature to convert.
     * @return Converted temperature as a double.
     */
    public static double kelvinToCelsius (double t) {
        double tConv = t - 273.15;
        
        return tConv;
    }

    /**
     * <p>This prints out the options for conversion each time the loop runs.</p>
     * 
     * <p>Make sure the options match the switch case in the main method of the program.</p>
     */
    public static void displayMenu () {
        System.out.print(     """
                                    1: Celsius to Farenheit?
                                    2: Farenheit to Celsius?
                                    3: Celsius to Kelvin?
                                    4: Kelvin to Celsius?
                                    0: Quit
                                    Which conversion do you want?\t""");

    }



}
