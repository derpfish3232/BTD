package indy;
import javafx.scene.layout.Pane;


/**
 * This is the Green subclass of Bloon, which moves at a speed of 1.8 times the red
 */
public class Green extends Bloon{

    /**
     * This is the constructor for the Green bloon, to be used when first creating it
     */
    public Green(Pane boardPane, Board board, BloonType type) {
        super(boardPane, board,Constants.BLOON_OFFSET, Constants.BLOON_OFFSET, type.getIcon(NormalType.GREEN), Constants.GREEN_HEALTH, Constants.GREEN_HEALTH, Direction.NONE, Direction.NONE, Direction.NONE, 0, type);
    }
    /**
     * This is the second constructor of the Green bloon to be used when coming from a popped bloon
     */
    public Green(Pane boardPane, Board board, double x, double y, int maxHealth, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type) {
        super(boardPane, board, x, y, type.getIcon(NormalType.GREEN), Constants.GREEN_HEALTH, maxHealth, dir, lastHorizDir, lastVertDir, dist, type);
    }

    /**
     * This is the move method, telling the superclass what rate to move at and implementing regen stuff
     */
    @Override
    public void move(){
        super.move(Constants.GREEN_SPEED);
    }

    /**
     * This is the pop method, overriding the superclass's so that the next bloon corresponds correctly
     */
    @Override
    public void pop(int pop){
        super.setNext(NormalType.GREEN.getNext(pop), 1);
        if(pop > 0) super.pop(pop);
        else super.pop();
    }

}
