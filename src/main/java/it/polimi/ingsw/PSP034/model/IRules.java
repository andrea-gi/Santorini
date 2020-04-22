package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Sex;

public interface IRules {

    Tile getPreviousTile();

    Sex getChosenSex();

    DefaultRules getDefaultRules();

    void move(Worker worker, Tile destinationTile);

    void build(Tile buildTile);

    boolean validMove(Worker worker, Tile destinationTile);

    boolean validBuild(Worker worker, Tile buildingTile);

    boolean checkWin(Worker worker);

    void setChosenSex(Worker chosenWorker);

    void setPreviousTile(Tile tile);

    boolean anyValidMove(Worker worker);

    boolean checkMoveLost(Player player);

    boolean anyValidBuild(Worker worker);

    boolean checkBuildLost(Player player);
}
