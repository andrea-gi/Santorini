package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.Color;

public class RequestNameColor extends RequestServerConfig{
    private final String[] alreadyChosenNames;
    private final Color[] availableColors;

    public RequestNameColor(String[] alreadyChosenNames, Color[] availableColors){
        super(ServerInfo.REQUEST_NAME_COLOR);
        this.alreadyChosenNames = alreadyChosenNames;
        this.availableColors = availableColors;
    }

    public Color[] getAvailableColors() {
        return availableColors;
    }

    public String[] getAlreadyChosenNames() {
        return alreadyChosenNames;
    }
}
