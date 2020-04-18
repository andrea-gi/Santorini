package it.polimi.ingsw.PSP034.observer;

public interface Observer<T> {
    void update(T message);
}
