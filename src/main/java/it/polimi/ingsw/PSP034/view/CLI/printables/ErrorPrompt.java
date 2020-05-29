package it.polimi.ingsw.PSP034.view.CLI.printables;

import java.util.ArrayList;

public class ErrorPrompt extends PrintableObject{

    public ErrorPrompt(String errorCode, String description){
        int borderSpace = 3;
        int maxLength = 37;
        String borderColor = ANSI.FG_rgbColor(255, 0, 0);

        String emptyLine = new String(new char[maxLength + 2* borderSpace]).replace('\u0000', ' ');
        String borderString = new String(new char[borderSpace]).replace('\u0000', ' ');
        ArrayList<String> constructionArray = new ArrayList<>();

        constructionArray.add(borderColor + "╔" + emptyLine.replace(' ', '═') + "╗" + ANSI.reset);
        constructionArray.add(borderColor + "║" + ANSI.reset + emptyLine + borderColor + "║" + ANSI.reset);
        constructionArray.add(borderColor + "║" + ANSI.reset + borderString +  "Sorry, an error occurred and the game   "+ borderColor +"║" + ANSI.reset);
        constructionArray.add(borderColor + "║" + ANSI.reset + borderString +  "stopped. Please close the application.  "+ borderColor +"║" + ANSI.reset);
        constructionArray.add(borderColor + "║" + ANSI.reset + emptyLine + borderColor + "║" + ANSI.reset);
        constructionArray.add(borderColor + "║" + ANSI.reset + emptyLine + borderColor + "║" + ANSI.reset + "\033[" + (1+ maxLength) + "D" + ANSI.FG_rgbColor(255, 0, 0) + "Error code: " + ANSI.reset + errorCode);
        constructionArray.add(borderColor + "║" + ANSI.reset + emptyLine + borderColor + "║" + ANSI.reset);

        int start = 0;
        int end = maxLength;
        while(end < description.length()){
            while(description.charAt(end) != ' '){
                end--;
            }
            constructionArray.add(borderColor + "║" + ANSI.reset + emptyLine + borderColor + "║" + ANSI.reset + "\033[" + (1+ maxLength) + "D" + description.substring(start, end));
            start = end+1;
            end = start + maxLength;
        }
        if(start <= description.length()){
            constructionArray.add(borderColor + "║" + ANSI.reset+ emptyLine + borderColor + "║" + ANSI.reset + "\033[" + (1+ maxLength) + "D" + description.substring(start));
        }
        constructionArray.add(borderColor +"╚" + emptyLine.replace(' ', '═') + "╝" + ANSI.reset);

        super.setObjectSize(constructionArray.size());
        for(int i = 0; i< constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }

    }
}
