package it.polimi.ingsw.PSP034.messages.PlayPhase;

public class AnswerBooleanChoice extends PlayAnswer {
    private final boolean choice;

    public AnswerBooleanChoice(boolean choice){
        this.choice = choice;
    }

    public boolean getChoice() {
        return choice;
    }
}
