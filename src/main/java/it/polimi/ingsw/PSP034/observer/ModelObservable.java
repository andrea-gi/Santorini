package it.polimi.ingsw.PSP034.observer;

import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Class extended by each {@link it.polimi.ingsw.PSP034.model} observable
 */
public abstract class ModelObservable {
    private final ArrayList<ModelObserver> modelObservers;

    /**
     * Instantiates a new empty ModelObserver.
     */
    public ModelObservable(){
        modelObservers = new ArrayList<>();
    }

    /**
     * Adds an observer to the observers set.
     *
     * @param observer Reference to the observer being added.
     */
    public void addObserver(@NotNull ModelObserver observer){
        modelObservers.add(observer);
    }

    /**
     * Removes an observer to the observers set.
     *
     * @param observer Reference to the observer being removed.
     */
    public void removeObserver(@NotNull ModelObserver observer){
        modelObservers.remove(observer);
    }

    /**
     * Calls the method {@link ModelObserver#update(ModelUpdate)} for each {@link ModelObserver} in the observers set,
     * notifying a {@link ModelUpdate} message.
     *
     * @param message Message notified to observers.
     */
    public void notifyObservers(ModelUpdate message){
        for(ModelObserver observer : modelObservers){
            observer.update(message);
        }
    }
}
