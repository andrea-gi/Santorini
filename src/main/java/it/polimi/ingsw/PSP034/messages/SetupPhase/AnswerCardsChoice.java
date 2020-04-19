package it.polimi.ingsw.PSP034.messages.SetupPhase;

public class AnswerCardsChoice extends SetupAnswer{
    private final String[] choice;

    public AnswerCardsChoice(String[] choice){
        this.choice = choice;
    }

    public String[] getChoice() {
        return choice;
    }
}
