package indy;

import javafx.scene.image.ImageView;

/**
 * This is the Direction enum, telling the tiles and bloons where to go
 */
public enum Direction {
    LEFT, RIGHT, UP, DOWN, NONE;
    public Direction nextDirection(){
        int randInt =(int) (Math.random() * 3);
        switch(this){
            case LEFT:
                switch(randInt){
                    case 0: return UP;
                    case 1: return DOWN;
                    case 2: return LEFT;
                }
            case RIGHT:
                switch(randInt){
                    case 0: return UP;
                    case 1: return DOWN;
                    case 2: return RIGHT;
                }
            case UP:
                switch(randInt){
                    case 0: return UP;
                    case 1: return RIGHT;
                    case 2: return LEFT;
                }
            case DOWN:
                switch(randInt){
                    case 0: return DOWN;
                    case 1: return RIGHT;
                    case 2: return LEFT;
                }
        }
        return NONE;
    }

    /**
     * checks if the tile at the predicted next position works
     */
    public boolean nextWorks(int row, int col, Board board){
        switch(this){
            case LEFT:
                if(col <= 0) return false;
                return (board.checkValid(row, col-1));
            case UP:
                if(row <= 0) return false;
                return (board.checkValid(row-1, col));
            case DOWN:
                return board.checkValid(row + 1, col);
            case RIGHT:
                return board.checkValid(row, col+1);
        }
        return true;
    }

    /**
     * returns the next rows and cols
     */
    public int nextRow(int row){
        switch(this){
            case UP: return row-1;
            case DOWN: return row+1;
            default: return row;
        }
    }
    public int nextCol(int col){
        switch(this){
            case LEFT: return col-1;
            case RIGHT: return col+1;
            default: return col;
        }
    }
    public boolean isVertical(){
        return (this == UP || this == DOWN);
    }

    /**
     * returns the tile image given the direction the tile leads to and the direction the tile came from
     */
    public ImageView getIcon(Direction prev){
        switch(this){
            case UP:
                if(prev == Direction.LEFT) return new ImageView(Constants.UP_RIGHT_PATH);
                if(prev == Direction.RIGHT) return new ImageView(Constants.UP_LEFT_PATH);
                return new ImageView(Constants.VERTICAL_PATH);
            case DOWN:
                if(prev == Direction.LEFT) return new ImageView(Constants.DOWN_RIGHT_PATH);
                if(prev == Direction.RIGHT) return new ImageView(Constants.DOWN_LEFT_PATH);
                return new ImageView(Constants.VERTICAL_PATH);
            case LEFT:
                if(prev == Direction.UP) return new ImageView(Constants.DOWN_LEFT_PATH);
                if(prev == Direction.DOWN) return new ImageView(Constants.UP_LEFT_PATH);
                return new ImageView(Constants.HORIZONTAL_PATH);
            case RIGHT:
                if(prev == Direction.UP) return new ImageView(Constants.DOWN_RIGHT_PATH);
                if(prev == Direction.DOWN) return new ImageView(Constants.UP_RIGHT_PATH);
                return new ImageView(Constants.HORIZONTAL_PATH);
        }
        return new ImageView(Constants.GRASS_PATH);
    }
}
