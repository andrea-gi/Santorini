package it.polimi.ingsw.PSP034.view.printables;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.view.printables.godcards.GodDescription;

import java.util.ArrayList;

public class PlayerBox extends PrintableObject{
    private String playerName;
    private String godName;
    private String godPower;
    private Color color;

    private ArrayList<String> constructionArray;

    public PlayerBox(String playerName, String godName, Color color){
        super();
        this.playerName = playerName;
        this.godName = godName;
        for(GodDescription god : GodDescription.values()){
            if (godName.equals(god.getName())){
                this.godPower = god.getPower();
                break;
            }
        }
        this.color = color;
        constructionArray = new ArrayList<>();

        String BG_color = this.color != null? color.getBG_color() : ANSI.reset;
        constructionArray.add(BG_color+"╔══════════════════════════════════════╗"+ANSI.reset);
        constructionArray.add(BG_color+"║"+ANSI.reset+"                                      "+BG_color+"║"+ANSI.reset+"\033["+((40+playerName.length())/2)+"D"+playerName);
        constructionArray.add(BG_color+"╠══════════════════════════════════════╣"+ANSI.reset);
        constructionArray.add(BG_color+"║"+ANSI.reset+"                                      "+BG_color+"║"+ANSI.reset+"\033["+((40+godName.length())/2)+"D"+godName);
        constructionArray.add(BG_color+"╟"+ANSI.reset+"──────────────────────────────────────"+BG_color+"╢"+ANSI.reset);
        int start = 0;
        int end = 36;
        while(end < godPower.length()){
            while(godPower.charAt(end) != ' '){
                end--;
            }
            constructionArray.add(BG_color+"║"+ANSI.reset+"                                      "+BG_color+"║"+ANSI.reset+"\033[38D"+godPower.substring(start, end));
            start = end+1;
            end = start + 36;
        }
        if(start <= godPower.length()){
            constructionArray.add(BG_color+"║"+ANSI.reset+"                                      "+BG_color+"║"+ANSI.reset+"\033[38D"+godPower.substring(start));
        }
        constructionArray.add(BG_color+"╚══════════════════════════════════════╝"+ANSI.reset);
        super.setObjectSize(constructionArray.size());
        for(int i = 0; i<constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGodName() {
        return godName;
    }

    public Color getColor() {
        return color;
    }
}
