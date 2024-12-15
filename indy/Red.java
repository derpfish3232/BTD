package indy;

import javafx.scene.layout.Pane;


/**
 * This is the Red subclass of Bloon, which acts as the base of all bloons
 */
public class Red extends Bloon{


    /**
     * This is the constructor of the Red bloon to be used when first making the bloon.
     */
    public Red(Pane boardPane, Board board, BloonType type) {
        super(boardPane, board,Constants.BLOON_OFFSET, Constants.BLOON_OFFSET, type.getIcon(NormalType.RED), 1, 1, Direction.NONE, Direction.NONE, Direction.NONE, 0, type);
    }

    /**
     * This is the second constructor of the Red bloon to be used when making it from
     * a popped bloon
     */
    public Red(Pane boardPane, Board board, double x, double y, int maxHealth, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type) {
        super(boardPane, board, x, y, type.getIcon(NormalType.RED), 1, maxHealth, dir, lastHorizDir, lastVertDir, dist, type);
    }


    /**
     * This is the pop method, overriding the superclass's so that regen works.
     */
    @Override
    public void pop(int pop){
        if(pop > 0) super.pop(pop);
        else{
            super.setNext(NormalType.RED.getNext(pop), 1);
            super.pop();
        }
    }
}
