package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.Color;

public class AnswerNameColor extends AnswerServerConfig{
    private final String name;
    private final Color color;

    public AnswerNameColor(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
