package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.messages.gameOverPhase.AnswerGameOver;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.GameOverAnswer;

public class GameOverPhase {
    private final Controller controller;
    private final boolean newGame;

    public GameOverPhase(Controller controller, boolean newGame){
        this.controller = controller;
        this.newGame = newGame;
    }

    public boolean getNewGame() {
        return newGame;
    }

    public void executeSelectedState(GameOverAnswer message){
        if (message instanceof AnswerGameOver) {
            boolean newGame = ((AnswerGameOver) message).getNewGame();
            if (newGame) {
                //setto tutto a null?

                //chiamo setup? O tutto sul server?
                controller.setNextGamePhase();
                controller.handleGamePhase();
            } //else
                //close connection
        }
    }
}
