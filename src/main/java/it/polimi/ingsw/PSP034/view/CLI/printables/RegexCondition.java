package it.polimi.ingsw.PSP034.view.CLI.printables;

/**
 * This class is used by the TextBox class to check that an input is valid.
 */
public class RegexCondition {
    private final String regex;
    private final String errorMessage;

    /**
     * Creates the regex and the associated error message.
     * @param regex Regex that will be checked.
     * @param errorMessage Message that will be shown if the string does not match the regex.
     */
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
