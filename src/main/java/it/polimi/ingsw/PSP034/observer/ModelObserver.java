package it.polimi.ingsw.PSP034.observer;

import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.messages.SlimBoard;

public interface ModelObserver {
    public void update(ModelUpdate message);
}
