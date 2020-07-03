package it.polimi.ingsw.PSP034.observer;

import it.polimi.ingsw.PSP034.messages.ModelUpdate;

/**
 * Interface implemented by each {@link it.polimi.ingsw.PSP034.model} observer.
 */
public interface ModelObserver {
    /**
     * Manages an update regarding a {@link ModelUpdate} message.
     *
     * @param message Reference to the update message.
     */
    void update(ModelUpdate message);
}
