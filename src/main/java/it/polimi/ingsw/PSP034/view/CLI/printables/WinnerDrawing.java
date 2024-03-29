package it.polimi.ingsw.PSP034.view.CLI.printables;


import it.polimi.ingsw.PSP034.constants.Colors;
import it.polimi.ingsw.PSP034.constants.PlayerColor;

/**
 * Represents the drawing that has to be displayed to the user he/she wins.
 */
public class WinnerDrawing extends Drawing{
    /**
     * Creates the drawing.
     * @param color Color of the player.
     */
    public WinnerDrawing(PlayerColor color){
        super();
        String buildColor = Colors.BUILDING_BG.get();
        String playerColor = color.getFG_color();
        super.setObjectSize(9);
        super.setObjectLine(0, "   " + playerColor + "\\o/   " + ANSI.reset);
        super.setObjectLine(1, "    " + playerColor + "█    " + ANSI.reset);
        super.setObjectLine(2, "   " + playerColor + "/ \\   " + ANSI.reset);
        super.setObjectLine(3, "  " + buildColor + "█████" + ANSI.reset + "  ");
        super.setObjectLine(4, "  " + buildColor + "█████" + ANSI.reset + "  ");
        super.setObjectLine(5, " " + buildColor + "███████" + ANSI.reset + " ");
        super.setObjectLine(6, " " + buildColor + "███████" + ANSI.reset + " ");
        super.setObjectLine(7, "" + buildColor + "█████████" + ANSI.reset);
        super.setObjectLine(8, "" + buildColor + "█████████" + ANSI.reset);
    }
}

/*
   \o/
    █
   / \
  █████
  █████
 ███████
 ███████
█████████
█████████
*/
