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
package main.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.io.*;
import javax.swing.*;


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
    private String highScore = "";
    private boolean ballLost;


    /**
     * Constructs and initializes the initial implementation for the wall
     * @param drawArea Rectangular area for the wall
     * @param ballPos Position for ball
     */
    public Wall(Rectangle drawArea, Point ballPos){

        this.startPoint = new Point(ballPos);

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        // Set ball position
        makeBall(ballPos);

        int speedX, speedY;
        do {
            speedX = rnd.nextInt(6) - 2;
        } while(speedX == 0);
        do {
            speedY = -rnd.nextInt(4);
        } while(speedY == 0);

        ball.setSpeed(speedX, speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;

        if(highScore.equals(""))
        {
            // Initialise high score
            highScore = this.readHighScore();
        }
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
            // Penalty of -100 points if ball is lost
            playerScore-=250;
            ballLost = true;
        }
    }

    // Detect brick collision and impact
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                // Vertical Impact
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    playerScore+=50;
                    return b.setImpact(ball.getDown(), Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    playerScore+=50;
                    return b.setImpact(ball.getUp(), Crack.DOWN);
                }

                // Horizontal Impact
                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    playerScore+=50;
                    return b.setImpact(ball.getRight(), Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    playerScore+=50;
                    return b.setImpact(ball.getLeft(), Crack.LEFT);
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

    public String readHighScore(){
        FileReader readFile;
        BufferedReader reader = null;

        try {
            readFile = new FileReader("resources/highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e) {
            return "Nobody:0";
        }
        finally {
            try{
                if(reader != null)
                    reader.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void checkScore(){
        if (highScore.equals("")) {
            return;
        }
        if(playerScore > Integer.parseInt(highScore.split(":")[1])){
            String name = JOptionPane.showInputDialog("Congratulations! You have made a new high score! What is your name?");
            highScore = name + ":" + playerScore;
            /*
            .dat file is used so the user cannot edit the highScore
            file to change the high score
            */
            File scoreFile = new File("resources/highscore.dat");
            if(!scoreFile.exists())
            {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter writeFile;
            BufferedWriter writer = null;
            try {
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                /*
                write operation is used instead of append operation
                to ensure only the name of the person who scores the highest score and
                the highest score is kept inside highscore.dat file
                 */
                writer.write(this.highScore);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (writer != null)
                        writer.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getHighScore() {
        return highScore;
    }

}
