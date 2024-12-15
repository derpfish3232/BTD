package indy;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the Sniper subclass of Monkey, which shoots the specific bullet subclass of dart
 */
public class Sniper extends Monkey{
    private double x;
    private double y;
    private Pane boardPane;
    public Sniper(Pane boardPane, double x, double y, HBox monkeyPane, int money, double range,
                     double reupTime, Targetting currTarget, TreeNode root, TreeNode left, TreeNode right,
                     double vel, int pierce, int pop, boolean seeCamo, boolean hitLead, boolean fast, int worth) {
        super(boardPane, x, y, monkeyPane, money, range, reupTime, currTarget, root, left, right, vel,
                pierce, pop, seeCamo, hitLead, fast, worth, Constants.SNIP_COLOR);
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
    }

    @Override
    public void shoot(Bloon target){
        ArrayList<Dart> darts = new ArrayList<>();
        darts.add(new Bullet(this.x, this.y, target.getLoc()[0], target.getLoc()[1], super.getVel(), super.getPierce(), super.getPop(), target, this.boardPane));
        super.shoot(darts);
    }
}
