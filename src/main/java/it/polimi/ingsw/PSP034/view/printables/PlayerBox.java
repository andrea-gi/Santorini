package it.polimi.ingsw.PSP034.view.printables;

import it.polimi.ingsw.PSP034.constants.Color;

import java.util.ArrayList;

public class PlayerBox extends PrintableObject{
    private String playerName;
    private String godName;
    private String godPower;
    private Color color;

    private ArrayList<String> constructionArray;

    public PlayerBox(String playerName, String godName, String godPower, Color color){
        super();
        this.playerName = playerName;
        this.godName = godName;
        this.godPower = godPower;
        this. color = color;
        constructionArray = new ArrayList<>();

        constructionArray.add(color.getBG_color()+"╔══════════════════════════════════════╗"+ANSI.reset);
        constructionArray.add(color.getBG_color()+"║"+ANSI.reset+"                                      "+color.getBG_color()+"║"+ANSI.reset+"\033["+((40+playerName.length())/2)+"D"+playerName);
        constructionArray.add(color.getBG_color()+"╠══════════════════════════════════════╣"+ANSI.reset);
        constructionArray.add(color.getBG_color()+"║"+ANSI.reset+"                                      "+color.getBG_color()+"║"+ANSI.reset+"\033["+((40+godName.length())/2)+"D"+godName);
        constructionArray.add(color.getBG_color()+"╟"+ANSI.reset+"──────────────────────────────────────"+color.getBG_color()+"╢"+ANSI.reset);
        int start = 0;
        int end = 36;
        while(end < godPower.length()){
            while(godPower.charAt(end) != ' '){
                end--;
            }
            constructionArray.add(color.getBG_color()+"║"+ANSI.reset+"                                      "+color.getBG_color()+"║"+ANSI.reset+"\033[38D"+godPower.substring(start, end));
            start = end+1;
            end = start + 36;
        }
        if(start <= godPower.length()){
            constructionArray.add(color.getBG_color()+"║"+ANSI.reset+"                                      "+color.getBG_color()+"║"+ANSI.reset+"\033[38D"+godPower.substring(start));
        }
        constructionArray.add(color.getBG_color()+"╚══════════════════════════════════════╝"+ANSI.reset);
        super.setObjectSize(constructionArray.size());
        for(int i = 0; i<constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }
}
