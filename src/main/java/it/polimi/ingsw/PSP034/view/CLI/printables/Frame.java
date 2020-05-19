package it.polimi.ingsw.PSP034.view.CLI.printables;

public class Frame extends PrintableObject{
    public Frame(){
        super.setObjectSize(50);
        super.setObjectLine(0, "__________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
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
        super.setObjectLine(49, "|______________|__________________________________________________________________________________________________________________________________________________________________________________________________|______________|");
    }
}
