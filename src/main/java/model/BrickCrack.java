package model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


/** Characteristics and features for a crack in a brick */
public class BrickCrack {

    private static final double JUMP_PROBABILITY = 0.7;

    private final Shape brickFace;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private final GeneralPath crack;
    private final int crackDepth;

    private final int steps;

    /**
     * Constructs and initializes a line through the brickFace.
     * @param crackDepth Integer value for the depth of the crack
     * @param steps Integer value
     * @param brickFace A shape
     */
    public BrickCrack(int crackDepth, int steps, Shape brickFace){
        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;

        this.brickFace = brickFace;
    }

    /** Determine path
     * @return Instance of the Crack class
     */
    public GeneralPath draw(){
        return crack;
    }

    /** Resets the generalPath */
    public void reset(){
        crack.reset();
    }

    /**
     * Determine start and end point of crack
     * @param point (x,y) coordinates of the brick
     * @param direction Integer value for the direction of the impact
     */
    protected void makeCrack(Point2D point, int direction){
        assert false;
        Rectangle bounds = brickFace.getBounds();

        Point impact = new Point((int)point.getX(), (int)point.getY());
        Point start = new Point();
        Point end = new Point();

        switch(direction){
            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);
                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start, end, VERTICAL);
                makeCrack(impact, tmp);
                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start, end, HORIZONTAL);
                makeCrack(impact, tmp);
                break;
        }
    }

    /**
     * Draw the crack
     * @param start (x,y) coordinates for the start of crack
     * @param end (x,y) coordinates for the end of crack
     */
    protected void makeCrack(Point start, Point end){

        GeneralPath path = new GeneralPath();

        path.moveTo(start.x, start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump = bound * 5;

        double x, y;

        for(int i = 1; i < steps; i++){

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i, steps))
                y += jumps(jump);

                /* Adds a point to the path by drawing a straight line from the current coordinates
                to the new specified coordinates specified in double precision
                 */
            path.lineTo(x, y);

        }

        path.lineTo(end.x, end.y);
        crack.append(path, true);
    }

    /**
     * Generates a random integer within the boundary
     * @param bound Integer value that limits the random number
     * @return a random integer value within the limits.*/
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return Brick.getRnd().nextInt(n) - bound;
    }

    /**
     * Locate the centre
     * @param divisions Integer value
     * @param i Integer value
     */
    private boolean inMiddle(int i, int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    /**
     * Determines if the random double is larger than the probability.
     * @param bound Integer value that limits the random number
     * @return 0
     */
    private int jumps(int bound){
        if(Brick.getRnd().nextDouble() > BrickCrack.JUMP_PROBABILITY)
            return randomInBounds(bound);
        return 0;
    }

    /**
     * Determines if the crack is horizontal or vertical
     * @param from (x,y) coordinates of the starting point
     * @param to (x,y) coordinates of the end point
     * @param direction Direction of crack
     * @return (x,y) coordinates of a random end point
     */
    private Point makeRandomPoint(Point from, Point to, int direction){
        Point out = new Point();
        int pos;

        switch (direction) {
            case HORIZONTAL -> {
                pos = Brick.getRnd().nextInt(to.x - from.x) + from.x;
                out.setLocation(pos, to.y);
            }
            case VERTICAL -> {
                pos = Brick.getRnd().nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x, pos);
            }
        }
        return out;
    }

}
