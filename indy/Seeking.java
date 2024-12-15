package indy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the Seeking subclass of dart, which, after popping
 * sends another seeking dart to the next closest bloon
 */
public class Seeking extends Dart{
    private Bloon target;
    private HashSet<Bloon> inRange;
    private double vel;
    private int pop;
    private Pane boardPane;
    private boolean hitLead;

    public Seeking(double startX, double startY, double endX, double endY, double vel, int pop, boolean hitLead, Bloon target, HashSet<Bloon> inRange, Pane boardPane){
        super(startX, startY, endX, endY, vel, 1, pop, 0, hitLead, true, Constants.SNIPER_DURATION, boardPane);
        this.inRange = inRange;
        this.vel = vel;
        this.pop = pop;
        this.target = target;
        this.boardPane = boardPane;
        this.hitLead = hitLead;
    }

    /**
     * Checks if the hit bloon is the targeted one, and dies if not
     */
    @Override
    public boolean extraCheck(Bloon bloon){
        if(bloon != this.target){
            super.remove();
            return true;
        }
        else{
            super.resetHit();
            return false;
        }
    }

    /**
     * Adds the next seeking dart to the list for the superclass to return.
     */
    @Override
    public void onDeath(double x, double y){
        ArrayList<Dart> newDart = new ArrayList<>();
        this.inRange.remove(this.target);
        if(!this.inRange.isEmpty()){
            Bloon nextTarget = this.findNext(x, y);
            newDart.add(new Seeking(x, y, nextTarget.getLoc()[0], nextTarget.getLoc()[1], this.vel, this.pop, this.hitLead, nextTarget, this.inRange, this.boardPane));
            super.setNewDarts(newDart);
        }
    }

    /**
     * This is the findNext helper method to find the next closest Bloon
     * to the dart for the seeking darts.
     */
    private Bloon findNext(double x, double y){
        double minDist = Double.MAX_VALUE;
        Bloon target = null;
        for(Bloon bloon : this.inRange){
            double dist = bloon.getDist(x, y);
            if(dist < minDist){
                minDist = dist;
                target = bloon;
            }
        }
        return target;
    }
}
