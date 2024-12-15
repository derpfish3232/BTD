package indy;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This is the MonkeySelector enum, which I have when I make more types of base monkeys
 * because I did not want to be able to place down the snipers, tack shooter, etc. without
 * upgrades for some reason
 */
public enum MonkeySelector {
    NONE, NORMAL;
    public Monkey getMonkey(Pane boardPane, double x, double y, HBox monkeyPane, int money) {
        switch (this) {
            case NONE:
                return null;
            case NORMAL:
                return new Monkey(boardPane, x, y, monkeyPane, money-Constants.MONKEY_PRICE);
        }
        return null;
    }
}
