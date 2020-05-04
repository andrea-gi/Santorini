package it.polimi.ingsw.PSP034.view.printables;

import java.util.ArrayList;

public class Message extends PrintableObject{
    private String text;
    private int maxLength;

    public Message(String text, int maxLength){
        super();
        this.text = text;
        if(maxLength == -1)
            this.maxLength = text.length();
        else
            this.maxLength = maxLength;


        ArrayList<String> constructionArray = new ArrayList<>();

        int start = 0;
        int end = this.maxLength;
        while(end < text.length()){
            while(text.charAt(end) != ' '  &&  end != 0){
                end--;
            }
            if(end == 0){
                end = start + this.maxLength;
            }
            constructionArray.add(text.substring(start, end));
            start = end+1;
            end = start + this.maxLength;
        }
        if(start <= text.length()){
            constructionArray.add(text.substring(start));
        }



        super.setObjectSize(constructionArray.size());
        for(int i = 0; i<constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }

    public String getText() {
        return text;
    }
}
