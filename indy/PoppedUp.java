package indy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This is the PoppedUp class, which appears once a bloon has been popped then disappears
 * in place of a popping animation
 */
public class PoppedUp {
    public PoppedUp(Pane BoardPane, double x, double y) {
        ImageView icon = new ImageView(Constants.POP_PATH);
        icon.setX(x);
        icon.setY(y);
        BoardPane.getChildren().add(icon);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(Constants.POP_TIME), (ActionEvent event) -> {BoardPane.getChildren().remove(icon);}));
        timeline.play();
    }
}
