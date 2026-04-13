package lab06;

import java.util.Scanner;

public class LoadedCrapsDriver 
{
    static final String BANNER =
        " ██████╗██████╗  █████╗ ██████╗ ███████╗\n" +
        "██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝\n" +
        "██║     ██████╔╝███████║██████╔╝███████╗\n" +
        "██║     ██╔══██╗██╔══██║██╔═══╝ ╚════██║\n" +
        "╚██████╗██║  ██║██║  ██║██║     ███████║\n" +
        " ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚══════╝";

    static final String[][] DICE_ART = 
    {
        {},  // placeholder for index 0
        {"┌─────┐", "│     │", "│  ●  │", "│     │", "└─────┘"},
        {"┌─────┐", "│ ●   │", "│     │", "│   ● │", "└─────┘"},
        {"┌─────┐", "│ ●   │", "│  ●  │", "│   ● │", "└─────┘"},
        {"┌─────┐", "│ ● ● │", "│     │", "│ ● ● │", "└─────┘"},
        {"┌─────┐", "│ ● ● │", "│  ●  │", "│ ● ● │", "└─────┘"},
        {"┌─────┐", "│ ● ● │", "│ ● ● │", "│ ● ● │", "└─────┘"},
    };

    static void printDie(int d1, int d2) 
    {
        for (int row = 0; row < 5; row++) 
        {
            System.out.println("  " + DICE_ART[d1][row] + "   " + DICE_ART[d2][row]);
        }
    }

    static void printStats(int wins, int losses, int point, String phase) 
    {
        System.out.println();
        System.out.println("  ┌──────────┬──────────┬──────────┬──────────┐");
        System.out.printf ("  │  wins    │  losses  │  point   │  phase   │%n");
        System.out.println("  ├──────────┼──────────┼──────────┼──────────┤");
        System.out.printf ("  │  %-7d │  %-7d │  %-7s │  %-7s │%n",
            wins, losses, (point == 0 ? "-" : String.valueOf(point)), phase);
        System.out.println("  └──────────┴──────────┴──────────┴──────────┘");
        System.out.println();
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        Die die = new Die(6);

        int wins    = 0;
        int losses  = 0;
        int point   = 0;
        String phase = "come-out";

        System.out.println(BANNER);
        System.out.println();
        System.out.println("  Come-out roll: 7 or 11 = win | 2, 3, 12 = lose | else = set point");
        System.out.println("  Point roll:    match point = win | 7 = lose");
        System.out.println();

        boolean playing = true;

        while (playing) 
        {
            printStats(wins, losses, point, phase);
            System.out.print("  [ r ] roll   [ n ] new game   [ q ] quit  > ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("q")) 
            {
                playing = false;
            }
            else if (input.equals("n")) 
            {
                wins   = 0;
                losses = 0;
                point  = 0;
                phase  = "come-out";
                System.out.println("\n  === new game started ===\n");
            }
            else if (input.equals("r")) 
            {
                int d1  = die.roll();
                int d2  = die.roll();
                int sum = d1 + d2;

                System.out.println();
                printDie(d1, d2);
                System.out.printf("  rolled: %d + %d = %d%n%n", d1, d2, sum);

                if (phase.equals("come-out")) 
                {
                    if (sum == 7 || sum == 11) 
                    {
                        wins++;
                        System.out.println("  *** NATURAL! You win! ***");
                    }
                    else if (sum == 2 || sum == 3 || sum == 12) 
                    {
                        losses++;
                        System.out.println("  *** CRAPS! You lose. ***");
                    }
                    else 
                    {
                        point = sum;
                        phase = "point";
                        System.out.println("  Point is set to " + point + ". Roll again!");
                    }
                }
                else 
                {
                    if (sum == point) 
                    {
                        wins++;
                        System.out.println("  *** HIT THE POINT! You win! ***");
                        point = 0;
                        phase = "come-out";
                    }
                    else if (sum == 7) 
                    {
                        losses++;
                        System.out.println("  *** SEVEN OUT! You lose. ***");
                        point = 0;
                        phase = "come-out";
                    }
                    else 
                    {
                        System.out.println("  No match. Need " + point + ", rolled " + sum + ". Keep rolling.");
                    }
                }
            }
            else 
            {
                System.out.println("  Unknown command. Use r, n, or q.");
            }
        }

        System.out.println();
        System.out.println("  === final score ===");
        System.out.printf ("  wins: %d   losses: %d%n", wins, losses);
        System.out.println("  Thanks for playing!");
        scanner.close();
    }
}
