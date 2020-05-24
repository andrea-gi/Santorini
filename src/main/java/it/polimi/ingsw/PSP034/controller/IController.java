package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.observer.ModelObserver;


public interface IController {
    void addPlayer(String name, PlayerColor color);
    void handleGamePhase();
    MessageManager getMessageManager();
    void addModelObserver(ModelObserver observer);
    void removeModelObserver(ModelObserver observer);
}
