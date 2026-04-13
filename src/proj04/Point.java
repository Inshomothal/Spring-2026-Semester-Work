/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Point.java
*
* Description: ↓↓↓
* A single point in 3-D space.  Draws as '*'.
* Child of: Shape
* Parent of: Circle
********************************************************************/
package proj04;

public class Point extends Shape {

    protected double x, y, z;

    public Point() { this(0, 0, 0); }

    public Point(double x, double y, double z) {
        super((int) Math.round(x), (int) Math.round(y), 1, 1);
        this.x = x;  this.y = y;  this.z = z;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    public void setPoint(double x, double y, double z) {
        this.x = x;  this.y = y;  this.z = z;
        this.sx = (int) Math.round(x);
        this.sy = (int) Math.round(y);
    }

    // =========================================================
    // TASK 1 — Override getName().
    // Return the string "Point"
    // =========================================================
    @Override
    public String getName() {
        return "Point";
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    @Override public int footprintW() { return 1; }
    @Override public int footprintH() { return 1; }

    @Override
    public void draw(AsciiCanvas c) {
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        c.put(sx, sy, '*', col);
    }
}
