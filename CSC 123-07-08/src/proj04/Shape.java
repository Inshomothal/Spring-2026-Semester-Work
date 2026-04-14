/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Shape.java
*
* Description: ↓↓↓
* Base class for ALL shapes.
*
* Hierarchy:
*   Shape
*   ├── Point
*   │   └── Circle
*   │       ├── Sphere
*   │       └── Cylinder
*   └── Square
*       ├── Rectangle
*       └── Cube
********************************************************************/
package proj04;

import java.util.List;
import java.util.Random;

public class Shape {

    public String getName() { return "Shape"; }

    // ----------------------------------------------------------
    // Color — drives rendering AND collision
    // Same color → pass through.  Different color → bounce.
    // ----------------------------------------------------------
    protected Color color;

    public Color getColor()        { return color; }
    public void  setColor(Color c) { this.color = c; }

    // =========================================================
    // TASK 2 — Write the body of equals().
    //
    // Two shapes are "equal" when they have the same color.
    //   same color  → return true  → shapes pass through
    //   diff color  → return false → shapes bounce
    //
    // Steps:
    //   1. If obj is null return false
    //   2. If obj is this exact object, return true.
    //   3. If obj is not a Shape, return false.
    //   4. Cast obj to Shape.
    //   5. Return true only if both colors are equal.
    //
    // Hint: Color is an enum — you can compare with ==
    // =========================================================
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Shape) {
            Shape colorCompare = (Shape) obj;
            if (this.color == null || colorCompare.color == null) {
                return false;
            }
            return this.color == colorCompare.color;
        }
        return false;

    }

    @Override
    public int hashCode() {
       return (color == null) ? 0 : color.hashCode();
    }

    // ----------------------------------------------------------
    // Screen position & velocity
    // ----------------------------------------------------------
    protected int sx, sy;
    protected int vx, vy;

    public Shape() { this(1, 1, 1, 0); }

    public Shape(int sx, int sy, int vx, int vy) {
        this.sx = sx;  this.sy = sy;
        this.vx = vx;  this.vy = vy;
    }

    public int  getScreenX()                { return sx; }
    public int  getScreenY()                { return sy; }
    public void setScreen(int x, int y)     { sx = x; sy = y; }
    public int  getVx()                     { return vx; }
    public int  getVy()                     { return vy; }
    public void setVelocity(int vx, int vy) { this.vx = vx; this.vy = vy; }

    public int footprintW() { return 1; }
    public int footprintH() { return 1; }

    public int     left()   { return sx; }
    public int     top()    { return sy; }
    public int     right()  { return sx + footprintW() - 1; }
    public int     bottom() { return sy + footprintH() - 1; }

    public boolean overlaps(Shape other) {
        return left()  <= other.right()  && right()  >= other.left()
            && top()   <= other.bottom() && bottom() >= other.top();
    }

    public void draw(AsciiCanvas c) {
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        c.put(sx, sy, '?', col);
    }

    public void tick(MovePattern policy, AsciiCanvas.Bounds b, Random rng, List<Shape> others) {
        applyPolicy(policy, b, rng);
        if (others != null) {
            for (Shape other : others) {
                if (other == this) continue;
                if (this.equals(other)) continue;   // same color → pass through
                if (overlaps(other)) {
                    vx = -vx;  vy = -vy;
                    sx += vx;  sy += vy;
                    clamp(b);
                }
            }
        }
    }

    public void tick(MovePattern policy, AsciiCanvas.Bounds b, Random rng) {
        tick(policy, b, rng, null);
    }

    private void applyPolicy(MovePattern policy, AsciiCanvas.Bounds b, Random rng) {
        if (policy == null || policy.mode == MovePattern.Mode.STATIC) return;
        switch (policy.mode) {
            case BOUNCE:
                sx += vx;  sy += vy;
                wallBounce(b);
                break;
            case WRAP:
                sx += vx;  sy += vy;
                wrapAround(b);
                break;
            case RANDOM_WALK:
                if (rng == null) rng = new Random();
                int dvx = (policy.jitter == 0) ? 0 : rng.nextInt(policy.jitter*2+1) - policy.jitter;
                int dvy = (policy.jitter == 0) ? 0 : rng.nextInt(policy.jitter*2+1) - policy.jitter;
                vx += dvx;  vy += dvy;
                if (policy.maxSpeed > 0) {
                    vx = Math.max(-policy.maxSpeed, Math.min(policy.maxSpeed, vx));
                    vy = Math.max(-policy.maxSpeed, Math.min(policy.maxSpeed, vy));
                }
                if (vx == 0 && vy == 0) vx = 1;
                sx += vx;  sy += vy;
                wallBounce(b);
                break;
            default: break;
        }
    }

    protected void wallBounce(AsciiCanvas.Bounds b) {
        int maxX = b.maxX - (footprintW()-1);
        int maxY = b.maxY - (footprintH()-1);
        if (sx < b.minX) { sx = b.minX; vx =  Math.abs(vx); }
        if (sx > maxX)   { sx = maxX;   vx = -Math.abs(vx); }
        if (sy < b.minY) { sy = b.minY; vy =  Math.abs(vy); }
        if (sy > maxY)   { sy = maxY;   vy = -Math.abs(vy); }
    }

    private void wrapAround(AsciiCanvas.Bounds b) {
        int maxX = b.maxX - (footprintW()-1);
        int maxY = b.maxY - (footprintH()-1);
        if (sx < b.minX) sx = maxX;  if (sx > maxX) sx = b.minX;
        if (sy < b.minY) sy = maxY;  if (sy > maxY) sy = b.minY;
    }

    private void clamp(AsciiCanvas.Bounds b) {
        int maxX = b.maxX - (footprintW()-1);
        int maxY = b.maxY - (footprintH()-1);
        sx = Math.max(b.minX, Math.min(maxX, sx));
        sy = Math.max(b.minY, Math.min(maxY, sy));
    }
}
