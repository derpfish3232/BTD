package indy;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the TripleShot subclass of Monkey, which overrides the shoot method to shoot three darts
 * instead of one
 */
public class TripleShot extends Monkey{
    private double x;
    private double y;
    private Pane boardPane;
    public TripleShot(Pane boardPane, double x, double y, HBox monkeyPane, int money, double range,
                      double reupTime, Targetting currTarget, TreeNode root, TreeNode left, TreeNode right,
                      double vel, int pierce, int pop, boolean seeCamo, boolean hitLead, boolean fast, int worth) {
        super(boardPane, x, y, monkeyPane, money, range, reupTime, currTarget, root, left, right, vel,
                pierce, pop, seeCamo, hitLead, fast, worth, Constants.TRIP_COLOR);
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
    }

    @Override
    public void shoot(Bloon target){
        ArrayList<Dart> darts = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            darts.add(new Dart(this.x, this.y, target.getLoc()[0], target.getLoc()[1], super.getVel(), super.getPierce(), super.getPop(), i * Constants.TRIP_ANGLE, super.getHitLead(), super.getSeeCamo(), Constants.DART_DURATION, this.boardPane));
        }
        super.shoot(darts);
    }
}
