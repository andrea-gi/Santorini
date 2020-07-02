package it.polimi.ingsw.PSP034.constants;

/**
 * Contains all the constants for the game.
 */
public class Constant {
    /**
     * Minimum player number.
     */
    public static final int MINPLAYERS = 2;
    /**
     * Maximum player number.
     */
    public static final int MAXPLAYERS = 3;

    /**
     * Dimension of the Board.
     */
    public static final int DIM = 5;

    /**
     * Ground level integer representation.
     */
    public static final int GROUND = 0;

    /**
     * Level one integer representation.
     */
    public static final int LEVEL_ONE = 1;

    /**
     * Level two integer representation.
     */
    public static final int LEVEL_TWO = 2;

    /**
     * Level three integer representation.
     */
    public static final int LEVEL_THREE = 3;

    /**
     * Maximum name length accepted due to graphical reasons.
     */
    public static final int MAX_NAME_LENGTH = 10;

    /**
     * Period of heartbeat (seconds), used to check if client-server connection is active.
     */
    public static final int HEARTBEAT_PERIOD = 5;
 }
