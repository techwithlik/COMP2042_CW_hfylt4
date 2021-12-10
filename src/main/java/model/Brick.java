package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private static Random rnd;
    protected Shape brickFace;

    private final Color border;
    private final Color inner;

    private final int fullStrength;
    private int strength;

    private boolean broken;


    /**
     * Initialize the Brick classes
     * @param pos Coordinates for the position of the shape
     * @param size Integer value for the height and width of the shape
     * @param border Color
     * @param inner Color
     * @param strength Strength of the brick
     */
    public Brick(Point pos, Dimension size, Color border, Color inner, int strength){
        rnd = new Random();
        broken = false;

        // Create brick
        brickFace = makeBrickFace(pos, size);

        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
    }

    /** Creates the shape of a brick.
     * @param pos (x,y) coordinates for the position of the shape
     * @param size Integer value for the height and width of the shape
     * @return the shape of a brick
     */
    protected abstract Shape makeBrickFace(Point pos, Dimension size);

    public  boolean setImpact(Point2D point, int dir){
        if(broken)
            return false;
        impact();

        return broken;
    }

    public abstract Shape getBrick();

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    /**
     * Determine where the ball is coming from
     * @param b A shape from Ball class
     * @return Integer values
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;

        int out  = 0;

        // If the right side of the ball impacts the left side of the brick
        if(brickFace.contains(b.getRight()))
            out = LEFT_IMPACT;
        // If the left side of the ball impacts the right side of the brick
        else if(brickFace.contains(b.getLeft()))
            out = RIGHT_IMPACT;
        // If the top of the ball impacts the bottom of the brick
        else if(brickFace.contains(b.getUp()))
            out = DOWN_IMPACT;
        // If the bottom of the ball impacts the top of the brick
        else if(brickFace.contains(b.getDown()))
            out = UP_IMPACT;

        return out;
    }

    /**
     * Determines whether the entity is broken
     * @return Boolean
     */
    public final boolean isBroken(){
        return !broken;
    }

    /** Entity regains its strength when called */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /** Reduces the strength of the entity */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    public static Random getRnd() {
        return rnd;
    }

    public Shape getBrickFace(){
        return brickFace;
    }
}





