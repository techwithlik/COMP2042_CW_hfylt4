package controller;

import model.Wall;
import view.GameBoard;

import java.awt.*;
import java.awt.event.*;

public class GameBoardController {

    private final GameBoard gameBoard; // view
    private final Wall wall; // model

    /**
     * This is GameBoardController class constructor. Construct object and initialise variables.
     * @param wall Represents wall class
     * @param gameBoard Represents gameBoardView class
     */
    public GameBoardController(Wall wall, GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.wall = wall;

        this.gameBoard.AddKeyEvent(new inputEvent());
        this.gameBoard.AddMouseListener(new inputEvent());
        this.gameBoard.AddMouseMotionListener(new inputEvent());
    }

    /** Listen for key and mouse events */
    class inputEvent implements KeyListener, MouseListener, MouseMotionListener {

        /**
         * A built-in method used to listen for any key typed.
         * @param keyEvent represents key event
         */
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        /**
         * Listen for any key pressed and perform specific actions
         * @param keyEvent represents key event
         */
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                // Moves player to the left if A or Left arrow button was pressed
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    wall.getPlayer().moveLeft();
                    break;
                // Moves player to the right if D or Right arrow button is pressed
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    wall.getPlayer().moveRight();
                    break;
                // Pauses the game and shows pause menu if Escape button is pressed
                case KeyEvent.VK_ESCAPE:
                    gameBoard.setShowPauseMenu(true);
                    gameBoard.timer.setGaming(false);
                    gameBoard.repaint();
                    gameBoard.getGameTimer().stop();
                    break;
                // Pauses the game if Space button is pressed
                case KeyEvent.VK_SPACE:
                    if (!gameBoard.isShowPauseMenu())
                        if (gameBoard.getGameTimer().isRunning())
                            gameBoard.getGameTimer().stop();
                        else
                            gameBoard.getGameTimer().start();
                    break;
                case KeyEvent.VK_F1:
                    if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                        gameBoard.getDebugConsole().setVisible(true);  //show debug console
                default:
                    wall.getPlayer().stop();
            }
        }

        /**
         * Listen for any key released and perform specific actions.
         * @param keyEvent represents key event
         */
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            wall.getPlayer().stop();
        }

        /**
         * Listen for any mouse clicked and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if(!gameBoard.isShowPauseMenu())
                return;
            if(gameBoard.getContinueButtonRect().contains(p)){
                gameBoard.setShowPauseMenu(false);
                gameBoard.repaint();
            }
            else if(gameBoard.getRestartButtonRect().contains(p)){
                gameBoard.setMessage1();
                gameBoard.setMessage2();
                gameBoard.timer.resetTimer();
                wall.ballReset();
                wall.setPlayerScore(0);
                wall.wallReset();
                gameBoard.setShowPauseMenu(false);
                gameBoard.repaint();
            }
            else if(gameBoard.getExitButtonRect().contains(p)){
                System.exit(0);
            }
        }

        /**
         * Listen for any mouse pressed and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        /**
         * Listen for any mouse released and perform specific actions.
         * Do nothing in the program.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        /**
         * Listen for any mouse entered and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        /**
         * Listen for any mouse exited and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        /**
         * Listen for any mouse dragged and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        /**
         * Listen for mouse movements and perform specific actions.
         * When the mouse moves over to the buttons, the cursor shape will change to a hand cursor.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if(gameBoard.getExitButtonRect() != null && gameBoard.isShowPauseMenu()) {   // change the cursor the hand
                if (gameBoard.getExitButtonRect().contains(p) || gameBoard.getContinueButtonRect().contains(p) || gameBoard.getRestartButtonRect().contains(p))
                    gameBoard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    gameBoard.setCursor(Cursor.getDefaultCursor());
            }
            else{
                gameBoard.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

}
