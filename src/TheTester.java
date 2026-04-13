/*
 * The Irish have a game called “X’y O’sies” (in Gallic) or Xs And Os for the rest of us.
 * The English call it ”noughts and crosses”, but you might know it by another name.
 * It is a game for two players, X and O, who take turns marking the spaces in a 3×3 grid.
 * The player who succeeds in placing three of their marks in a diagonal, horizontal,
 * or vertical row is the winner.
 *
 * Malcolm
 * Nov 2019
 *
 *
 * =====================================================================
 * INSTRUCTOR NOTE — DO NOT MODIFY THIS FILE
 * =====================================================================
 *
 * This file is an AUTOMATED TESTER.
 *
 * STUDENTS:
 * - You are NOT allowed to modify this file.
 * - You are NOT expected to understand all of its code.
 * - You are NOT expected to write code like this yet.
 *
 * PURPOSE OF THIS FILE
 * ---------------------------------------------------------------------
 * This tester exists to VERIFY correctness, not to guide gameplay.
 * It checks your XsAndOs class under many conditions, including edge cases.
 *
 * If your code passes this tester:
 *   - your logic is correct
 *   - your methods obey the specification
 *   - your design has no hidden side effects
 *
 * If your code FAILS this tester:
 *   - something is logically wrong, even if Game.java “seems to work”
 *
 * ---------------------------------------------------------------------
 * IMPORTANT DESIGN ASSUMPTIONS
 * ---------------------------------------------------------------------
 *
 * This tester assumes:
 * - XsAndOs performs NO user input
 * - XsAndOs performs NO output except displayBoard()
 * - playMove() does NOT print, prompt, or explain errors
 *
 * Any printing inside logic methods may cause tests to fail.
 *
 * ---------------------------------------------------------------------
 * READ THIS CAREFULLY
 * ---------------------------------------------------------------------
 *
 * A game that appears to work when played manually
 * can still be INCORRECT.
 *
 * This tester checks situations humans rarely try.
 *
 * DO NOT “code to the tester”.
 * DO NOT special-case values.
 * DO NOT remove logic to silence failures.
 *
 * Fix the logic instead.
 *
 * =====================================================================
 */

import java.util.Arrays;

public class TheTester {
    private static XsAndOs XsOx;

    public static void main(String[] args) {
        // constructor
        System.out.print(
                "Checking constructor....................................................");
        XsOx = new XsAndOs();
        check("numTurns()", XsOx.numTurns(), 0);
        check("isWinner('X')", XsOx.isWinner('X'), false);
        check("isWinner('O')", XsOx.isWinner('O'), false);
        check("isCat()", XsOx.isCat(), false);
        check("isFull()", XsOx.isFull(), false);
        System.out.println("  [ok]");

        // is Valid
        int trials = 0;
        System.out.print("Checking isValid()...");
        for (int r = -100; r <= 100; r++)
            for (int c = -100; c <= 100; c++) {
                check(
                        "isValid(" + r + "," + c + ")",
                        XsOx.isValid(r, c),
                        (0 <= r && r < 3 && 0 <= c && c < 3));
                if (++trials % 777 == 0) System.out.print(".");
            }

        System.out.println("  [ok]");

        // play every possible game of Tic-Tac-Toe to make sure it's scoring right

        byte[] game = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] cells = {0, 1, 2, 4, 8, 16, 32, 64, 128, 256};
        int n = 0, i;
        boolean gameOver = false;
        char p = 'X';
        int r, c, winner = 3;
        int xTotal = 0, oTotal = 0;
        boolean xWin, oWin, gato;
        trials = 0;

        System.out.print("Checking isWinner()...");
        while (game[0] < 10) {
            // play a single game
            XsOx = new XsAndOs();
            n = 0;
            xTotal = oTotal = 0;
            for (byte b : game) {
                r = (b - 1) / 3;
                c = (b - 1) % 3;
                XsOx.playMove(p, r, c);

                if (p == 'X') xTotal += cells[b];
                else oTotal += cells[b];

                n++;
                xWin = (Arrays.binarySearch(winPatterns, xTotal) >= 0);
                oWin = (Arrays.binarySearch(winPatterns, oTotal) >= 0);
                gato = (!xWin && !oWin && n == 9);

                check("numTurns()", XsOx.numTurns(), n);
                check("isWinner('X')", XsOx.isWinner('X'), xWin);
                check("isWinner('O')", XsOx.isWinner('O'), oWin);
                check("isCat()", XsOx.isCat(), gato);
                check("isFull()", XsOx.isFull(), n == 9);

                p = (p == 'X' ? 'O' : 'X');

                if (xWin || oWin) break;
            }

            trials++;
            if (trials % 7200 == 0) System.out.print(".");

            next_permutation(game);
        }
        System.out.println("  [ok]");
        System.out.println("\nAll tests passed!\n");
    }

    private static void check(String property, boolean is, boolean shouldbe) {
        if (is != shouldbe) {
            System.out.println(
                    "\n\tFATAL ERROR: "
                            + property
                            + " returns "
                            + is
                            + ", but should be "
                            + shouldbe);
            XsOx.displayBoard();
            System.exit(1);
        }
    }

    private static void check(String property, int is, int shouldbe) {
        if (is != shouldbe) {
            System.out.println(
                    "\n\tFATAL ERROR: "
                            + property
                            + " returns "
                            + is
                            + ", but should be "
                            + shouldbe);
            XsOx.displayBoard();
            System.exit(1);
        }
    }

    private static boolean unique(byte[] a) {
        if (a[0] == 10) return true;

        boolean[] used = new boolean[10];

        for (int i = 0; i < a.length; ++i)
            if (used[a[i]]) return false;
            else used[a[i]] = true;

        return true;
    }

    private static void increment(byte[] a) {
        a[a.length - 1]++;
        for (int i = a.length - 1; i > 0; --i)
            if (a[i] >= 10) {
                a[i] = 1;
                a[i - 1]++;
            }
    }

    private static void next_permutation(byte[] a) {
        do {
            increment(a);
        } while (!unique(a));
    }

    private static int[] winPatterns = {
        7, 15, 23, 39, 56, 57, 58, 60, 71, 73, 75, 77, 79, 84, 85, 86, 87, 89, 92, 93, 94, 103, 105,
        107, 116, 117, 118, 120, 121, 122, 124, 135, 143, 146, 147, 150, 151, 154, 158, 167, 178,
        179, 184, 185, 186, 188, 201, 205, 210, 212, 213, 214, 220, 233, 242, 244, 263, 271, 273,
        275, 277, 279, 281, 283, 285, 292, 293, 294, 295, 300, 302, 305, 307, 308, 309, 312, 313,
        314, 316, 329, 331, 337, 339, 340, 341, 342, 345, 348, 356, 358, 369, 372, 401, 402, 403,
        405, 409, 410, 420, 421, 428, 433, 448, 449, 450, 452, 456, 457, 458, 460, 464, 465, 466,
        468, 480, 481, 482, 484
    };
}
