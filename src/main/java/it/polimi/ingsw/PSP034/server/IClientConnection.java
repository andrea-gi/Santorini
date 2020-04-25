package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.observer.ServerObserver;
import org.jetbrains.annotations.NotNull;

public interface IClientConnection {
    void closeConnection();

    void addObserver(@NotNull ServerObserver observer);

    void removeObserver(@NotNull ServerObserver observer);
}
