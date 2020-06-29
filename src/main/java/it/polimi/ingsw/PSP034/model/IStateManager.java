package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;

/**
 * Interface containing the methods used to arrange proper game actions (such as move, build, power, victory).
 */
public interface IStateManager {
    NextStateInfo nextState(TurnPhase currentPhase);
    boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice);
}
