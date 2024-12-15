package indy;

import javafx.scene.layout.Pane;


/**
 * This is the Lead subclass of Bloon, which moves at the same speed as the red but
 * cannot be popped by all darts and breaks into two black bloons
 */
public class Lead extends Bloon{ ;

    /**
     * This is the Lead bloon constructor, just really calling super
     */
    public Lead(Pane boardPane, Board board, BloonType type){
        super(boardPane, board, Constants.BLOON_OFFSET, Constants.BLOON_OFFSET, type.getIcon(NormalType.LEAD), Constants.LEAD_HEALTH, Constants.LEAD_HEALTH, Direction.NONE, Direction.NONE, Direction.NONE, 0, type);
    }


    /**
     * This is the pop method, overriding the superclass's so that the next bloon corresponds correctly
     * because the lead pops into two black bloons.
     */
    @Override
    public void pop(int pop){
        //I DONT KNOW WHAT TO DO HERE IM LIKE ONE BEHIND OR SOMETHING
        super.setNext(NormalType.LEAD.getNext(pop), 2);
        if(pop < 0) super.pop();
        else super.pop(pop);
    }
}
