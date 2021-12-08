package main.model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


/** Characteristics and features for Cement Brick */
public class CementBrick extends Brick {

    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(95, 95, 95); // Dark Grey
    private static final int CEMENT_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;


    /**
     * Constructs and initializes the CementBrick and Crack
     * @param point (x,y) coordinates
     * @param size Integer values for the brick's height and width
     */
    public CementBrick(Point point, Dimension size){
        super(point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS, super.getBrickFace());
        brickFace = super.getBrickFace();
    }

    /**
     * Overrides the makeBrickFace in the parent class
     * @param pos (x,y) coordinates for the position of the shape
     * @param size Integer value for the height and width of the brick
     * @return Return the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    /**
     * An overridden method from the parent class.
     * Responsible for knowing if an impact has occurred or not
     * @param point Point of impact
     * @param dir Direction of impact
     * @return Boolean value
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(!super.isBroken())
            return false;
        super.impact();

        if(super.isBroken()){
            crack.makeCrack(point, dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * Implements the abstract method from the parent class
     * @return Return Cement Brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * A private method that is responsible for updating the Cement Brick if not broken
     */
    private void updateBrick(){
        if(super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.getBrickFace(), false);
            brickFace = gp;
        }
    }

    /**
     * Method responsible for repairing the Cement Brick
     * Resets the cracks on the cement bricks by calling the reset method
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }
}