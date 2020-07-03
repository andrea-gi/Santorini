package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

/**
 * Message from the client to the server that communicate the name and the color chosen by a user.
 */
public class AnswerNameColor extends AnswerServerConfig{
    static final long serialVersionUID = 4172596044L;

    private final String name;
    private final PlayerColor color;

    /**
     * initializes the message.
     * @param name Name chosen by the user.
     * @param color Color chosen by the user.
     */
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
