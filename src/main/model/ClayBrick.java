package main.model;

import main.controller.Brick;

import java.awt.*;
import java.awt.Point;

/** Characteristics and features for Clay Brick */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(215, 90, 68).darker();
    private static final Color DEF_BORDER = new Color(170, 170, 170);
    private static final int CLAY_STRENGTH = 1;

    /**
     * Constructs and initializes ClayBrick
     * @param point (x,y) coordinates
     * @param size Integer values for the brick's height and width
     */
    public ClayBrick(Point point, Dimension size){
        super(point, size, DEF_BORDER, DEF_INNER, CLAY_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }

}