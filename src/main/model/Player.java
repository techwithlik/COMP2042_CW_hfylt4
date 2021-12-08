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


/** Characteristics and features for Player's rectangle */
public class Player {

    public static final Color BORDER_COLOR = Color.WHITE;
    public static final Color INNER_COLOR = Color.WHITE;

    private static final int DEF_MOVE_AMOUNT = 5;

    private final Rectangle playerFace;
    private final Point ballPoint;
    private int moveAmount;
    private final int min;
    private final int max;


    /**
     * Constructs and initializes the player's rectangle
     * @param ballPoint Set of coordinates
     * @param width Width of the player
     * @param height Height of the player
     * @param container Boundaries for the rectangle
     */
    public Player(Point ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;

        // Define movement and limits
        moveAmount = 0;
        min = container.x + (width / 2);
        max = min + container.width - width;

        // Create player's shape
        playerFace = makeRectangle(width, height);
    }

    /**
     * Create the rectangle.
     * @param width Width of the player
     * @param height Height of the player
     * @return A rectangle drawing
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)), (int)ballPoint.getY());

        return new Rectangle(p,new Dimension(width, height));
    }

    /**
     * Determines if the ball is touching the player's rectangle.
     * @param b Instance of the ball class
     * @return Boolean value
     */
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

    /** Moves the player's bar within the limit */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;

        // Set location of ball point
        ballPoint.setLocation(x, ballPoint.getY());

        // Set location of bar
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2, ballPoint.y);
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    /** Get the shape of the player. */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * Moves the player to a location
     * @param p x and y coordinates.*/
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth() / 2, ballPoint.y);
    }
}
