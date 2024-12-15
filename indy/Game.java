package indy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the Game class, the top level logic class. It handles the implementation of
 * mouse events and things of that nature. It runs multiple timelines for the game to run
 * under different scenarios like inRound and spawning bloons in the next round.
 * It keeps tracks of money, lives, round, the bloons in the round, the darts in the round,
 * and the monkeys. It also has the button to pick a base monkey, of which there is only one
 * even though I have multiple types of monkeys.
 */
public class Game {
    private Board board;
    private Pane boardPane;
    private ArrayList<Bloon> wave;
    private ArrayList<Monkey> monkeys;
    private ArrayList<Dart> darts;
    private HashSet<Dart> seenDarts;
    private boolean playing;
    private boolean fast;
    private Button start;
    private int lives;
    private int rounds;
    private Timeline timeline;
    private Timeline checkTimeline;
    private Timeline startTimeline;
    private Label liveLabel;
    private HBox monkeyPane;
    private Label moneyLabel;
    private int money;
    private MonkeySelector selectedType;
    private boolean gameEnd;

    /**
     * This is the constructor for the game class, intilaizing a bunch of stuff and setting the button
     * actions.
     */
    public Game(Pane boardPane, Button start, Button fastForward, Button restart, Button quit, Label liveLabel, Label roundLabel, Label moneyLabel, HBox monkeyPane, VBox controlPane, BloonsButton buy) {
        this.selectedType = MonkeySelector.NONE;
        this.lives = Constants.LIVES;
        this.money = Constants.INITIAL_MONEY;
        this.rounds = 0;
        this.monkeyPane = monkeyPane;
        this.liveLabel = liveLabel;
        this.moneyLabel = moneyLabel;
        this.boardPane = boardPane;
        this.board = new Board(boardPane, this);
        this.wave = new ArrayList<>();
        this.monkeys = new ArrayList<>();
        this.darts = new ArrayList<>();
        this.seenDarts = new HashSet<>();
        this.playing = false;
        this.fast = false;
        this.gameEnd = false;
        this.start = start;
        start.setOnAction((ActionEvent e) -> this.startRound(roundLabel));
        start.setFocusTraversable(false);
        fastForward.setOnAction((ActionEvent e) -> this.fastForward(fastForward));
        fastForward.setFocusTraversable(false);
        restart.setOnAction((ActionEvent e) -> this.restart(roundLabel, buy));
        restart.setFocusTraversable(false);
        quit.setOnAction((ActionEvent e) -> {System.exit(0);});
        quit.setFocusTraversable(false);
        this.boardPane.setOnMouseClicked((MouseEvent e) -> this.setUpClick(e, buy));
        controlPane.setOnMouseClicked((MouseEvent e) -> this.setUpControl(e, buy));

        this.setTimeline();
        this.setCheckTimeline();
    }

    /**
     * Initalizes the timeline for that runs to check money stuff between monkey and game
     */
    private void setCheckTimeline() {
        this.checkTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), (ActionEvent e) -> this.checkTimeline()));
        this.checkTimeline.setCycleCount(Timeline.INDEFINITE);
        this.checkTimeline.play();
    }

    /**
     * Checks and updates money stuff for the monkeys as well as updates the list if the
     * upgrade for the monkey led to a subclass of monkey being made
     */
    private void checkTimeline() {
        for(int i = 0; i < this.monkeys.size(); i++) {
            Monkey monkey = this.monkeys.get(i);
            if(monkey.hasUpgraded()){
                this.setMoney(-monkey.cost());
                monkey.setUpgraded(false);
                Monkey next = monkey.getReplacement();
                if(next != null){
                    this.monkeys.add(next);
                    this.monkeys.remove(i);
                    i--;
                }
            }
            if(monkey.isSold()){
                this.board.getTile((monkey.getLoc()[1] - Constants.BLOON_OFFSET)/Constants.TILE_SIZE,
                        (monkey.getLoc()[0] - Constants.BLOON_OFFSET)/Constants.TILE_SIZE).unsell();
                this.setMoney(monkey.getValue());
                this.monkeys.remove(i);
                i--;
            }
            monkey.updateMoney(this.money);
        }
    }

    /**
     * Initalizes the timeline that runs for the round.
     */
    private void setTimeline(){
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(Constants.FRAME_DUR),
                (ActionEvent e) -> this.updateTimeline()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * This is the updateTimeline method, which runs every
     * keyframe, calling the inRound method, and checking for
     * when rounds end
     */
    private void updateTimeline(){
        this.inRound();
        if(this.playing && this.wave.isEmpty()){
            for(int i = 0; i < this.darts.size(); i++){
                this.darts.get(i).remove();
                this.darts.remove(i);
                i--;
            }
            this.setMoney(99 + this.rounds);
            this.start.setDisable(false);
            this.playing = false;
            this.timeline.stop();
        }
    }

    /**
     * This is the startRound method, which calculates the random number of bloons each
     * round, that grows logarithmically based on the rounds.
     */
    private void startRound(Label roundLabel){
        this.rounds++;
        roundLabel.setText("Round: " + this.rounds);
        this.start.setDisable(true);
        //calculate min and max number of bloons
        double min = Math.ceil(3 * Math.sqrt(this.rounds) + 4);
        double max = Math.ceil(4 * Math.sqrt(this.rounds) + 7);
        int numBloons = (int) (Math.random() * (max - min) + min);
        this.timeline.play();
        this.startTimeline = new Timeline(new KeyFrame(Duration.seconds(Constants.BLOON_SPREAD), (ActionEvent e) -> {
            this.makeBloon();
        }));
        this.startTimeline.setCycleCount(numBloons);
        this.startTimeline.play();
        if(this.fast) this.startTimeline.setRate(Constants.FAST_TIME);
        this.startTimeline.setOnFinished((ActionEvent e) -> {
            this.playing = true;
        });
    }

    /**
     * This is the inRound method which goes through and checks all the darts for movement and
     * collision with all the bloons, goes through all the bloons for movement, checking
     * for if its offscreen, and if its popped, and goes through all the monkeys to add new
     * darts and check if the bloons are in range of the monkeys.
     */
    private void inRound(){
        this.checkDarts();
        this.checkBloons();
        this.checkMonkeys();
    }

    /**
     * This is the checkDarts helper method, which iterates through all the darts in
     * the game, checking if they need to be removed or not, and checking for intersections
     * with the bloons.
     */
    private void checkDarts(){
        for(int i = 0; i < this.darts.size(); i++){
            Dart lookingAt = this.darts.get(i);
            lookingAt.move();
            if(lookingAt.isGone()){
                this.darts.remove(i);
                i--;
                continue;
            }
            for(Bloon bloon : this.wave){
                if(lookingAt.intersects(bloon)){
                    bloon.pop(lookingAt.getPop());
                    if(bloon.getNext() != null && !bloon.getNext().isEmpty()) lookingAt.addToSet(bloon.getNext());
                    if(lookingAt.isGone()) {
                        this.darts.addAll(lookingAt.getNewDarts());
                        this.darts.remove(lookingAt);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    /**
     * This is the checkBloons helper methods, which iterates through all the bloons
     * to see if they have gone offscreen or has been popped, in both cases they are logically
     * removed from the list, and either decrementing lives or adding all of the children
     * bloons to the list.
     */
    private void checkBloons(){
        for(int i = 0; i < this.wave.size(); i++){
            Bloon lookingAt = this.wave.get(i);
            if(lookingAt == null){
                continue;
            }

            //check to delete stuff and decrement lives
            if(lookingAt.getIsOffScreen()){
                lookingAt.remove();
                this.wave.remove(i);
                this.lives -= lookingAt.getDamage();
                this.liveLabel.setText("Lives: " + this.lives);
                if(this.lives <= 0) this.gameOver();
                i--;
            }

            //check to see if the bloon was popped, adding children bloons to the list
            if(lookingAt.isPopped()){
                if(lookingAt.getNext() != null && !lookingAt.getNext().isEmpty()){
                    this.wave.addAll(lookingAt.getNext());
                }
                this.setMoney(lookingAt.getHealth());
                this.wave.remove(i);
                i--;
            }
            else lookingAt.move();

        }

    }

    /**
     * This is the checkMonkeys helper method, which iterates through all the monkeys
     * and making a hashset of bloons that the monkey sees so that there aren't
     * any duplicates and becuase the order doesn't really matter since the find
     * target method needs to iterate through the set anyway.
     */
    private void checkMonkeys(){
        for(Monkey monkey : this.monkeys){
            HashSet<Bloon> seen = new HashSet<>();
            for(Bloon bloon : this.wave){
                if(monkey.intersects(bloon.getBounds(), bloon.getType())){
                    seen.add(bloon);
                }
            }
            monkey.addToList(seen);
            monkey.action(this.fast);
            if(monkey.getShot() && this.seenDarts.addAll(monkey.getDart())){
                this.darts.addAll(monkey.getDart());
            }
        }
    }

    /**
     * This is the makeBloon method. It creates density functions for each of the bloon types
     * to model the probability of each one spawning as the rounds increase. Each model
     * is a modified bell curve so that each one reaches a horizontal asymptote. However,
     * I did not find a way to do area under / between images without a chain of else-ifs.
     */
    private void makeBloon(){
        double rand = Math.random() * 10;
        double pRed = 9.75 * Math.exp(-(Math.pow((this.rounds-1), 2) * 0.0625)) + .25;
        double pBlue = 8.75 * Math.exp(-(Math.pow((this.rounds-2), 2) * 0.0277777778)) + 1.25;
        double pGreen = 7.5 * Math.exp(-(Math.pow((this.rounds-3), 2) * 0.02040816)) + 2.5;
        double pYellow = 6 * Math.exp(-(Math.pow((this.rounds-10), 2) * 0.015625)) + 4;
        double pPink = 4 * Math.exp(-(Math.pow((this.rounds-15), 2) * 0.0004)) + 6;
        double pBlack = 2 * Math.exp(-(Math.pow((this.rounds-20), 2) * 0.015625)) + 8;
        BloonType type = BloonType.makeType(this.rounds);
        if(rand <= pRed) this.wave.add(new Red(this.boardPane, this.board, type));
        else if(rand <= pBlue) this.wave.add(new Blue(this.boardPane, this.board, type));
        else if(rand <= pGreen) this.wave.add(new Green(this.boardPane, this.board, type));
        else if(rand <= pYellow) this.wave.add(new Yellow(this.boardPane, this.board, type));
        else if(this.rounds <= 10) this.wave.add(new Green(this.boardPane, this.board, type));
        else if(rand <= pPink) this.wave.add(new Pink(this.boardPane, this.board, type));
        else if(this.rounds <= 15) this.wave.add(new Yellow(this.boardPane, this.board, type));
        else if(rand <= pBlack) this.wave.add(new Black(this.boardPane, this.board, BloonType.BLACK));
        else if(this.rounds <= 20) this.wave.add(new Pink(this.boardPane, this.board, type));
        else this.wave.add(new Lead(this.boardPane, this.board, BloonType.LEAD));

    }

    /**
     * This is the placeMonkey method, which puts a monkey down if you have the money and if
     * you have selected a monkey.
     */
    public void placeMonkey(double x, double y){
        if(this.selectedType != MonkeySelector.NONE){
            this.monkeys.add(this.selectedType.getMonkey(this.boardPane, x, y, this.monkeyPane, this.money));
            this.setMoney(-Constants.MONKEY_PRICE);
            this.selectedType = MonkeySelector.NONE;
        }
    }

    /**
     * This is the gameOver method called when you have no lives.
     */
    private void gameOver(){
        this.timeline.stop();
        this.startTimeline.stop();
        this.boardPane.setOnMouseClicked(null);
        this.gameEnd = true;
    }

    /**
     * This is the setUpClick method, which implements the functionality
     * of selecting a monkey on the board
     */
    private void setUpClick(MouseEvent e, BloonsButton buy){
        boolean clicked = false;
        this.selectedType = MonkeySelector.NONE;
        for(Monkey monkey : this.monkeys){
            if(monkey.inBody(e.getX(), e.getY())){
                monkey.onClick();
                clicked = true;
            }
            else{
                monkey.deselect();
                if(!clicked) this.monkeyPane.getChildren().clear();
            }
        }
        buy.deselect();
    }

    /**
     * This is the setUpControl method, which implements the monkey selector button
     * thing.
     */
    private void setUpControl(MouseEvent e, BloonsButton buy){
        this.monkeyPane.getChildren().clear();
        if(this.gameEnd) return;
        for(Monkey monkey : this.monkeys){
            monkey.deselect();
        }
        if(buy.inBounds(e.getX(), e.getY(), this.money) && this.selectedType != buy.getMonkey()){
            this.selectedType = buy.getMonkey();
        }
        else{
            buy.deselect();
            this.selectedType = MonkeySelector.NONE;
        }
    }

    /**
     * This is the restart method, which resets everything
     */
    private void restart(Label roundLabel, BloonsButton buy){
        this.rounds = 0;
        roundLabel.setText("Rounds: " + this.rounds);
        this.lives = Constants.LIVES;
        this.liveLabel.setText("Lives: " + this.lives);
        this.setMoney(Constants.INITIAL_MONEY - this.money);
        this.playing = false;
        if(this.timeline != null) this.timeline.stop();
        if(this.startTimeline != null) this.startTimeline.stop();
        this.start.setDisable(false);
        this.selectedType = MonkeySelector.NONE;
        this.gameEnd = false;
        this.boardPane.setOnMouseClicked((MouseEvent e) -> this.setUpClick(e, buy));
        for(Bloon bloon : this.wave){
            bloon.remove();
        }
        this.wave.clear();
        for(Monkey monkey : this.monkeys){
            monkey.remove();
        }
        this.monkeys.clear();
        for(Dart dart : this.darts){
            dart.remove();
        }
        this.board.restart();
        this.darts.clear();
    }

    /**
     * This is the fastForward method which switches between
     * fast forwarding and slowing back down to normal speed.
     */
    private void fastForward(Button button){
        if(!this.fast){
            if(this.timeline != null) this.timeline.setRate(Constants.FAST_TIME);
            if(this.startTimeline !=null) this.startTimeline.setRate(Constants.FAST_TIME);
            this.fast = true;
            button.setText("Normal Speed");
        }
        else{
            if(this.timeline !=null) this.timeline.setRate(1);
            if(this.startTimeline !=null) this.startTimeline.setRate(1);
            this.fast = false;
            button.setText("Fast Forward");
        }
    }

    /**
     * This is the setMoney helper method, which adds x amount
     * to the money instance variable and updating the label
     * to reflect the change.
     */
    private void setMoney(int adder){
        this.money += adder;
        this.moneyLabel.setText("Money: " + this.money);
    }
}
