package indy;

/**
 * This is the Upgrade enum used to implement different upgrade effects to a monkey
 */
public enum Upgrade {
    NONE, INCREASE_RANGE, FASTER_SHOOTING, INCREASE_ACCURACY, INCREASE_POP, MULTI_DART, INCREASE_PIERCE, NINJA, SNIPER, TACKSHOOTER, ALLROUNDER, FRAGBOMBER, SUPERMONKEY, APPRENTICE, BOOMERRANG;
    public String upgradeText(){
        switch (this){
            case NONE:
                return "No Available Upgrades";
            case INCREASE_RANGE:
                return "Increase Range";
            case FASTER_SHOOTING:
                return "Faster Shooting";
            case INCREASE_ACCURACY:
                return "Increase Accuracy";
            case INCREASE_POP:
                return "Increase Pop";
            case MULTI_DART:
                return "Multi Dart";
            case INCREASE_PIERCE:
                return "Increase Pierce";
            case NINJA:
                return "Ninja!";
            case SNIPER:
                return "Sniper!";
            case TACKSHOOTER:
                return "Tack Shooter!";
            case ALLROUNDER:
                return "Jack of All!";
            case FRAGBOMBER:
                return "Bomb Tower!";
            case SUPERMONKEY:
                return "Supermonkey!";
            case APPRENTICE:
                return "Apprentice!";
            case BOOMERRANG:
                return "Glaive Thrower!";
            default:
                return "huh";
        }
    }
}
