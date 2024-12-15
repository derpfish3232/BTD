package indy;

import javafx.scene.layout.Pane;


/**
 * This is the Blue subclass of Bloon, which moves at a speed of 1.4 times the red
 */
public class Blue extends Bloon{

    /**
     * This is the constructor for the Blue bloon, to be used when first creating it
     */
    public Blue(Pane boardPane, Board board, BloonType type) {
        super(boardPane, board,Constants.BLOON_OFFSET, Constants.BLOON_OFFSET, type.getIcon(NormalType.BLUE), Constants.BLUE_HEALTH, Constants.BLUE_HEALTH, Direction.NONE, Direction.NONE, Direction.NONE,0, type);
    }

    /**
     * This is the second constructor of the Blue bloon to be used when coming from a popped bloon
     */
    public Blue(Pane boardPane, Board board, double x, double y, int maxHealth, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type) {
        super(boardPane, board, x, y, type.getIcon(NormalType.BLUE), Constants.BLUE_HEALTH, maxHealth, dir, lastHorizDir, lastVertDir, dist, type);
    }

    /**
     * This is the move method, telling the superclass what rate to move at and implementing regen stuff
     */
    @Override
    public void move(){
        super.move(Constants.BLUE_SPEED);
    }

    /**
     * This is the pop method, overriding the superclass's so that the next bloon corresponds correctly
     */
    @Override
    public void pop(int pop){
        super.setNext(NormalType.BLUE.getNext(pop), 1);
        if(pop > 0) super.pop(pop);
        else super.pop();
    }

}
