package indy;

/**
 * This is the Targetting Enum, allowing to implement the different
 * systems smoother and switch between them
 */
public enum Targetting {

    FIRST, LAST, CLOSE, STRONG;
    public Targetting getNext() {
        switch(this){
            case FIRST:
                return LAST;
            case LAST:
                return CLOSE;
            case CLOSE:
                return STRONG;
            case STRONG:
                return FIRST;
            default:
                return FIRST;
        }
    }
    public String getText(){
        switch(this){
            case FIRST:
                return "First";
            case LAST:
                return "Last";
            case CLOSE:
                return "Close";
            case STRONG:
                return "Strong";
            default:
                return "First";
        }
    }
}
