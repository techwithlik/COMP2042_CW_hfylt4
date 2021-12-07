package main.controller;

import main.model.Ball;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;


/**
 * Created by filippo on 04/09/16.
 */

abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private static Random rnd;
    private final String name;
    protected Shape brickFace;

    private final Color border;
    private final Color inner;

    private final int fullStrength;
    private int strength;

    private boolean broken;


    public Brick(String name, Point pos, Dimension size, Color border, Color inner, int strength){
        rnd = new Random();
        broken = false;
        this.name = name;

        // Create brick
        brickFace = makeBrickFace(pos, size);

        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
    }

    // Abstract method for creating the brick
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

    public final boolean isBroken(){
        return !broken;
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    public static Random getRnd() {
        return rnd;
    }
    public static void setRnd(Random rnd) {
        Brick.rnd = rnd;
    }
    public Shape getBrickFace(){
        return brickFace;
    }
    public void setBrickFace(Shape brickFace) {
        this.brickFace = brickFace;
    }
}





