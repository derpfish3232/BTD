package indy;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the Seeker subclass of Monkey, which is for the Monkey Apprentice
 * and the Glaive Ricochet monkey to shoot out the seeking darts
 */
public class Seeker extends Monkey{
    private double x;
    private double y;
    private Pane boardPane;
    private boolean seeCamo;
    public Seeker(Pane boardPane, double x, double y, HBox monkeyPane, int money, double range,
                  double reupTime, Targetting currTarget, TreeNode root, TreeNode left, TreeNode right,
                  double vel, int pierce, int pop, boolean seeCamo, boolean hitLead, boolean fast, int worth) {
        super(boardPane, x, y, monkeyPane, money, range, reupTime, currTarget, root, left, right, vel,
                pierce, pop, seeCamo, hitLead, fast, worth, Constants.SEEKER_COLOR);
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
        this.seeCamo = seeCamo;
    }

    @Override
    public void shoot(Bloon target){
        ArrayList<Dart> darts = new ArrayList<>();
        darts.add(new Seeking(this.x, this.y, target.getLoc()[0], target.getLoc()[1], super.getVel(), super.getPop(), this.seeCamo,  target, super.getInRange(), this.boardPane));
        super.shoot(darts);
    }
}
