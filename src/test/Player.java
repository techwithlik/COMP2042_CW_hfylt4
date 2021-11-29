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
package test;

import java.awt.*;


public class Player {

    public static final Color BORDER_COLOR = new Color(15, 85, 110);
    public static final Color INNER_COLOR = new Color(75, 160, 219);

    private static final int DEF_MOVE_AMOUNT = 5;

    private final Rectangle playerFace;
    private final Point ballPoint;
    private int moveAmount;
    private final int min;
    private final int max;


    public Player(Point ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;

        // Define movement and limits
        moveAmount = 0;
        min = container.x + (width / 2);
        max = min + container.width - width;

        // Create player's shape
        playerFace = makeRectangle(width, height);
    }

    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)), (int)ballPoint.getY());

        return new Rectangle(p,new Dimension(width, height));
    }

    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    // Move the player
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x, ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2, ballPoint.y);
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2, ballPoint.y);
    }
}
