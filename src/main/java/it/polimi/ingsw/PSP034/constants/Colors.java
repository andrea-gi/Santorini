package it.polimi.ingsw.PSP034.constants;

import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;

public enum Colors {
    SEA_BG(ANSI.BG_blue),
    LIGHT_WAVE_FG(ANSI.FG_bright_white),
    DARK_WAVE_FG(ANSI.FG_cyan),
    GRASS_BG(ANSI.BG_green),
    GRID_FG(ANSI.FG_white),
    BUILDING_BG(ANSI.BG_white),
    BUILDING_NUMBER_FG(ANSI.FG_blue),
    DOME(ANSI.FG_blue);

    String color;

    Colors(String color){
        this.color = color;
    }


    public String get(){
        return color;
    }
}