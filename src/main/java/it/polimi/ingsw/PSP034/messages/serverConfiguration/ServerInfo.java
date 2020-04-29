package it.polimi.ingsw.PSP034.messages.serverConfiguration;

public enum ServerInfo {
    REQUEST_NAME_COLOR,
    REQUEST_PLAYER_NUMBER,
    LOBBY, // sent to players joining an already started game
    WELCOME_WAIT, // one player receives REQUEST_NAME_COLOR, the following players receive WELCOME_WAIT
    SUCCESSFULLY_ADDED // sent to players after setting their name and color
}
