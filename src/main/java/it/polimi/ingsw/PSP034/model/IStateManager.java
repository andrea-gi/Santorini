package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public interface IStateManager {
    public TurnPhase nextState(TurnPhase currentPhase);
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice);
}
