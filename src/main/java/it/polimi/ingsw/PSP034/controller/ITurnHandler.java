package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Worker;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.constants.TurnPhase;

public interface ITurnHandler {
    public TurnPhase nextState(TurnPhase currentPhase);
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice);
}
