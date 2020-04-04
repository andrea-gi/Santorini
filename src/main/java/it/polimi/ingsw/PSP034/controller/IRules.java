package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public interface IRules {

    public void move(Worker worker, Tile destinationTile);

    public void build(Tile buildTile);

    public boolean validMove(Player player, Worker worker, Tile destinationTile);

    public boolean validBuild(Player player, Worker worker, Tile buildingTile);
}
