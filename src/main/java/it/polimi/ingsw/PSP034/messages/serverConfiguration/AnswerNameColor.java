package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

public class AnswerNameColor extends AnswerServerConfig{
    static final long serialVersionUID = 4172596044L;

    private final String name;
    private final PlayerColor color;

    public AnswerNameColor(String name, PlayerColor color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return color;
    }
}
