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
package view;

import controller.GameBoardController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;


/** GameBoard is the panel / label that Brick Destroy is played on. */
public class GameBoard extends JComponent{

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

    private Timer gameTimer;
    public GameTimer timer;

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


    /**
     * Constructs and initializes a frame for the game to be played on.
     * Initializes the gameTimer, DebugConsole frame, a wall and level object.
     */
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

        GameBoardController gameBoardController = new GameBoardController(wall, this);
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
        // Set window size
        this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /** Renders the wall, ball, player, and brick drawings */
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.RED);
        g2d.drawString(timeMessage,250,200);
        g2d.setColor(Color.WHITE);
        g2d.drawString(message1, 250, 220);
        g2d.drawString(message2, 250, 240);
        g2d.setColor(Color.DARK_GRAY);
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

    /** Clears the stage or frame it is called into. */
    private void clear(Graphics2D g2d){
        g2d.fillRect(0, 0, getWidth(), getHeight());

        Image picture = Toolkit.getDefaultToolkit().getImage("src/main/resources/game-bg.gif");
        g2d.drawImage(picture, 0, 0, this);
    }

    /** Initializes and draws a rectangle.
     * Set color of the border and inside of the bricks
     * @param brick Instance of Brick
     */
    private void drawBrick(Brick brick, Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    /**
     * Initializes and draws a ball. Sets color of ball
     * @param ball Instance of the abstract class Ball
     */
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

    /** Initializes and draws a rectangle.
     * @param p Instance of the class Player.
     */
    private void drawPlayer(Player p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /** Initializes and draws the Pause Menu. */
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /** Initializes the render details of the 2d drawings. */
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

    /** Creates and initializes the Pause Menu */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        // Display the Pause Menu title
        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 5;

        // Display the Continue button
        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x, y - continueButtonRect.height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        // Display the Restart button
        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x, y - restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 1.5;

        // Display the Exit button
        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x, y - exitButtonRect.height);
        }

        g2d.drawString(EXIT, x, y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /** This method pauses the game and returns a message once focus is lost. */
    public void onLostFocus(){
        gameTimer.stop();
        timer.setGaming(false);
        message1 = "Focus Lost.";
        message2 = "Press SPACE to resume.";
        repaint();
    }

    /**
     * Check if showPauseMenu is true or false.
     * @return true or false
     */
    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    /**
     * Get the reaction for Continue button.
     * @return continueButtonRect
     */
    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    /**
     * Get the reaction for Exit button.
     * @return exitButtonRect
     */
    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    /**
     * Get the reaction for Restart button.
     * @return restartButtonRect
     */
    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }

    /**
     * Set showPauseMenu to true or false.
     * @param showPauseMenu represents true or false
     */
    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    /** Used to set message */
    public void setMessage1() {
        this.message1 = message1;
    }
    public void setMessage2() {
        this.message2 = message2;
    }

    /**
     * When user pressed on any key.
     * @param keyEvent represents keyboard event
     */
    public void AddKeyEvent(KeyListener keyEvent){
        this.addKeyListener(keyEvent);
    }

    /**
     * When user performs mouse click
     * @param mouseEvent represents mouse event
     */
    public void AddMouseListener(MouseListener mouseEvent){
        this.addMouseListener(mouseEvent);
    }

    /**
     * When user moves their mouse
     * @param mouseEvent listen for mouse motion event
     */
    public void AddMouseMotionListener(MouseMotionListener mouseEvent){
        this.addMouseMotionListener(mouseEvent);
    }

    /**
     * Get game timer
     * @return game timer
     */
    public Timer getGameTimer(){
        return gameTimer;
    }

    /**
     * Get Debug Console
     * @return debugConsole
     */
    public DebugConsole getDebugConsole(){
        return debugConsole;
    }
}
