package Main.Ball;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16
 */

abstract public class Ball {

    private Shape ballFace;

    private final Point2D center;
    public Point2D up;
    public Point2D down;
    public Point2D left;
    public Point2D right;

    private final Color border;
    private final Color inner;

    private int speedX;
    private int speedY;

    public Ball(Point2D center, float radiusA, float radiusB, Color inner, Color border){
        // Define location
        this.center = center;

        // Define points
        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(), center.getY() - (radiusB / 2));
        down.setLocation(center.getX(), center.getY() + (radiusB / 2));

        left.setLocation(center.getX() - (radiusA / 2), center.getY());
        right.setLocation(center.getX() + (radiusA / 2), center.getY());

        ballFace = makeBall(center, radiusA, radiusB);
        this.border = border;
        this.inner  = inner;
        // Initialise speed
        speedX = 0;
        speedY = 0;
    }

    // Abstract method for making the ball
    protected abstract Shape makeBall(Point2D center, float radiusA, float radiusB);

    // Move the ball when called
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX), (center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        // Sets the location and size of the framing rectangle
        tmp.setFrame((center.getX() -(w / 2)), (center.getY() - (h / 2)), w, h);
        setPoints(w, h);

        ballFace = tmp;
    }

    public void setSpeed(int x, int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
        speedY = s;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() - (w / 2)), (center.getY() - (h / 2)), w, h);
        ballFace = tmp;
    }

    private void setPoints(double width, double height){
        up.setLocation(center.getX(), center.getY() - (height / 2));
        down.setLocation(center.getX(), center.getY() + (height / 2));

        left.setLocation(center.getX() - (width / 2), center.getY());
        right.setLocation(center.getX() + (width / 2), center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }
}
