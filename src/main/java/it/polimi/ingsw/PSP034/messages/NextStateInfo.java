package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

import java.util.ArrayList;
import java.util.Arrays;

public class NextStateInfo {
    private final TurnPhase nextPhase;
    private final ArrayList<RequiredActions> actions;


    public NextStateInfo(TurnPhase nextPhase, RequiredActions... args) {
        this.nextPhase = nextPhase;
        actions = new ArrayList<>(Arrays.asList(args));
    }

    public TurnPhase getNextPhase(){
        return nextPhase;
    }

    public RequiredActions[] getRequiredActions(){
        RequiredActions[] returnActions = new RequiredActions[actions.size()];
        for(int i = 0; i < actions.size(); i++){
            returnActions[i] = actions.get(i);
        }
        return returnActions;
    }
}
