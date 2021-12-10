package model;

import java.awt.*;

/** This class is for constructing in-game levels */
public class Level {

    private static final int LEVELS_COUNT = 6;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private final Brick[][] levels;
    private final Wall wall;
    private int level;

    public Level(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Wall wall) {
        levels = makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
        this.wall = wall;
    }

    /**
     * Generate Level 1 brick only
     * @param drawArea Area that the bricks will be drawn on
     * @param brickCnt Amount of bricks
     * @param lineCnt Number of rows of bricks
     * @param brickSizeRatio A set double value which contains the brick size ratio in comparison to the window
     * @param type Brick type (Clay, Steel or Cement)
     * @return Array of bricks
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type) {
        /*
          If brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
        */
        brickCnt -= brickCnt % lineCnt;

        // Number of bricks per line
        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, Level.CLAY);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, type);
        }
        return tmp;

    }

    /**
     * Generate Level 2, 3, 4, 5 bricks
     * @param drawArea Area that the bricks will be drawn on
     * @param brickCnt Amount of bricks
     * @param lineCnt Number of rows of bricks
     * @param brickSizeRatio A set double value indicating the height to width ratio
     * @param typeA Brick type (Clay, Steel or Cement)
     * @param typeB Brick type (Clay, Steel or Cement)
     * @return Array of bricks
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        // Length of each brick
        double brickLen = drawArea.getWidth() / brickOnLine;
        // Height of the line
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, typeA);
        }

        return tmp;
    }

    /**
     * Call levels after generation
     * @param drawArea Area that the bricks will be drawn on
     * @param brickCount Amount of bricks
     * @param lineCount Number of rows of bricks
     * @param brickDimensionRatio Set of double values
     * @return 2d array linking the level number with the level type
     */
    public Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY);
        tmp[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
        tmp[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, STEEL);
        tmp[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, CEMENT);
        tmp[4] = makeChessboardLevel(drawArea, 40, 4, brickDimensionRatio, CEMENT, STEEL);
        tmp[5] = makeChessboardLevel(drawArea, 50, 5, brickDimensionRatio, STEEL, CEMENT);

        return tmp;
    }

    /** Increase level */
    public void nextLevel() {
        wall.setBricks(levels[level++]);
        wall.setBrickCount(wall.getBricks().length);
    }

    /**
     * Check if the last level has been reached
     * @return Boolean value
     */
    public boolean hasLevel() {
        return level < levels.length;
    }

    /**
     * Creates the different types of bricks
     * @param point Brick position
     * @param size Brick size
     * @param type Brick type (Clay, Steel or Cement)
     * @return Returns the brick
     */
    private Brick makeBrick(Point point, Dimension size, int type) {
        return switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case STEEL -> new SteelBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
    }

}
