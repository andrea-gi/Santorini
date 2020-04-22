package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.PlayPhase.NextStateInfo;

public interface IStateManager {
    NextStateInfo nextState(TurnPhase currentPhase);
    boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice);
}
