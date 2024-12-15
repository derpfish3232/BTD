package indy;

import javafx.scene.layout.Pane;

/**
 * This is the normal type enum, which is the layers of bloons.
 */
public enum NormalType {
    LEAD, BLACK, PINK, YELLOW, GREEN, BLUE, RED;

    /**
     * Finds the next bloon based off the pop of the dart that hit it
     */
    public NormalType getNext(int pop){
        switch(this) {
            case LEAD:
                return Constants.BLOONS[Constants.BLACK_IN];
            case BLACK:
                if(Constants.BLACK_IN + pop < Constants.BLOONS.length && Constants.BLACK_IN + pop >= 0) return Constants.BLOONS[Constants.BLACK_IN + pop];
                break;
            case PINK:
                if(Constants.PINK_IN + pop < Constants.BLOONS.length && Constants.PINK_IN + pop >= 0) return Constants.BLOONS[Constants.PINK_IN + pop];
                break;
            case YELLOW:
                if(Constants.YELLOW_IN + pop < Constants.BLOONS.length &&  Constants.YELLOW_IN + pop >= 0) return Constants.BLOONS[Constants.YELLOW_IN + pop];
                break;
            case GREEN:
                if(Constants.GREEN_IN + pop < Constants.BLOONS.length && Constants.GREEN_IN + pop >= 0) return Constants.BLOONS[Constants.GREEN_IN + pop];
                break;
            case BLUE:
                if(Constants.BLUE_IN + pop < Constants.BLOONS.length && Constants.BLUE_IN + pop >= 0) return Constants.BLOONS[Constants.BLUE_IN + pop];
                break;
            case RED:
                if(Constants.RED_IN + pop < Constants.BLOONS.length && Constants.RED_IN + pop >= 0) return Constants.BLOONS[Constants.RED_IN + pop];
                break;
        }
        return null;
    }

    /**
     * returns the bloon
     */
    public Bloon getBloon(Pane boardPane, Board board, double x, double y, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type, int maxHealth){
        switch(this) {
            case BLACK:
                return new Black(boardPane, board, x, y, maxHealth, dir, lastHorizDir, lastVertDir, dist , type);
            case PINK:
                return new Pink(boardPane, board, x, y, maxHealth, dir, lastHorizDir, lastVertDir, dist , type);
            case YELLOW:
                return new Yellow(boardPane, board, x, y, maxHealth, dir, lastHorizDir, lastVertDir, dist , type);
            case GREEN:
                return new Green(boardPane, board, x, y, maxHealth, dir, lastHorizDir, lastVertDir, dist , type);
            case BLUE:
                return new Blue(boardPane, board, x, y, maxHealth, dir, lastHorizDir, lastVertDir, dist , type);
            case RED:
                return new Red(boardPane, board, x, y, maxHealth, dir, lastHorizDir, lastVertDir, dist , type);
        }
        return null;
    }
}
