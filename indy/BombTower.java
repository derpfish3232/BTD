package indy;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the BombTower subclass of monkey, which makes it able to shoot out
 * the frag bombs.
 */
public class BombTower extends Monkey{
    private double x;
    private double y;
    private Pane boardPane;
    public BombTower(Pane boardPane, double x, double y, HBox monkeyPane, int money, double range,
                       double reupTime, Targetting currTarget, TreeNode root, TreeNode left, TreeNode right,
                       double vel, int pierce, int pop, boolean seeCamo, boolean hitLead, boolean fast, int worth) {
        super(boardPane, x, y, monkeyPane, money, range, reupTime, currTarget, root, left, right, vel,
                pierce, pop, seeCamo, hitLead, fast, worth, Constants.BOMB_COLOR);
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
    }

    @Override
    public void shoot(Bloon target){
        ArrayList<Dart> darts = new ArrayList<>();
        darts.add(new FragBomb(this.x, this.y, target.getLoc()[0], target.getLoc()[1], super.getVel(), super.getPierce(), super.getPop(), this.boardPane));
        super.shoot(darts);
    }
}
