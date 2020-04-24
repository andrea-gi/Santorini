package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.messages.Answer;

public class AnswerServerConfig extends Answer {
    private int playerNumber;
    private String name;
    private Color color;

    public AnswerServerConfig(int number){
        this.playerNumber = number;
    }

    public AnswerServerConfig(String name){
        this.name = name;
    }

    public AnswerServerConfig(Color color) {
        this.color = color;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
