package it.polimi.ingsw.PSP034.observer;

import it.polimi.ingsw.PSP034.messages.Answer;

public interface ServerObserver {
    void update(Answer message, String name);
}
