/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.controller;

import main.model.Ball;
import main.view.DebugConsole;
import main.model.Level;
import main.view.HighScore;
import main.model.Player;
import main.model.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;


public class GameBoard extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    // Text in Pause Menu
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";

    // Format for Pause Menu
    private static final int TEXT_SIZE = 34;
    private static final Color MENU_COLOR = new Color(200, 238, 252); // Light blue

    // Game frame size
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = new Color(240, 240, 240); // Light grey

    private Timer gameTimer;
    private final GameTimer timer;

    private final Wall wall;
    private final Level level;
    private HighScore highScore;

    private String message1;
    private String message2;
    private String highScoreMessage;
    private String timeMessage;

    // Indicate visibility of Pause Menu
    private boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;

    private int strLen;

    private final DebugConsole debugConsole;


    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("Monospaced", Font.PLAIN, TEXT_SIZE);

        this.initialize();

        message1 = "Press SPACE to start";
        message2 = "";
        highScoreMessage = "";
        timeMessage = "";

        wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), new Point(300, 430));
        level = new Level(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, (float)6/2, wall);

        timer = new GameTimer();

        debugConsole = new DebugConsole(owner, wall, this, level);

        // Initialize the first level
        level.nextLevel();

        gameTimer = new Timer(10, e ->{
            wall.move();
            wall.findImpacts();
            timer.setGaming(true);
            message1 = String.format("Bricks: %d Balls: %d", wall.getBrickCount(), wall.getBallCount());
            message2 = String.format("Score: %d", wall.getPlayerScore());
            highScoreMessage = String.format("Record set by %s", wall.readHighScore());
            timeMessage = String.format("Time: %02d minute(s) %02d second(s)", timer.getMinutes(), timer.getSeconds());

            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.checkScore();
                    wall.wallReset();
                    highScore = new HighScore(wall);
                    message1 = String.format("Game Over! You scored %d points", wall.getPlayerScore());
                    message2 = "Press SPACE to play again.";
                    wall.setPlayerScore(0);
                }
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(level.hasLevel()){
                    message1 = "Going to the Next Level";
                    message2 = "";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    level.nextLevel();
                }
                else{
                    message1 = "GOOD JOB!";
                    message2 = "ALL WALLS HAVE BEEN DESTROYED";
                    wall.checkScore();
                    timer.resetTimer();
                    gameTimer.stop();
                    wall.setPlayerScore(0);
                }
            }

            repaint();
        });
    }

    // Set up screen and listens for keyboard and mouse input
    private void initialize(){
        // set window size
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.RED);
        g2d.drawString(timeMessage,250,200);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString(message1, 250, 220);
        g2d.drawString(message2, 250, 240);
        g2d.setColor(Color.GRAY);
        g2d.drawString(highScoreMessage, 250, 260);

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks)
            if(b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(wall.player, g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    // Clear the stage
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);

        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(tmp);
    }

    // Set color of the border and inside of the bricks
    private void drawBrick(Brick brick, Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    // Set color of ball
    private void drawBall(Ball ball, Graphics2D g2d){
        Color tmp = g2d.getColor();

        // Get shape of ball
        Shape s = ball.getBallFace();

        // Get color of ball
        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        // Get border color of ball
        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    // Set color of player's bar
    private void drawPlayer(Player p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    // Draw pause menu
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    // Remove game board
    private void obscureGameBoard(Graphics2D g2d){
        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, DEF_WIDTH, DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    // Draw pause menu, and set up buttons
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 5;

        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x, y - continueButtonRect.height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x, y- restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 1.5;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x, y - exitButtonRect.height);
        }

        g2d.drawString(EXIT, x, y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    // Detect key pressed
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            // Moves player to the left if A or Left arrow button was pressed
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                wall.player.moveLeft();
                break;
            // Moves player to the right if D or Right arrow button is pressed
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                wall.player.moveRight();
                break;
            // Pauses the game and shows pause menu if Escape button is pressed
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                timer.setGaming(false);
                repaint();
                gameTimer.stop();
                break;
            // Pauses the game if Space button is pressed
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning()) {
                        gameTimer.stop();
                        timer.setGaming(false);
                    }
                    else
                        gameTimer.start();
                break;
            // Show Debug Panel
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.player.stop();
    }

    // Checks if mouse is clicked
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message1 = "Restarting Game...";
            message2 = "Press SPACE to Start.";
            timer.resetTimer();
            wall.ballReset();
            wall.setPlayerScore(0);
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    // Checks if mouse is moved
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    // Pause game timer when paused or interrupted
    public void onLostFocus(){
        gameTimer.stop();
        timer.setGaming(false);
        message1 = "Focus Lost";
        message2 = "";
        repaint();
    }
}
