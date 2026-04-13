/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: project04
* Date: 03/05/2026
* File: MovePattern.java
*
* Description: ↓↓↓
* Movement policy passed into Shape.tick().
* Keeps motion logic out of individual shape subclasses.
********************************************************************/
package proj04;

public class MovePattern {

    public enum Mode { STATIC, BOUNCE, WRAP, RANDOM_WALK }

    public final Mode mode;
    public final int  maxSpeed;
    public final int  jitter;

    public MovePattern(Mode mode, int maxSpeed, int jitter) {
        this.mode     = mode;
        this.maxSpeed = Math.max(0, maxSpeed);
        this.jitter   = Math.max(0, jitter);
    }

    public static MovePattern staticMode()                         { return new MovePattern(Mode.STATIC,      0, 0); }
    public static MovePattern bounce()                             { return new MovePattern(Mode.BOUNCE,      0, 0); }
    public static MovePattern wrap()                               { return new MovePattern(Mode.WRAP,        0, 0); }
    public static MovePattern randomWalk(int maxSpeed, int jitter) { return new MovePattern(Mode.RANDOM_WALK, maxSpeed, jitter); }
}
