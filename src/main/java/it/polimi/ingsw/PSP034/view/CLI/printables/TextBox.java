package it.polimi.ingsw.PSP034.view.CLI.printables;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

/**
 * This class is responsible for the interaction with the user.
 */
public class TextBox extends PrintableObject{
    /**
     * <pre>
     * Creates the TextBox.
     * NOTE: a TextBox is always two rows high.
     * </pre>
     * @param width Number of columns of the TextBox.
     */
    public TextBox(int width){
        super();
        super.setObjectSize(2);
        super.setObjectLine(0, new String(new char[width]).replace('\u0000', ' '));
        super.setObjectLine(1, new String(new char[width]).replace('\u0000', ' '));
    }

    /**
     * This function waits for an input from the user then checks if the input string matches all the given regex. If it doesn't, an error is shown and the functions waits for another input.
     * @param regex List of RegexConditions that the string must match. The conditions are checked from first to last.
     * @return The first input string that matches all the given regex.
     */
    public String waitAnswer(@NotNull RegexCondition...regex){
        ANSI.moveTo(super.getStartLine()+1, super.getStartColumn());
        Scanner scan = new Scanner(System.in);
        boolean incorrectInput;
        String answer = "";
        int lastErrorLength = 0;
        do {
            incorrectInput = false;
            if(scan.hasNextLine())
                answer = scan.nextLine();
            for (RegexCondition regexCondition : regex) {
                if (!answer.matches(regexCondition.getRegex())) {
                    incorrectInput = true;
                    ANSI.clearLineInterval(super.getStartLine(), super.getStartColumn(), super.getStartColumn()+lastErrorLength);
                    System.out.print(ANSI.FG_red + regexCondition.getErrorMessage() + ANSI.reset);
                    lastErrorLength = regexCondition.getErrorMessage().length();
                    ANSI.clearLineInterval(super.getStartLine() + 1, super.getStartColumn(), super.getStartColumn() + super.getWidth());
                    break;
                }
            }
        }while (incorrectInput);
        ANSI.clearLineInterval(super.getStartLine(), super.getStartColumn(), super.getStartColumn()+lastErrorLength);
        ANSI.moveTo(super.getStartLine(), super.getStartColumn() + answer.length());
        return answer;
    }

    /**
     * This function waits for the user to insert a new line character, ignoring any other previous character.
     */
    public void waitAnswer(){
        ANSI.moveTo(super.getStartLine()+1, super.getStartColumn());
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextLine())
            scan.nextLine();
    }
}
