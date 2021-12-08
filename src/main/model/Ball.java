package main.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;


/** This class makes the ball */
abstract public class Ball {

    private Shape ballFace;

    private final Point2D center;
    private final Point2D up;
    private final Point2D down;
    private final Point2D left;
    private final Point2D right;

    private final Color border;
    private final Color inner;

    private int speedX;
    private int speedY;

    /**
     * Constructs and initializes Ball movements.
     * @param center (x,y) coordinates
     * @param radiusA Integer value
     * @param radiusB Integer value
     * @param inner Color
     * @param border Color
     */
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

    /** Abstract method that makes the shape of the ball */
    protected abstract Shape makeBall(Point2D center, float radiusA, float radiusB);

    /** Move the ball when called */
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

    /** Sets the speed of the ball. */
    public void setSpeed(int x, int y){
        speedX = x;
        speedY = y;
    }

    /** Sets the speed of ball movement in the x axis. */
    public void setXSpeed(int s){
        speedX = s;
    }

    /** Sets the speed of ball movement in the y axis. */
    public void setYSpeed(int s){
        speedY = s;
    }

    /** Reverses the speed at the x axis. */
    public void reverseX(){
        speedX *= -1;
    }

    /** Reverses the speed at the y axis. */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * Gets the colour of the border.
     * @return Colour of border
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * Gets the colour of the ball.
     * @return Colour of ball
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Gets the coordinates
     * @return (x,y) coordinates
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * Gets the shape of the ball
     * @return Shape
     * */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * Moves the ball to point P
     * @param p (x,y) coordinates
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() - (w / 2)), (center.getY() - (h / 2)), w, h);
        ballFace = tmp;
    }

    /**
     * Sets the height and width
     * @param width Width
     * @param height Height
     */
    private void setPoints(double width, double height){
        up.setLocation(center.getX(), center.getY() - (height / 2));
        down.setLocation(center.getX(), center.getY() + (height / 2));

        left.setLocation(center.getX() - (width / 2), center.getY());
        right.setLocation(center.getX() + (width / 2), center.getY());
    }

    /**
     * Gets the speed of x
     * @return Integer value
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * Gets the speed of y
     * @return Integer value
     */
    public int getSpeedY(){
        return speedY;
    }

    public Point2D getDown() {
        return down;
    }

    public Point2D getUp() {
        return up;
    }

    public Point2D getLeft() {
        return left;
    }

    public Point2D getRight() {
        return right;
    }
}
