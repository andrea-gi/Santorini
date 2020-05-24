package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

public class RequestNameColor extends RequestServerConfig{
    static final long serialVersionUID = 67577775623L;

    private final String[] alreadyChosenNames;
    private final PlayerColor[] availableColors;
    private final PlayerColor[] alreadyChosenColors;

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
