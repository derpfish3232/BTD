package indy;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the Dart superclass, which is basically a wrapper class for the rectangle but has
 * some other logic other than movement including intersections and special case darts.
 */
public class Dart {
    private Rectangle hitbox;
    private double xVel;
    private double yVel;
    private Pane boardPane;
    private int pierce;
    private int pop;
    private int dist;
    private boolean hitLead;
    private boolean seeCamo;
    private int maxDist;
    private HashSet<Bloon> hit;
    private ArrayList<Dart> newDarts;

    /**
     * This is one of the Dart constructors, which is used normally as it gives a target, and um
     * the constructor itself really just instantiates stuff.
     */
    public Dart(double startX, double startY, double endX, double endY, double vel, int pierce, int pop, double angle, boolean hitLead, boolean seeCamo, int maxDist, Pane boardPane) {
        this.pierce = pierce;
        this.boardPane = boardPane;
        this.hit = new HashSet<>();
        this.pop = pop;
        this.dist = 0;
        this.hitbox = new Rectangle(startX, startY, Constants.DART_SIZE, Constants.DART_SIZE);
        double theta = Math.atan2(endY - startY, endX - startX);
        this.xVel = vel * Math.cos(theta + angle);
        this.yVel = vel * Math.sin(theta + angle);
        this.boardPane.getChildren().add(this.hitbox);
        this.hitLead = hitLead;
        this.seeCamo = seeCamo;
        this.maxDist = maxDist;
        this.newDarts = new ArrayList<>();
    }

    /**
     * This is another constructor for dart with a different signature to make darts
     * that don't have a specific target but rather at an angle. This is used
     * for things like the frag shots of the bomb tower and the tack shooter.
     */
    public Dart(double startX, double startY, double vel, double angle, Pane boardPane) {
        this.pierce = 2;
        this.boardPane = boardPane;
        this.pop = 1;
        this.hit = new HashSet<>();
        this.dist = 0;
        this.hitbox = new Rectangle(startX, startY, Constants.FRAG_SIZE, Constants.FRAG_SIZE);
        this.xVel = vel * Math.cos(angle);
        this.yVel = vel * Math.sin(angle);
        this.boardPane.getChildren().add(this.hitbox);
        this.newDarts = new ArrayList<>();
        this.hitLead = false;
        this.seeCamo = false;
        this.maxDist = Constants.FRAG_DURATION;
    }

    /**
     * This is the move method, which also has a counter for dist so that
     * there's a limit on how far the dart can move.
     */
    public void move(){
        if(this.pierce == 0) return;
        this.hitbox.setX(this.hitbox.getX() + this.xVel * Constants.FRAME_DUR);
        this.hitbox.setY(this.hitbox.getY() + this.yVel * Constants.FRAME_DUR);
        this.dist++;
        if(this.dist >= this.maxDist) this.boardPane.getChildren().remove(this.hitbox);
    }

    /**
     * This is the intersects method, which checks if the dart hits a bloon,
     * constrained by if the dart had already hit it or its parent, if the bloon
     * still exists, and if the bloon can be hit (lead / camo).
     */
    public boolean intersects(Bloon bloon){
        if(this.pierce == 0) return false;
        if(this.hitbox.intersects(bloon.getBounds()) && !this.hit.contains(bloon) && !bloon.isPopped()){
            this.pierce--;
            if(this.extraCheck(bloon)){
                this.pierce++;
                return false;
            }
            if(!bloon.getType().canHit(this.hitLead, this.seeCamo)){
                this.pierce = 0;
                this.remove();
                return false;
            }
            if(this.pierce == 0){
                this.onDeath(this.hitbox.getX(), this.hitbox.getY());
                this.remove();
            }
            return true;
        }
        return false;
    }

    public boolean extraCheck(Bloon bloon){
        return false;
    }

    public void onDeath(double x, double y){}
    /**
     * Getter for if the dart should be removed logically
     */
    public boolean isGone(){
        return this.pierce == 0 || this.dist >= this.maxDist;
    }

    /**
     * Getter for how many layers of bloon to pop
     */
    public int getPop(){
        return this.pop;
    }

    /**
     * Kind of a setter that adds the bloons to the hashset of bloons that
     * the darts shouldn't be able to pop.
     */
    public void addToSet(ArrayList<Bloon> bloons){
        this.hit.addAll(bloons);
    }

    public void resetHit(){
        this.hit.clear();
    }

    /**
     * Method to remove the dart graphically
     */
    public void remove(){
        this.boardPane.getChildren().remove(this.hitbox);
    }

    /**
     * Getter for the darts that this dart spawns when the popped.
     */

    public void setNewDarts(ArrayList<Dart> newDarts){
        this.newDarts = newDarts;
    }

    public ArrayList<Dart> getNewDarts(){
        return this.newDarts;
    }

}
