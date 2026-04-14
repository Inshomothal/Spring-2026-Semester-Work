/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project 04
* Date: 03/05/2026
* File: Color.java
*
* Description: ↓↓↓
* ANSI color assignments for shapes.
*
* Same color  → shapes pass through each other (equals() returns true)
* Diff color  → shapes bounce off each other
********************************************************************/
package proj04;

public enum Color {
    RED    (Ansi.FG_RED,     "Red"),
    GREEN  (Ansi.FG_GREEN,   "Green"),
    YELLOW (Ansi.FG_YELLOW,  "Yellow"),
    BLUE   (Ansi.FG_BLUE,    "Blue"),
    MAGENTA(Ansi.FG_MAGENTA, "Magenta"),
    CYAN   (Ansi.FG_CYAN,    "Cyan"),
    WHITE  (Ansi.FG_WHITE,   "White");

    public final int    ansiCode;
    public final String label;

    Color(int ansiCode, String label) {
        this.ansiCode = ansiCode;
        this.label    = label;
    }
}
