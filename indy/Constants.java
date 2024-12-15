package indy;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Constants {
    public static final int APP_WIDTH = 1200;
    public static final int APP_HEIGHT = 675;
    public static final int SIDE_HEIGHT = 600;
    public static final int SIDE_WIDTH = 150;
    public static final int BOTTOM_PANE_HEIGHT = 75;
    public static final int BOARD_WIDTH = 1050;
    public static final int BUTTON_WIDTH = 130;
    public static final int BUTTON_HEIGHT = 60;
    public static final int BUTTON_SPACING = 20;
    public static final Font TEXT_FONT = new Font("Times New Roman", 18);
    public static final String PANEL_STYLE = "-fx-background-color: lightblue;";

    public static final String RED_PATH = "indy/sprites/RedBloon.png";
    public static final String RED_C_PATH = "indy/sprites/RedCamo.png";
    public static final String RED_R_PATH = "indy/sprites/RedRegen.png";
    public static final String BLUE_PATH = "indy/sprites/BlueBloon.png";
    public static final String BLUE_C_PATH = "indy/sprites/BlueCamo.png";
    public static final String BLUE_R_PATH = "indy/sprites/BlueRegen.png";
    public static final String GREEN_PATH = "indy/sprites/GreenBloon.png";
    public static final String GREEN_C_PATH = "indy/sprites/GreenCamo.png";
    public static final String GREEN_R_PATH = "indy/sprites/GreenRegen.png";
    public static final String YELLOW_PATH = "indy/sprites/YellowBloon.png";
    public static final String YELLOW_C_PATH = "indy/sprites/YellowCamo.png";
    public static final String YELLOW_R_PATH = "indy/sprites/YellowRegen.png";
    public static final String PINK_PATH = "indy/sprites/PinkBloon.png";
    public static final String PINK_C_PATH = "indy/sprites/PinkCamo.png";
    public static final String PINK_R_PATH = "indy/sprites/PinkRegen.png";
    public static final String BLACK_PATH = "indy/sprites/BlackBloon.png";
    public static final String LEAD_PATH = "indy/sprites/LeadBloon.png";
    public static final String POP_PATH = "indy/sprites/Pop.png";
    public static final String HORIZONTAL_PATH = "indy/sprites/HorizontalPath.png";
    public static final String VERTICAL_PATH = "indy/sprites/VerticalPath.png";
    public static final String UP_RIGHT_PATH = "indy/sprites/UpRight.png";
    public static final String DOWN_RIGHT_PATH = "indy/sprites/DownRight.png";
    public static final String DOWN_LEFT_PATH = "indy/sprites/LeftDown.png";
    public static final String UP_LEFT_PATH = "indy/sprites/LeftUp.png";
    public static final String GRASS_PATH = "indy/sprites/Grass.png";


    public static final double FAST_TIME = 1.5;
    public static final double FRAME_DUR = .025;
    public static final double BLOON_SPREAD = .8;
    public static final int INITIAL_MONEY = 650;

    public static final NormalType[] BLOONS = {NormalType.LEAD, NormalType.BLACK, NormalType.PINK, NormalType.YELLOW, NormalType.GREEN, NormalType.BLUE, NormalType.RED};
    public static final int LEAD_HEALTH = 23;
    public static final int LEAD_IN = 0;
    public static final int BLACK_HEALTH = 11;
    public static final int BLACK_IN = 1;
    public static final int PINK_HEALTH = 5;
    public static final double PINK_SPEED = 3.5;
    public static final int PINK_IN = 2;
    public static final int YELLOW_HEALTH = 4;
    public static final double YELLOW_SPEED = 3.2;
    public static final int YELLOW_IN = 3;
    public static final int GREEN_HEALTH = 3;
    public static final double GREEN_SPEED = 1.8;
    public static final int GREEN_IN = 4;
    public static final double BLUE_SPEED = 1.4;
    public static final int BLUE_HEALTH = 2;
    public static final int BLUE_IN = 5;
    public static final int RED_IN = 6;
    public static final int REGEN_TIME = 45;
    public static final int BLOON_HITBOX = 20;
    public static final int BLOON_OFFSET = 25;


    public static final Color MONKEY_COLOR = Color.BROWN;
    public static final Color VISION_COLOR = Color.web("gray", .5);
    public static final Color INVISIBLE = Color.TRANSPARENT;


    public static final double INITIAL_COOLDOWN = .85;
    public static final double WORTH_MULTIPLIER = .75;
    public static final int BASE_RANGE = 125;
    public static final int BASE_VEL = 400;
    public static final int BODY_SIZE = 20;
    public static final double TRIP_ANGLE = .43;
    public static final int TACK_AMT = 8;
    public static final int FIRST_POS = 10;
    public static final int RANGE_POS = 5;
    public static final int SPEED_POS = 15;
    public static final int ACCUR_POS = 13;
    public static final int NINJA_POS = 11;
    public static final int SUPER_POS = 14;
    public static final int POP_POS = 7;
    public static final int BOMB_POS = 6;
    public static final int SNIP_POS = 9;
    public static final int TRIP_POS = 3;
    public static final int TACK_POS = 1;
    public static final int ALLR_POS = 4;
    public static final int PIERCE_POS = 17;
    public static final int APPR_POS = 16;
    public static final int BOOM_POS = 18;
    public static final int RANGE_COST = 200;
    public static final int SPEED_COST = 250;
    public static final int ACCUR_COST = 500;
    public static final int NINJA_COST = 1800;
    public static final int SUPER_COST = 2500;
    public static final int POP_COST = 600;
    public static final int BOMB_COST = 1500;
    public static final int SNIP_COST = 1700;
    public static final int TRIP_COST = 700;
    public static final int TACK_COST = 1500;
    public static final int ALLR_COST = 1700;
    public static final int PIERCE_COST = 500;
    public static final int APPR_COST = 1800;
    public static final int BOOM_COST = 1900;

    public static final Color SEEKER_COLOR = Color.BLUEVIOLET;
    public static final Color BOMB_COLOR = Color.GRAY;
    public static final Color SNIP_COLOR = Color.OLIVEDRAB;
    public static final Color TRIP_COLOR = Color.ORANGE;
    public static final Color TACK_COLOR = Color.HOTPINK;

    public static final int FRAG_AMT = 6;
    public static final int DART_SIZE = 10;
    public static final int DART_DURATION = 20;
    public static final int SNIPER_DURATION = 75;
    public static final int FRAG_SIZE = 5;
    public static final int FRAG_DURATION = 10;

    public static final int ROWS = 12;
    public static final int COLS = 21;
    public static final int ROW_I = 11;
    public static final int COLS_I = 20;

    public static final int LIVES = 100;
    public static final int MONKEY_PRICE = 315;
    public static final int TILE_SIZE = 50;
    public static final double POP_TIME = .15;
}
