package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public interface ITurnHandler {
    public TurnPhase nextState();
    public Boolean executeState();
}
