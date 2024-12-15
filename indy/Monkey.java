package indy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.HashSet;


/**
 * This is the Monkey superclass, which does a lot. It acts as a wrapper class for the body. It
 * has a hitbox to see what Bloons are in range. It also contains the logic for the upgrade
 * pathing, selling, and changing targets. When the monkey can shoot, it does so by spawning
 * darts and then goes back on cooldown before being able to do it again.
 */
public class Monkey{
    private ArrayList<Dart> darts;
    private Rectangle vision;
    private Circle body;
    private double range;
    private double reupTime;
    private Pane boardPane;
    private boolean shot;
    private boolean selected;
    private HashSet<Bloon> inRange;
    private Targetting currTarget;
    private HBox monkeyPane;
    private TreeNode root;
    private TreeNode left;
    private TreeNode right;
    private double vel;
    private int pierce;
    private int pop;
    private boolean seeCamo;
    private boolean hitLead;
    private boolean fast;
    private int gameMoney;
    private int worth;
    private boolean sold;
    private boolean upgraded;
    private Button leftPath;
    private Button rightPath;
    private int cost;
    private Monkey replacement;

    /**
     * This is the constructor for the class, initializing all of the instance variables.
     */
    public Monkey(Pane boardPane, double x, double y, HBox monkeyPane, int money){
        this.gameMoney = money;
        this.reupTime = Constants.INITIAL_COOLDOWN;
        this.pierce = 1;
        this.pop = 1;
        this.worth = (int)(Constants.MONKEY_PRICE * Constants.WORTH_MULTIPLIER);
        this.currTarget = Targetting.FIRST;
        this.monkeyPane = monkeyPane;
        this.selected = false;
        this.shot = false;
        this.range = Constants.BASE_RANGE;
        this.vel = Constants.BASE_VEL;
        this.boardPane = boardPane;
        this.inRange = new HashSet<>();
        this.body = new Circle(x, y, Constants.BODY_SIZE);
        this.body.setFill(Constants.MONKEY_COLOR);
        this.darts = new ArrayList<>();
        this.seeCamo = false;
        this.hitLead = false;
        this.fast = false;
        this.sold = false;
        this.upgraded = false;
        this.leftPath = new Button();
        this.rightPath = new Button();
        this.rangeShower();
        this.setUpgradeTree();
    }

    /**
     * This is the secondary monkey constructor class for the subclasses to give all of the
     * information from the previous monkey that it upgraded from to the superclass
     */
    public Monkey(Pane boardPane, double x, double y, HBox monkeyPane, int money, double range,
                  double reupTime, Targetting currTarget, TreeNode root, TreeNode left, TreeNode right,
                  double vel, int pierce, int pop, boolean seeCamo, boolean hitLead, boolean fast, int worth, Color fill){
        this.boardPane = boardPane;
        this.body = new Circle(x, y, Constants.BODY_SIZE);
        this.body.setFill(fill);
        this.range = range;
        this.reupTime = reupTime;
        this.currTarget = currTarget;
        this.monkeyPane = monkeyPane;
        this.gameMoney = money;
        this.worth = worth;
        this.root = root;
        this.left = left;
        this.right = right;
        this.vel = vel;
        this.pierce = pierce;
        this.pop = pop;
        this.seeCamo = seeCamo;
        this.hitLead = hitLead;
        this.fast = fast;
        this.inRange = new HashSet<>();
        this.sold = false;
        this.upgraded = false;
        this.leftPath = new Button();
        this.rightPath = new Button();
        this.darts = new ArrayList<>();
        this.updatePane();
        this.rangeShower();
    }
    /**
     * This is the action method, which is called every timeline update in game if
     * a round is playing, it finds a target and shoots if possible.
     */
    public void action(boolean gameFast){
        if(gameFast && !this.fast) this.fast = true;
        if(!gameFast && this.fast) this.fast = false;
        Bloon target;
        if(!this.inRange.isEmpty()){
            target = this.findTarget();
            if(!this.shot && target != null) this.shoot(target);
        }
    }

    /**
     * This is a setter method so that the monkey knows how much money the player has.
     */
    public void updateMoney(int newMoney){
        this.gameMoney = newMoney;
        this.checkButton();
    }

    /**
     * This is the intersects method, which is used to check if all the bloons
     * in the game are in the range of the monkey. Additional checks for
     * camo stuff.
     */
    public boolean intersects(Bounds bloon, BloonType type){
        return this.vision.intersects(bloon) && type.canSee(this.seeCamo);
    }

    /**
     * This is a setter, which gives the monkey all the bloons in its vision
     */
    public void addToList(HashSet<Bloon> bloons){
        this.inRange = bloons;
    }

    /**
     * This is the shoot method, which appends the darts to its arraylist of all darts.
     */
    public void shoot(Bloon target){
        this.darts.clear();
        this.darts.add(new Dart(this.body.getCenterX(), this.body.getCenterY(), target.getLoc()[0], target.getLoc()[1], this.vel, this.pierce, this.pop, 0, this.hitLead, this.seeCamo, Constants.DART_DURATION, this.boardPane));
        this.reload();
        this.shot = true;
    }

    /**
     * This is a second shoot method, which is used to implement the different darts
     * that the different subclasses shoot out.
     */
    public void shoot(ArrayList<Dart> darts){
        this.darts = darts;
        this.reload();
        this.shot = true;
    }

    /**
     * This is a getter to return the list of darts shot.
     */
    public ArrayList<Dart> getDart(){
        return this.darts;
    }

    /**
     * This is a getter to know if the monkey can shoot
     */
    public boolean getShot() {
        return this.shot;
    }

    /**
     * This is the reload method, which makes the monkey not able to shoot
     * until the timeline runs, which takes reupTime long to do
     */
    public void reload(){
        if(this.shot) return;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(this.reupTime), (ActionEvent e) -> {
            this.shot = false;
        }));
        timeline.play();
        timeline.setRate(this.fast ? Constants.FAST_TIME : 1);
    }

    /**
     * This is a helper method to switch the current targetting system
     * to the next and shows it on the bottom monkey pane.
     */
    private void switchTarget(Label label){
        this.currTarget = this.currTarget.getNext();
        label.setText(this.currTarget.getText());
    }


    /**
     * This is the onClick method, which is run everytime the monkey's body is clicked.
     * It selects / deselects the monkey, by showing its range and the information
     * contained in the monkey pane
     */
    public void onClick(){
        if(!this.selected){
            this.vision.setFill(Constants.VISION_COLOR);
            this.boardPane.getChildren().add(this.vision);
            this.updatePane();
            this.selected = true;
            return;
        }
        this.deselect();
    }

    /**
     * This is the deselect helper method, which is used to
     * make the monkey no longer selected
     */
    public void deselect(){
        if(this.selected){
            this.boardPane.getChildren().remove(this.vision);
            this.selected = false;
        }
    }

    /**
     * This is the inBody method, which is used to check if the
     * monkey's body was clicked on by checking the cursor
     */
    public boolean inBody(double x, double y){
        return this.body.contains(x,y);
    }

    /**
     * This is the updatePane method, which shows all the information about the monkey,
     * including the current targeting system and a button to switch it,
     * the monkey's sale price and a sell button,the monkey's possible upgrades
     * and their prices.
     */
    private void updatePane(){
        this.monkeyPane.getChildren().clear();
        VBox infoPane = new VBox();
        HBox targetPane = new HBox();
        HBox moneyPane = new HBox();

        //targeting stuff
        Button target= new Button("Change Target");
        Label targetLabel = new Label(this.currTarget.getText());
        target.setOnAction(e -> this.switchTarget(targetLabel));
        target.setFocusTraversable(false);
        targetPane.getChildren().addAll(targetLabel, target);

        //money stuff
        Button sell = new Button("Sell");
        Label sellValue = new Label("Value: " + this.worth);
        sell.setOnAction(e -> this.sell());
        sell.setFocusTraversable(false);
        moneyPane.getChildren().addAll(sellValue, sell);
        infoPane.getChildren().addAll(targetPane, moneyPane);

        //left upgrade path
        VBox leftPane = new VBox();
        this.leftPath = new Button(this.left.getUpgrade().upgradeText());
        this.leftPath.setDisable(this.left.getCost() > this.gameMoney);
        Label cost = new Label("$: " + this.left.getCost());
        leftPane.getChildren().addAll(this.leftPath, cost);

        //right upgrade path
        VBox rightPane = new VBox();
        this.rightPath = new Button(this.right.getUpgrade().upgradeText());
        this.rightPath.setDisable(this.right.getCost() > this.gameMoney);
        Label cost2 = new Label("$: " + this.right.getCost());
        rightPane.getChildren().addAll(this.rightPath, cost2);

        this.leftPath.setOnAction(e -> this.selectUpgrade(this.left, this.left.getUpgrade()));
        this.rightPath.setOnAction(e -> this.selectUpgrade(this.right, this.right.getUpgrade()));

        this.monkeyPane.getChildren().addAll(infoPane, leftPane, rightPane);
    }

    /**
     * This is the findTarget method, which is used to find what bloon to shoot at
     * based on the targeting system - first (the bloon that is in-range and farthest along the track),
     * last (the bloon that is in-range and closest to the entrance), close (the bloon that is
     * mathematically closest to the center of the monkey by distance formula), and strong (the
     * bloon that is the stongest in range) - and if there are ties in closest and strongest, it
     * finds the first of that set.
     */
    private Bloon findTarget(){
        Bloon target = null;
        switch(this.currTarget){
            case FIRST:
               return this.findFirst(this.inRange);
            case LAST:
                double minDist = Double.MAX_VALUE;
                for(Bloon bloon : this.inRange){
                    double dist = bloon.getDistTravelled();
                    if(dist < minDist){
                        target = bloon;
                        minDist = dist;
                    }
                }
                return target;
            case CLOSE:
                double minDistFromMonkey = Double.MAX_VALUE;
                HashSet<Bloon> sameDist = new HashSet<>();
                for(Bloon bloon : this.inRange){
                    double dist = bloon.getDist(this.body.getCenterX(), this.body.getCenterY());
                    if(dist < minDistFromMonkey){
                        sameDist.clear();
                        sameDist.add(bloon);
                        minDistFromMonkey = dist;
                    }
                    else if(dist == minDistFromMonkey) sameDist.add(bloon);
                }
                return this.findFirst(sameDist);
            case STRONG:
                int maxHealth = 0;
                HashSet<Bloon> sameStrength = new HashSet<>();
                for(Bloon bloon : this.inRange){
                    if(bloon.getHealth() > maxHealth){
                        sameStrength.clear();
                        sameStrength.add(bloon);
                        maxHealth = bloon.getHealth();
                    }
                    else if(bloon.getHealth() == maxHealth) sameStrength.add(bloon);
                }
                return this.findFirst(sameStrength);
            default:
                return null;
        }
    }

    /**
     * This is the helper method to find the bloon farthest along the track.
     */
    private Bloon findFirst(HashSet<Bloon> bloonSet){
        Bloon target = null;
        double maxDist = 0;
        for(Bloon bloon : bloonSet){
            double dist = bloon.getDistTravelled();
            if(dist > maxDist){
                target = bloon;
                maxDist = dist;
            }
        }
        return target;
    }

    /**
     * This is the setUpgradeTree method, which hard codes the upgrade
     * path b-tree by using it like a sorted tree and mapping out the values
     * to the locations / positions I wanted them to be in.
     */
    private void setUpgradeTree(){
        this.root = new TakenNode(Upgrade.NONE, Constants.FIRST_POS, 0);
        this.root.insert(Upgrade.INCREASE_RANGE, Constants.RANGE_POS, Constants.RANGE_COST);
        this.root.insert(Upgrade.FASTER_SHOOTING, Constants.SPEED_POS, Constants.SPEED_COST);
        this.root.insert(Upgrade.INCREASE_ACCURACY, Constants.ACCUR_POS, Constants.ACCUR_COST);
        this.root.insert(Upgrade.NINJA, Constants.NINJA_POS, Constants.NINJA_COST);
        this.root.insert(Upgrade.SUPERMONKEY, Constants.SUPER_POS, Constants.SUPER_COST);
        this.root.insert(Upgrade.INCREASE_POP, Constants.POP_POS, Constants.POP_COST);
        this.root.insert(Upgrade.FRAGBOMBER, Constants.BOMB_POS, Constants.BOMB_COST);
        this.root.insert(Upgrade.SNIPER, Constants.SNIP_POS, Constants.SNIP_COST);
        this.root.insert(Upgrade.MULTI_DART, Constants.TRIP_POS, Constants.TRIP_COST);
        this.root.insert(Upgrade.TACKSHOOTER, Constants.TACK_POS, Constants.TACK_COST);
        this.root.insert(Upgrade.ALLROUNDER, Constants.ALLR_POS, Constants.ALLR_COST);
        this.root.insert(Upgrade.INCREASE_PIERCE, Constants.PIERCE_POS, Constants.PIERCE_COST);
        this.root.insert(Upgrade.APPRENTICE, Constants.APPR_POS, Constants.APPR_COST);
        this.root.insert(Upgrade.BOOMERRANG, Constants.BOOM_POS, Constants.BOOM_COST);
        this.root.select();
        this.right = this.root.getRight();
        this.left = this.root.getLeft();
    }

    /**
     * This is the selectUpgrade method, which does the functionality for the different upgrade
     * paths. I would've used some sort of superclass and inheritance deal but since I am mostly
     * just changing the attributes I thought it unnecessary and a hassle rather than a more
     * optimal solution.
     */
    private void selectUpgrade(TreeNode node, Upgrade upgrade){
        this.deselect();
        node.select();
        this.root = node;
        this.left = node.getLeft();
        this.right = node.getRight();
        this.worth += (int)(this.root.getCost() * Constants.WORTH_MULTIPLIER);
        this.cost = this.root.getCost();
        this.upgraded = true;
        this.updatePane();
        switch(upgrade){
            case INCREASE_RANGE:
                this.range *= 1.5;
                this.boardPane.getChildren().removeAll(this.body, this.vision);
                this.rangeShower();
                break;
            case FASTER_SHOOTING:
                this.reupTime -= .2;
                break;
            case INCREASE_ACCURACY:
                this.vel *= 3;
                this.range *= 1.2;
                this.reupTime -= .2;
                this.boardPane.getChildren().removeAll(this.body, this.vision);
                this.rangeShower();
                break;
            case INCREASE_PIERCE:
                this.pierce = 3;
                break;
            case INCREASE_POP:
                this.pop++;
                break;
            case MULTI_DART:
                this.remove();
                this.replacement = new TripleShot(this.boardPane, this.body.getCenterX(), this.body.getCenterY(),
                        this.monkeyPane, this.gameMoney, this.range, this.reupTime, this.currTarget, this.root,
                        this.left, this.right, this.vel, this.pierce, this.pop, this.seeCamo, this.hitLead, this.fast, this.worth);
                break;
            case NINJA:
                this.pop++;
                this.seeCamo = true;
                this.range *= 1.15;
                this.vel *= 1.5;
                this.reupTime -= .125;
                this.boardPane.getChildren().removeAll(this.body, this.vision);
                this.rangeShower();
                break;
            case SNIPER:
                this.pop = 4;
                this.hitLead = true;
                this.vel = 2000;
                this.reupTime += .3;
                this.range = 1500;
                this.remove();
                this.replacement = new Sniper(this.boardPane, this.body.getCenterX(), this.body.getCenterY(),
                        this.monkeyPane, this.gameMoney, this.range, this.reupTime, this.currTarget, this.root,
                        this.left, this.right, this.vel, this.pierce, this.pop, this.seeCamo, this.hitLead, this.fast, this.worth);
                break;
            case TACKSHOOTER:
                this.vel *=1.5;
                this.reupTime -= .2;
                this.pierce = 2;
                this.pop = 2;
                this.remove();
                this.replacement = new TackShooter(this.boardPane, this.body.getCenterX(), this.body.getCenterY(),
                        this.monkeyPane, this.gameMoney, this.range, this.reupTime, this.currTarget, this.root,
                        this.left, this.right, this.vel, this.pierce, this.pop, this.seeCamo, this.hitLead, this.fast, this.worth);
                break;
            case ALLROUNDER:
                this.hitLead = true;
                this.seeCamo = true;
                this.pierce = 2;
                this.reupTime -= .35;
                break;
            case FRAGBOMBER:
                this.hitLead = true;
                this.pop = 3;
                this.reupTime += .15;
                this.remove();
                this.replacement = new BombTower(this.boardPane, this.body.getCenterX(), this.body.getCenterY(),
                        this.monkeyPane, this.gameMoney, this.range, this.reupTime, this.currTarget, this.root,
                        this.left, this.right, this.vel, this.pierce, this.pop, this.seeCamo, this.hitLead, this.fast, this.worth);
                break;
            case SUPERMONKEY:
                this.range *= 2.5;
                this.vel *= 1.5;
                this.reupTime -= .325;
                this.boardPane.getChildren().removeAll(this.body, this.vision);
                this.rangeShower();
                break;
            case APPRENTICE:
                this.range *= 2.5;
                this.vel *= 1.8;
                this.seeCamo = true;
                this.hitLead = true;
                this.pierce = 1;
                this.pop = 1;
                this.remove();
                this.replacement = new Seeker(this.boardPane, this.body.getCenterX(), this.body.getCenterY(),
                        this.monkeyPane, this.gameMoney, this.range, this.reupTime, this.currTarget, this.root,
                        this.left, this.right, this.vel, this.pierce, this.pop, this.seeCamo, this.hitLead, this.fast, this.worth);
                break;
            case BOOMERRANG:
                this.vel *= 1.3;
                this.seeCamo = true;
                this.pop = 2;
                this.pierce = 1;
                this.range *= 1.15;
                this.reupTime  += .1;
                this.remove();
                this.replacement = new Seeker(this.boardPane, this.body.getCenterX(), this.body.getCenterY(),
                        this.monkeyPane, this.gameMoney, this.range, this.reupTime, this.currTarget, this.root,
                        this.left, this.right, this.vel, this.pierce, this.pop, this.seeCamo, this.hitLead, this.fast, this.worth);
                break;
            default:
                break;
        }
    }

    /**
     * This is the rangeShower helper method, which resets how the player sees the monkey
     * after an upgrade that makes its vision wider.
     */
    private void rangeShower(){
        this.vision = new Rectangle(this.body.getCenterX() - this.range/2, this.body.getCenterY() - this.range/2, this.range, this.range);
        this.boardPane.getChildren().remove(this.vision);
        this.boardPane.getChildren().add(this.body);
    }

    /**
     * This is the remove helper method, which graphically gets rid of everything.
     */
    public void remove(){
        this.boardPane.getChildren().removeAll(this.vision, this.body);
        this.monkeyPane.getChildren().clear();
    }

    /**
     * This is the sell method, which removes the monkey and updates the sell boolean,
     * telling the game that it has been sold
     */
    private void sell(){
        this.remove();
        this.sold = true;
    }

    /**
     * The following is a list of getter and setters to make features like buying
     * upgrades and selling monkeys work so that the game has the same amount of
     * money as the monkey
     */
    public int getValue(){
        return this.worth;
    }

    public boolean isSold(){
        return this.sold;
    }

    public boolean hasUpgraded(){
        return this.upgraded;
    }

    public int cost(){
        return this.cost;
    }

    public void setUpgraded(boolean val){
        this.upgraded = val;
    }

    public double getVel(){
        return this.vel;
    }

    public int getPierce(){
        return this.pierce;
    }

    public int getPop(){
        return this.pop;
    }

    public boolean getSeeCamo(){
        return this.seeCamo;
    }

    public boolean getHitLead(){
        return this.hitLead;
    }

    public Monkey getReplacement(){
        return this.replacement;
    }

    public int[] getLoc(){
        return new int[]{(int)this.body.getCenterX(), (int)this.body.getCenterY()};
    }

    public HashSet<Bloon> getInRange(){
        return this.inRange;
    }
    /**
     * This is the checkButton helper method, needing to be called so that
     * the button's functionality is connected to the money in game.
     */
    private void checkButton(){
        this.leftPath.setDisable(this.left.getCost() > this.gameMoney);
        this.rightPath.setDisable(this.right.getCost() > this.gameMoney);
    }
}