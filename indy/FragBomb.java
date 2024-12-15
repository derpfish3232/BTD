package indy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the FragBomb subclass of dart, which spawns a bunch of
 * mini darts when it dies
 */
public class FragBomb extends Dart{
    private double vel;
    private Pane boardPane;
    public FragBomb(double startX, double startY, double endX, double endY, double vel, int pierce, int pop, Pane boardPane) {
        super(startX, startY, endX, endY, vel, pierce, pop, 0, true, false, Constants.DART_DURATION, boardPane);
        this.vel = vel;
        this.boardPane = boardPane;
    }

    @Override
    public void onDeath(double x, double y){
        ArrayList<Dart> newDarts = new ArrayList<>();
        for(int i = 0 ; i < Constants.FRAG_AMT; i++){
            newDarts.add(new Dart(x, y, this.vel, i * 2 * Math.PI/ Constants.FRAG_AMT, this.boardPane));
        }
        super.setNewDarts(newDarts);
    }
}
