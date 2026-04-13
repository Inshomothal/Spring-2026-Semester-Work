/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project 04
* Date: 03/05/2026
* File: Driver.java
*
* Description: ↓↓↓
* ASCII Shape Parade — CSC123 Inheritance Demo
*
* What to look for while it runs:
*   RED shapes  (Circle, Sphere, Cylinder)  pass THROUGH each other
*   GREEN shapes (Square, Cube, Rectangle)  pass THROUGH each other
*   RED vs GREEN                            BOUNCE off each other
*
* Why? equals() in Shape compares color.
*   same color  → equals() returns true  → pass through
*   diff color  → equals() returns false → bounce
********************************************************************/
package proj04;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Driver {

    public static void main(String[] args) throws Exception {

        // Canvas size and play area (inside the border)
        final int W = 100, H = 28;
        AsciiCanvas        canvas = new AsciiCanvas(W, H);
        AsciiCanvas.Bounds bounds = new AsciiCanvas.Bounds(1, 1, W - 2, H - 2);
        Random rng = new Random(42);

        // ----------------------------------------------------------
        // Create shapes
        // Circle  (radius, startX, startY, z)
        // Sphere  (radius, startX, startY, z)
        // Cylinder(height, radius, startX, startY, z)
        // Square  (startX, startY, side)
        // Cube    (startX, startY, side)
        // Rectangle(startX, startY, width, height)
        // ----------------------------------------------------------

        // RED group — these pass through each other
        Circle   ci = new Circle  (3,  5,  3, 0);
        Sphere   sp = new Sphere  (3, 55,  3, 0);
        Cylinder cy = new Cylinder(5,  3, 15, 3, 0);
        ci.setColor(Color.RED);
        sp.setColor(Color.RED);
        cy.setColor(Color.RED);

        // GREEN group — these pass through each other
        Square    sq = new Square   (60,  3, 6);
        Cube      cu = new Cube     (30, 12, 5);
        Rectangle re = new Rectangle(70, 15, 9, 4);
        sq.setColor(Color.GREEN);
        cu.setColor(Color.GREEN);
        re.setColor(Color.GREEN);

        // ----------------------------------------------------------
        // Put all shapes in an array — this is polymorphism:
        // one array holds every shape type, and we call the
        // same tick() and draw() on all of them in the loop below.
        // ----------------------------------------------------------
        Shape[] shapes = { ci, sp, cy, sq, cu, re };
        List<Shape> shapeList = Arrays.asList(shapes);

        // Give each shape a starting direction (vx, vy)
        ci.setVelocity( 2,  1);
        sp.setVelocity(-2,  1);
        cy.setVelocity( 3, -1);
        sq.setVelocity(-1,  2);
        cu.setVelocity( 1, -1);
        re.setVelocity(-2, -2);

        // All shapes use the same bounce-off-walls movement policy
        MovePattern policy = MovePattern.bounce();

        // ----------------------------------------------------------
        // Animation loop
        // ----------------------------------------------------------
        System.out.print(Ansi.HIDE_CUR);
        try {
            for (int frame = 0; frame < 600; frame++) {

                canvas.clear(' ');
                canvas.border();

                // Move every shape one step (and check collisions)
                for (Shape s : shapes)
                    s.tick(policy, bounds, rng, shapeList);

                // Draw every shape onto the canvas
                for (Shape s : shapes)
                    s.draw(canvas);

                System.out.print(Ansi.CLEAR);
                System.out.print(canvas);
                System.out.println(legend(shapes));
                System.out.flush();

                Thread.sleep(60);
            }
        } finally {
            System.out.print(Ansi.SHOW_CUR);
        }

        // ----------------------------------------------------------
        // After animation: show equals() results in the terminal
        // ----------------------------------------------------------
        System.out.println("\n" + Ansi.bold("  equals() Demo — same color = true = pass through"));
        System.out.println("  " + "-".repeat(60));
        demo(ci, "Circle   RED",  sp, "Sphere    RED");
        demo(ci, "Circle   RED",  cy, "Cylinder  RED");
        demo(sq, "Square   GREEN", cu, "Cube      GREEN");
        demo(sq, "Square   GREEN", re, "Rectangle GREEN");
        demo(ci, "Circle   RED",  sq, "Square    GREEN");
        demo(cy, "Cylinder RED",  re, "Rectangle GREEN");
        System.out.println("  " + "-".repeat(60) + "\n");
    }

    // ----------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------

    // Print one equals() comparison with a clear plain-English result
    static void demo(Shape a, String labelA, Shape b, String labelB) {
        boolean same   = a.equals(b);
        String  result = same ? "true  →  pass through"
                               : "false →  bounce";
        System.out.printf("  %-18s .equals( %-18s ) = %s%n",
                          labelA, labelB, result);
    }

    // Legend bar shown below the canvas each frame
    static String legend(Shape[] shapes) {
        StringBuilder sb = new StringBuilder();
        sb.append(Ansi.bold(" Shapes:"));
        for (Shape s : shapes) {
            int code = s.getColor() == null ? Ansi.FG_WHITE : s.getColor().ansiCode;
            sb.append("  ").append(Ansi.color(code, s.getName()));
        }
        sb.append("   |   same color = pass through,  diff color = bounce");
        return sb.toString();
    }
}
