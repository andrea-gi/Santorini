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
//  ___________________________________
// (@^,^,^,^,^,@)         (@^,^,^,^,^,@)
//   )`){o}(`(              )`){o}(`(
//   ,`,`,`,`,`             ,`,`,`,`,`
//   ==========             ==========
//    ||||||||    #######    ||||||||
//    ||||||||    #######    ||||||||
//    ||||||||    #######    ||||||||
//    ||||||||    #######    ||||||||
//    ||||||||    #######    ||||||||
//   ,________,             ,________,
//     )    (                 )    (
//   ,       `              ,       `
// _/__________\_         _/__________\_
//|______________|_______|______________|