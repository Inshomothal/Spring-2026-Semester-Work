
/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: CrabBattleTester.java
*
* Description: Auto-grader for Project 05 – Crab Battle.
*              Run with: java CrabBattleTester.java
*              Requires all project .java files in the same directory.
********************************************************************/
package proj05;

import java.lang.reflect.*;
import java.util.Random;

public class CrabBattleTester {

    // -------------------------------------------------------
    // Counters
    // -------------------------------------------------------
    private static int passed = 0;
    private static int failed = 0;

    // -------------------------------------------------------
    // Entry point
    // -------------------------------------------------------
    public static void main(String[] args) {
        printHeader();

        testDie();
        testLoadedDie();
        testWeaponInterface();
        testStick();
        testSword();
        testMagicSword();
        testPlayer();
        testCrabBoss();
        testCrabBattleGameStructure();

        printFooter();
    }

    // ===================================================
    // Section 1 – Die
    // ===================================================
    private static void testDie() {
        printSection("Die");

        // --- field visibility ---
        checkField(Die.class, "random",
                "random field exists",
                f -> {
                    int mod = f.getModifiers();
                    return Modifier.isProtected(mod) && !Modifier.isStatic(mod);
                },
                "random must be protected (not private, not public)");

        checkField(Die.class, "numSides",
                "numSides field exists",
                f -> Modifier.isPrivate(f.getModifiers()),
                "numSides must be private");

        // --- roll() range: default 6-sided ---
        Die d6 = new Die();
        boolean allInRange = true;
        for (int i = 0; i < 200; i++) {
            int r = d6.roll();
            if (r < 1 || r > 6) { allInRange = false; break; }
        }
        pass_fail(allInRange,
                "Die() roll() always in [1..6]",
                "Die() roll() produced value outside [1..6]");

        // --- roll() range: Die(4) ---
        Die d4 = new Die(4);
        boolean allInRange4 = true;
        for (int i = 0; i < 200; i++) {
            int r = d4.roll();
            if (r < 1 || r > 4) { allInRange4 = false; break; }
        }
        pass_fail(allInRange4,
                "Die(4) roll() always in [1..4]",
                "Die(4) roll() produced value outside [1..4]");

        // --- getNumSides() ---
        pass_fail(new Die(8).getNumSides() == 8,
                "Die(8).getNumSides() == 8",
                "Die(8).getNumSides() did not return 8");

        // --- invalid sides defaults to 6 ---
        Die bad = new Die(0);
        pass_fail(bad.getNumSides() == 6,
                "Die(0) defaults to 6 sides",
                "Die(0) did not default to 6 sides");

        // --- toString() not null/empty ---
        String ts = new Die(6).toString();
        pass_fail(ts != null && !ts.isEmpty(),
                "Die.toString() returns non-empty string",
                "Die.toString() returned null or empty");

        // --- Die extends nothing unexpected ---
        pass_fail(Die.class.getSuperclass() == Object.class,
                "Die extends Object (no unexpected superclass)",
                "Die has an unexpected superclass: " + Die.class.getSuperclass());
    }

    // ===================================================
    // Section 2 – LoadedDie
    // ===================================================
    private static void testLoadedDie() {
        printSection("LoadedDie");

        // --- inheritance ---
        pass_fail(Die.class.isAssignableFrom(LoadedDie.class),
                "LoadedDie extends Die",
                "LoadedDie does not extend Die");

        // --- fields are private ---
        checkField(LoadedDie.class, "loadedSide",
                "loadedSide field exists",
                f -> Modifier.isPrivate(f.getModifiers()),
                "loadedSide must be private");

        checkField(LoadedDie.class, "bias",
                "bias field exists",
                f -> Modifier.isPrivate(f.getModifiers()),
                "bias must be private");

        // --- does NOT create a second Random field ---
        long randomFieldCount = 0;
        Class<?> c = LoadedDie.class;
        for (Field f : c.getDeclaredFields()) {
            if (f.getType() == Random.class) randomFieldCount++;
        }
        pass_fail(randomFieldCount == 0,
                "LoadedDie declares no extra Random field (uses inherited one)",
                "LoadedDie declares its own Random field — should use inherited random");

        // --- roll() range ---
        LoadedDie ld = new LoadedDie(6, 6, 0.50);
        boolean rangeOk = true;
        for (int i = 0; i < 500; i++) {
            int r = ld.roll();
            if (r < 1 || r > 6) { rangeOk = false; break; }
        }
        pass_fail(rangeOk,
                "LoadedDie(6,6,0.50) roll() always in [1..6]",
                "LoadedDie(6,6,0.50) roll() produced value outside [1..6]");

        // --- bias actually works (loaded side appears > 35% over 1000 rolls) ---
        LoadedDie biased = new LoadedDie(6, 6, 0.50);
        int sixes = 0;
        int trials = 1000;
        for (int i = 0; i < trials; i++) {
            if (biased.roll() == 6) sixes++;
        }
        double rate = (double) sixes / trials;
        pass_fail(rate >= 0.35,
                String.format("LoadedDie bias works: 6 appeared %.1f%% (expected ≥35%%)", rate * 100),
                String.format("LoadedDie bias too weak: 6 appeared only %.1f%% over %d rolls (expected ≥35%%)", rate * 100, trials));

        // --- getBias() / setBias() ---
        LoadedDie ld2 = new LoadedDie();
        ld2.setBias(0.75);
        pass_fail(ld2.getBias() == 0.75,
                "setBias(0.75) / getBias() round-trips correctly",
                "getBias() did not return 0.75 after setBias(0.75)");

        ld2.setBias(-1.0);
        pass_fail(ld2.getBias() == 0.0,
                "setBias(-1.0) rejected, bias stays 0.0",
                "setBias(-1.0) was accepted; bias should default to 0.0");

        // --- getLoadedSide() / setLoadedSide() ---
        LoadedDie ld3 = new LoadedDie(6, 3, 0.5);
        pass_fail(ld3.getLoadedSide() == 3,
                "getLoadedSide() == 3 after LoadedDie(6,3,0.5)",
                "getLoadedSide() did not return 3");

        // --- toString() not null/empty ---
        String ts = new LoadedDie(6, 6, 0.5).toString();
        pass_fail(ts != null && !ts.isEmpty(),
                "LoadedDie.toString() returns non-empty string",
                "LoadedDie.toString() returned null or empty");
    }

    // ===================================================
    // Section 3 – Weapon interface
    // ===================================================
    private static void testWeaponInterface() {
        printSection("Weapon (interface)");

        // --- is actually an interface ---
        pass_fail(Weapon.class.isInterface(),
                "Weapon is an interface",
                "Weapon is not an interface — it must be declared as interface");

        // --- has getName() ---
        checkMethod(Weapon.class, "getName", "Weapon declares getName()",
                String.class);

        // --- has rollDamage() ---
        checkMethod(Weapon.class, "rollDamage", "Weapon declares rollDamage()",
                int.class);

        // --- has display() as default ---
        try {
            Method m = Weapon.class.getMethod("display");
            pass_fail(m.isDefault(),
                    "Weapon.display() exists and is a default method",
                    "Weapon.display() exists but is not declared default");
        } catch (NoSuchMethodException e) {
            fail("Weapon.display() method not found");
        }
    }

    // ===================================================
    // Section 4 – Stick
    // ===================================================
    private static void testStick() {
        printSection("Stick");

        // --- implements Weapon ---
        pass_fail(Weapon.class.isAssignableFrom(Stick.class),
                "Stick implements Weapon",
                "Stick does not implement Weapon");

        Stick s = new Stick();

        // --- getName() ---
        pass_fail(s.getName() != null && !s.getName().isEmpty(),
                "Stick.getName() returns a non-empty string",
                "Stick.getName() returned null or empty");

        // --- rollDamage() range matches Die(4): 1-4 ---
        boolean rangeOk = true;
        for (int i = 0; i < 200; i++) {
            int r = s.rollDamage();
            if (r < 1 || r > 4) { rangeOk = false; break; }
        }
        pass_fail(rangeOk,
                "Stick.rollDamage() always in [1..4] (uses d4)",
                "Stick.rollDamage() produced value outside [1..4]");

        // --- field type is Die (not LoadedDie) ---
        checkField(Stick.class, "die",
                "Stick has a die field",
                f -> f.getType() == Die.class,
                "Stick's die field must be type Die, not LoadedDie or other");

        // --- field is private ---
        checkField(Stick.class, "die",
                "Stick.die is private",
                f -> Modifier.isPrivate(f.getModifiers()),
                "Stick.die must be private");

        // --- usable as Weapon reference ---
        Weapon w = new Stick();
        pass_fail(w.rollDamage() >= 1,
                "Stick usable through Weapon reference",
                "Stick not usable through Weapon reference");
    }

    // ===================================================
    // Section 5 – Sword
    // ===================================================
    private static void testSword() {
        printSection("Sword");

        // --- implements Weapon ---
        pass_fail(Weapon.class.isAssignableFrom(Sword.class),
                "Sword implements Weapon",
                "Sword does not implement Weapon");

        Sword s = new Sword();

        // --- getName() ---
        pass_fail(s.getName() != null && !s.getName().isEmpty(),
                "Sword.getName() returns a non-empty string",
                "Sword.getName() returned null or empty");

        // --- rollDamage() range matches Die(6): 1-6 ---
        boolean rangeOk = true;
        for (int i = 0; i < 200; i++) {
            int r = s.rollDamage();
            if (r < 1 || r > 6) { rangeOk = false; break; }
        }
        pass_fail(rangeOk,
                "Sword.rollDamage() always in [1..6] (uses d6)",
                "Sword.rollDamage() produced value outside [1..6]");

        // --- field type is Die ---
        checkField(Sword.class, "die",
                "Sword has a die field of type Die",
                f -> f.getType() == Die.class,
                "Sword's die field must be type Die");

        // --- field is private ---
        checkField(Sword.class, "die",
                "Sword.die is private",
                f -> Modifier.isPrivate(f.getModifiers()),
                "Sword.die must be private");

        // --- does more max damage than Stick (d6 vs d4) ---
        // verify upper bound is 6, not 4
        boolean canRoll5or6 = false;
        for (int i = 0; i < 500; i++) {
            if (s.rollDamage() > 4) { canRoll5or6 = true; break; }
        }
        pass_fail(canRoll5or6,
                "Sword can roll > 4 (has higher ceiling than Stick)",
                "Sword never rolled > 4 in 500 tries — may be using wrong die size");

        // --- has display() method ---
        try {
            Sword.class.getMethod("display");
            pass("Sword.display() method exists");
        } catch (NoSuchMethodException e) {
            fail("Sword.display() method not found");
        }

        // --- usable as Weapon reference ---
        Weapon w = new Sword();
        pass_fail(w.rollDamage() >= 1,
                "Sword usable through Weapon reference",
                "Sword not usable through Weapon reference");
    }

    // ===================================================
    // Section 6 – MagicSword
    // ===================================================
    private static void testMagicSword() {
        printSection("MagicSword");

        // --- implements Weapon ---
        pass_fail(Weapon.class.isAssignableFrom(MagicSword.class),
                "MagicSword implements Weapon",
                "MagicSword does not implement Weapon");

        MagicSword ms = new MagicSword();

        // --- getName() ---
        pass_fail(ms.getName() != null && !ms.getName().isEmpty(),
                "MagicSword.getName() returns a non-empty string",
                "MagicSword.getName() returned null or empty");

        // --- rollDamage() range 1-6 ---
        boolean rangeOk = true;
        for (int i = 0; i < 200; i++) {
            int r = ms.rollDamage();
            if (r < 1 || r > 6) { rangeOk = false; break; }
        }
        pass_fail(rangeOk,
                "MagicSword.rollDamage() always in [1..6]",
                "MagicSword.rollDamage() produced value outside [1..6]");

        // --- field type is LoadedDie ---
        checkField(MagicSword.class, "die",
                "MagicSword has a die field of type LoadedDie",
                f -> f.getType() == LoadedDie.class,
                "MagicSword's die field must be type LoadedDie, not Die");

        // --- field is private ---
        checkField(MagicSword.class, "die",
                "MagicSword.die is private",
                f -> Modifier.isPrivate(f.getModifiers()),
                "MagicSword.die must be private");

        // --- bias actually skews results (max appears > 35% over 1000 rolls) ---
        int maxCount = 0;
        int trials = 1000;
        for (int i = 0; i < trials; i++) {
            if (ms.rollDamage() == 6) maxCount++;
        }
        double rate = (double) maxCount / trials;
        pass_fail(rate >= 0.35,
                String.format("MagicSword bias works: max damage appeared %.1f%% (expected ≥35%%)", rate * 100),
                String.format("MagicSword max damage appeared only %.1f%% — LoadedDie bias may not be working", rate * 100));

        // --- has display() method ---
        try {
            MagicSword.class.getMethod("display");
            pass("MagicSword.display() method exists");
        } catch (NoSuchMethodException e) {
            fail("MagicSword.display() method not found");
        }

        // --- usable as Weapon reference ---
        Weapon w = new MagicSword();
        pass_fail(w.rollDamage() >= 1,
                "MagicSword usable through Weapon reference",
                "MagicSword not usable through Weapon reference");
    }

    // ===================================================
    // Section 7 – Player
    // ===================================================
    private static void testPlayer() {
        printSection("Player");

        // --- field types via reflection ---
        checkField(Player.class, "leftHand",
                "Player.leftHand declared as type Weapon",
                f -> f.getType() == Weapon.class,
                "Player.leftHand must be declared as Weapon, not a concrete type");

        checkField(Player.class, "rightHand",
                "Player.rightHand declared as type Weapon",
                f -> f.getType() == Weapon.class,
                "Player.rightHand must be declared as Weapon, not a concrete type");

        checkField(Player.class, "health",
                "Player.health is private",
                f -> Modifier.isPrivate(f.getModifiers()),
                "Player.health must be private");

        checkField(Player.class, "name",
                "Player.name is private",
                f -> Modifier.isPrivate(f.getModifiers()),
                "Player.name must be private");

        // --- constructor / getters ---
        Player p = new Player("Alex", 50);

        pass_fail("Alex".equals(p.getName()),
                "Player.getName() returns constructor name",
                "Player.getName() did not return 'Alex'");

        pass_fail(p.getHealth() == 50,
                "Player.getHealth() == 50 after construction",
                "Player.getHealth() did not return 50");

        // --- starts with Stick in each hand ---
        pass_fail(p.getLeftHand() instanceof Stick,
                "Player starts with Stick in left hand",
                "Player left hand is not a Stick on construction");

        pass_fail(p.getRightHand() instanceof Stick,
                "Player starts with Stick in right hand",
                "Player right hand is not a Stick on construction");

        // --- hands returned as Weapon references ---
        pass_fail(p.getLeftHand() != null,
                "getLeftHand() returns non-null Weapon",
                "getLeftHand() returned null");

        pass_fail(p.getRightHand() != null,
                "getRightHand() returns non-null Weapon",
                "getRightHand() returned null");

        // --- setLeftHand / setRightHand ---
        Weapon sword = new Sword();
        p.setLeftHand(sword);
        pass_fail(p.getLeftHand() == sword,
                "setLeftHand(Sword) updates left hand",
                "setLeftHand() did not update left hand");

        Weapon ms = new MagicSword();
        p.setRightHand(ms);
        pass_fail(p.getRightHand() == ms,
                "setRightHand(MagicSword) updates right hand",
                "setRightHand() did not update right hand");

        // --- recordDamage() basic subtraction ---
        Player p2 = new Player("Test", 50);
        p2.recordDamage(10);
        pass_fail(p2.getHealth() == 40,
                "recordDamage(10): health 50 → 40",
                "recordDamage(10) did not reduce health to 40, got: " + p2.getHealth());

        // --- recordDamage() clamps at 0 ---
        Player p3 = new Player("Test", 50);
        p3.recordDamage(999);
        pass_fail(p3.getHealth() == 0,
                "recordDamage(999) clamps health at 0, not negative",
                "recordDamage(999) produced health of " + p3.getHealth() + " (should be 0)");

        // --- exact zero ---
        Player p4 = new Player("Test", 20);
        p4.recordDamage(20);
        pass_fail(p4.getHealth() == 0,
                "recordDamage(20) on health=20 gives exactly 0",
                "recordDamage(20) on health=20 gave: " + p4.getHealth());

        // --- toString() not null/empty ---
        String ts = new Player("X", 10).toString();
        pass_fail(ts != null && !ts.isEmpty(),
                "Player.toString() returns non-empty string",
                "Player.toString() returned null or empty");
    }

    // ===================================================
    // Section 8 – CrabBoss
    // ===================================================
    private static void testCrabBoss() {
        printSection("CrabBoss");

        // --- field types via reflection ---
        checkField(CrabBoss.class, "leftClaw",
                "CrabBoss.leftClaw declared as type Die",
                f -> f.getType() == Die.class,
                "CrabBoss.leftClaw must be declared as Die");

        checkField(CrabBoss.class, "rightClaw",
                "CrabBoss.rightClaw declared as type Die (not LoadedDie)",
                f -> f.getType() == Die.class,
                "CrabBoss.rightClaw must be declared as Die, not LoadedDie");

        checkField(CrabBoss.class, "health",
                "CrabBoss.health is private",
                f -> Modifier.isPrivate(f.getModifiers()),
                "CrabBoss.health must be private");

        // --- constructor / getters ---
        CrabBoss crab = new CrabBoss(50);

        pass_fail(crab.getHealth() == 50,
                "CrabBoss.getHealth() == 50 after construction",
                "CrabBoss.getHealth() did not return 50");

        pass_fail(crab.getLeftClaw() != null,
                "CrabBoss.getLeftClaw() returns non-null",
                "CrabBoss.getLeftClaw() returned null");

        pass_fail(crab.getRightClaw() != null,
                "CrabBoss.getRightClaw() returns non-null",
                "CrabBoss.getRightClaw() returned null");

        // --- left claw is a normal Die (not LoadedDie) ---
        pass_fail(!(crab.getLeftClaw() instanceof LoadedDie),
                "leftClaw object is a normal Die (not LoadedDie)",
                "leftClaw is a LoadedDie — only right claw should be loaded");

        // --- right claw is actually a LoadedDie at runtime ---
        pass_fail(crab.getRightClaw() instanceof LoadedDie,
                "rightClaw object is a LoadedDie (polymorphism through Die reference)",
                "rightClaw is not a LoadedDie — it must hold a LoadedDie object");

        // --- left claw roll range: Die(4) → 1-4 ---
        boolean leftOk = true;
        for (int i = 0; i < 200; i++) {
            int r = crab.getLeftClaw().roll();
            if (r < 1 || r > 4) { leftOk = false; break; }
        }
        pass_fail(leftOk,
                "leftClaw.roll() always in [1..4]",
                "leftClaw.roll() produced value outside [1..4]");

        // --- right claw roll range: 1-6 ---
        boolean rightOk = true;
        for (int i = 0; i < 200; i++) {
            int r = crab.getRightClaw().roll();
            if (r < 1 || r > 6) { rightOk = false; break; }
        }
        pass_fail(rightOk,
                "rightClaw.roll() always in [1..6]",
                "rightClaw.roll() produced value outside [1..6]");

        // --- right claw bias works ---
        int maxCount = 0;
        int trials = 1000;
        for (int i = 0; i < trials; i++) {
            if (crab.getRightClaw().roll() == 6) maxCount++;
        }
        double rate = (double) maxCount / trials;
        pass_fail(rate >= 0.35,
                String.format("rightClaw bias works: 6 appeared %.1f%% (expected ≥35%%)", rate * 100),
                String.format("rightClaw bias too weak: 6 appeared %.1f%% — LoadedDie may not be set up correctly", rate * 100));

        // --- recordDamage() basic subtraction ---
        CrabBoss c2 = new CrabBoss(50);
        c2.recordDamage(15);
        pass_fail(c2.getHealth() == 35,
                "CrabBoss.recordDamage(15): health 50 → 35",
                "CrabBoss.recordDamage(15) gave: " + c2.getHealth() + " (expected 35)");

        // --- recordDamage() does NOT need to clamp (can go negative) ---
        CrabBoss c3 = new CrabBoss(10);
        c3.recordDamage(999);
        pass_fail(c3.getHealth() <= 0,
                "CrabBoss.recordDamage(999) reduces health to 0 or below (no clamp required)",
                "CrabBoss.recordDamage(999) gave unexpected health: " + c3.getHealth());

        // --- toString() not null/empty ---
        String ts = new CrabBoss(50).toString();
        pass_fail(ts != null && !ts.isEmpty(),
                "CrabBoss.toString() returns non-empty string",
                "CrabBoss.toString() returned null or empty");
    }

    // ===================================================
    // Section 9 – CrabBattleGame structure
    // ===================================================
    private static void testCrabBattleGameStructure() {
        printSection("CrabBattleGame (structure only)");

        // --- field: player is type Player ---
        checkField(CrabBattleGame.class, "player",
                "CrabBattleGame has a Player field named 'player'",
                f -> f.getType() == Player.class,
                "CrabBattleGame.player must be type Player");

        // --- field: crab is type CrabBoss ---
        checkField(CrabBattleGame.class, "crab",
                "CrabBattleGame has a CrabBoss field named 'crab'",
                f -> f.getType() == CrabBoss.class,
                "CrabBattleGame.crab must be type CrabBoss");

        // --- field: keyboard is Scanner ---
        checkField(CrabBattleGame.class, "keyboard",
                "CrabBattleGame has a Scanner field named 'keyboard'",
                f -> f.getType() == java.util.Scanner.class,
                "CrabBattleGame.keyboard must be type Scanner");

        // --- all fields are private ---
        for (String name : new String[]{"player", "crab", "keyboard"}) {
            checkField(CrabBattleGame.class, name,
                    "CrabBattleGame." + name + " is private",
                    f -> Modifier.isPrivate(f.getModifiers()),
                    "CrabBattleGame." + name + " must be private");
        }

        // --- required methods exist with correct visibility ---
        checkMethodExists(CrabBattleGame.class, "play",
                "play() is public", true);

        for (String m : new String[]{
                "displayMenu", "playerAttack", "crabAttack",
                "searchForWeapons", "replaceLeftHand", "replaceRightHand",
                "showStatus", "isGameOver"}) {
            checkMethodExists(CrabBattleGame.class, m,
                    m + "() is private", false);
        }

        // --- randomWeapon() is private and returns Weapon ---
        try {
            Method m = CrabBattleGame.class.getDeclaredMethod("randomWeapon");
            pass_fail(Modifier.isPrivate(m.getModifiers()),
                    "randomWeapon() is private",
                    "randomWeapon() must be private");
            pass_fail(m.getReturnType() == Weapon.class,
                    "randomWeapon() return type is Weapon",
                    "randomWeapon() must return Weapon, not a concrete type");
        } catch (NoSuchMethodException e) {
            fail("randomWeapon() method not found in CrabBattleGame");
        }

        // --- Main has only main() — check it's tiny ---
        Method[] mainMethods = Main.class.getDeclaredMethods();
        pass_fail(mainMethods.length == 1,
                "Main class has only one method (main)",
                "Main class has " + mainMethods.length + " methods — it should only have main()");
    }


    // ===================================================
    // Helpers
    // ===================================================

    @FunctionalInterface
    interface FieldCheck {
        boolean test(Field f);
    }

    private static void checkField(Class<?> cls, String fieldName,
            String label, FieldCheck check, String failMsg) {
        try {
            Field f = getField(cls, fieldName);
            if (f == null) {
                fail(label + " — field '" + fieldName + "' not found in " + cls.getSimpleName());
                return;
            }
            f.setAccessible(true);
            pass_fail(check.test(f), label, failMsg);
        } catch (Exception e) {
            fail(label + " — exception: " + e.getMessage());
        }
    }

    /** Searches declared fields and superclass fields. */
    private static Field getField(Class<?> cls, String name) {
        Class<?> current = cls;
        while (current != null && current != Object.class) {
            try { return current.getDeclaredField(name); }
            catch (NoSuchFieldException e) { current = current.getSuperclass(); }
        }
        return null;
    }

    private static void checkMethod(Class<?> cls, String methodName,
            String label, Class<?> returnType, Class<?>... params) {
        try {
            Method m = cls.getMethod(methodName, params);
            pass_fail(m.getReturnType() == returnType,
                    label,
                    label + " — wrong return type: " + m.getReturnType());
        } catch (NoSuchMethodException e) {
            fail(label + " — method not found");
        }
    }

    private static void checkMethodExists(Class<?> cls, String methodName,
            String label, boolean expectPublic) {
        try {
            // search declared methods (handles private)
            for (Method m : cls.getDeclaredMethods()) {
                if (m.getName().equals(methodName)) {
                    boolean isPublic = Modifier.isPublic(m.getModifiers());
                    boolean isPrivate = Modifier.isPrivate(m.getModifiers());
                    if (expectPublic) {
                        pass_fail(isPublic, label, label + " — must be public");
                    } else {
                        pass_fail(isPrivate, label, label + " — must be private");
                    }
                    return;
                }
            }
            fail(label + " — method '" + methodName + "' not found");
        } catch (Exception e) {
            fail(label + " — exception: " + e.getMessage());
        }
    }

    private static void pass_fail(boolean condition, String passMsg, String failMsg) {
        if (condition) pass(passMsg);
        else fail(failMsg);
    }

    private static void pass(String msg) {
        System.out.println("  [PASS]  " + msg);
        passed++;
    }

    private static void fail(String msg) {
        System.out.println("  [FAIL]  " + msg);
        failed++;
    }

    private static void printHeader() {
        System.out.println("====================================================");
        System.out.println("  CSC123  Project 05 – Crab Battle  Auto-Grader");
        System.out.println("====================================================");
    }

    private static void printSection(String name) {
        System.out.println();
        System.out.println("=== " + name + " ===");
    }

    private static void printFooter() {
        int total = passed + failed;
        System.out.println();
        System.out.println("====================================================");
        System.out.printf("  SCORE:  %d / %d tests passed%n", passed, total);
        if (failed == 0) {
            System.out.println("  All tests passed.");
        } else {
            System.out.println("  Review [FAIL] items above.");
        }
        System.out.println("====================================================");
    }
}
