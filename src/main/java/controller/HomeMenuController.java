package controller;

import view.HomeMenu;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/** This class is used to perform action events, and updates to view class. */
public class HomeMenuController {
    private final GameFrame gameFrame; // model
    private final HomeMenu homeMenu;  // view

    /**
     * Construct object and initialise variables.
     * @param gameFrame represents GameFrame class
     * @param view represents HomeMenu class
     */
    public HomeMenuController(GameFrame gameFrame, HomeMenu view) {
        this.gameFrame = gameFrame;
        this.homeMenu = view;

        this.homeMenu.addMouseEvent(new addMouseEvnt());
        this.homeMenu.AddMouseMotionListener(new addMouseEvnt());
    }

    /** This addMouseEvnt class is used to listen for mouse events and perform actions */
    class addMouseEvnt implements MouseListener, MouseMotionListener{

        /**
         * Listen for any mouse clicked and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();

            if (homeMenu.getStartButton().contains(p)) {
                gameFrame.enableGameBoard();
            }
            else if (homeMenu.getExitButton().contains(p)) {
                System.out.println("Goodbye " + System.getProperty("user.name"));
                System.exit(0);
            }
            else if(homeMenu.getTutorialButton().contains(p)){
                homeMenu.setTutorialClicked(true);
                homeMenu.setShowTutorial(true);
                homeMenu.repaint();
            }
            else if(homeMenu.getBackButton().contains(p)){
                homeMenu.setBackClicked(true);
                homeMenu.setShowTutorial(false);
                homeMenu.repaint();
            }
        }

        /**
         * Listen for any mouse pressed and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();
            if (homeMenu.getStartButton().contains(p)) {
                homeMenu.setStartClicked(true);
                homeMenu.Repaint(homeMenu.getStartButton());
            }
            else if (homeMenu.getExitButton().contains(p)) {
                homeMenu.setExitClicked(true);
                homeMenu.Repaint(homeMenu.getExitButton());
            }
            else if (homeMenu.getTutorialButton().contains(p)) {
                homeMenu.setTutorialClicked(true);
                homeMenu.Repaint(homeMenu.getTutorialButton());
            }
            else if (homeMenu.getBackButton().contains(p)) {
                homeMenu.setBackClicked(true);
                homeMenu.Repaint(homeMenu.getBackButton());
            }
        }

        /**
         * Listen for any mouse released and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
            if (homeMenu.isStartClicked()) {
                homeMenu.setStartClicked(false);
                homeMenu.Repaint(homeMenu.getStartButton());
            }
            else if (homeMenu.isExitClicked()) {
                homeMenu.setExitClicked(false);
                homeMenu.Repaint(homeMenu.getExitButton());
            }
            else if (homeMenu.isTutorialClicked()) {
                homeMenu.setTutorialClicked(false);
                homeMenu.Repaint(homeMenu.getTutorialButton());
            }
            else if (homeMenu.isBackClicked()) {
                homeMenu.setBackClicked(false);
                homeMenu.Repaint(homeMenu.getBackButton());
            }
        }

        /**
         * Listen for any mouse entered and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {

        }

        /**
         * Listen for any mouse exited and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseExited(java.awt.event.MouseEvent mouseEvent) {

        }

        /**
         * Listen for any mouse dragged and perform specific actions.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseDragged(java.awt.event.MouseEvent mouseEvent) {

        }

        /**
         * When the mouse moves over to the buttons, the cursor shape will change to a hand cursor.
         * @param mouseEvent represents mouse event
         */
        @Override
        public void mouseMoved(java.awt.event.MouseEvent mouseEvent) {
            Point p = mouseEvent.getPoint();

            if(homeMenu.getStartButton().contains(p) || homeMenu.getExitButton().contains(p) || homeMenu.getTutorialButton().contains(p) || homeMenu.getBackButton().contains(p))
                homeMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                homeMenu.setCursor(Cursor.getDefaultCursor());
        }
    }
}
