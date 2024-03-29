package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

import java.util.ArrayList;

/**
 * Move or build message containing all the possible actions for both workers of a given player.
 */
public class RequestAction extends PlayRequest {
    static final long serialVersionUID = 760277991L;

    private final Directions[] possibleMaleDirections;
    private final Directions[] possibleFemaleDirections;

    private final int xMale;
    private final int yMale;
    private final int xFemale;
    private final int yFemale;

    /**
     * Initializes the message, calculates possible actions.
     *
     * @param info Required game phase info.
     * @param player Message is been sent to the given player.
     */
    public RequestAction(NextStateInfo info, Player player){
        super(info);
        Worker male = player.getWorker(Sex.MALE);
        Worker female = player.getWorker(Sex.FEMALE);

        Tile maleStartTile = male.getMyTile();
        Tile femaleStartTile = female.getMyTile();

        this.xMale = maleStartTile.getX();
        this.yMale = maleStartTile.getY();

        this.xFemale = femaleStartTile.getX();
        this.yFemale = femaleStartTile.getY();

        ArrayList<Tile> malePossibleTiles;
        ArrayList<Tile> femalePossibleTiles;

        if (info.getNextPhase() == TurnPhase.MOVE) {
            malePossibleTiles = GodsRules.availableMovingTiles(male);
            femalePossibleTiles = GodsRules.availableMovingTiles(female);
        }
        else if (info.getNextPhase() == TurnPhase.BUILD) {
            malePossibleTiles = GodsRules.availableBuildingTiles(male);
            femalePossibleTiles = GodsRules.availableBuildingTiles(female);
        }
        else
            throw new IllegalArgumentException("Action must be MOVE or BUILD");

        this.possibleMaleDirections = new Directions[malePossibleTiles.size()];
        this.possibleFemaleDirections = new Directions[femalePossibleTiles.size()];

        for(int i = 0; i < malePossibleTiles.size(); i++){
            this.possibleMaleDirections[i] = maleStartTile.directionCalculator(malePossibleTiles.get(i));
        }
        for(int i = 0; i < femalePossibleTiles.size(); i++){
            this.possibleFemaleDirections[i] = femaleStartTile.directionCalculator(femalePossibleTiles.get(i));
        }

    }

    public int getXFemale() {
        return xFemale;
    }

    public int getYFemale() {
        return yFemale;
    }

    public int getXMale() {
        return xMale;
    }

    public int getYMale() {
        return yMale;
    }

    public Directions[] getPossibleFemaleDirections() {
        return possibleFemaleDirections;
    }

    public Directions[] getPossibleMaleDirections() {
        return possibleMaleDirections;
    }

    @Override
    public TurnPhase getNextPhase() {
        return super.getNextPhase();
    }

    @Override
    public RequiredActions[] getRequiredActions(){
        return super.getRequiredActions();
    }
}
