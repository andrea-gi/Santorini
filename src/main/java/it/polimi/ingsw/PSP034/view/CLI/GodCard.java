package it.polimi.ingsw.PSP034.view.CLI;

import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;
import it.polimi.ingsw.PSP034.view.CLI.printables.PrintableObject;
import it.polimi.ingsw.PSP034.view.GodDescription;

import java.util.ArrayList;

/**
 * Represents a card containing the information about a god.
 */
public class GodCard extends PrintableObject {
    private final String frameColor;
    private final String godName;

    /**
     * Creates a card with default color.
     * @param godName Name of the god to be represented in the card.
     */
    public GodCard(String godName){
        this.godName = godName;
        this.frameColor = ANSI.reset;
        construct();
    }

    /**
     * Creates a card with a coloured frame.
     * @param godName Name of the god to be represented in the card.
     * @param frameColor Color of the frame of the card.
     */
    public GodCard(String godName, String frameColor){
        this.godName = godName;
        this.frameColor = frameColor;
        construct();
    }

    private void construct(){
        String godPower = GodDescription.getPower(godName);

        ArrayList<String> constructionArray = new ArrayList<>();

        constructionArray.add(frameColor + "╔══════════════════════════════════════╗"+ ANSI.reset);
        constructionArray.add(frameColor + "║"+ANSI.reset+"                                      " + frameColor + "║"+ANSI.reset+"\033["+((40+godName.length())/2)+"D"+godName);
        constructionArray.add(frameColor + "╠══════════════════════════════════════╣"+ANSI.reset);

        int start = 0;
        int end = 36;
        while(end < godPower.length()){
            while(godPower.charAt(end) != ' '){
                end--;
            }
            constructionArray.add(frameColor + "║" + ANSI.reset + "                                      " + frameColor + "║"+ANSI.reset+"\033[38D"+godPower.substring(start, end));
            start = end+1;
            end = start + 36;
        }
        if(start <= godPower.length()){
            constructionArray.add(frameColor + "║" + ANSI.reset + "                                      " + frameColor + "║"+ANSI.reset+"\033[38D"+godPower.substring(start));
        }
        constructionArray.add(frameColor + "╚══════════════════════════════════════╝" + ANSI.reset);
        super.setObjectSize(constructionArray.size());
        for(int i = 0; i<constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }
}
