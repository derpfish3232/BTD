package indy;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * This is the bloonbutton class, which is like a custom button type of thing
 * to handle selection of the monkeys before placing them
 */
public class BloonsButton {
    private Rectangle body;
    private MonkeySelector monkeySelector;


    /**
     * This is the constructor, which makes the composite shape
     */
    public BloonsButton(VBox controlPane){
        Pane root = new Pane();
        root.setPrefSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        this.body = new Rectangle(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        this.body.setFill(Color.GRAY);
        this.body.setStroke(Color.BLACK);
        this.setSelect(MonkeySelector.NORMAL);
        HBox rectBox = new HBox();
        rectBox.setPrefSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        Circle icon = new Circle(Constants.BODY_SIZE);
        icon.setFill(Color.BROWN);
        Label cost = new Label("315");
        rectBox.getChildren().addAll(icon, cost);
        rectBox.setAlignment(Pos.CENTER_LEFT);
        rectBox.setSpacing(Constants.BUTTON_SPACING);
        root.getChildren().addAll(this.body, rectBox);
        controlPane.getChildren().add(root);
    }

    /**
     * Sets the selected monkey enum to the given enum, for when there is multiple monkeys
     */
    public void setSelect(MonkeySelector selected){
        this.monkeySelector = selected;
    }

    /**
     * Gets the type of monkey selected
     */
    public MonkeySelector getMonkey(){
        return this.monkeySelector;
    }

    /**
     * This is the inBounds method, which acts essentially works as
     * the action when the button is clicked
     */
    public boolean inBounds(double x, double y, int money){
        if(this.body.contains(x, y) && money >= Constants.MONKEY_PRICE){
            this.body.setStroke(Color.BLUE);
            return true;
        }
        else {
            this.deselect();
            return false;
        }
    }

    /**
     * this method deselects it visually
     */
    public void deselect(){
        this.body.setStroke(Color.BLACK);
    }
}
