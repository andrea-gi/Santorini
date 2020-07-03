package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

/**
 * Message from the server to the client that asks to the user to pick a name and a color.
 */
public class RequestNameColor extends RequestServerConfig{
    static final long serialVersionUID = 67577775623L;

    private final String[] alreadyChosenNames;
    private final PlayerColor[] availableColors;
    private final PlayerColor[] alreadyChosenColors;

    /**
     * Initializes the message.
     * @param alreadyChosenNames List of names that have already been chosen by other players (and therefore cannot be used).
     * @param availableColors List of of colors available for the player to choose.
     * @param alreadyChosenColors List of color that have already been chosen by other players (and therefore cannot be used).
     */
    public RequestNameColor(String[] alreadyChosenNames, PlayerColor[] availableColors, PlayerColor[] alreadyChosenColors){
        super(ServerInfo.REQUEST_NAME_COLOR);
        this.alreadyChosenNames = alreadyChosenNames;
        this.availableColors = availableColors;
        this.alreadyChosenColors = alreadyChosenColors;
    }

    public PlayerColor[] getAvailableColors() {
        return availableColors;
    }

    public String[] getAlreadyChosenNames() {
        return alreadyChosenNames;
    }

    public PlayerColor[] getAlreadyChosenColors() {
        return alreadyChosenColors;
    }
}
