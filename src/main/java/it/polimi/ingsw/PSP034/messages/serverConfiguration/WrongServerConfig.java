package it.polimi.ingsw.PSP034.messages.serverConfiguration;

public enum WrongServerConfig {
    ALREADY_USED_NAME ("has already been chosen by someone else. Try again!"),
    ALREADY_USED_COLOR ("has already been chosen by someone else. Try again!");

    private final String name;

    WrongServerConfig(String s){
        name = s;
    }

    public String toString(){
        return this.name;
    }
}
