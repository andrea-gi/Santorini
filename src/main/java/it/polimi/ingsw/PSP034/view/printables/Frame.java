package it.polimi.ingsw.PSP034.view.printables;

public class Frame extends PrintableObject{
    public Frame(){
        super.setObjectSize(37);
        super.setObjectLine(0, "__________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        super.setObjectLine(1, " (@^,^,^,^,^,@)                                                                                                                                                                                                    (@^,^,^,^,^,@) ");
        super.setObjectLine(2, "   )`){o}(`(                                                                                                                                                                                                         )`){o}(`(    ");
        super.setObjectLine(3, "   ,`,`,`,`,`                                                                                                                                                                                                        ,`,`,`,`,`   ");
        super.setObjectLine(4, "   ==========                                                                                                                                                                                                        ==========   ");
        for(int line = 5; line < 32; line++){
            super.setObjectLine(line, "    ||||||||                                                                                                                                                                                                          ||||||||    ");
        }
        super.setObjectLine(32, "   ,________,                                                                                                                                                                                                        ,________,   ");
        super.setObjectLine(33, "     )    (                                                                                                                                                                                                            )    (     ");
        super.setObjectLine(34, "   ,       `                                                                                                                                                                                                         ,       `    ");
        super.setObjectLine(35, " _/__________\\_                                                                                                                                                                                                    _/__________\\_ ");
        super.setObjectLine(36, "|______________|__________________________________________________________________________________________________________________________________________________________________________________________________|______________|");
    }
}
