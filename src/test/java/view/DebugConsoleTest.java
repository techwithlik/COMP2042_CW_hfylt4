package view;

import model.Level;
import model.Wall;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DebugConsoleTest {

    private Wall wall;
    DebugConsole debugConsole = new DebugConsole(new JFrame(), new Wall(new Rectangle(0, 0, 600, 450),new Point(300,430)), new GameBoard(new JFrame()), new Level(new Rectangle(0, 0, 600, 450), 30, 3, (float)6/2, wall));

    @Test
    void windowOpened() {
        WindowEvent windowEvent =  new WindowEvent(new Window(debugConsole), WindowEvent.WINDOW_OPENED);
        debugConsole.windowOpened(windowEvent);
        assertNotNull(debugConsole);
    }

    @Test
    void windowClosing() {
        WindowEvent windowEvent = new WindowEvent(new Window(debugConsole), WindowEvent.WINDOW_CLOSING);
        debugConsole.windowClosing(windowEvent);
        assertNotNull(debugConsole);
    }

    @Test
    void windowClosed() {
        WindowEvent windowEvent = new WindowEvent(new Window(debugConsole), WindowEvent.WINDOW_CLOSED);
        debugConsole.windowClosed(windowEvent);
        assertNotNull(debugConsole);
    }

    @Test
    void windowIconified() {
        WindowEvent windowEvent = new WindowEvent(new Window(debugConsole), WindowEvent.WINDOW_ICONIFIED);
        debugConsole.windowClosed(windowEvent);
        assertNotNull(debugConsole);
    }

    @Test
    void windowActivated() {
        WindowEvent windowEvent = new WindowEvent(new Window(debugConsole), WindowEvent.WINDOW_ACTIVATED);
        debugConsole.windowClosed(windowEvent);
        assertNotNull(debugConsole);
    }
}