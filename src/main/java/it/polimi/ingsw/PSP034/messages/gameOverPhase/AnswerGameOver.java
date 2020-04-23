package it.polimi.ingsw.PSP034.messages.gameOverPhase;

public class AnswerGameOver extends GameOverAnswer{
    private final boolean newGame;

    public AnswerGameOver(boolean newGame){
        this.newGame = newGame;
    }

    public boolean getNewGame(){
        return newGame;
    }
}
