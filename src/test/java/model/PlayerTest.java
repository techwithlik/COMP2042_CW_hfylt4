package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class PlayerTest {

    Player player = new Player(new Point(300, 430), 150, 10, new Rectangle(0, 0, 600, 450));
    RubberBall ball = new RubberBall(new Point(300, 430));

    @Test
    void impact() {
        assertTrue(player.impact(ball));
    }

    @Test
    void move() {
        player.move();
        int x = (int) ball.getPosition().getX() + 5;
        Point p = new Point(x, 430);
        assertEquals(new Point(300, 435), p);
    }

    @Test
    void moveLeft() {
        player.moveLeft();
        player.setMoveAmount(-5);
        assertEquals(-5, player.getMoveAmount());
    }

    @Test
    void moveRight() {
        player.moveRight();
        player.setMoveAmount(5);
        assertEquals(5, player.getMoveAmount());
    }

    @Test
    void stop() {
        player.stop();
        assertEquals(0, player.getMoveAmount());
    }

    @Test
    void getPlayerFace() {
        Rectangle playerFace = new Rectangle(225,430,150,10);
        assertEquals(playerFace,player.getPlayerFace());
    }

    @Test
    void moveTo() {
        player.moveTo(new Point(300, 430));
        Point playerPosition = new Point(300 - (150/2), 430);
        assertEquals(new Point(225, 430), playerPosition);
    }
}