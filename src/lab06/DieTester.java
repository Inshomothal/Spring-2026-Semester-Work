package lab06;

import java.util.Random;

public class DieTester 
{
    static int passed = 0;
    static int failed = 0;

    static final String BANNER =
        "██████╗ ██╗ ██████╗███████╗    ████████╗███████╗███████╗████████╗███████╗██████╗ \n" +
        "██╔══██╗██║██╔════╝██╔════╝    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██╔════╝██╔══██╗\n" +
        "██║  ██║██║██║     █████╗         ██║   █████╗  ███████╗   ██║   █████╗  ██████╔╝\n" +
        "██║  ██║██║██║     ██╔══╝         ██║   ██╔══╝  ╚════██║   ██║   ██╔══╝  ██╔══██╗\n" +
        "██████╔╝██║╚██████╗███████╗       ██║   ███████╗███████║   ██║   ███████╗██║  ██║\n" +
        "╚═════╝ ╚═╝ ╚═════╝╚══════╝       ╚═╝   ╚══════╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝";

    // ─── assertion helpers ────────────────────────────────────────────────────

    static void assertTrue(String label, boolean condition) 
    {
        if (condition) 
        {
            System.out.printf("  [PASS] %s%n", label);
            passed++;
        } 
        else 
        {
            System.out.printf("  [FAIL] %s%n", label);
            failed++;
        }
    }

    static void assertEquals(String label, int expected, int actual) 
    {
        if (expected == actual) 
        {
            System.out.printf("  [PASS] %s  (expected=%d, got=%d)%n", label, expected, actual);
            passed++;
        } 
        else 
        {
            System.out.printf("  [FAIL] %s  (expected=%d, got=%d)%n", label, expected, actual);
            failed++;
        }
    }

    static void assertRange(String label, int value, int lo, int hi) 
    {
        if (value >= lo && value <= hi) 
        {
            System.out.printf("  [PASS] %s  (%d in [%d..%d])%n", label, value, lo, hi);
            passed++;
        } 
        else 
        {
            System.out.printf("  [FAIL] %s  (%d NOT in [%d..%d])%n", label, value, lo, hi);
            failed++;
        }
    }

    static void assertContains(String label, boolean seen) 
    {
        assertTrue(label, seen);
    }

    static void header(String title) 
    {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.printf ("  ║  %-48s║%n", title);
        System.out.println("  ╚══════════════════════════════════════════════════╝");
    }

    // ─── test suites ──────────────────────────────────────────────────────────

    static void testDefaultConstructor() 
    {
        header("1. DEFAULT CONSTRUCTOR");
        Die d = new Die();
        assertEquals("default sides = 6", 6, d.getNumSides());
        int r = d.roll();
        assertRange("roll in [1..6]", r, 1, 6);
    }

    static void testParameterizedConstructor() 
    {
        header("2. PARAMETERIZED CONSTRUCTOR");
        Die d4  = new Die(4);
        Die d20 = new Die(20);
        Die d1  = new Die(1);

        assertEquals("d4  sides = 4",  4,  d4.getNumSides());
        assertEquals("d20 sides = 20", 20, d20.getNumSides());
        assertEquals("d1  sides = 1",  1,  d1.getNumSides());

        assertRange("d4  roll in [1..4]",  d4.roll(),  1, 4);
        assertRange("d20 roll in [1..20]", d20.roll(), 1, 20);
        assertEquals("d1  always rolls 1", 1, d1.roll());
    }

    static void testInvalidSidesFallback() 
    {
        header("3. INVALID SIDES -> FALLBACK TO 6");
        System.out.println("  (expect error messages below from Die)");

        Die dZero = new Die(0);
        assertEquals("sides=0  falls back to 6", 6, dZero.getNumSides());

        Die dNeg = new Die(-99);
        assertEquals("sides=-99 falls back to 6", 6, dNeg.getNumSides());

        Die dNegOne = new Die(-1);
        assertEquals("sides=-1  falls back to 6", 6, dNegOne.getNumSides());
    }

    static void testSetNumSides() 
    {
        header("4. setNumSides()");
        Die d = new Die(6);

        d.setNumSides(12);
        assertEquals("set to 12", 12, d.getNumSides());

        d.setNumSides(1);
        assertEquals("set to 1",  1,  d.getNumSides());

        System.out.println("  (expect error message below from Die)");
        d.setNumSides(-5);
        assertEquals("set -5 falls back to 6", 6, d.getNumSides());

        System.out.println("  (expect error message below from Die)");
        d.setNumSides(0);
        assertEquals("set 0 falls back to 6", 6, d.getNumSides());
    }

    static void testSeededRandom() 
    {
        header("5. SEEDED RANDOM CONSTRUCTOR");
        Random rng1 = new Random(42);
        Random rng2 = new Random(42);

        Die d1 = new Die(6, rng1);
        Die d2 = new Die(6, rng2);

        boolean allMatch = true;
        for (int i = 0; i < 1000; i++) 
        {
            if (d1.roll() != d2.roll()) 
            {
                allMatch = false;
                break;
            }
        }
        assertTrue("same seed -> identical 1000 rolls", allMatch);
    }

    static void testRollBoundaryExhaustive() 
    {
        header("6. ROLL BOUNDARY (exhaustive, 100k rolls each)");
        int[] sideCounts = {2, 4, 6, 8, 10, 12, 20, 100};

        for (int sides : sideCounts) 
        {
            Die d   = new Die(sides);
            int  min = Integer.MAX_VALUE;
            int  max = Integer.MIN_VALUE;

            for (int i = 0; i < 100_000; i++) 
            {
                int r = d.roll();
                if (r < min) min = r;
                if (r > max) max = r;
            }
            assertTrue(String.format("d%-3d  min=1   (got %d)", sides, min), min == 1);
            assertTrue(String.format("d%-3d  max=%-3d (got %d)", sides, sides, max), max == sides);
        }
    }

    static void testDistributionUniformity() 
    {
        header("7. DISTRIBUTION UNIFORMITY (chi-square, 600k rolls on d6)");
        int sides  = 6;
        int trials = 600_000;
        int[]  freq     = new int[sides + 1];
        Die    d        = new Die(sides);
        double expected = (double) trials / sides;

        for (int i = 0; i < trials; i++) 
            freq[d.roll()]++;

        double chi2 = 0;
        for (int face = 1; face <= sides; face++) 
        {
            double diff = freq[face] - expected;
            chi2 += (diff * diff) / expected;
            System.out.printf("  face %d: %6d rolls  (expected ~%6.0f)%n", face, freq[face], expected);
        }

        System.out.printf("  chi2 = %.4f  (threshold < 15.09 for p=0.01, df=5)%n", chi2);
        assertTrue("chi-square test passes (uniform distribution)", chi2 < 15.09);
    }

    static void testAllFacesSeen() 
    {
        header("8. ALL FACES HIT (1000 rolls each die)");
        int[] sizes = {2, 4, 6, 8, 10, 20};

        for (int sides : sizes) 
        {
            boolean[] seen = new boolean[sides + 1];
            Die d = new Die(sides);

            for (int i = 0; i < 1000; i++) 
                seen[d.roll()] = true;

            boolean allSeen = true;
            for (int f = 1; f <= sides; f++) 
                if (!seen[f]) { allSeen = false; break; }

            assertTrue(String.format("d%d  all %d faces seen in 1000 rolls", sides, sides), allSeen);
        }
    }

    static void testRapidFireStress() 
    {
        header("9. RAPID-FIRE STRESS (1,000,000 rolls, no exceptions)");
        Die d = new Die(6);
        boolean ok = true;

        try 
        {
            for (int i = 0; i < 1_000_000; i++) 
            {
                int r = d.roll();
                if (r < 1 || r > 6) { ok = false; break; }
            }
        } 
        catch (Exception e) 
        {
            ok = false;
            System.out.println("  EXCEPTION: " + e.getMessage());
        }

        assertTrue("1,000,000 rolls all in [1..6], no exceptions", ok);
    }

    static void testMassiveSides() 
    {
        header("10. EXTREME SIDES VALUES");
        Die dMax = new Die(Integer.MAX_VALUE);
        assertEquals("sides = Integer.MAX_VALUE", Integer.MAX_VALUE, dMax.getNumSides());

        int r = dMax.roll();
        assertRange("roll in [1..MAX_VALUE]", r, 1, Integer.MAX_VALUE);

        Die d1000 = new Die(1000);
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < 100_000; i++) 
        {
            int v = d1000.roll();
            if (v < min) min = v;
            if (v > max) max = v;
        }
        assertTrue("d1000 min >= 1",    min >= 1);
        assertTrue("d1000 max <= 1000", max <= 1000);
    }

    static void testMutationAfterRolls() 
    {
        header("11. MUTATION MID-GAME (setNumSides after rolls)");
        Die d = new Die(6);

        for (int i = 0; i < 50; i++) d.roll();

        d.setNumSides(20);
        assertEquals("sides updated to 20 mid-game", 20, d.getNumSides());

        boolean allInRange = true;
        for (int i = 0; i < 10_000; i++) 
        {
            int r = d.roll();
            if (r < 1 || r > 20) { allInRange = false; break; }
        }
        assertTrue("10k rolls after mutation all in [1..20]", allInRange);
    }

    static void testToString() 
    {
        header("12. toString()");
        Die d6  = new Die(6);
        Die d20 = new Die(20);

        assertTrue("d6  toString not null/empty",    d6.toString()  != null && !d6.toString().isEmpty());
        assertTrue("d20 toString not null/empty",    d20.toString() != null && !d20.toString().isEmpty());
        assertTrue("d6  toString contains '6'",      d6.toString().contains("6"));
        assertTrue("d20 toString contains '20'",     d20.toString().contains("20"));

        System.out.println("  d6  -> " + d6);
        System.out.println("  d20 -> " + d20);
    }

    static void testConcurrentRolls() 
    {
        header("13. CONCURRENT STRESS (10 threads x 100k rolls each)");
        int THREADS = 10;
        int ROLLS   = 100_000;
        int[] outOfRange = {0};
        Thread[] threads = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) 
        {
            final int tid = t;
            threads[t] = new Thread(() -> 
            {
                Die d = new Die(6);
                for (int i = 0; i < ROLLS; i++) 
                {
                    int r = d.roll();
                    if (r < 1 || r > 6) 
                        outOfRange[0]++;
                }
            });
        }

        for (Thread th : threads) th.start();
        try 
        {
            for (Thread th : threads) th.join();
        } 
        catch (InterruptedException e) 
        {
            System.out.println("  INTERRUPTED: " + e.getMessage());
        }

        assertEquals("0 out-of-range rolls across all threads", 0, outOfRange[0]);
    }

    // ─── main ─────────────────────────────────────────────────────────────────

    public static void main(String[] args) 
    {
        System.out.println(BANNER);
        System.out.println();

        long start = System.currentTimeMillis();

        testDefaultConstructor();
        testParameterizedConstructor();
        testInvalidSidesFallback();
        testSetNumSides();
        testSeededRandom();
        testRollBoundaryExhaustive();
        testDistributionUniformity();
        testAllFacesSeen();
        testRapidFireStress();
        testMassiveSides();
        testMutationAfterRolls();
        testToString();
        testConcurrentRolls();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║                   FINAL RESULTS                 ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.printf ("  ║  PASSED : %-38d║%n", passed);
        System.out.printf ("  ║  FAILED : %-38d║%n", failed);
        System.out.printf ("  ║  TOTAL  : %-38d║%n", passed + failed);
        System.out.printf ("  ║  TIME   : %-35dms ║%n", elapsed);
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        if (failed == 0)
            System.out.println("  ║         ALL TESTS PASSED. DICE IS SOLID.        ║");
        else
            System.out.printf("  ║  %-47s║%n", failed + " TEST(S) FAILED. CHECK OUTPUT ABOVE.");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }
}
