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
package Main.BrickFactory;

import Main.Ball.Ball;
import Main.Player.Player;
import Main.Ball.RubberBall;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class Wall {

    private final Random rnd;
    private final Rectangle area;

    public Brick[] bricks;
    public Ball ball;
    public Player player;

    private final Point startPoint;
    private int brickCount;
    private int ballCount;
    private int playerScore;
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        // set ball position
        makeBall(ballPos);

        int speedX, speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX, speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;

    }

    // Instantiate ball
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    // Detects player and ball movements
    public void move(){
        player.move();
        ball.move();
    }

    // Detect ball collision
    public void findImpacts(){
        // If ball collides with player bar
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /* for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
        }
        // If ball collides with window border
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        // If ball goes beyond the bottom border, ball is lost
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            playerScore-=100;
            ballLost = true;
        }
    }

    // Detect brick collision and impact
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    playerScore+=100;
                    return b.setImpact(ball.down, Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    playerScore+=100;
                    return b.setImpact(ball.up, Crack.DOWN);
                }

                // Horizontal Impact
                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    playerScore+=100;
                    return b.setImpact(ball.right, Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    playerScore+=100;
                    return b.setImpact(ball.left, Crack.LEFT);
                }
            }
        }
        return false;
    }

    // Checks border collision left and right
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public int getBrickCount(){
        return brickCount;
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public int getPlayerScore(){return playerScore;}

    public void setPlayerScore(int num){playerScore = num;}

    // Set back players' rectangle and ball to initial position, and speed of x and y
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);

        int speedX, speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX, speedY);
        ballLost = false;
    }

    // Reset wall positions, and ball count to 3
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();

        brickCount = bricks.length;
        ballCount = 3;
    }

    // Ball count will be 0 when there are no balls left
    public boolean ballEnd(){
        return ballCount == 0;
    }

    // Brick number will be set to 0 when level is done
    public boolean isDone(){
        return brickCount == 0;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }

    public Brick[] getBricks(){
        return bricks;
    }

    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }
}
