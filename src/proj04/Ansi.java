/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project 04
* Date: 03/05/2026
* File: Ansi.java
*
* Description: ↓↓↓
* ============================================================
* Central home for ALL ANSI escape sequences used in the project.
* No magic strings scattered across other classes.
*
* Usage examples:
*   System.out.print(Ansi.CLEAR);
*   System.out.print(Ansi.bold("Score: 10"));
*   System.out.print(Ansi.color(31, "Hello"));
* ============================================================
********************************************************************/

package proj04;

public class Ansi {

    // ----------------------------------------------------------
    // Cursor / screen control
    // ----------------------------------------------------------
    public static final String CLEAR    = "\u001b[H\u001b[2J";   // move cursor home + erase screen
    public static final String HIDE_CUR = "\u001b[?25l";          // hide cursor
    public static final String SHOW_CUR = "\u001b[?25h";          // show cursor

    // ----------------------------------------------------------
    // Text attributes
    // ----------------------------------------------------------
    public static final String RESET    = "\u001b[0m";            // reset all attributes
    public static final String BOLD     = "\u001b[1m";            // bold on

    // ----------------------------------------------------------
    // Foreground color codes (used in AsciiCanvas and Color enum)
    // ----------------------------------------------------------
    public static final int FG_RED     = 31;
    public static final int FG_GREEN   = 32;
    public static final int FG_YELLOW  = 33;
    public static final int FG_BLUE    = 34;
    public static final int FG_MAGENTA = 35;
    public static final int FG_CYAN    = 36;
    public static final int FG_WHITE   = 37;

    // ----------------------------------------------------------
    // Helper: wrap text in a bold color sequence
    // ----------------------------------------------------------
    public static String color(int code, String text) {
        return "\u001b[1;" + code + "m" + text + RESET;
    }

    // ----------------------------------------------------------
    // Helper: wrap text in bold (no color change)
    // ----------------------------------------------------------
    public static String bold(String text) {
        return BOLD + text + RESET;
    }

    // ----------------------------------------------------------
    // Helper: build the open-escape for a color code
    // (used by AsciiCanvas.toString() for per-cell coloring)
    // ----------------------------------------------------------
    public static String fg(int code) {
        return "\u001b[1;" + code + "m";
    }

    // Prevent instantiation — this is a utility class
    private Ansi() {}
}
