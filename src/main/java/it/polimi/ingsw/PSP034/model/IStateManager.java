package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.NextStateInfo;

public interface IStateManager {
    public NextStateInfo nextState(TurnPhase currentPhase);
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice);
}
