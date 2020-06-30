package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.observer.ModelObserver;

/**
 * External access to model and controller (server side).
 */
public interface IController {
    /**
     * Adds a new player to the current game.
     * @param name Name of the player to be added.
     * @param color Color of the player to be added.
     */
    void addPlayer(String name, PlayerColor color);

    /**
     * Executes the given game phase.
     * Sends the first request message to initialize the current game phase.
     */
    void handleGamePhase();

    /**
     * Gets the MessageManager reference, which is created by the controller.
     * @return The MessageManager reference.
     */
    MessageManager getMessageManager();

    /**
     * Forwards a model observer add request to the current game.
     * @param observer Observer that needs to be added.
     */
    void addModelObserver(ModelObserver observer);

    /**
     * Forwards a model observer remove request to the current game.
     * @param observer Observer that needs to be removed.
     */
    void removeModelObserver(ModelObserver observer);
}
