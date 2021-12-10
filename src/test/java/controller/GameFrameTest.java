package controller;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.WindowEvent;

import static org.junit.jupiter.api.Assertions.*;

class GameFrameTest {

    GameFrame gameFrame = new GameFrame();

    @Test
    void initialize() {
        gameFrame.initialize();
        assertNotNull(gameFrame);
    }

    @Test
    void enableGameBoard() {
        gameFrame.enableGameBoard();
        assertNotNull(gameFrame);
    }

    @Test
    void windowGainedFocus() {
        WindowEvent windowEvent = new WindowEvent(new Window(gameFrame), WindowEvent.WINDOW_GAINED_FOCUS);
        gameFrame.windowGainedFocus(windowEvent);
        assertNotNull(gameFrame);
    }

    @Test
    void windowLostFocus() {
        WindowEvent windowEvent = new WindowEvent(new Window(gameFrame), WindowEvent.WINDOW_LOST_FOCUS);
        gameFrame.windowLostFocus(windowEvent);
        assertNotNull(gameFrame);
    }
}