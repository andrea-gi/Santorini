package it.polimi.ingsw.PSP034.messages.setupPhase;

import java.util.ArrayList;

/**
 * Message from the server to the client that requests to the player the personal god choice.
 */
public class RequestPersonalGod extends SetupRequest{
    static final long serialVersionUID = 102272495201L;

    private final String[] possibleGods;
    private final String[] alreadyChosenGods;

    /**
     * Initializes the message.
     *
     * @param possibleGods List of possible available gods to pick from.
     * @param alreadyChosenGods List of gods already chosen by other players.
     */
    public RequestPersonalGod(ArrayList<String> possibleGods, ArrayList<String> alreadyChosenGods){
        this.possibleGods = possibleGods.toArray(new String[0]);
        this.alreadyChosenGods = alreadyChosenGods.toArray(new String [0]);
    }

    public String[] getPossibleGods() {
        return possibleGods;
    }

    public String[] getAlreadyChosenGods() {
        return alreadyChosenGods;
    }
}
