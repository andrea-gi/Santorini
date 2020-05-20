package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.Color;

public class RequestNameColor extends RequestServerConfig{
    static final long serialVersionUID = 67577775623L;

    private final String[] alreadyChosenNames;
    private final Color[] availableColors;
    private final Color[] alreadyChosenColors;

    public RequestNameColor(String[] alreadyChosenNames, Color[] availableColors, Color[] alreadyChosenColors){
        super(ServerInfo.REQUEST_NAME_COLOR);
        this.alreadyChosenNames = alreadyChosenNames;
        this.availableColors = availableColors;
        this.alreadyChosenColors = alreadyChosenColors;
    }

    public Color[] getAvailableColors() {
        return availableColors;
    }

    public String[] getAlreadyChosenNames() {
        return alreadyChosenNames;
    }

    public Color[] getAlreadyChosenColors() {
        return alreadyChosenColors;
    }
}
