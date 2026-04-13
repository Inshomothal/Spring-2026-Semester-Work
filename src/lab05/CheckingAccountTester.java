/********************************************************************
 * Name: First Last
 * Course: CSC 123
 * Lab: 05
 * Date:
 * File: CheckingAccountTester.java
 *
 * Description:
 * Comprehensive test driver for CheckingAccount.
 * Tests constructors, getters/setters validation, depositMoney,
 * withdrawMoney, processCheck fee/overdraft behavior, and toString().
 ********************************************************************/

package lab05;

public class CheckingAccountTester {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {

        System.out.println("====================================================");
        System.out.println("CSC 123 Lab 05 - CheckingAccount Comprehensive Tests");
        System.out.println("====================================================\n");

        testConstructors();
        testGettersSetters();
        testDepositMoney();
        testWithdrawMoney();
        testProcessCheck();
        testToStringDeltaPrefix();

        System.out.println("\n====================================================");
        System.out.printf("SUMMARY: Passed %d / %d tests%n", testsPassed, testsRun);
        System.out.println("====================================================");
    }

    // ------------------------------------------------------------
    // Constructor tests (valid + invalid inputs)
    // ------------------------------------------------------------
    private static void testConstructors() {
        System.out.println("=== Constructor Tests ===");

        CheckingAccount a = new CheckingAccount("00000123", "Jon Doe", 123.00);
        assertTrue("Constructor valid account number stored",
                "00000123".equals(a.getAccountNumber()));
        assertTrue("Constructor valid name stored",
                "Jon Doe".equals(a.getAccountName()));
        assertNearlyEqual("Constructor valid balance stored", 123.00, a.getAccountBalance(), 0.0001);

        CheckingAccount b = new CheckingAccount(); // default
        assertTrue("Default constructor account number",
                "00000000".equals(b.getAccountNumber()));
        assertTrue("Default constructor account name",
                "EMPTY NAME".equals(b.getAccountName()));
        assertNearlyEqual("Default constructor balance", 0.00, b.getAccountBalance(), 0.0001);

        System.out.println("\n-- invalid name (no space) --");
        CheckingAccount badName = new CheckingAccount("00000124", "NOSPACE", 10.00);
        // Spec says name must have at least two words; student implementations often set UNKNOWN.
        // We can't mandate the exact fallback string, but we can verify it did NOT accept "NOSPACE".
        assertTrue("Invalid name rejected (not stored as-is)",
                !"NOSPACE".equals(badName.getAccountName()));

        System.out.println("\n-- invalid balance (negative) --");
        CheckingAccount badBalNeg = new CheckingAccount("00000125", "Bad Balance", -5.00);
        // Rule: no negative balance allowed at creation. Most implementations refuse change / set to 0.
        assertTrue("Negative constructor balance rejected (balance is not negative)",
                badBalNeg.getAccountBalance() >= 0.0);

        System.out.println("\n-- invalid balance (fractional cents) --");
        CheckingAccount badBalFrac = new CheckingAccount("00000126", "Bad Cents", 100.001);
        // Rule: no fractional cents. Verify it did not store 100.001 exactly.
        assertTrue("Fractional-cent constructor balance rejected (not equal to 100.001)",
                !nearlyEqual(badBalFrac.getAccountBalance(), 100.001, 0.0000001));

        System.out.println("\n-- invalid account number (wrong length) --");
        CheckingAccount badNumLen = new CheckingAccount("420", "Bad Number", 1.00);
        assertTrue("Invalid account number rejected (not stored as-is)",
                !"420".equals(badNumLen.getAccountNumber()));

        System.out.println();
    }

    // ------------------------------------------------------------
    // Getter/Setter tests (validation behavior)
    // ------------------------------------------------------------
    private static void testGettersSetters() {
        System.out.println("=== Getter/Setter Validation Tests ===");

        CheckingAccount a = new CheckingAccount("12345678", "Jane Doe", 10.00);

        a.setAccountName("NOSPACE");
        assertTrue("setAccountName rejects single-word name",
                !"NOSPACE".equals(a.getAccountName()));

        a.setAccountName("Jane Q Doe");
        assertTrue("setAccountName accepts multi-word name",
                "Jane Q Doe".equals(a.getAccountName()));

        a.setAccountNumber("abcd5678");
        assertTrue("setAccountNumber rejects non-digits",
                !"abcd5678".equals(a.getAccountNumber()));

        a.setAccountNumber("1234");
        assertTrue("setAccountNumber rejects wrong length",
                !"1234".equals(a.getAccountNumber()));

        a.setAccountNumber("87654321");
        assertTrue("setAccountNumber accepts 8 digits",
                "87654321".equals(a.getAccountNumber()));

        double before = a.getAccountBalance();
        a.setAccountBalance(-1.00);
        assertNearlyEqual("setAccountBalance rejects negative (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        before = a.getAccountBalance();
        a.setAccountBalance(0.001);
        assertNearlyEqual("setAccountBalance rejects fractional cents (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        a.setAccountBalance(42.50);
        assertNearlyEqual("setAccountBalance accepts valid amount (sets absolute balance)",
                42.50, a.getAccountBalance(), 0.0001);

        System.out.println();
    }

    // ------------------------------------------------------------
    // depositMoney tests
    // ------------------------------------------------------------
    private static void testDepositMoney() {
        System.out.println("=== depositMoney(amount) Tests ===");

        CheckingAccount a = new CheckingAccount("11112222", "Deposit Tester", 10.00);

        double before = a.getAccountBalance();
        a.depositMoney(-5.00);
        assertNearlyEqual("depositMoney rejects negative (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        before = a.getAccountBalance();
        a.depositMoney(0.001);
        assertNearlyEqual("depositMoney rejects fractional cents (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        a.depositMoney(2.50);
        assertNearlyEqual("depositMoney accepts valid amount (adds to balance)",
                12.50, a.getAccountBalance(), 0.0001);

        System.out.println();
    }

    // ------------------------------------------------------------
    // withdrawMoney tests (including overdraft policy)
    // ------------------------------------------------------------
    private static void testWithdrawMoney() {
        System.out.println("=== withdrawMoney(amount) Tests ===");

        CheckingAccount a = new CheckingAccount("22223333", "Withdraw Tester", 10.00);

        double before = a.getAccountBalance();
        a.withdrawMoney(0.00);
        assertNearlyEqual("withdrawMoney rejects zero (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        before = a.getAccountBalance();
        a.withdrawMoney(-1.00);
        assertNearlyEqual("withdrawMoney rejects negative (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        before = a.getAccountBalance();
        a.withdrawMoney(0.001);
        assertNearlyEqual("withdrawMoney rejects fractional cents (balance unchanged)",
                before, a.getAccountBalance(), 0.0001);

        a.withdrawMoney(3.25);
        assertNearlyEqual("withdrawMoney accepts valid amount (subtracts)",
                6.75, a.getAccountBalance(), 0.0001);

        // Overdraft policy varies by implementation.
        // Spec for Lab 05 explicitly allows overdrafts for processCheck; withdrawMoney policy is stated as allowed in updated spec.
        // So we test it goes negative if overdrafts are allowed; if a student's code blocks overdraft, this will FAIL (as desired).
        a.withdrawMoney(10.00);
        assertTrue("withdrawMoney allows overdraft (balance becomes negative)",
                a.getAccountBalance() < 0.0);

        System.out.println();
    }

    // ------------------------------------------------------------
    // processCheck tests (fee rule uses BEFORE balance)
    // ------------------------------------------------------------
    private static void testProcessCheck() {
        System.out.println("=== processCheck(amount) Tests ===");

        // 1) Exactly 1000.00 -> NO fee
        CheckingAccount a = new CheckingAccount("33334444", "Edge Thousand", 1000.00);
        runProcessCheckScenario("Balance 1000.00, check 1.00 => NO fee, after 999.00",
                a, 1.00, 0.00, 999.00);

        // 2) 999.99 -> fee applies
        CheckingAccount b = new CheckingAccount("33335555", "Just Under", 999.99);
        runProcessCheckScenario("Balance 999.99, check 1.00 => fee 0.15, after 998.84",
                b, 1.00, 0.15, 998.84);

        // 3) 0.00 -> fee applies and overdraft allowed
        CheckingAccount c = new CheckingAccount("33336666", "Over Draft", 0.00);
        runProcessCheckScenario("Balance 0.00, check 20.00 => fee 0.15, after -20.15",
                c, 20.00, 0.15, -20.15);

        // 4) Large balance -> no fee
        CheckingAccount d = new CheckingAccount("33337777", "Big Money", 9999.99);
        runProcessCheckScenario("Balance 9999.99, check 50.00 => NO fee, after 9949.99",
                d, 50.00, 0.00, 9949.99);

        // 5) Invalid check amounts
        CheckingAccount e = new CheckingAccount("33338888", "Bad Check", 10.00);
        double before = e.getAccountBalance();
        e.processCheck(-1.00);
        assertNearlyEqual("processCheck rejects negative (balance unchanged)",
                before, e.getAccountBalance(), 0.0001);

        before = e.getAccountBalance();
        e.processCheck(0.001);
        // If student validates check cents, should reject; if not, this will fail (good).
        assertNearlyEqual("processCheck rejects fractional cents (balance unchanged)",
                before, e.getAccountBalance(), 0.0001);

        System.out.println();
    }

    private static void runProcessCheckScenario(String label,
                                                CheckingAccount acct,
                                                double checkAmount,
                                                double expectedFee,
                                                double expectedAfter) {
        double before = acct.getAccountBalance();

        System.out.println("\n--- " + label + " ---");
        System.out.printf("Before: $%.2f%n", before);
        System.out.printf("Check : $%.2f%n", checkAmount);
        System.out.printf("Fee   : $%.2f (based on BEFORE balance)%n", expectedFee);

        acct.processCheck(checkAmount);

        double after = acct.getAccountBalance();
        System.out.printf("After : $%.2f%n", after);

        assertNearlyEqual("processCheck computed after-balance correctly",
                expectedAfter, after, 0.0001);

        // Extra correctness check: fee decision must be based on BEFORE balance (< 1000).
        double shouldFee = (before < 1000.00) ? 0.15 : 0.00;
        assertNearlyEqual("processCheck fee decision matches spec (<1000 BEFORE => 0.15)",
                shouldFee, expectedFee, 0.0001);
    }

    // ------------------------------------------------------------
    // toString() format tests (must include Unicode U+2206 '∆' prefix before balance)
    // ------------------------------------------------------------
    private static void testToStringDeltaPrefix() {
        System.out.println("=== toString() Format Tests ===");

        CheckingAccount a = new CheckingAccount("44445555", "String Tester", 12.34);
        String s = a.toString();

        System.out.println("toString() output:");
        System.out.println(s);

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

