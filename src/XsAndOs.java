/*
 * The Irish have a game called “X’y O’sies” (in Gallic) or Xs And Os for the rest of us.
 * The English call it ”noughts and crosses”, but you might know it by another name.
 * It is a game for two players, X and O, who take turns marking the spaces in a 3×3 grid.
 * The player who succeeds in placing three of their marks in a diagonal, horizontal,
 * or vertical row is the winner.
 *
 * Malcolm
 * Nov 2019
 *
 * =====================================================================
 * STUDENT WARNING — DO NOT MODIFY PROVIDED CODE
 * =====================================================================
 *
 * You are working in the MODEL class for the game.
 *
 * What you MAY do:
 *   - Add code ONLY where the file explicitly tells you to “write your code here”
 *     or where method bodies are intentionally incomplete.
 *
 * What you may NOT do (these break grading and will lose credit):
 *   - Do not change existing lines of code (logic, operators, conditionals, loops).
 *   - Do not rename anything (class name, method names, variable names).
 *   - Do not change method signatures (parameters / return types).
 *   - Do not add new methods, fields, or classes in this file.
 *   - Do not add a main() method in this file.
 *
 * ---------------------------------------------------------------------
 * MODEL / DRIVER SEPARATION (STRICT)
 * ---------------------------------------------------------------------
 * XsAndOs.java is LOGIC + STATE only.
 * Game.java (and/or TheTester.java) handles ALL user interaction.
 *
 * I/O RULE:
 *   - The ONLY method in this file that may print is displayBoard().
 *   - No System.out printing anywhere else (especially NOT in playMove()).
 *   - No Scanner / input reading in this file.
 *
 * playMove() RULE:
 *   - Must only update board state and turn count.
 *   - Must not print, prompt, or “explain” errors.
 *
 * Automated tests assume these rules. Violations can pass “by hand”
 * but will fail the tester or lose design points.
 * =====================================================================
 */

/*
Mango
Name: Trevon Collins
Course: CSC 123
Project: Project 03
Date: 02/12/2026
Descriptions: Creates the objects that go on the game board.
*/

public class XsAndOs {
    // Instance Variables
    private char[][] board;             // 2-dimensional array for the board
    private int turns;                  // after 9 turns the game must be over

    // Constructors
    public XsAndOs() {
        board = new char[3][3];         // create the board
        turns = 0;                      // set turns to zero

        for (int r = 0; r < 3; r++)     // for three rows
            for (int c = 0; c < 3; c++) // for three coluns
                board[r][c] = ' ';      // put a SPACE
    }

    // Accessor Methods

    // Do we have a winner
    // check for same char (p) all in a row
    // in a column or either diagonal
    // p is either 'O' or 'X'
    public boolean isWinner(char p) {       // Do we have a winner
        // for each row
        for (int i = 0; i < 3; i++) {       // i	check each row
            if (board[i][0] == p)           // 0
                if (board[i][1] == p)       // 1	p 	p 	p
                    if (board[i][2] == p)   // 2
                        return true;
        }
        // for each column
        for (int i = 0; i < 3; i++) {       // i  0  1  2
            if (board[0][i] == p)           //       p
                if(board[1][i] == p)        //       p
                    if(board[2][i] == p)    //       p
                        return true;
        }
        // for diagonal left to right down or up        //  0  1  2
        if (board[1][1] == p){                          //0 p      
            if(board[0][0] == p && board[2][2] == p){   //1    p
                return true;                            //2       p
            }
            if(board[0][2] == p && board[2][0] == p){   //0       p
                return true;                            //1    p
            }                                           //2 p
        }

        return false;
    }

    // isFull
    // returns true if turns equals 9 other wise false
    public boolean isFull() {
       if (turns == 9){
        return true;
       }
        return false;
    }

    // if 'O' not a winner and 'X" not Winner"
    // and if there are not more spaces avalible
    // then return true otherwise return false
    public boolean isCat() {
        if (isWinner('O')) return false;
        if (isWinner('X')) return false;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++) 
                if (board[r][c] == ' ') 
                    return false;
        return true;
    }

    // if square [r][c] is on the board
    // r and c both have to betweeen zero or two inclusively
    public boolean isValid(int r, int c) {
       if ( r >= 0 && r < 3 &&
            c >= 0 && c < 3 ){
            return true;
       } else {
        return false;
       }
    }

    // return number of turn taken so far
    public int numTurns() {
        return turns;
    }

    // return marker at borad[r][c]
    // if r and or c are invalid returns '@'
    public char playerAt(int r, int c) {
        if (isValid(r, c)) return board[r][c];
        else return '@';
    }

    // diplay the board to the user(s)
    public void displayBoard() {
        System.out.println("");
        System.out.println("  0  " + board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println("    --+-+--");
        System.out.println("  1  " + board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println("    --+-+--");
        System.out.println("  2  " + board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
        System.out.println("");
        System.out.println("     0 1 2 ");
        System.out.println("");
    }

    // Modifiers
    // if row r and column c is free (space)
    // then put may marker (p) there
    // and increament turns
    public void playMove(char p, int r, int c) {
        if (playerAt(r, c) == ' ') {
            board[r][c] = p;
            turns++;
        }
    }
}
