/********************************************************************
 * Name: First Last
 * Course: CSC 123
 * Lab: 04
 * Date:
 * File: CheckingAccountTester.java
 *
 * Description:
 * Test driver for CheckingAccount (Lab 04).
 * Tests constructors, getters, setters, and toString().
 * This tester does NOT test validation rules.
 ********************************************************************/
package checkingaccount;

public class CheckingAccountTester {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {

        System.out.println("====================================================");
        System.out.println("CSC 123 Lab 04 - CheckingAccount Basic Tests");
        System.out.println("====================================================\n");

        testConstructors();
        testGettersSetters();
        testToString();

        System.out.println("\n====================================================");
        System.out.printf("SUMMARY: Passed %d / %d tests%n", testsPassed, testsRun);
        System.out.println("====================================================");
    }

    // ------------------------------------------------------------
    // Constructor tests (valid inputs only)
    // ------------------------------------------------------------
    private static void testConstructors() {
        System.out.println("=== Constructor Tests ===");

        CheckingAccount a = new CheckingAccount("00000123", "Jon Doe", 123.00);
        assertTrue("Constructor stores account number",
                "00000123".equals(a.getAccountNumber()));
        assertTrue("Constructor stores account name",
                "Jon Doe".equals(a.getAccountName()));
        assertNearlyEqual("Constructor stores balance",
                123.00, a.getAccountBalance(), 0.0001);

        CheckingAccount b = new CheckingAccount(); // default
        // These defaults assume the Lab04 spec used in class (adjust if your defaults differ).
        assertTrue("Default constructor sets account number",
                "00000000".equals(b.getAccountNumber()));
        assertTrue("Default constructor sets account name",
                "EMPTY NAME".equals(b.getAccountName()));
        assertNearlyEqual("Default constructor sets balance",
                0.00, b.getAccountBalance(), 0.0001);

        System.out.println();
    }

    // ------------------------------------------------------------
    // Getter/Setter tests (valid inputs only)
    // ------------------------------------------------------------
    private static void testGettersSetters() {
        System.out.println("=== Getter/Setter Tests ===");

        CheckingAccount a = new CheckingAccount("12345678", "Jane Doe", 10.00);

        // Set new valid values
        a.setAccountNumber("87654321");
        a.setAccountName("Jane Q Doe");
        a.setAccountBalance(42.50);

        // Verify getters reflect changes
        assertTrue("setAccountNumber updates number",
                "87654321".equals(a.getAccountNumber()));
        assertTrue("setAccountName updates name",
                "Jane Q Doe".equals(a.getAccountName()));
        assertNearlyEqual("setAccountBalance updates balance",
                42.50, a.getAccountBalance(), 0.0001);

        System.out.println();
    }

    // ------------------------------------------------------------
    // toString() tests (basic sanity check)
    // ------------------------------------------------------------
    private static void testToString() {
        System.out.println("=== toString() Test ===");

        CheckingAccount a = new CheckingAccount("99990000", "String Tester", 12.34);
        String s = a.toString();

        System.out.println("toString() output:");
        System.out.println(s);

        // Minimal checks (do not over-specify formatting)
        assertTrue("toString contains account number",
                s != null && s.contains("99990000"));
        assertTrue("toString contains account name",
                s != null && s.contains("String Tester"));

        System.out.println();
    }

    // ------------------------------------------------------------
    // Assertion helpers
    // ------------------------------------------------------------
    private static void assertTrue(String testName, boolean condition) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println("[PASS] " + testName);
        } else {
            System.out.println("[FAIL] " + testName);
        }
    }

    private static void assertNearlyEqual(String testName, double expected, double actual, double eps) {
        assertTrue(testName + String.format(" (expected %.4f, got %.4f)", expected, actual),
                nearlyEqual(expected, actual, eps));
    }

    private static boolean nearlyEqual(double a, double b, double eps) {
        return Math.abs(a - b) <= eps;
    }
}

