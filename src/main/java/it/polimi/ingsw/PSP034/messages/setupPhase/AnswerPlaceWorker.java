package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.constants.Sex;

public class AnswerPlaceWorker extends SetupAnswer{
    private final int x, y;
    private final Sex sex;

    public AnswerPlaceWorker(Sex sex, int x, int y){
        this.sex = sex;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sex getSex() {
        return sex;
    }
}
