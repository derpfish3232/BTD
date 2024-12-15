package indy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * This is the PaneOrganizer class, the top level graphics class
 */
public class PaneOrganizer {
    private BorderPane root;

    /**
     * Constructor of the PaneOrganizer class, creating like the panes
     * and buttons
     */
    public PaneOrganizer(){
        this.root = new BorderPane();
        Pane boardPane = new Pane();
        boardPane.setPrefSize(Constants.BOARD_WIDTH, Constants.APP_HEIGHT);
        Button start = new Button("Start");
        Button fastForward = new Button("Fast Forward");
        BorderPane sidePane = new BorderPane();
        VBox textPane = new VBox();
        VBox controlPane = new VBox();
        BorderPane bottomPane = new BorderPane();
        HBox monkeyPane = new HBox();
        VBox buttonPane = new VBox();
        HBox smallButtons = new HBox();
        Button restart = new Button("Restart");
        Button quit = new Button("Quit");
        smallButtons.getChildren().addAll(restart, quit);
        monkeyPane.setPrefSize(Constants.BOARD_WIDTH, Constants.BOTTOM_PANE_HEIGHT);
        bottomPane.setStyle(Constants.PANEL_STYLE);
        Label lives = new Label("Lives: 100");
        lives.setFont(Constants.TEXT_FONT);
        Label round = new Label("Round: 0");
        round.setFont(Constants.TEXT_FONT);
        Label money = new Label("Money: 650");
        money.setFont(Constants.TEXT_FONT);
        textPane.getChildren().addAll(lives, round, money);
        buttonPane.getChildren().addAll(start, fastForward);
        buttonPane.setPrefWidth(Constants.SIDE_WIDTH);
        bottomPane.setCenter(monkeyPane);
        bottomPane.setRight(buttonPane);
        controlPane.setPrefWidth(Constants.SIDE_WIDTH);
        sidePane.setTop(textPane);
        sidePane.setCenter(controlPane);
        sidePane.setBottom(smallButtons);
        sidePane.setStyle(Constants.PANEL_STYLE);
        sidePane.setPrefSize(Constants.SIDE_WIDTH, Constants.SIDE_HEIGHT);
        new Game(boardPane, start, fastForward, restart, quit, lives, round, money, monkeyPane, controlPane, new BloonsButton(controlPane));
        this.root.setCenter(boardPane);
        this.root.setBottom(bottomPane);
        this.root.setRight(sidePane);
    }

    /**
     * Getter for the root pane
     */
    public BorderPane getRoot(){
        return this.root;
    }
}
