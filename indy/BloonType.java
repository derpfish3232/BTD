package indy;

/**
 * This is the BloonType enum for the special types of bloons
 * that have unique stuff when popped and stuff
 */
public enum BloonType {
    NORMAL, REGEN, CAMO, BLACK, LEAD;

    /**
     * returns the correct color, shape, and design of the bloon
     */
    public String getIcon(NormalType base){
        switch(base){
            case RED:
                switch(this){
                    case CAMO: return Constants.RED_C_PATH;
                    case REGEN: return Constants.RED_R_PATH;
                    default: return Constants.RED_PATH;
                }
            case BLUE:
                switch(this){
                    case CAMO: return Constants.BLUE_C_PATH;
                    case REGEN: return Constants.BLUE_R_PATH;
                    default: return Constants.BLUE_PATH;
                }
            case GREEN:
                switch(this){
                    case CAMO: return Constants.GREEN_C_PATH;
                    case REGEN: return Constants.GREEN_R_PATH;
                    default: return Constants.GREEN_PATH;
                }
            case YELLOW:
                switch(this){
                    case CAMO: return Constants.YELLOW_C_PATH;
                    case REGEN: return Constants.YELLOW_R_PATH;
                    default: return Constants.YELLOW_PATH;
                }
            case PINK:
                switch(this){
                    case CAMO: return Constants.PINK_C_PATH;
                    case REGEN: return Constants.PINK_R_PATH;
                    default: return Constants.PINK_PATH;
                }
            case BLACK:
                return Constants.BLACK_PATH;
            case LEAD:
                return Constants.LEAD_PATH;
        }
        return null;
    }

    /**
     * this is the method to make the type, randomized in a similar way to
     * the rounds
     */
    public static BloonType makeType(int round) {
        double rand = Math.random() * 10;
        double pNorm = Math.exp(-((round-17) * .2)) + 5;
        double pCamo = 2 / (1 + Math.exp(-((double)(round - 17) / 5))) + 6;
        if(rand < pNorm) return NORMAL;
        else if(rand < pCamo) return CAMO;
        else return REGEN;
    }

    /**
     * special case for if the monkeys can include them in range
     */
    public boolean canSee(boolean camoDetect){
        if (this == BloonType.CAMO) return camoDetect;
        return true;
    }

    /**
     * Special case for if the monkeys can pop the bloon
     */
    public boolean canHit(boolean leadPop, boolean camoPop){
        switch(this){
            case BLACK:
                return !leadPop;
            case LEAD:
                return leadPop;
            case CAMO:
                return camoPop;
        }
        return true;
    }
}
