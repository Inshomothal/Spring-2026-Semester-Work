/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project 04
* Date: 03/05/2026
* File: Tester.java
*
* Description: ↓↓↓
* ============================================================
* CSC123  —  Inheritance Project  Auto-Grader
*
* Tests all four student tasks without running the animation.
* Compile alongside the student's submitted .java files:
*
*     javac *.java
*     java InheritanceTester
*
* Each test prints [PASS] or [FAIL] followed by a short label.
* A score summary is printed at the end.
* ============================================================
********************************************************************/
package proj04;

public class Tester {

    private static int testsRun    = 0;
    private static int testsPassed = 0;

    // ── main ─────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("====================================================");
        System.out.println("  CSC123  Inheritance Project  —  Auto-Grader");
        System.out.println("====================================================\n");

        testTask1_getName();
        testTask2_equals();
        testTask3_sphereDraw();
        testTask4_rectangle();

        System.out.println("\n====================================================");
        System.out.printf ("  SCORE:  %d / %d tests passed%n", testsPassed, testsRun);
        System.out.println("====================================================");
    }

    // ============================================================
    // TASK 1  —  getName() overrides
    // 7 subclasses must each return their own name (not "Shape").
    // ============================================================
    private static void testTask1_getName() {
        System.out.println("=== Task 1: getName() Overrides ===");

        assertTrue("Point    .getName() == \"Point\"",
                "Point".equals(new Point().getName()));
        assertTrue("Circle   .getName() == \"Circle\"",
                "Circle".equals(new Circle().getName()));
        assertTrue("Sphere   .getName() == \"Sphere\"",
                "Sphere".equals(new Sphere().getName()));
        assertTrue("Cylinder .getName() == \"Cylinder\"",
                "Cylinder".equals(new Cylinder().getName()));
        assertTrue("Square   .getName() == \"Square\"",
                "Square".equals(new Square().getName()));
        assertTrue("Rectangle.getName() == \"Rectangle\"",
                "Rectangle".equals(new Rectangle().getName()));
        assertTrue("Cube     .getName() == \"Cube\"",
                "Cube".equals(new Cube().getName()));

        // Make sure base class still returns "Shape"
        assertTrue("Shape    .getName() == \"Shape\" (base class unchanged)",
                "Shape".equals(new Shape().getName()));

        System.out.println();
    }

    // ============================================================
    // TASK 2  —  equals() override in Shape
    //
    // Same color  → true  (pass through)
    // Diff color  → false (bounce)
    // Self        → true
    // null        → false  (required by equals() contract)
    // ============================================================
    private static void testTask2_equals() {
        System.out.println("=== Task 2: equals() / Collision Rule ===");

        Circle    ci = new Circle();
        Sphere    sp = new Sphere();
        Square    sq = new Square();
        Rectangle re = new Rectangle();

        // Same color → true
        ci.setColor(Color.RED);
        sp.setColor(Color.RED);
        assertTrue("Same color (RED==RED)  → true  (pass through)",
                ci.equals(sp));

        // Different color → false
        sq.setColor(Color.GREEN);
        re.setColor(Color.BLUE);
        assertTrue("Diff color (GREEN!=BLUE) → false (bounce)",
                !sq.equals(re));

        // Same color same branch (Square vs Cube)
        Cube cu = new Cube();
        sq.setColor(Color.GREEN);
        cu.setColor(Color.GREEN);
        assertTrue("Same color (GREEN==GREEN, Square&Cube) → true",
                sq.equals(cu));

        // Cross-branch different color → false
        ci.setColor(Color.RED);
        sq.setColor(Color.GREEN);
        assertTrue("Diff color cross-branch (RED!=GREEN) → false",
                !ci.equals(sq));

        // Reflexive: a.equals(a) must be true
        assertTrue("Reflexive: shape.equals(itself) → true",
                ci.equals(ci));

        // null argument must not throw, must return false
        boolean nullSafe = false;
        try {
            nullSafe = !ci.equals(null);
        } catch (Exception e) {
            nullSafe = false;
        }
        assertTrue("Null-safe: ci.equals(null) does not throw and returns false",
                nullSafe);

        // Non-Shape argument must return false
        boolean nonShapeSafe = false;
        try {
            nonShapeSafe = !ci.equals("hello");
        } catch (Exception e) {
            nonShapeSafe = false;
        }
        assertTrue("Type-safe: ci.equals(\"hello\") returns false",
                nonShapeSafe);

        // No-color shapes: both null colors should NOT count as equal
        // (the spec requires non-null color)
        Shape noColor1 = new Shape();
        Shape noColor2 = new Shape();
        assertTrue("No-color shapes: equals() returns false (color is null)",
                !noColor1.equals(noColor2));

        System.out.println();
    }

    // ============================================================
    // TASK 3  —  Sphere.draw() — must produce a FILLED ball
    //
    // Strategy: draw a Sphere onto a small canvas and inspect
    // the characters written to it.
    //
    // Correct:  contains 'O' and/or '.'
    //           does NOT contain only 'o' (that would be Circle's ring)
    // Wrong:    only 'o' characters → student forgot to override draw()
    // ============================================================
    private static void testTask3_sphereDraw() {
        System.out.println("=== Task 3: Sphere.draw() — Filled Ball ===");

        final int W = 30, H = 20;
        AsciiCanvas canvas = new AsciiCanvas(W, H);
        canvas.clear(' ');

        Sphere sp = new Sphere(5, 5, 5, 0);   // radius 5, positioned at (5,5)
        sp.setColor(Color.RED);
        sp.draw(canvas);

        // Inspect every character written to the canvas
        boolean hasO         = false;   // outer ring character
        boolean hasDot       = false;   // inner fill character
        boolean hasLowerO    = false;   // Circle's ring character (wrong)
        int     totalWritten = 0;

        String rendered = stripAnsi(canvas.toString());
        for (char ch : rendered.toCharArray()) {
            if (ch == 'O') { hasO      = true; totalWritten++; }
            if (ch == '.') { hasDot    = true; totalWritten++; }
            if (ch == 'o') { hasLowerO = true; totalWritten++; }
        }

        assertTrue("Sphere.draw() writes at least one character to canvas",
                totalWritten > 0);
        assertTrue("Sphere.draw() uses 'O' (outer ring character)",
                hasO);
        assertTrue("Sphere.draw() uses '.' (inner fill character)",
                hasDot);
        assertTrue("Sphere.draw() does NOT use 'o' (that is Circle's character)",
                !hasLowerO);

        // Sanity: Circle should still draw only 'o' (unchanged)
        AsciiCanvas canvas2 = new AsciiCanvas(W, H);
        canvas2.clear(' ');
        Circle ci = new Circle(5, 5, 5, 0);
        ci.setColor(Color.RED);
        ci.draw(canvas2);
        boolean circleHasLowerO = stripAnsi(canvas2.toString()).indexOf('o') >= 0;
        assertTrue("Circle.draw() still uses 'o' (base behavior unchanged)",
                circleHasLowerO);

        System.out.println();
    }

    // ============================================================
    // TASK 4  —  Rectangle footprintW/H and draw()
    //
    // A Rectangle(sx, sy, width, height) with width != height must:
    //   footprintW() == width   (not height)
    //   footprintH() == height  (not width)
    //   draw() uses '=', '|', '+' — not '#' (Square's character)
    // ============================================================
    private static void testTask4_rectangle() {
        System.out.println("=== Task 4: Rectangle footprint + draw() ===");

        // Use clearly non-square dimensions so we can detect mix-ups
        Rectangle re = new Rectangle(1, 1, 10, 4);  // width=10, height=4

        // footprintW must equal the width (10), not the height (4)
        assertTrue("footprintW() == width (10)",
                re.footprintW() == 10);

        // footprintH must equal the height (4), not the width (10)
        assertTrue("footprintH() == height (4)",
                re.footprintH() == 4);

        // footprintW != footprintH  (must be non-square)
        assertTrue("footprintW() != footprintH()  (rectangle, not square)",
                re.footprintW() != re.footprintH());

        // Draw and inspect characters
        final int W = 40, H = 20;
        AsciiCanvas canvas = new AsciiCanvas(W, H);
        canvas.clear(' ');
        re.setColor(Color.BLUE);
        re.draw(canvas);

        String rendered = stripAnsi(canvas.toString());
        boolean hasEquals  = rendered.indexOf('=') >= 0;
        boolean hasPipe    = rendered.indexOf('|') >= 0;
        boolean hasPlus    = rendered.indexOf('+') >= 0;
        boolean hasHash    = rendered.indexOf('#') >= 0;   // Square's char (wrong)

        assertTrue("Rectangle.draw() uses '=' for top/bottom edges",
                hasEquals);
        assertTrue("Rectangle.draw() uses '|' for side edges",
                hasPipe);
        assertTrue("Rectangle.draw() uses '+' for corners",
                hasPlus);
        assertTrue("Rectangle.draw() does NOT use '#' (that is Square's character)",
                !hasHash);

        // Verify footprint with a square rectangle — both dims equal
        Rectangle sq_re = new Rectangle(1, 1, 6, 6);
        assertTrue("footprintW() == footprintH() when width==height",
                sq_re.footprintW() == sq_re.footprintH());

        System.out.println();
    }

    // ── Assertion helpers ────────────────────────────────────────
    private static void assertTrue(String label, boolean condition) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println("  [PASS]  " + label);
        } else {
            System.out.println("  [FAIL]  " + label);
        }
    }

    /** Strip ANSI escape sequences so we can inspect raw characters. */
    private static String stripAnsi(String s) {
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
