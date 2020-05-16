package it.polimi.ingsw.PSP034.messages.playPhase;

public class AnswerBooleanChoice extends PlayAnswer {
    static final long serialVersionUID = 624257738L;

    private final boolean choice;

    public AnswerBooleanChoice(boolean choice){
        this.choice = choice;
    }

    public boolean getChoice() {
        return this.choice;
    }
}
