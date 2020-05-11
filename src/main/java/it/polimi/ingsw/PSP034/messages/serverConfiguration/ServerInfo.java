package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import java.io.Serializable;

public enum ServerInfo implements Serializable {
    REQUEST_NAME_COLOR,
    REQUEST_PLAYER_NUMBER,
    LOBBY, // sent to players joining an already started game
    WELCOME_WAIT, // one player receives REQUEST_NAME_COLOR, the following players receive WELCOME_WAIT
    SUCCESSFULLY_ADDED, // sent to players after setting their name and color
    CARDS_CHOICE_WAIT, // sent to player after card choice
    ALREADY_STARTED // sent to players connected after the game has already started, before being kicked out
}
