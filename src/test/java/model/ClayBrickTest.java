package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class ClayBrickTest {

    ClayBrick clayBrick = new ClayBrick(new Point(0, 0), new Dimension(60,40));
    Rectangle brickFace = new Rectangle(new Point(0, 0), new Dimension(60,40));

    @Test
    void makeBrickFace() {
        Point pos = new Point(0, 0);
        Dimension size = new Dimension(60, 40);
        assertEquals(brickFace, clayBrick.makeBrickFace(pos, size));
    }

    @Test
    void getBrick() {
        assertEquals(brickFace, clayBrick.getBrickFace());
    }
}