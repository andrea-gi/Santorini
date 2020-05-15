package it.polimi.ingsw.PSP034.constants;

import it.polimi.ingsw.PSP034.view.printables.ANSI;

import java.util.ArrayList;
import java.util.Arrays;

public enum Color {
    RED(ANSI.FG_red, ANSI.BG_red),
    BLUE(ANSI.FG_blue, ANSI.BG_blue),
    MAGENTA(ANSI.FG_magenta, ANSI.BG_magenta);

    String FG_color;
    String BG_color;
    Color(String FG_color, String BG_color){
        this.FG_color = FG_color;
        this.BG_color = BG_color;
    }

    public String getFG_color() {
        return FG_color;
    }

    public String getBG_color() {
        return BG_color;
    }

    public static Color[] getRemainingColors(Color... args){
        Color[] remainingColors = new Color[3 - args.length];
        ArrayList<Color> everyColor = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.MAGENTA));
        for(Color color : args){
            everyColor.remove(color);
        }
        for(int i = 0; i < remainingColors.length; i++){
            remainingColors[i] = everyColor.get(i);
        }
        return remainingColors;
    }
}
