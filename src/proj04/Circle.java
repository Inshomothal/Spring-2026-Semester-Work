/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project 04
* Date: 03/05/2026
* File: Circle.java
*
* Description: ↓↓↓
* A circle: a Point (center) with a radius.  Draws as a ring of 'o'.
* Child of: Point
* Parent of: Sphere, Cylinder
********************************************************************/
package proj04;

public class Circle extends Point {

    protected double radius;

    public Circle() {
        this(3, 0, 0, 0);
    }

    // r = radius, (a,b,c) = center coordinates
    public Circle(double r, double a, double b, double c) {
        super(a, b, c);
        setRadius(r);
    }

    public void setRadius(double r) {
        radius = (r >= 1 ? r : 1);
    }

    public double getRadius() {
        return radius;
    }

    // =========================================================
    // TASK 1 — Override getName().
    // Return the string "Circle"
    // =========================================================
    @Override
    public String getName() {
        return "Circle";
    }

    @Override
    public String toString() {
        return "Center = " + super.toString() + "; Radius = " + radius;
    }

    protected int rInt() {
        return (int) Math.max(1, Math.round(radius));
    }

    @Override
    public int footprintW() {
        return 2 * rInt() + 1;
    }

    @Override
    public int footprintH() {
        return 2 * rInt() + 1;
    }

    @Override
    public void draw(AsciiCanvas c) {
        int r = rInt();
        int cx = sx + r;
        int cy = sy + r;
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        for (int dy = -r; dy <= r; dy++) {
            for (int dx = -r; dx <= r; dx++) {
                int d2 = dx * dx + dy * dy;
                if (Math.abs(d2 - r * r) <= r)
                    c.put(cx + dx, cy + dy, 'o', col);
            }
        }
    }
}
