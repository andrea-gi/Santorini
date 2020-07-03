package it.polimi.ingsw.PSP034.view.CLI.printables;

/**
 * This class creates and prints the frame of the CLI and also defines the dimension of the screen that will be used.
 */
public class Frame extends PrintableObject{
    /**
     * Number of column the whole CLI screen will use.
     */
    public static final int SCREEN_WIDTH = 229;

    /**
     * Number of lines the whole CLI screen will use.
     */
    public static final int SCREEN_HEIGHT = 50;

    /**
     * Number of column of the part of the screen that can be used to print object.
     */
    public static final int FRAME_WIDTH = 198;

    /**
     * Number of lines of the part of the screen that can be used to print object.
     */
    public static final int FRAME_HEIGHT = 40;

    /**
     * Starting line of the part of the screen that can be used to print object.
     */
    public static final int FRAME_START_LINE = 6;

    /**
     * Starting column of the part of the screen that can be used to print object.
     */
    public static final int FRAME_START_COLUMN = 16;

    /**
     * Creates the frame representing two lateral greek columns in ASCII art.
     */
    public Frame(){
        super.setObjectSize(SCREEN_HEIGHT);

        String emptyFrameLine = new String(new char[FRAME_WIDTH]).replace('\u0000', ' ');

        super.setObjectLine(0, "________________" + emptyFrameLine.replace(' ', '_') + "________________");
        super.setObjectLine(1, " (@^,^,^,^,^,@) " + emptyFrameLine + " (@^,^,^,^,^,@) ");
        super.setObjectLine(2, "   )`){o}(`(    " + emptyFrameLine + "   )`){o}(`(    ");
        super.setObjectLine(3, "   ,`,`,`,`,`   " + emptyFrameLine + "   ,`,`,`,`,`   ");
        super.setObjectLine(4, "   ==========   " + emptyFrameLine + "   ==========   ");

        for(int line = 5; line < FRAME_HEIGHT+5; line++){
            super.setObjectLine(line, "    ||||||||    " + emptyFrameLine + "    ||||||||    ");
        }

        super.setObjectLine(FRAME_HEIGHT+5, "   ,________,   " + emptyFrameLine + "   ,________,   ");
        super.setObjectLine(FRAME_HEIGHT+6, "     )    (     " + emptyFrameLine + "     )    (     ");
        super.setObjectLine(FRAME_HEIGHT+7, "   ,       `    " + emptyFrameLine + "   ,       `    ");
        super.setObjectLine(FRAME_HEIGHT+8, " _/__________\\_ " + emptyFrameLine +" _/__________\\_ ");
        super.setObjectLine(FRAME_HEIGHT+9, "|______________|" + emptyFrameLine.replace(' ', '_') + "|______________|");
    }
}