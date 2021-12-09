package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

class SteelBrickTest {

    SteelBrick steelBrick = new SteelBrick(new Point(0, 0), new Dimension(60, 40));
    Rectangle brickFace = new Rectangle(new Point(0, 0), new Dimension(60, 40));

    @Test
    void makeBrickFace() {
        Point pos = new Point(0, 0);
        Dimension size = new Dimension(60, 40);
        assertEquals(brickFace, steelBrick.makeBrickFace(pos, size));
    }

    @Test
    void getBrick() {
        assertEquals(brickFace, steelBrick.getBrickFace());
    }

    @Test
    void setImpact() {
        Point2D down = new Point2D.Double();
        down.setLocation(300.0, 430.0);
        int up = 30;
        assertFalse(steelBrick.setImpact(down, up));
    }

    @Test
    void impact() {
        Random rnd = new Random();
        double STEEL_PROBABILITY = 0.5;
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            assertFalse(steelBrick.isBroken());
        }
    }
}