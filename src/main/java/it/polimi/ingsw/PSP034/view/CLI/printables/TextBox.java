package it.polimi.ingsw.PSP034.view.CLI.printables;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class TextBox extends PrintableObject{
    public TextBox(int width){
        super();
        super.setObjectSize(2);
        super.setObjectLine(0, new String(new char[width]).replace('\u0000', ' '));
        super.setObjectLine(1, new String(new char[width]).replace('\u0000', ' '));
    }

    public String waitAnswer(@NotNull RegexCondition...regex){
        ANSI.moveTo(super.getStartLine()+1, super.getStartColumn());
        Scanner scan = new Scanner(System.in);
        boolean incorrectInput;
        String answer;
        int lastErrorLength = 0;
        do {
            incorrectInput = false;
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
        return answer;
    }

    public void waitAnswer(){
        ANSI.moveTo(super.getStartLine()+1, super.getStartColumn());
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}
