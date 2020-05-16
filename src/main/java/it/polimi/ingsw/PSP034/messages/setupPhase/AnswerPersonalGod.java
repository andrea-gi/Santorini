package it.polimi.ingsw.PSP034.messages.setupPhase;

public class AnswerPersonalGod extends SetupAnswer{
    static final long serialVersionUID = 1765935412417L;

    private final String myGod;

    public AnswerPersonalGod(String myGod){
        this.myGod = myGod;
    }

    public String getMyGod() {
        return myGod;
    }
}
