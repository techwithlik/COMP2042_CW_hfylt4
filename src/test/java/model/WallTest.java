package model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    Wall wall = new Wall(new Rectangle(0, 0, 0, 0), new Point(0, 0));

    @Test
    void ballReset() {
        wall.setBallXSpeed(2);
        wall.setBallYSpeed(2);
        wall.setBallLost(true);
        wall.ballReset();
        assertEquals(4, wall.getBallSpeedX());
        assertEquals(-4, wall.getBallSpeedY());
        assertFalse(wall.isBallLost());
    }

    @Test
    void wallReset() {
        ClayBrick clayBrick = new ClayBrick(new Point(0,0), new Dimension(60, 45));
        if(clayBrick.isBroken()) {
            wall.wallReset();
        }

        assertEquals(0, wall.getBrickCount());
        assertEquals(3, wall.getBallCount());
    }

    @Test
    void ballEnd() {
        if (wall.getBallCount() == 0)
            assertTrue(wall.ballEnd());
    }

    @Test
    void isDone() {
        if(wall.getBrickCount() == 0){
            assertTrue(wall.isDone());
        }
    }

    @Test
    void getBrickCount() {
        assertEquals(0, wall.getBrickCount());
    }

    @Test
    void getBallCount() {
        assertEquals(3, wall.getBallCount());
    }

    @Test
    void checkScore() {
        wall.checkScore();
        assertNotNull(wall);
    }

    @Test
    void getHighScore() {
        assertNotNull(wall.getHighScore());
    }
}