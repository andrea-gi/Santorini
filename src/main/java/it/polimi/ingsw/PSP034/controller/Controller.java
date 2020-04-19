package it.polimi.ingsw.PSP034.controller;
import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.messages.PlayPhase.PlayRequest;
import it.polimi.ingsw.PSP034.model.*;

/**It controls the unfolding of the game, checking the GamePhase, giving control of the TurnPhase to the TurnHandler
 * and decorating the Gods in the right order, choosing their right moves*/
public class Controller {
    private final Game currentGame;
    private final TurnHandler turnHandler;
    private final SetupHandler setup;
    private final GameOverPhase gameOver;
    private final MessageManager messageManager;

    /**Creates the controller associated to a Game. It builds itself the SetupPhase, the TurnHandler and the GameOverPhase
     * It creates also the DefaultRules in order to have ready all the Gods cards
     * @param currentGame is the link to the Game*/
    public Controller(Game currentGame) {
        this.currentGame = currentGame;
        this.turnHandler = new TurnHandler(this);
        this.setup = new SetupHandler(this);
        this.gameOver = new GameOverPhase(this);
        this.messageManager = new MessageManager(this);
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public void sendToPlayer(Player player, PlayRequest message){
        messageManager.sendToPlayer(player, message);
    }

    public void sendToAll();

    /**Sets the next game phase, in order*/
    public void nextGamePhase(){
        switch (currentGame.getGamePhase()) {
            case SETUP:
                currentGame.setGamePhase(GamePhase.PLAY);
            case PLAY:
                currentGame.setGamePhase(GamePhase.GAMEOVER);
            case GAMEOVER:
                //chiama chi gestisce la partita
        }
    }

    /**Executes the right game phase*/
    public boolean startGamePhase(){
        boolean validPhase = false;
        switch (currentGame.getGamePhase()) {
            case SETUP:
                //validPhase = setup.startSetup();
            case PLAY:
                //turnHandler alla prima mossa della prima persona
                //if(!turnHandler.gotWinner())
                //    validPhase = turnHandler.startTurnHandler();
            case GAMEOVER:
                //validPhase = gameOver.startGameOver();
        }

        if (validPhase)
            nextGamePhase();
        return validPhase;
    }

    public void setNextPlayer(){
        currentGame.setNextPlayer();
    }

    public Player getCurrentPlayer(){
        return currentGame.getCurrentPlayer();
    }

    public boolean isGameOver(){
        Player toBeDeletedPlayer = null;
        for(Player player : currentGame.getPlayers()){
            if(player.hasLost()){
                toBeDeletedPlayer = player;
            }
        }
        if(toBeDeletedPlayer != null) {
            if (currentGame.getPlayers().size() == 2){
                return true;
            }
            else{
                currentGame.removePlayer(toBeDeletedPlayer);
            }
        }

        return false;
    }
}
