package indy;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * This is the Bloon Superclass, which is basically just a wrapper class with some additional
 * functionality for movement and for when it gets popped.
 */
public class Bloon {

    private Circle balloon;
    private Board board;
    private Pane boardPane;
    private Direction curDir;
    private Direction lastVertDir;
    private Direction lastHorizDir;
    private boolean isOffScreen;
    private ImageView icon;
    private boolean popped;
    private int health;
    private int maxHealth;
    private ArrayList<Bloon> next;
    private double dist;
    private BloonType type;
    private int time;

    /**
     * This is the constructor for the Bloon superclass, mostly initializing stuff
     */
    public Bloon(Pane boardPane, Board board, double x, double y, String path, int health, int maxHealth, Direction dir, Direction lastHorizDir, Direction lastVertDir, double dist, BloonType type) {
        this.boardPane = boardPane;
        this.time = 0;
        this.dist = dist;
        this.board = board;
        this.balloon = new Circle(Constants.BLOON_HITBOX);
        this.health = health;
        this.maxHealth = maxHealth;
        this.popped = false;
        this.type = type;
        this.next = new ArrayList<>();
        this.setImage(path);
        this.setLoc(x, y);
        if(dir != Direction.NONE)this.curDir = dir;
        if(lastHorizDir != Direction.NONE)this.lastHorizDir = lastHorizDir;
        if(lastVertDir != Direction.NONE)this.lastVertDir = lastVertDir;
        this.isOffScreen = false;
    }

    /**
     * This is the setImage method, allowing the bloon to show the spritework rather than just
     * the hitbox
     */
    public void setImage(String path) {
        this.balloon.setFill(Constants.INVISIBLE);
        Image image = new Image(path);
        this.icon = new ImageView(image);
        this.boardPane.getChildren().addAll(this.icon, this.balloon);
    }

    /**
     * This is the pop method, which graphically removes the icon and the hitbox
     * and sets the popped boolean to true so that it can be logically removed.
     * It additionally creates a new PoppedUp to visually show that the bloon was
     * popped
     */
    public void pop(int pop){
        this.popped = true;
        new PoppedUp(this.boardPane, this.icon.getX(), this.icon.getY());
        this.remove();
    }

    /**
     * This is the second pop method, which does the same as the last, but
     * does not create a popup as it is used for regen bloons.
     */
    public void pop(){
        this.popped = true;
        this.remove();
    }

    /**
     * Seperate method signature for move, which allows for polymorphic design.
     */
    public void move(){
        this.move(1);
    }

    /**
     * This is the actual move method, which first gets the boardtile the bloon is at
     * to know what vertical and horizontal direction for computations to use. Then
     * checks if the bloon is offscreen. Then moves the bloon and icon
     */
    public void move(double speed){
        int tileY = (this.curDir == Direction.UP || this.lastVertDir == Direction.UP)
                ? (int) Math.ceil((this.balloon.getCenterY() - Constants.BLOON_OFFSET) / Constants.TILE_SIZE) :
                (int) Math.floor((this.balloon.getCenterY() - Constants.BLOON_OFFSET) / Constants.TILE_SIZE);
        int tileX = (this.curDir == Direction.LEFT || this.lastHorizDir ==Direction.LEFT)
                ? (int)Math.ceil((this.balloon.getCenterX() - Constants.BLOON_OFFSET) / Constants.TILE_SIZE) :
                (int)Math.floor((this.balloon.getCenterX() - Constants.BLOON_OFFSET) / Constants.TILE_SIZE);
        if(tileY > Constants.ROW_I || tileY < 0 || tileX > Constants.COLS_I || tileX < 0){
            if(tileY > Constants.ROW_I) this.isOffScreen = true;
            return;
        }

        //check for regen and its implementation
        this.time++;
        if(this.time == Constants.REGEN_TIME && this.canRegen()){
            this.pop(-1);
            this.time = 0;
        }

        //edge case check (doesn't fully work :( ).
        if(this.curDir != null && this.curDir != this.lastVertDir && this.curDir.isVertical()) this.lastVertDir = this.curDir;
        if(this.curDir != null && this.curDir != this.lastHorizDir && !this.curDir.isVertical()) this.lastHorizDir = this.curDir;
        this.curDir = this.board.getTile(tileY, tileX).getDirection();
        if(this.curDir == Direction.NONE){
            tileY = (int) Math.floor((this.balloon.getCenterY() - Constants.BLOON_OFFSET) / Constants.TILE_SIZE);
            tileX = (int)Math.floor((this.balloon.getCenterX() - Constants.BLOON_OFFSET) / Constants.TILE_SIZE);
            if(tileY > Constants.ROW_I || tileY < 0 || tileX > Constants.COLS_I || tileX < 0){
                return;
            }
            this.curDir = this.board.getTile(tileY, tileX).getDirection();
        }
        switch(this.curDir){
            case UP:
                this.icon.setY(this.icon.getY() - speed);
                this.balloon.setCenterY(this.balloon.getCenterY() - speed);
                break;
            case DOWN:
                this.icon.setY(this.icon.getY() + speed);
                this.balloon.setCenterY(this.balloon.getCenterY() + speed);
                break;
            case LEFT:
                this.icon.setX(this.icon.getX() - speed);
                this.balloon.setCenterX(this.balloon.getCenterX() - speed);
                break;
            case RIGHT:
                this.icon.setX(this.icon.getX() + speed);
                this.balloon.setCenterX(this.balloon.getCenterX() + speed);
                break;
            default:
                break;
        }
        if(this.curDir != Direction.NONE) this.dist += speed;
    }

    /**
     * Setter for the bloon's icon and hitbox such that the bloon icon is offset to be
     * more aligned with the hitbox
     */
    private void setLoc(double x, double y){
        this.icon.setX(x-Constants.BLOON_OFFSET);
        this.icon.setY(y-Constants.BLOON_OFFSET);
        this.balloon.setCenterX(x);
        this.balloon.setCenterY(y);
    }

    /**
     * The following are various getters for functionality in the game
     */
    public double[] getLoc(){
        return new double[]{this.balloon.getCenterX(), this.balloon.getCenterY()};
    }

    public boolean getIsOffScreen() {
        return this.isOffScreen;
    }

    public int getDamage(){
        return this.health;
    }

    /**
     * This is the remove method, which graphically removes the hitbox and the icon
     */
    public void remove(){
        this.boardPane.getChildren().removeAll(this.balloon, this.icon);
    }

    /**
     * This is the setNext method which is used by the overriding classes to
     * set what their children will be
     */
    public void setNext(NormalType nextT, int numChildren){
        ArrayList<Bloon> next = new ArrayList<>();
        if(nextT == null){
            this.pop();
            return;
        }
        Bloon nextB;
        for(int i = 1; i <= numChildren; i++) {
            nextB = nextT.getBloon(this.boardPane, this.board, this.getLoc()[0], this.getLoc()[1], this.getDir(), this.getLastHorizDir(), this.getLastVertDir(), this.getDistTravelled(), this.type, this.maxHealth);
            if(nextB != null) next.add(nextB);
        }
        if(!next.isEmpty()) this.next = next;
    }


    /**
     * Getter for the child bloons
     */
    public ArrayList<Bloon> getNext(){
        return this.next;
    }

    /**
     * The following are more getters
     */
    public boolean isPopped(){
        return this.popped;
    }
    public Direction getDir(){
        return this.curDir;
    }

    public Direction getLastVertDir(){
        return this.lastVertDir;
    }

    public Direction getLastHorizDir(){
        return this.lastHorizDir;
    }

    public BloonType getType(){
        return this.type;
    }

    public Bounds getBounds(){
        return this.balloon.getBoundsInParent();
    }

    public double getDist(double x, double y){
        return Math.hypot(x - this.balloon.getCenterX(), y - this.balloon.getCenterY());
    }

    public double getDistTravelled(){
        return this.dist;
    }

    public int getHealth(){
        return this.health;
    }

    /**
     * helper method for regen bloons to check if they can regen.
     */
    public boolean canRegen(){
        return this.health < this.maxHealth && this.type == BloonType.REGEN;
    }

}
