package indy;

import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the TackShooter subclass of Monkey, allowing for it to shoot
 * 8(? i forgot the number but its in constants XD) darts at once.
 */
public class TackShooter extends Monkey{
    private double x;
    private double y;
    private Pane boardPane;
    public TackShooter(Pane boardPane, double x, double y, HBox monkeyPane, int money, double range,
                      double reupTime, Targetting currTarget, TreeNode root, TreeNode left, TreeNode right,
                      double vel, int pierce, int pop, boolean seeCamo, boolean hitLead, boolean fast, int worth) {
        super(boardPane, x, y, monkeyPane, money, range, reupTime, currTarget, root, left, right, vel,
                pierce, pop, seeCamo, hitLead, fast, worth, Constants.TACK_COLOR);
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
    }

    @Override
    public void shoot(Bloon target){
        ArrayList<Dart> darts = new ArrayList<>();
        for(int i = 0; i <= Constants.TACK_AMT; i++){
            darts.add(new Dart(this.x, this.y, target.getLoc()[0], target.getLoc()[1], super.getVel(), super.getPierce(), super.getPop(), i * 2 *  Math.PI / Constants.TACK_AMT, super.getHitLead(), super.getSeeCamo(), Constants.DART_DURATION, this.boardPane));
        }
        super.shoot(darts);
    }
}
