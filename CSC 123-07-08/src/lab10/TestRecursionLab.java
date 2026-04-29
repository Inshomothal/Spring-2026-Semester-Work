package lab10;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;
import java.util.regex.*;

/**
 * TestRecursionLab.java — Tester for Lab 10 (RecursionLab)
 *
 * Usage:
 *   javac TestRecursionLab.java
 *   java TestRecursionLab [path/to/RecursionLab.java]
 *
 * Default target: RecursionLab.java in the current directory.
 */
public class TestRecursionLab {

    // ── Configuration ────────────────────────────────────────
    static final String CLASS_NAME   = "RecursionLab";
    static final int    TIMEOUT_SEC  = 5;

    // ── Counters ─────────────────────────────────────────────
    static int pass  = 0;
    static int fail  = 0;
    static int total = 0;

    // ── Entry point ──────────────────────────────────────────
    public static void main(String[] args) throws Exception {

        String javaFile = (args.length > 0) ? args[0] : CLASS_NAME + ".java";
        File   src      = new File(javaFile).getCanonicalFile();
        File   workDir  = src.getParentFile();

        sep();
        System.out.println(" RecursionLab Tester");
        sep();

        // ── Build ─────────────────────────────────────────────
        section("BUILD");
        if (!src.exists()) {
            System.out.println("  [ERROR] File not found: " + src);
            System.exit(1);
        }

        ProcessResult compile = run(
            workDir, null,
            "javac", src.getAbsolutePath(), "-d", workDir.getAbsolutePath()
        );
        if (compile.exitCode != 0) {
            System.out.println("  [BUILD FAIL]");
            System.out.println(compile.stderr);
            System.exit(1);
        }
        System.out.println("  [BUILD OK]");

        // ── Source text for token checks ──────────────────────
        String source = new String(Files.readAllBytes(src.toPath()));

        // ── Token checks ──────────────────────────────────────
        section("TOKEN CHECKS");

        // Extract body of inputLoop and allPositive, then scan for loops
        checkNoLoopInMethod(source, "inputLoop");
        checkNoLoopInMethod(source, "allPositive");

        tokenCheck("allPositive signature: (int[], int)",
            source, "boolean\\s+allPositive\\s*\\(\\s*int\\[\\]\\s+\\w+\\s*,\\s*int\\s+\\w+\\s*\\)");

        tokenCheck("inputLoop signature: (Scanner)",
            source, "int\\s+inputLoop\\s*\\(\\s*Scanner\\s+\\w+\\s*\\)");

        // ── Part 1: inputLoop ─────────────────────────────────
        section("PART 1: inputLoop");

        runTest(workDir, "Lower boundary (20) accepted",
            "20\n3\n1\n2\n3\n",
            "valid number: 20");

        runTest(workDir, "Upper boundary (80) accepted",
            "80\n1\n5\n",
            "valid number: 80");

        runTest(workDir, "Midrange (50) accepted",
            "50\n1\n5\n",
            "valid number: 50");

        runTest(workDir, "Below range (19) re-prompts, then accepts 30",
            "19\n30\n1\n5\n",
            "valid number: 30");

        runTest(workDir, "Above range (81) re-prompts, then accepts 75",
            "81\n75\n1\n5\n",
            "valid number: 75");

        runTest(workDir, "Multiple invalid (0, 100, 19) then valid (45)",
            "0\n100\n19\n45\n1\n5\n",
            "valid number: 45");

        runTest(workDir, "Error message printed on invalid input",
            "5\n40\n1\n5\n",
            "(?i)error");

        // ── Part 2: allPositive ───────────────────────────────
        section("PART 2: allPositive");

        runTest(workDir, "All positive {1,2,3} -> true",
            "40\n3\n1\n2\n3\n",
            "(?i)all elements.*positive");

        runTest(workDir, "Zero present {1,0,3} -> false",
            "40\n3\n1\n0\n3\n",
            "(?i)not all");

        runTest(workDir, "Negative present {1,-5,3} -> false",
            "40\n3\n1\n-5\n3\n",
            "(?i)not all");

        runTest(workDir, "Single element {5} -> true",
            "40\n1\n5\n",
            "(?i)all elements.*positive");

        runTest(workDir, "Single element {-1} -> false",
            "40\n1\n-1\n",
            "(?i)not all");

        runTest(workDir, "Empty array (length 0) -> true  [base case]",
            "40\n0\n",
            "(?i)all elements.*positive");

        runTest(workDir, "All negative {-1,-2,-3} -> false",
            "40\n3\n-1\n-2\n-3\n",
            "(?i)not all");

        runTest(workDir, "Large array {1..10} -> true",
            "40\n10\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n",
            "(?i)all elements.*positive");

        runTest(workDir, "Failure at index 0 {-1,2,3} -> false",
            "40\n3\n-1\n2\n3\n",
            "(?i)not all");

        runTest(workDir, "Failure at last element {1,2,-3} -> false",
            "40\n3\n1\n2\n-3\n",
            "(?i)not all");

        // ── Summary ───────────────────────────────────────────
        sep();
        System.out.printf(" Results: %d passed  %d failed  (%d total)%n",
                          pass, fail, total);
        if (fail == 0) {
            System.out.println(" ALL TESTS PASSED");
        } else {
            System.out.println(" " + fail + " TEST(S) FAILED");
        }
        sep();

        System.exit(fail == 0 ? 0 : 1);
    }

    // ── Test runners ──────────────────────────────────────────

    /**
     * Feed input to the program, check stdout contains a match for pattern.
     */
    static void runTest(File workDir, String label, String input, String pattern) {
        total++;
        ProcessResult result = run(workDir, input,
            "java", "-cp", workDir.getAbsolutePath(), CLASS_NAME);

        if (result.timedOut) {
            System.out.println("  [FAIL] " + label);
            System.out.println("         TIMEOUT — possible infinite recursion");
            fail++;
            return;
        }

        String combined = result.stdout + "\n" + result.stderr;
        if (Pattern.compile(pattern, Pattern.DOTALL).matcher(combined).find()) {
            System.out.println("  [PASS] " + label);
            pass++;
        } else {
            System.out.println("  [FAIL] " + label);
            System.out.println("         Expected pattern : " + pattern);
            System.out.println("         Actual output    : "
                + combined.replace("\n", " ").trim());
            fail++;
        }
    }

    /**
     * Verify no loop keyword (for/while/do) appears inside the named method body.
     */
    static void checkNoLoopInMethod(String source, String methodName) {
        total++;
        String body = extractMethodBody(source, methodName);
        if (body == null) {
            // Method not found — signature check will catch this
            System.out.println("  [PASS] No loop in " + methodName
                + " (method not located; checked by signature test)");
            pass++;
            return;
        }
        Matcher m = Pattern.compile("\\b(for|while|do)\\b").matcher(body);
        if (m.find()) {
            System.out.println("  [FAIL] No loop in " + methodName);
            System.out.println("         Found loop keyword: " + m.group());
            fail++;
        } else {
            System.out.println("  [PASS] No loop in " + methodName);
            pass++;
        }
    }

    /**
     * Regex check against full source text.
     */
    static void tokenCheck(String label, String source, String pattern) {
        total++;
        if (Pattern.compile(pattern).matcher(source).find()) {
            System.out.println("  [PASS] " + label);
            pass++;
        } else {
            System.out.println("  [FAIL] " + label);
            fail++;
        }
    }

    // ── Process runner ────────────────────────────────────────

    static ProcessResult run(File workDir, String stdin, String... cmd) {
        ProcessResult result = new ProcessResult();
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.directory(workDir);
            pb.redirectErrorStream(false);

            Process proc = pb.start();

            // Feed stdin in a separate thread to avoid blocking on full pipe buffer
            if (stdin != null && !stdin.isEmpty()) {
                final byte[] inputBytes = stdin.getBytes();
                Thread writer = new Thread(() -> {
                    try (OutputStream os = proc.getOutputStream()) {
                        os.write(inputBytes);
                        os.flush();
                    } catch (IOException ignored) {}
                });
                writer.setDaemon(true);
                writer.start();
            } else {
                proc.getOutputStream().close();
            }

            // Read stdout and stderr concurrently
            Future<String> stdoutFuture = readAsync(proc.getInputStream());
            Future<String> stderrFuture = readAsync(proc.getErrorStream());

            boolean finished = proc.waitFor(TIMEOUT_SEC, TimeUnit.SECONDS);
            if (!finished) {
                proc.destroyForcibly();
                result.timedOut = true;
                return result;
            }

            result.exitCode = proc.exitValue();
            result.stdout   = stdoutFuture.get(1, TimeUnit.SECONDS);
            result.stderr   = stderrFuture.get(1, TimeUnit.SECONDS);

        } catch (Exception e) {
            result.stderr = e.getMessage();
        }
        return result;
    }

    static Future<String> readAsync(InputStream is) {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<String> f = ex.submit(() -> {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] b = new byte[4096];
            int n;
            while ((n = is.read(b)) != -1) buf.write(b, 0, n);
            return buf.toString();
        });
        ex.shutdown();
        return f;
    }

    // ── Source analysis ───────────────────────────────────────

    /**
     * Extracts the body of the first method matching the given name.
     * Returns null if not found.
     */
    static String extractMethodBody(String source, String methodName) {
        // Anchor on a declaration (return type precedes the name) to skip call sites
        Pattern decl = Pattern.compile(
            "(?:public|private|protected|static|final|\\s)+"
            + "[\\w\\[\\]<>]+"
            + "\\s+"
            + Pattern.quote(methodName)
            + "\\s*\\(");
        Matcher m = decl.matcher(source);
        if (!m.find()) return null;

        // Walk forward to find the opening brace
        int pos = m.start();
        int openBrace = source.indexOf('{', pos);
        if (openBrace < 0) return null;

        // Balance braces to find the closing brace
        int depth = 0;
        for (int i = openBrace; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == '{') depth++;
            else if (c == '}') {
                depth--;
                if (depth == 0) return source.substring(openBrace + 1, i);
            }
        }
        return null;
    }

    // ── Formatting ────────────────────────────────────────────

    static void sep()             { System.out.println("============================================================"); }
    static void section(String s) { System.out.println("\n--- " + s + " ---"); }

    // ── Result container ──────────────────────────────────────

    static class ProcessResult {
        int     exitCode = 0;
        String  stdout   = "";
        String  stderr   = "";
        boolean timedOut = false;
    }
}
