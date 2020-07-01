package it.polimi.ingsw.PSP034.view.CLI.printables;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class creates a dialog to interact with the user.
 */
public class Dialog extends Message {
    private final ArrayList<String> options;

    /**
     * Creates the dialog
     * @param text Text of the questions of the dialog.
     * @param maxMessageWidth Maximum length of a line of the question after which a new line is started. If this è parameter is -1 the max length is set to the length of the string.
     * @param optionsRows Number of rows in which the options are organised.
     * @param options List of options that the user can choose.
     */
    public Dialog (String text, int maxMessageWidth, int optionsRows, String... options){
        super(text, maxMessageWidth);

        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        int maxOptionLength = 0;
        for(String option : this.options){
            if(option.length() > maxOptionLength)
                maxOptionLength = option.length();
        }

        String[] message = super.getObject();
        ArrayList<String> constructionArray = new ArrayList<>(Arrays.asList(message));


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

    public String getOption(int optionNumber){
        return options.get(optionNumber-1);
    }
}
