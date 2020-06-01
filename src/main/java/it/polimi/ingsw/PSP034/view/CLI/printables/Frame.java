package it.polimi.ingsw.PSP034.view.CLI.printables;

public class Frame extends PrintableObject{
    public static final int SCREEN_WIDTH = 229;
    public static final int SCREEN_HEIGHT = 50;
    public static final int FRAME_WIDTH = 198;
    public static final int FRAME_HEIGHT = 40;
    public static final int FRAME_START_LINE = 6;
    public static final int FRAME_START_COLUMN = 16;

    public Frame(){
        super.setObjectSize(SCREEN_HEIGHT);


        super.setObjectLine(0, new String(new char[SCREEN_WIDTH]).replace('\u0000', '_'));
        String emptyFrameLine = new String(new char[FRAME_WIDTH]).replace('\u0000', ' ');
        super.setObjectLine(1, " (@^,^,^,^,^,@) " + emptyFrameLine + " (@^,^,^,^,^,@) ");
        super.setObjectLine(2, "   )`){o}(`(    " + emptyFrameLine + "   )`){o}(`(    ");
        super.setObjectLine(3, "   ,`,`,`,`,`   " + emptyFrameLine + "   ,`,`,`,`,`   ");
        super.setObjectLine(4, "   ==========   " + emptyFrameLine + "   ==========   ");
        for(int line = 5; line < FRAME_HEIGHT+5; line++){
            super.setObjectLine(line, "    ||||||||    " + emptyFrameLine + "    ||||||||    ");
        }
        super.setObjectLine(FRAME_HEIGHT+5, "   ,________,  " + emptyFrameLine + "   ,________,  ");
        super.setObjectLine(FRAME_HEIGHT+6, "     )    (    " + emptyFrameLine + "     )    (    ");
        super.setObjectLine(FRAME_HEIGHT+7, "   ,       `   " + emptyFrameLine + "   ,       `   ");
        super.setObjectLine(FRAME_HEIGHT+8, " _/__________\\_" + emptyFrameLine + " _/__________\\_");
        super.setObjectLine(FRAME_HEIGHT+9, "|______________|" + new String(new char[SCREEN_WIDTH-2*("|______________|".length())]).replace('\u0000','_') + "|______________|");



        /*super.setObjectLine(0, "__________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        super.setObjectLine(1, " (@^,^,^,^,^,@)                                                                                                                                                                                                    (@^,^,^,^,^,@) ");
        super.setObjectLine(2, "   )`){o}(`(                                                                                                                                                                                                         )`){o}(`(    ");
        super.setObjectLine(3, "   ,`,`,`,`,`                                                                                                                                                                                                        ,`,`,`,`,`   ");
        super.setObjectLine(4, "   ==========                                                                                                                                                                                                        ==========   ");
        for(int line = 5; line < 45; line++){
            super.setObjectLine(line, "    ||||||||                                                                                                                                                                                                          ||||||||    ");
        }
        super.setObjectLine(45, "   ,________,                                                                                                                                                                                                        ,________,   ");
        super.setObjectLine(46, "     )    (                                                                                                                                                                                                            )    (     ");
        super.setObjectLine(47, "   ,       `                                                                                                                                                                                                         ,       `    ");
        super.setObjectLine(48, " _/__________\\_                                                                                                                                                                                                    _/__________\\_ ");
        super.setObjectLine(49, "|______________|__________________________________________________________________________________________________________________________________________________________________________________________________|______________|");*/
    }
}
