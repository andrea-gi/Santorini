package it.polimi.ingsw.PSP034.view.printables.godcards;

import it.polimi.ingsw.PSP034.view.printables.ANSI;
import it.polimi.ingsw.PSP034.view.printables.PrintableObject;

import java.util.ArrayList;

public class GodCard extends PrintableObject {
    public GodCard(String godName){
        String godPower = "";
        for(GodDescription god : GodDescription.values()){
            if (godName.equals(god.getName())){
                godPower = god.getPower();
                break;
            }
        }
        ArrayList<String> constructionArray = new ArrayList<>();

        constructionArray.add("╔══════════════════════════════════════╗"+ ANSI.reset);
        constructionArray.add("║"+ANSI.reset+"                                      "+"║"+ANSI.reset+"\033["+((40+godName.length())/2)+"D"+godName);
        constructionArray.add("╠══════════════════════════════════════╣"+ANSI.reset);

        int start = 0;
        int end = 36;
        while(end < godPower.length()){
            while(godPower.charAt(end) != ' '){
                end--;
            }
            constructionArray.add("║                                      ║"+ANSI.reset+"\033[38D"+godPower.substring(start, end));
            start = end+1;
            end = start + 36;
        }
        if(start <= godPower.length()){
            constructionArray.add("║                                      ║"+ANSI.reset+"\033[38D"+godPower.substring(start));
        }
        constructionArray.add("╚══════════════════════════════════════╝");
        super.setObjectSize(constructionArray.size());
        for(int i = 0; i<constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }
}
