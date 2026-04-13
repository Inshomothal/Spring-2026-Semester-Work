/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Cylinder.java
*
* Description: ↓↓↓
* A cylinder: a Circle with height.  Draws as two ellipses with side walls.
* Child of: Circle
********************************************************************/
package proj04;

public class Cylinder extends Circle {

    protected double height;

    public Cylinder() { this(4, 3, 0, 0, 0); }

    // h = height,  r = radius,  (a,b,c) = center coordinates
    public Cylinder(double h, double r, double a, double b, double c) {
        super(r, a, b, c);
        setHeight(h);
    }

    public void   setHeight(double h) { height = (h >= 1 ? h : 1); }
    public double getHeight()         { return height; }

    // =========================================================
    // TASK 2 — Override getName().
    // Return the string "Cylinder"
    // =========================================================
    @Override
    public String getName() {
        return "Cylinder";
    }

    @Override
    public String toString() {
        return super.toString() + "; Height = " + height;
    }

    private int hInt() { return (int) Math.max(2, Math.round(height)); }

    @Override public int footprintW() { return 2 * rInt() + 1; }
    @Override public int footprintH() { return hInt() + 2; }

    @Override
    public void draw(AsciiCanvas c) {
        int r   = rInt();
        int h   = hInt();
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        // Top ellipse
        c.put(sx,         sy, '(', col);
        c.put(sx + 2*r,   sy, ')', col);
        for (int i = 1; i < 2*r; i++) c.put(sx+i, sy, '-', col);
        // Side walls
        for (int row = 1; row <= h; row++) {
            c.put(sx,       sy+row, '|', col);
            c.put(sx + 2*r, sy+row, '|', col);
        }
        // Bottom ellipse
        int by = sy + h + 1;
        c.put(sx,         by, '(', col);
        c.put(sx + 2*r,   by, ')', col);
        for (int i = 1; i < 2*r; i++) c.put(sx+i, by, '-', col);
    }
}
