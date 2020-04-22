package it.polimi.ingsw.PSP034.observer;

import it.polimi.ingsw.PSP034.messages.Answer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServerObservable {
    ArrayList<ServerObserver> serverObservers;

    public ServerObservable(){
        serverObservers = new ArrayList<>();
    }

    public void addObserver(@NotNull ServerObserver observer){
        serverObservers.add(observer);
    }

    public void removeObserver(@NotNull ServerObserver observer){
        serverObservers.remove(observer);
    }

    public void notifyObservers(Answer message, String player){
        for(ServerObserver observer : serverObservers){
            observer.update(message, player);
        }
    }
}
