package it.polimi.ingsw.PSP034.messages.toClient;

import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.RequiredActions;
import it.polimi.ingsw.PSP034.model.Tile;

import java.util.ArrayList;

public class RequestAction extends ToClientMessage {

    private final NextStateInfo actionInfo;
    private final Directions[] possibleMaleDirections;
    private final Directions[] possibleFemaleDirections;

    public RequestAction(NextStateInfo info, Tile startTile, ArrayList<Tile> malePossibleTiles, ArrayList<Tile> femalePossibleTiles){
        this.actionInfo = info;
        this.possibleMaleDirections = new Directions[malePossibleTiles.size()];
        this.possibleFemaleDirections = new Directions[femalePossibleTiles.size()];

        for(int i = 0; i < malePossibleTiles.size(); i++){
            this.possibleMaleDirections[i] = startTile.directionCalculator(malePossibleTiles.get(i));
        }
        for(int i = 0; i < femalePossibleTiles.size(); i++){
            this.possibleFemaleDirections[i] = startTile.directionCalculator(femalePossibleTiles.get(i));
        }

    }



    public Directions[] getPossibleFemaleDirections() {
        return possibleFemaleDirections;
    }

    public Directions[] getPossibleMaleDirections() {
        return possibleMaleDirections;
    }

    public TurnPhase getNextPhase() {
        return actionInfo.getNextPhase();
    }

    public RequiredActions[] getRequiredActions(){
        return actionInfo.getRequiredActions();
    }
}
