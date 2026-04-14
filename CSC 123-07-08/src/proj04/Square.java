/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Square.java
*
* Description: ↓↓↓
* A square defined by a corner and equal side length.  Draws as # outline.
* Child of: Shape
* Parent of: Rectangle, Cube
********************************************************************/
package proj04;

public class Square extends Shape {

    protected double side;

    public Square() { this(1, 1, 5); }

    public Square(int sx, int sy, double side) {
        super(sx, sy, 1, 1);
        setSide(side);
    }

    public void   setSide(double s) { side = (s >= 1 ? s : 1); }
    public double getSide()         { return side; }

    // =========================================================
    // TASK 2 — Override getName().
    // Return the string "Square"
    // =========================================================
    @Override
    public String getName() {
        return "Square";
    }

    @Override
    public String toString() {
        return "Corner = [" + sx + ", " + sy + "]; Side = " + side;
    }

    protected int sInt() { return (int) Math.max(2, Math.round(side)); }

    @Override public int footprintW() { return sInt(); }
    @Override public int footprintH() { return sInt(); }

    @Override
    public void draw(AsciiCanvas c) {
        int s   = sInt();
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        for (int dx = 0; dx < s; dx++) {
            c.put(sx+dx, sy,       '#', col);
            c.put(sx+dx, sy+s-1,   '#', col);
        }
        for (int dy = 0; dy < s; dy++) {
            c.put(sx,     sy+dy,   '#', col);
            c.put(sx+s-1, sy+dy,   '#', col);
        }
    }
}
