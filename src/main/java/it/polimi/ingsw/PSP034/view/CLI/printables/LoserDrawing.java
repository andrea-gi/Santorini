package it.polimi.ingsw.PSP034.view.CLI.printables;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

/**
 * Represents the drawing that has to be displayed when the first player in a three player game loses.
 */
public class LoserDrawing extends Drawing{
    /**
     * Creates the drawing.
     * @param color The color of the player that lost.
     */
    public LoserDrawing(PlayerColor color){
        super();
        String buildColor = ANSI.FG_white;
        String domeColor = ANSI.FG_blue;
        String playerColor = color.getFG_color();
        super.setObjectSize(7);
        super.setObjectLine(0,"  " + domeColor + "▄███▄       " + playerColor + "o       " + domeColor + "▄███▄  " + ANSI.reset);
        super.setObjectLine(1,"  " + buildColor + "█████      " + playerColor + "/█\\      " + buildColor + "█████  " + ANSI.reset);
        super.setObjectLine(2,"  " + buildColor + "█████      " + playerColor + "/ \\      " + buildColor + "█████  " + ANSI.reset);
        super.setObjectLine(3," " + buildColor + "███████   ███████   ███████ " + ANSI.reset);
        super.setObjectLine(4," " + buildColor + "███████   ███████   ███████ " + ANSI.reset);
        super.setObjectLine(5,"" + buildColor + "█████████ █████████ █████████" + ANSI.reset);
        super.setObjectLine(6,"" + buildColor + "█████████ █████████ █████████" + ANSI.reset);
    }
}
/*
  ▄███▄       o       ▄███▄
  █████      /█\      █████
  █████      / \      █████
 ███████   ███████   ███████
 ███████   ███████   ███████
█████████ █████████ █████████
█████████ █████████ █████████
*/
