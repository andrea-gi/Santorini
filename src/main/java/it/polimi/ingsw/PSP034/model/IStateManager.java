package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;

/**
 * Interface containing the methods used to arrange proper game actions (such as move, build, power, victory).
 */
public interface IStateManager {
    /**
     * Sets the right next phase, given the current phase.
     *
     * @param currentPhase Actual phase at the given moment.
     * @return Encapsulated info containing next phase and required actions.
     */
    NextStateInfo nextState(TurnPhase currentPhase);

    /**
     * Executes and manages the current phase actions.
     *
     * @param currentPhase Actual phase at the given moment.
     * @param worker Worker who performs actions during the current phase.
     * @param tile Tile destination of an action.
     * @param choice Boolean that sets the possible power choice.
     * @return {@code true} if the current phase is executed normally, {@code false} if there are problems
     * that prevent the normal execution to complete.
     */
    boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice);
}
