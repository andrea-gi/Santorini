package it.polimi.ingsw.PSP034.constants;

import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stores the colors associated to the players and their workers.
 */
public enum PlayerColor {
    RED(ANSI.FG_red, ANSI.BG_red),
    BLUE(ANSI.FG_blue, ANSI.BG_blue),
    MAGENTA(ANSI.FG_magenta, ANSI.BG_magenta);

    private final String FG_color;
    private final String BG_color;

    PlayerColor(String FG_color, String BG_color){
        this.FG_color = FG_color;
        this.BG_color = BG_color;
    }

    public String getFG_color() {
        return FG_color;
    }

    public String getBG_color() {
        return BG_color;
    }

    /**
     * Calculates the remaining possible colors to choose.
     * @param args Colors already chosen.
     * @return Available colors to choose.
     */
    public static PlayerColor[] getRemainingColors(PlayerColor... args){
        PlayerColor[] remainingColors = new PlayerColor[3 - args.length];
        ArrayList<PlayerColor> everyColor = new ArrayList<>(Arrays.asList(PlayerColor.RED, PlayerColor.BLUE, PlayerColor.MAGENTA));
        for(PlayerColor color : args){
            everyColor.remove(color);
        }
        for(int i = 0; i < remainingColors.length; i++){
            remainingColors[i] = everyColor.get(i);
        }
        return remainingColors;
    }
}
