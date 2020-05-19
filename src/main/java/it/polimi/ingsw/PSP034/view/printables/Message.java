package it.polimi.ingsw.PSP034.view.printables;

import java.util.ArrayList;

public class Message extends PrintableObject{
    private final String text;

    public Message(String text, int maxMessageWidth){
        super();
        this.text = text;
        int maxLength;
        if(maxMessageWidth == -1)
            maxLength = text.length();
        else
            maxLength = maxMessageWidth;


        ArrayList<String> constructionArray = new ArrayList<>();

        int start = 0;
        int end = maxLength;
        while(end < text.length()){
            while(text.charAt(end) != ' '  &&  end != 0){
                end--;
            }
            if(end == 0){
                end = start + maxLength;
            }
            String extraSpace = new String(new char[maxLength -text.substring(start, end).length()]).replace('\u0000', ' ');
            constructionArray.add(text.substring(start, end) + extraSpace);
            start = end+1;
            end = start + maxLength;
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
