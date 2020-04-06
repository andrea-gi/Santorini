package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public interface IRules {

    public Tile getPreviousTile();

    public Sex getChosenSex();

    public DefaultRules getDefaultRules();

    public void move(Worker worker, Tile destinationTile);

    public void build(Tile buildTile);

    public boolean validMove(Player player, Worker worker, Tile destinationTile);

    public boolean validBuild(Player player, Worker worker, Tile buildingTile);

    public boolean checkWin(Worker worker);

    public void setChosenSex(Worker chosenWorker);

    public void setPreviousTile(Tile tile);

    public boolean anyValidMove(Player player, Worker worker);

    public boolean checkMoveLost(Player player);

    public boolean anyValidBuild(Player player, Worker worker);

    public boolean checkBuildLost(Player player);
}
