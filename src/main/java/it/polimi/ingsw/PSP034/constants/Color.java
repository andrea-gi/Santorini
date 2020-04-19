package it.polimi.ingsw.PSP034.constants;

import it.polimi.ingsw.PSP034.view.ANSI;

public enum Color {GREY(ANSI.FG_black, ANSI.BG_black), BLUE(ANSI.FG_blue, ANSI.BG_blue), WHITE(ANSI.FG_white, ANSI.BG_white);
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
}
