package it.polimi.ingsw.PSP034.view.printables;

import java.util.ArrayList;
import java.util.Arrays;

public class Dialog extends PrintableObject {
    private final String text;
    private final ArrayList<String> options;

    public Dialog(String text, int maxMessageLength, int optionsRows, String... options){
        super();
        this.text = text;
        int maxLength;
        if(maxMessageLength == -1)
            maxLength = text.length();
        else
            maxLength = maxMessageLength;

        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        int maxOptionLength = 0;
        for(String option : this.options){
            if(option.length() > maxOptionLength)
                maxOptionLength = option.length();
        }

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
            constructionArray.add(text.substring(start, end));
            start = end+1;
            end = start + maxLength;
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
            StringBuilder line = new StringBuilder();
            int index = row;
            line.append(index).append(" - ").append(this.options.get(index - 1));
            for(int col = 1; col <= optionColumns; col++){
                index += optionsRows;
                if(index <= this.options.size()){
                    int distance = maxOptionLength - this.options.get(index - optionsRows - 1).length();
                    String spaces = new String(new char[distance]).replace('\u0000', ' ') + "     ";
                    line.append(spaces).append(index).append(" - ").append(this.options.get(index - 1));
                }
            }
            constructionArray.add(line.toString());
        }


        super.setObjectSize(constructionArray.size());
        for(int line = 0; line<constructionArray.size(); line++){
            super.setObjectLine(line, constructionArray.get(line));
        }
    }

    public String getText(){
        return text;
    }

    public String getOption(int optionNumber){
        return options.get(optionNumber-1);
    }
}
