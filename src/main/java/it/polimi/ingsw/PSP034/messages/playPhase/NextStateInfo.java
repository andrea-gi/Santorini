package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the information relative to the game phase to be executed.
 */
public class NextStateInfo implements Serializable {
    static final long serialVersionUID = 305121031L;

    private final TurnPhase nextPhase;
    private final ArrayList<RequiredActions> actions;

    /**
     * Initializes the object with the required information.
     * @param nextPhase The game phase that has to be executed next.
     * @param args List of actions that have to be executed in the next phase.
     */
    public NextStateInfo(TurnPhase nextPhase, RequiredActions... args) {
        this.nextPhase = nextPhase;
        actions = new ArrayList<>(Arrays.asList(args));
    }

    public TurnPhase getNextPhase(){
        return nextPhase;
    }

    public RequiredActions[] getRequiredActions(){
        return actions.toArray(new RequiredActions[0]);
    }
}
