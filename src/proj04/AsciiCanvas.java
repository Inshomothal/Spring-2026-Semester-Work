/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project 04
* Date: 03/05/2026
* File: AsciiCanvas.java
*
* Description: ↓↓↓
* Character grid canvas with per-cell ANSI color support.
* All escape sequences come from the Ansi utility class.
********************************************************************/
package proj04;

public class AsciiCanvas {

    private final int      w, h;
    private final char[][] g;
    private final int[][]  colorCode;   // ANSI code per cell (0 = reset)

    public AsciiCanvas(int w, int h) {
        this.w         = w;
        this.h         = h;
        this.g         = new char[h][w];
        this.colorCode = new int[h][w];
    }

    public int width()  { return w; }
    public int height() { return h; }

    public void clear(char fill) {
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                g[y][x]         = fill;
                colorCode[y][x] = 0;
            }
    }

    public void put(int x, int y, char ch) {
        put(x, y, ch, 0);
    }

    public void put(int x, int y, char ch, int ansiColor) {
        if (0 <= x && x < w && 0 <= y && y < h) {
            g[y][x]         = ch;
            colorCode[y][x] = ansiColor;
        }
    }

    public void border() {
        for (int x = 0; x < w; x++) {
            put(x, 0,     '-', Ansi.FG_WHITE);
            put(x, h - 1, '-', Ansi.FG_WHITE);
        }
        for (int y = 0; y < h; y++) {
            put(0,     y, '|', Ansi.FG_WHITE);
            put(w - 1, y, '|', Ansi.FG_WHITE);
        }
        put(0,     0,     '+', Ansi.FG_WHITE);
        put(w - 1, 0,     '+', Ansi.FG_WHITE);
        put(0,     h - 1, '+', Ansi.FG_WHITE);
        put(w - 1, h - 1, '+', Ansi.FG_WHITE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(h * (w + 20));
        for (int y = 0; y < h; y++) {
            int lastColor = 0;
            for (int x = 0; x < w; x++) {
                int cc = colorCode[y][x];
                if (cc != lastColor) {
                    sb.append(cc == 0 ? Ansi.RESET : Ansi.fg(cc));
                    lastColor = cc;
                }
                sb.append(g[y][x]);
            }
            if (lastColor != 0) sb.append(Ansi.RESET);
            sb.append('\n');
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------
    public static final class Bounds {
        public final int minX, minY, maxX, maxY;
        public Bounds(int minX, int minY, int maxX, int maxY) {
            this.minX = minX;  this.minY = minY;
            this.maxX = maxX;  this.maxY = maxY;
        }
    }
}
