package indy;

import javafx.scene.layout.Pane;

/**
 * This is the bullet subclass of dart, which is spawned by the
 * sniper
 */
public class Bullet extends Dart{
    private Bloon target;
    public Bullet(double startX, double startY, double endX, double endY, double vel, int pierce, int pop, Bloon target, Pane boardPane){
        super(startX, startY, endX, endY, vel, pierce, pop, 0, true, false, Constants.SNIPER_DURATION, boardPane);
        this.target = target;
    }

    /**
     * If the targetted bloon is not the same as the one it hit,
     * it returns true, telling the dart to keep checking through the
     * other bloons without dying.
     */
    @Override
    public boolean extraCheck(Bloon bloon){
        if(bloon != this.target){
            return true;
        }
        else{
            super.resetHit();
            return false;
        }
    }
}
