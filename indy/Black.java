package indy;

import javafx.scene.layout.Pane;


/**
 * This is the Black Bloon, which moves at the same speed as a green but
 * cannot be popped by lead-breaking darts and breaks into two pink bloons
 */
public class Black extends Bloon{
    /**
     * This is the Bloon bloon constructor, just really calling super
     */
    public Black(Pane boardPane, Board board, BloonType type){
        super(boardPane, board, Constants.BLOON_OFFSET, Constants.BLOON_OFFSET, type.getIcon(NormalType.BLACK), Constants.BLACK_HEALTH, Constants.BLACK_HEALTH, Direction.NONE, Direction.NONE, Direction.NONE,0, BloonType.BLACK);
    }
    /**
     * This is the second black bloon constructor for when made from a popped bloon
     */
    public Black(Pane boardPane, Board board, double x, double y, int maxHealth, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type) {
        super(boardPane, board, x, y, type.getIcon(NormalType.BLACK), Constants.BLACK_HEALTH, maxHealth, dir, lastHorizDir, lastVertDir, dist, BloonType.BLACK);
    }

    /**
     * This is the move method, telling the superclass what rate to move at
     */
    @Override
    public void move(){
        super.move(Constants.GREEN_SPEED);
    }

    /**
     * This is the pop method, overriding the superclass's so that the next bloon corresponds correctly
     * because the lead pops into two subbloons.
     */
    @Override
    public void pop(int pop){
        //I DONT KNOW WHAT TO DO HERE IM LIKE ONE BEHIND OR SOMETHING
        super.setNext(NormalType.BLACK.getNext(pop), 2);
        if(pop < 0) super.pop();
        else super.pop(pop);
    }
}
