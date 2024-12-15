package indy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the App class that sets the scene for my Indy:
 * BLOONS TOWER DEFENSE (but really bad and turned in late ;( )
 */

public class App extends Application {
    @Override
    public void start(Stage stage) {
        PaneOrganizer paneOrganizer = new PaneOrganizer();
        Scene scene = new Scene(paneOrganizer.getRoot(), Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage.setTitle("BLOONS TOWER DEFENSE");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args); // launch is a method inherited from Application
    }
}
