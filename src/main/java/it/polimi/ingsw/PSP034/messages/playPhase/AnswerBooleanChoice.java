package it.polimi.ingsw.PSP034.messages.playPhase;

public class AnswerBooleanChoice extends PlayAnswer {
    private final boolean choice;

    public AnswerBooleanChoice(boolean choice){
        this.choice = choice;
    }

    public boolean getChoice() {
        return this.choice;
    }
}
