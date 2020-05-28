package it.polimi.ingsw.PSP034.view.CLI.printables;

import java.util.ArrayList;

public class ErrorPrompt extends PrintableObject{
    public ErrorPrompt(int errorCode){
        super.setObjectSize(8);
        super.setObjectLine(0, "╔═══════════════════════════════════════════╗");
        super.setObjectLine(1, "║                                           ║");
        super.setObjectLine(2, "║   Sorry, an error occurred and the game   ║");
        super.setObjectLine(3, "║   stopped. Please close di application.   ║");
        super.setObjectLine(4, "║                                           ║");
        super.setObjectLine(5, "║   " + ANSI.FG_rgbColor(255, 0, 0) + "Error code: " + ANSI.reset + errorCode + "                      ║");
        super.setObjectLine(6, "║                                           ║");
        super.setObjectLine(7, "╚═══════════════════════════════════════════╝");
    }
}
