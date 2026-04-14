/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: Rectangle.java
*
* Description: ↓↓↓
* A rectangle: a Square with an independent height dimension.
* Child of: Square
********************************************************************/
package proj04;

public class Rectangle extends Square {

    protected double height2;   // second dimension (width comes from Square's "side")

    public Rectangle() { this(1, 1, 7, 3); }

    // sx, sy = top-left corner   w = width   h = height
    public Rectangle(int sx, int sy, double w, double h) {
        super(sx, sy, w);       // passes width up as "side"
        setHeight2(h);
    }

    public void   setHeight2(double h) { height2 = (h >= 1 ? h : 1); }
    public double getHeight2()         { return height2; }

    // =========================================================
    // TASK 2 — Override getName().
    // Return the string "Rectangle"
    // =========================================================
    @Override
    public String getName() {
        return "Rectangle";
    }

    @Override
    public String toString() {
        return "Corner = [" + sx + ", " + sy + "]"
             + "; Width = " + side + "; Height = " + height2;
    }

    // private helper — already written for you
    private int hInt2() { return (int) Math.max(1, Math.round(height2)); }

    // =========================================================
    // TASK 4a — Override footprintW() and footprintH().
    //
    // Square uses a square footprint (side x side).
    // Rectangle is wider than tall, so we need to override both.
    //
    //   footprintW() should return sInt()   ← width  (from Square)
    //   footprintH() should return hInt2()  ← height (from Rectangle)
    //
    // If you skip this, the collision box will be wrong.
    // =========================================================
    @Override
    public int footprintW() {
        return sInt(); 
    }

    @Override
    public int footprintH() {
        return hInt2(); 
    }

    // =========================================================
    // TASK 4b — Override draw() for the rectangle style.
    //
    // Without this, Rectangle inherits Square's draw() and
    // looks like a square drawn with # characters.
    //
    // The rectangle style uses:
    //   '='  for the top and bottom edges
    //   '|'  for the left and right edges
    //   '+'  for all four corners
    //
    // Available variables:
    //   int w   = sInt();    ← width  (call this first)
    //   int h   = hInt2();   ← height
    //   int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
    //   sx, sy  ← top-left corner (inherited from Shape)
    //
    // Pattern — same loops as Square's draw(), just different chars:
    //   top edge:    for dx 0..w-1  put '=' at (sx+dx, sy)
    //   bottom edge: for dx 0..w-1  put '=' at (sx+dx, sy+h-1)
    //   left edge:   for dy 0..h-1  put '|' at (sx, sy+dy)
    //   right edge:  for dy 0..h-1  put '|' at (sx+w-1, sy+dy)
    //   corners:     put '+' at each of the four corners
    // =========================================================
    @Override
    public void draw(AsciiCanvas c) {
        int w = sInt();
        int h = hInt2();
        int col = color == null ? Ansi.FG_WHITE : color.ansiCode;
        

        c.put(sx, sy, '+', col);
        c.put(sx, sy+h-1, '+', col);
        c.put(sx+w-1, sy, '+', col);
        c.put(sx + w - 1, sy + h - 1, '+', col);
        
        for (int dx = 1; dx < w - 1; dx++) {
            c.put(dx + sx, sy, '=', col);
            c.put(dx + sx, sy + h - 1, '=', col);
        }
        
        for (int dy = 1; dy < h - 1; dy++) {
            c.put(sx, dy+sy, '|', col);
            c.put(sx+w-1, dy+sy, '|', col);
        }


    }
}
