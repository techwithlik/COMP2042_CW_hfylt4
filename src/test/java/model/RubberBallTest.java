package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

class RubberBallTest {

    RubberBall rubberBall = new RubberBall(new Point2D.Double(430, 300));

    @Test
    void makeBall() {
        assertEquals(rubberBall.getBallFace(), rubberBall.makeBall(new Point2D.Double(430, 300), 10, 10));
    }
}