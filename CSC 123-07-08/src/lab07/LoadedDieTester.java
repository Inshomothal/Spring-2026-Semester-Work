package lab07;

import java.util.Random;
import lab06.Die;

/***********************************************************
 * Instructor Tester
 * Course: CSC123
 * Lab: Lab 07
 * File: LoadedDieTester.java
 * Description: Deterministic tests for LoadedDie.
 ***********************************************************/
public class LoadedDieTester {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testDefaultConstructor();
        testSidesOnlyConstructor();
        testFullConstructor();
        testInvalidLoadedSideLow();
        testInvalidLoadedSideHigh();
        testInvalidBiasLow();
        testInvalidBiasHigh();
        testBiasZeroBehavesFairly();
        testLoadedSideZeroBehavesFairly();
        testBiasOneAlwaysReturnsLoadedSide();
        testRollStaysInRange();
        testToStringFormat();

        System.out.println();
        System.out.println("================================");
        System.out.println("LOADEDDIE TESTER RESULTS");
        System.out.println("================================");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void assertTrue(String label, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + label);
        } else {
            failed++;
            System.out.println("[FAIL] " + label);
        }
    }

    private static void testDefaultConstructor() {
        LoadedDie d = new LoadedDie();
        assertTrue("LoadedDie() uses 6 sides", d.getNumSides() == 6);
        assertTrue("LoadedDie() default loadedSide is 0", d.getLoadedSide() == 0);
        assertTrue("LoadedDie() default bias is 0.0", d.getBias() == 0.0);
    }

    private static void testSidesOnlyConstructor() {
        LoadedDie d = new LoadedDie(10);
        assertTrue("LoadedDie(10) uses 10 sides", d.getNumSides() == 10);
        assertTrue("LoadedDie(10) default loadedSide is 0", d.getLoadedSide() == 0);
        assertTrue("LoadedDie(10) default bias is 0.0", d.getBias() == 0.0);
    }

    private static void testFullConstructor() {
        LoadedDie d = new LoadedDie(12, 7, 0.25);
        assertTrue("full constructor sets sides", d.getNumSides() == 12);
        assertTrue("full constructor sets loadedSide", d.getLoadedSide() == 7);
        assertTrue("full constructor sets bias", d.getBias() == 0.25);
    }

    private static void testInvalidLoadedSideLow() {
        LoadedDie d = new LoadedDie(6, -1, 0.4);
        assertTrue("loadedSide < 0 resets to 0", d.getLoadedSide() == 0);
    }

    private static void testInvalidLoadedSideHigh() {
        LoadedDie d = new LoadedDie(6, 9, 0.4);
        assertTrue("loadedSide > numSides resets to 0", d.getLoadedSide() == 0);
    }

    private static void testInvalidBiasLow() {
        LoadedDie d = new LoadedDie(6, 3, -0.1);
        assertTrue("bias < 0 resets to 0.0", d.getBias() == 0.0);
    }

    private static void testInvalidBiasHigh() {
        LoadedDie d = new LoadedDie(6, 3, 1.5);
        assertTrue("bias > 1 resets to 0.0", d.getBias() == 0.0);
    }

    private static void testBiasZeroBehavesFairly() {
        Random seed = new Random(42);
        LoadedDie loaded = new LoadedDie(6, seed, 5, 0.0);

        Random seed2 = new Random(42);
        Die fair = new Die(6, seed2);

        boolean same = true;
        for (int i = 0; i < 20; i++) {
            if (loaded.roll() != fair.roll()) {
                same = false;
                break;
            }
        }

        assertTrue("bias 0.0 behaves exactly like fair die with same seed", same);
    }

    private static void testLoadedSideZeroBehavesFairly() {
        Random seed = new Random(99);
        LoadedDie loaded = new LoadedDie(6, seed, 0, 0.8);

        Random seed2 = new Random(99);
        Die fair = new Die(6, seed2);

        boolean same = true;
        for (int i = 0; i < 20; i++) {
            if (loaded.roll() != fair.roll()) {
                same = false;
                break;
            }
        }

        assertTrue("loadedSide 0 behaves exactly like fair die with same seed", same);
    }

    private static void testBiasOneAlwaysReturnsLoadedSide() {
        LoadedDie d = new LoadedDie(8, new Random(7), 6, 1.0);

        boolean alwaysLoaded = true;
        for (int i = 0; i < 50; i++) {
            if (d.roll() != 6) {
                alwaysLoaded = false;
                break;
            }
        }

        assertTrue("bias 1.0 always returns loaded side", alwaysLoaded);
    }

    private static void testRollStaysInRange() {
        LoadedDie d = new LoadedDie(10, new Random(123), 4, 0.35);
        boolean ok = true;

        for (int i = 0; i < 200; i++) {
            int r = d.roll();
            if (r < 1 || r > 10) {
                ok = false;
                break;
            }
        }

        assertTrue("roll() always stays in valid range", ok);
    }

    private static void testToStringFormat() {
        LoadedDie d = new LoadedDie(6, 4, 0.5);
        String expected = "LoadedDie[sides=6, loadedSide=4, bias=0.5]";
        assertTrue("toString() exact format", expected.equals(d.toString()));
    }
}
