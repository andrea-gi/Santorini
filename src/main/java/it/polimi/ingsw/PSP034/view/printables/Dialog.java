package it.polimi.ingsw.PSP034.view.printables;

import java.util.ArrayList;
import java.util.Arrays;

public class Dialog extends PrintableObject {
    private String text;
    private int maxLength;
    private ArrayList<String> options;

    public Dialog(String text, int maxMessageLength, int optionsRows, String... options){
        super();
        this.text = text;
        if(maxMessageLength == -1)
            this.maxLength = text.length();
        else
            this.maxLength = maxMessageLength;

        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        int maxOptionLength = 0;
        for(String option : this.options){
            if(option.length() > maxOptionLength)
                maxOptionLength = option.length();
        }

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

        int optionColumns;
        if(this.options.size()%optionsRows > 0)
            optionColumns = this.options.size()/optionsRows + 1;
        else
            optionColumns = this.options.size()/optionsRows;


        for(int row = 1; row <= Math.min(optionsRows, options.length); row++){
            String line = "";
            int index = row;
            line += index + " - " + this.options.get(index - 1);
            for(int col = 1; col <= optionColumns; col++){
                index += optionsRows;
                if(index <= this.options.size()){
                    int distance = maxOptionLength - this.options.get(index - optionsRows - 1).length();
                    String spaces = new String(new char[distance]).replace('\u0000', ' ') + "     ";
                    line += spaces + index + " - " + this.options.get(index - 1);
                }
            }
            constructionArray.add(line);
        }


        super.setObjectSize(constructionArray.size());
        for(int line = 0; line<constructionArray.size(); line++){
            super.setObjectLine(line, constructionArray.get(line));
        }
    }
}
