package indy;

import javafx.scene.layout.Pane;


/**
 * This is the Pink subclass of Bloon, which moves at a speed of 3.5 times the red
 */
public class Pink extends Bloon{

    /**
     * This is the constructor for the Pink bloon, to be used when first creating it
     */
    public Pink(Pane boardPane, Board board, BloonType type){
        super(boardPane, board, Constants.BLOON_OFFSET, Constants.BLOON_OFFSET, type.getIcon(NormalType.PINK), Constants.PINK_HEALTH, Constants.PINK_HEALTH, Direction.NONE, Direction.NONE, Direction.NONE, 0, type);
    }

    /**
     * This is the second constructor of the pink bloon to be used when coming from a popped bloon
     */
    public Pink(Pane boardPane, Board board, double x, double y, int maxHealth, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type) {
        super(boardPane, board, x, y, type.getIcon(NormalType.PINK), Constants.PINK_HEALTH, maxHealth, dir, lastHorizDir, lastVertDir, dist, type);
    }

    /**
     * This is the move method, telling the superclass what rate to move at and implementing regen stuff
     */
    @Override
    public void move(){
        super.move(Constants.PINK_SPEED);
    }

    /**
     * This is the pop method, overriding the superclass's so that the next bloon corresponds correctly
     */
    @Override
    public void pop(int pop){
        //I DONT KNOW WHAT TO DO HERE IM LIKE ONE BEHIND OR SOMETHING
        super.setNext(NormalType.PINK.getNext(pop), 1);
        if(pop > 0) super.pop(pop);
        else super.pop();
    }

}
