/* GUI driver for XsAndOs
 * 
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
 * INSTRUCTOR NOTE — DO NOT MODIFY THIS FILE (UNLESS EXPLICITLY TOLD)
 * =====================================================================
 *
 * This file is the GUI / DRIVER layer for the project.
 *
 * STUDENTS:
 * - Do NOT change this file unless the assignment explicitly tells you to.
 * - Your grade is based on the correctness of the MODEL logic in XsAndOs.java.
 * - You are NOT expected to understand every GUI detail yet.
 *
 * PURPOSE OF THIS FILE
 * ---------------------------------------------------------------------
 * gui.java provides a graphical interface so you can PLAY and VISUALIZE
 * the game. It is NOT where the game rules should live.
 *
 * RESPONSIBILITY SPLIT (MODEL vs DRIVER)
 * ---------------------------------------------------------------------
 * XsAndOs.java (MODEL):
 *   - stores board state
 *   - tracks turns
 *   - enforces rules
 *   - answers questions like isWinner(), isCat(), isFull(), isValid()
 *   - updates state via playMove()
 *   - performs NO user input
 *   - performs NO output except displayBoard() (if required by spec)
 *
 * gui.java (DRIVER / VIEW):
 *   - handles user interaction (mouse clicks, buttons, etc.)
 *   - displays board state
 *   - shows messages to the user
 *   - calls methods on XsAndOs to perform moves and check results
 *
 * IMPORTANT: NO GAME LOGIC SHOULD BE ADDED HERE.
 * ---------------------------------------------------------------------
 * If you “fix” a bug by editing gui.java, you have NOT fixed your model.
 * The automated tester does not rely on this GUI.
 * It tests XsAndOs directly.
 *
 * =====================================================================
 */

// GUI driver for XsAndOs
// 2007-09-18

// malcolm
// nov 2019

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class gui extends Canvas implements MouseListener {
  public static void main(String args[]) {
    JFrame win = new JFrame("Xs And Os");
    win.setSize(800, 800);
    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    win.add(new gui());
    win.setVisible(true);
  }

  private char player = 'X';
  private XsAndOs XsOs;
  private boolean started = false;
  private boolean gameOver = false;

  public gui() {
    addMouseListener(this);
    XsOs = new XsAndOs();
  }

  public void paint(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, 800, 800);

    if (!started) {
      g.setColor(Color.black);
      g.setFont(new Font("Arial", Font.BOLD, 48));
      g.drawString("click to play", 275, 400);
    } else {
      drawEmptyBoard(g);
      for (int r = 0; r < 3; r++)
        for (int c = 0; c < 3; c++)
          if (XsOs.playerAt(r, c) != ' ') drawSymbol(g, XsOs.playerAt(r, c), r, c);

      gameOver = (XsOs.isWinner('X') || XsOs.isWinner('O') || XsOs.isFull());
      if (gameOver) {
        g.setFont(new Font("Arial", Font.BOLD, 144));
        g.setColor(Color.black);
        if (XsOs.isWinner('X')) g.drawString("X wins!", 160, 400);
        if (XsOs.isWinner('O')) g.drawString("O wins!", 160, 400);
        if (XsOs.isCat()) g.drawString("TIE GAME", 50, 400);
      } else {
        g.setFont(new Font("Arial", Font.BOLD, 72));
        g.setColor(Color.black);
        g.drawString(player + ", go.", 10, 730);
      }
    }
  }

  public void drawEmptyBoard(Graphics g) {
    g.setColor(Color.black);
    // horizontal lines
    g.drawLine(100, 250, 700, 250);
    g.drawLine(100, 450, 700, 450);
    // vertical lines
    g.drawLine(300, 50, 300, 650);
    g.drawLine(500, 50, 500, 650);
    // labels
    g.drawString("(0,0)", 102, 62);
    g.drawString("(1,0)", 102, 262);
    g.drawString("(2,0)", 102, 462);

    g.drawString("(0,1)", 302, 62);
    g.drawString("(1,1)", 302, 262);
    g.drawString("(2,1)", 302, 462);

    g.drawString("(0,2)", 502, 62);
    g.drawString("(1,2)", 502, 262);
    g.drawString("(2,2)", 502, 462);
  }

  public void drawSymbol(Graphics g, char p, int r, int c) {
    int x = 200 * c + 125;
    int y = 200 * r + 85;
    if (p == 'X') drawX(g, x, y);
    else if (p == 'O') drawO(g, x, y);
    else drawError(g, x, y);
  }

  public void drawX(Graphics g, int x, int y) {
    g.setColor(Color.red);
    g.drawLine(x, y, x + 150, y + 150);
    g.drawLine(x + 150, y, x, y + 150);
  }

  public void drawO(Graphics g, int x, int y) {
    g.setColor(Color.blue);
    g.drawOval(x, y, 150, 150);
  }

  public void drawError(Graphics g, int x, int y) {
    // debugging code, in case drawSymbol is called with something other
    //   than 'X' or 'O'
    g.setColor(Color.red);
    g.fill3DRect(x - 2, y - 2, 155, 155, true);
    g.setColor(Color.yellow);
    g.fillArc(x, y, 150, 150, 0, 90);
    g.fillArc(x, y, 150, 150, 180, 90);
    g.setColor(Color.orange);
    g.fillArc(x, y, 150, 150, 90, 90);
    g.fillArc(x, y, 150, 150, 270, 90);
  }

  public void mouseClicked(MouseEvent evt) {
    int x = evt.getX();
    int y = evt.getY();

    if (gameOver) {
      started = false;
      gameOver = false;
      XsOs = new XsAndOs();
      repaint();
      return;
    }

    if (!started) {
      started = true;
      repaint();
      return;
    }

    int r = (y - 85) / 200;
    int c = (x - 125) / 200;

    if (XsOs.playerAt(r, c) == ' ') {
      XsOs.playMove(player, r, c);
      player = (player == 'X' ? 'O' : 'X');
    }
    repaint();
  }

  public void mousePressed(MouseEvent evt) {}

  public void mouseReleased(MouseEvent evt) {}

  public void mouseEntered(MouseEvent evt) {}

  public void mouseExited(MouseEvent evt) {}
}
