/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Sphere.java
*
* Description: ↓↓↓
* A sphere: a Circle with equal depth.  Draws as a filled ball.
* Child of: Circle
********************************************************************/
package proj04;

public class Sphere extends Circle {

    public Sphere() { this(3, 0, 0, 0); }

    public Sphere(double r, double a, double b, double c) {
        super(r, a, b, c);
    }

    // =========================================================
    // TASK 2 — Override getName().
    // Return the string "Sphere"
    // =========================================================
    @Override
    public String getName() {
        return "Sphere";
    }

    @Override
    public String toString() {
        return "Center = " + super.toString() + " (Sphere)";
    }

    // =========================================================
    // TASK 3 — Override draw() so Sphere looks FILLED,
    //          not hollow like Circle.
    //
    // Without this method, Sphere inherits Circle's draw()
    // and looks like a ring of 'o' characters.
    // With it, Sphere should look like a solid ball.
    //
    // Steps:
    //   1. Get the integer radius: int r = rInt();
    //      (rInt() is inherited from Circle)
    //   2. Calculate the screen center:
    //        int cx = sx + r;
    //        int cy = sy + r;
    //   3. Get the color code:
    //        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
    //   4. Loop dy from -r to +r, and dx from -r to +r
    //   5. For each cell where dx*dx + dy*dy <= r*r  (inside the circle):
    //        int d2 = dx*dx + dy*dy;
    //        char ch = (Math.abs(d2 - r*r) <= r+1) ? 'O' : '.';
    //        c.put(cx+dx, cy+dy, ch, col);
    // =========================================================
    @Override
    public void draw(AsciiCanvas c) {
        int r = rInt();
        int cx = sx + r;
        int cy = sy + r;
        int col = color == null ? Ansi.FG_WHITE : this.color.ansiCode; 

        for (int dy = -r; dy <= r; dy++) {
            for (int dx = -r; dx <= r; dx++) {
                int d2 = dx * dx + dy * dy;
                char ch = Math.abs(d2 - r * r) <= r + 1 ? 'O' : (d2 == 18 ? ' ' : '.');
                c.put(cx + dx, cy + dy, ch, col);
            }
        }



    }
}
