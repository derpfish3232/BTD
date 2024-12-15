package indy;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * This is the Tile class, a wrapper class for the square/tile image. It contains a directoin
 * to tell the bloon where to go next
 */
public class Tile {
    private Rectangle square;
    private Direction prev;
    private Direction direction;
    private boolean taken;

    /**
     * This is the tile constructor initializing stuff, but not calling anything else
     * to try and cut down on method calls
     */
    public Tile(int row, int col, Direction direction, Direction prev){
        this.direction = direction;
        this.square = new Rectangle(Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.INVISIBLE);
        this.square.setX(Constants.TILE_SIZE * col);
        this.square.setY(Constants.TILE_SIZE * row);
        this.taken  = false;
        this.prev = prev;
    }

    /**
     * This is the makeImage method, which visually puts down the tile's image
     */
    public void makeImage(Pane boardPane){
        ImageView background = new ImageView(Constants.GRASS_PATH);
        background.setX(this.square.getX());
        background.setY(this.square.getY());
        ImageView icon = this.direction.getIcon(this.prev);
        icon.setX(this.square.getX());
        icon.setY(this.square.getY());
        boardPane.getChildren().addAll(background, icon, this.square);
    }

    /**
     * This is the setUpClick helper method, which allows for the tile to be filled with a monkey
     */
    public void setUpClick(Game game){
        if(this.direction != Direction.NONE){
            return;
        }
        this.square.setOnMouseClicked((MouseEvent e) -> this.onClick(game));
        this.square.setFocusTraversable(true);
    }

    /**
     * This is the helper method to place the monkey
     */
    private void onClick(Game game){
        if(this.taken){
            return;
        }
        game.placeMonkey(this.square.getX() + Constants.BLOON_OFFSET, this.square.getY() + Constants.BLOON_OFFSET);
        this.taken = true;
    }

    public void unsell(){
        this.taken = false;
    }

    /**
     * This is a getter for the bloon to know what direction to go in
     */
    public Direction getDirection(){
        return this.direction;
    }
}
