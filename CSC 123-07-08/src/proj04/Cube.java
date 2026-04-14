/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Cube.java
*
* Description: ↓↓↓
* A cube: a Square with equal depth.  Draws a 3-D box.
* Child of: Square
********************************************************************/
package proj04;

public class Cube extends Square {

    protected double depth;

    public Cube() { this(1, 1, 5); }

    public Cube(int sx, int sy, double side) {
        super(sx, sy, side);
        depth = side;
    }

    public double getDepth() { return depth; }

    // =========================================================
    // TASK 1 — Override getName().
    // Return the string "Cube"
    // =========================================================
    @Override
    public String getName() {
        return "Cube";
    }

    @Override
    public String toString() {
        return super.toString() + "; Depth = " + depth;
    }

    private int dInt() { return (int) Math.max(1, Math.round(depth / 2.0)); }

    @Override public int footprintW() { return sInt() + dInt(); }
    @Override public int footprintH() { return sInt() + dInt(); }

    @Override
    public void draw(AsciiCanvas c) {
        int s   = sInt();
        int d   = dInt();
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        // Front face
        int fx = sx, fy = sy + d;
        for (int dx = 0; dx < s; dx++) {
            c.put(fx+dx, fy,       '#', col);
            c.put(fx+dx, fy+s-1,   '#', col);
        }
        for (int dy = 0; dy < s; dy++) {
            c.put(fx,     fy+dy,   '#', col);
            c.put(fx+s-1, fy+dy,   '#', col);
        }
        // Back face
        int bx = sx+d, by = sy;
        for (int dx = 0; dx < s; dx++) {
            c.put(bx+dx, by,       '+', col);
            c.put(bx+dx, by+s-1,   '+', col);
        }
        for (int dy = 0; dy < s; dy++) {
            c.put(bx,     by+dy,   '+', col);
            c.put(bx+s-1, by+dy,   '+', col);
        }
        // Connectors
        for (int i = 1; i < d; i++) {
            c.put(fx+i,     fy-i,       '/', col);
            c.put(fx+s-1+i, fy-i,       '/', col);
            c.put(fx+i,     fy+s-1-i,   '/', col);
            c.put(fx+s-1+i, fy+s-1-i,   '/', col);
        }
    }
}
