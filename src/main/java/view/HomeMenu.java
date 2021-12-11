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

import controller.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import model.AudioPlayer;


/** Components that make up the Start screen */
public class HomeMenu extends JComponent {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.2";

    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";
    private static final String BACK_TEXT = "Back";
    private static final String TUTORIAL_TEXT = "Tutorial";

    // Start menu colors
    private static final Color BG_COLOR = new Color (181, 229, 255); // Light Blue
    private static final Color BORDER_COLOR = Color.WHITE;
    private static final Color DASH_BORDER_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;

    // Border sizes
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};

    private final AudioPlayer audio = new AudioPlayer("src/main/resources/music.wav");

    // Start menu buttons
    private final Rectangle menuFace;
    private final Rectangle startButton;
    private final Rectangle exitButton;
    private final Rectangle tutorialButton;

    // Tutorial Menu buttons
    private final Rectangle backButton;

    private boolean showTutorial;

    // Border strokes
    private final BasicStroke borderStoke;
    private final BasicStroke borderStoke_noDashes;

    private final Font greetingsFont;
    private final Font gameTitleFont;
    private final Font creditsFont;
    private final Font buttonFont;

    private boolean startClicked;
    private boolean exitClicked;
    private boolean backClicked;
    private boolean tutorialClicked;

    /**
     * Constructs and initializes the Start screen.
     * @param owner Instance of GameFrame that the component is on
     * @param area Sets the dimensions of the screen
     */
    public HomeMenu(GameFrame owner, Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);
        tutorialButton = new Rectangle(btnDim);
        backButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, DASHES, 0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono", Font.PLAIN, 25);
        gameTitleFont = new Font("Noto Mono", Font.BOLD, 40);
        creditsFont = new Font("Monospaced", Font.PLAIN, 10);
        buttonFont = new Font("Monospaced", Font.PLAIN, startButton.height - 2);
    }

    public void paint(Graphics g){
        audio.play();
        audio.loop();
        // Audio player

        drawMenu((Graphics2D)g);
    }

    /**
     * Draws the MenuFace
     * @param g2d Instance of class Graphics2d that provides information on 2d drawings
     */
    public void drawMenu(Graphics2D g2d){

        if (showTutorial) {
            drawContainer(g2d);
            drawButton(g2d);
        }
        else {
            drawContainer(g2d);

            Color prevColor = g2d.getColor();
            Font prevFont = g2d.getFont();

            double x = menuFace.getX();
            double y = menuFace.getY();

            g2d.translate(x, y);

            // methods calls
            drawText(g2d);
            drawButton(g2d);
            // end of methods calls

            g2d.translate(-x, -y);
            g2d.setFont(prevFont);
            g2d.setColor(prevColor);
        }
    }

    /** Initializes components on which the menuFace will be drawn on. */
    private void drawContainer(Graphics2D g2d){

        if(showTutorial)
        {
            g2d.fill(menuFace);
            Image picture = Toolkit.getDefaultToolkit().getImage("src/main/resources/tutorial-bg.jpg");
            g2d.drawImage(picture, 0, 0, this);
        }
        else
        {
            // Background image
            g2d.fill(menuFace);
            Image picture = Toolkit.getDefaultToolkit().getImage("src/main/resources/start-bg.jpg");
            g2d.drawImage(picture, 0, 0, this);
        }

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);
    }

    /** Initializes the location and draws the text on the menuFace */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS, frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE, frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS, frc);

        int sX, sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS, sX, sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1; // Add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE, sX, sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS, sX, sY);
    }

    /** Initializes the location and draws the start, exit, tutorial, and back buttons. */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT, frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT, frc);
        Rectangle2D tTxtRect = buttonFont.getStringBounds(TUTORIAL_TEXT, frc);
        Rectangle2D bTxtRect = buttonFont.getStringBounds(BACK_TEXT, frc);

        g2d.setFont(buttonFont);

        if(showTutorial) {
            int x = (menuFace.width - backButton.width) - 10;
            int y = (menuFace.height - backButton.height) - 10;

            backButton.setLocation(x, y);

            x = (int) (backButton.getWidth() - txtRect.getWidth()) / 2;
            y = (int) (backButton.getHeight() - txtRect.getHeight()) / 2;

            x += backButton.x;
            y += backButton.y + (backButton.height * 0.9);

            if (backClicked) {
                Color tmp = g2d.getColor();
                g2d.setColor(CLICKED_BUTTON_COLOR);
                g2d.draw(backButton);
                g2d.setColor(CLICKED_TEXT);
                g2d.drawString(BACK_TEXT, x, y);
                g2d.setColor(tmp);
            }
            else {
                g2d.draw(backButton);
                g2d.drawString(BACK_TEXT, x, y);
            }
        }
        else {
            int x = (menuFace.width - startButton.width) / 2;
            int y = (int) ((menuFace.height - startButton.height) * 0.6);

            startButton.setLocation(x, y);

            x = (int) (startButton.getWidth() - bTxtRect.getWidth()) / 2;
            y = (int) (startButton.getHeight() - bTxtRect.getHeight()) / 2;

            x += startButton.x;
            y += startButton.y + (startButton.height * 0.9);

            if (startClicked) {
                Color tmp = g2d.getColor();
                g2d.setColor(CLICKED_BUTTON_COLOR);
                g2d.draw(startButton);
                g2d.setColor(CLICKED_TEXT);
                g2d.drawString(START_TEXT, x, y);
                g2d.setColor(tmp);
            }
            else {
                g2d.draw(startButton);
                g2d.drawString(START_TEXT, x, y);
            }

            x = startButton.x;
            y = startButton.y + 40;

            tutorialButton.setLocation(x, y);

            x = (int) (tutorialButton.getWidth() - tTxtRect.getWidth()) / 2;
            y = (int) (tutorialButton.getHeight() - tTxtRect.getHeight()) / 2;

            x += tutorialButton.x;
            y += tutorialButton.y + (startButton.height * 0.9);

            if (tutorialClicked) {
                Color tmp = g2d.getColor();
                g2d.setColor(CLICKED_BUTTON_COLOR);
                g2d.draw(exitButton);
                g2d.setColor(CLICKED_TEXT);
                g2d.drawString(TUTORIAL_TEXT, x, y);
                g2d.setColor(tmp);

                showTutorial= !showTutorial;
            }
            else {
                g2d.draw(tutorialButton);
                g2d.drawString(TUTORIAL_TEXT, x, y);
            }

            x = tutorialButton.x;
            y = tutorialButton.y + 40;

            exitButton.setLocation(x,y);
            x = (int)(exitButton.getWidth() - mTxtRect.getWidth()) / 2;
            y = (int)(exitButton.getHeight() - mTxtRect.getHeight()) / 2;
            x += exitButton.x;
            y += exitButton.y + (startButton.height * 0.9);

            if(exitClicked){
                Color tmp = g2d.getColor();
                g2d.setColor(CLICKED_BUTTON_COLOR);
                g2d.draw(exitButton);
                g2d.setColor(CLICKED_TEXT);
                g2d.drawString(EXIT_TEXT, x, y);
                g2d.setColor(tmp);
            }
            else{
                g2d.draw(exitButton);
                g2d.drawString(EXIT_TEXT, x, y);
            }
        }
    }

    /**
     * Display actions perform when user mouse clicked.
     * @param mouseEvent represents mouse event
     */
    public void addMouseEvent(MouseListener mouseEvent) {
        this.addMouseListener(mouseEvent);
    }

    /**
     * Display actions perform when user moving their mouse.
     * @param mouseEvent represents mouse event
     */
    public void AddMouseMotionListener(MouseMotionListener mouseEvent) {
        this.addMouseMotionListener(mouseEvent);
    }


    /**
     * Design buttons when button is clicked.
     * @param button represents button
     */
    public void Repaint(Rectangle button){
        repaint(button.x, button.y, button.width + 1, button.height + 1);
    }

    /**
     * Get start button.
     * @return represents start button
     */
    public Rectangle getStartButton() {
        return startButton;
    }

    /**
     * Check if the start button is clicked.
     * @return true if start button is clicked
     */
    public boolean isStartClicked() {
        return startClicked;
    }

    /**
     * Set the start button to true if the button is clicked.
     * @param startClicked represents start button action
     */
    public void setStartClicked(boolean startClicked) {
        this.startClicked = startClicked;
    }


    /**
     * Get exit button
     * @return represents exit button
     */
    public Rectangle getExitButton() {
        return exitButton;
    }

    /**
     * Get if the exit button is clicked.
     * @return true if exit button is clicked
     */
    public boolean isExitClicked() {
        return exitClicked;
    }

    /**
     * Set the exit button to true if the button is clicked.
     * @param exitClicked represents exit button action
     */
    public void setExitClicked(boolean exitClicked) {
        this.exitClicked = exitClicked;
    }

    /**
     * Get tutorial button.
     * @return represents tutorial button
     */
    public Rectangle getTutorialButton() {
        return tutorialButton;
    }

    /**
     * Check if the tutorial button is clicked.
     * @return true if tutorial button is clicked
     */
    public boolean isTutorialClicked() {
        return tutorialClicked;
    }

    /**
     * Set the Tutorial button to true if the tutorial button is clicked.
     * @param tutorialClicked represents start button action
     */
    public void setTutorialClicked(boolean tutorialClicked) {
        this.tutorialClicked = tutorialClicked;
    }

    /**
     * Get Back button.
     * @return represents Back button
     */
    public Rectangle getBackButton() {
        return backButton;
    }

    /**
     * Check if the Back button is clicked.
     * @return true if Back button is clicked
     */
    public boolean isBackClicked() {
        return backClicked;
    }

    /**
     * Set the Back button to true if the button is clicked.
     * @param backClicked represents Back button action
     */
    public void setBackClicked(boolean backClicked) {
        this.backClicked = backClicked;
    }

    /**
     * Set the Tutorial to true or false.
     * @param showTutorial Boolean that represents display of tutorial screen
     */
    public void setShowTutorial(boolean showTutorial) {
        this.showTutorial = showTutorial;
    }
}
