package it.polimi.ingsw.PSP034.view.CLI.printables;

public class RegexCondition {
    private final String regex;
    private final String errorMessage;

    public RegexCondition(String regex, String errorMessage){
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    public String getRegex() {
        return regex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
