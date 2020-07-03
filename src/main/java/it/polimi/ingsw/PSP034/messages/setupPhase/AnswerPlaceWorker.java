package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.constants.Sex;

/**
 * Message from the client to the server that communicate the tile where the player wants to place his workers.
 */
public class AnswerPlaceWorker extends SetupAnswer{
    static final long serialVersionUID = 909494736165L;

    private final int x, y;
    private final Sex sex;

    /**
     * Initializes the message.
     *
     * @param sex Worker's to be placed sex.
     * @param x Column coordinate.
     * @param y Row coordinate.
     */
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
