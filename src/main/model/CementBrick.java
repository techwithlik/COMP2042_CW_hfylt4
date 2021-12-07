package main.model;

import main.controller.Brick;

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

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

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

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick(){
        if(super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.getBrickFace(), false);
            brickFace = gp;
        }
    }

    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }
}