package it.polimi.ingsw.PSP034.observer;

import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ModelObservable {
    ArrayList<ModelObserver> modelObservers;

    public ModelObservable(){
        modelObservers = new ArrayList<>();
    }

    public void addObserver(@NotNull ModelObserver observer){
        modelObservers.add(observer);
    }

    public void removeObserver(@NotNull ModelObserver observer){
        modelObservers.remove(observer);
    }

    public void notifyObservers(ModelUpdate message){
        for(ModelObserver observer : modelObservers){
            observer.update(message);
        }
    }
}
