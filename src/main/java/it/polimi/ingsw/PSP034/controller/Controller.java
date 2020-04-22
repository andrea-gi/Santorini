package it.polimi.ingsw.PSP034.controller;
import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.PlayPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.PlayPhase.RequestStart;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SetupPhase.RequestCardsChoice;
import it.polimi.ingsw.PSP034.model.*;
import it.polimi.ingsw.PSP034.server.MessageManager;

import java.util.ArrayList;

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

    public ArrayList<String> getRemainingGods() {
        return currentGame.getRemainingGods();
    }

    public void removeRemainingGod(String god) {
        this.currentGame.removeRemainingGod(god);
    }

    public void addRemainingGod(String god){
        this.currentGame.addRemainingGod(god);
    }

    public int getPlayerNumber(){
        return this.currentGame.getPlayerNumber();
    }

    public ArrayList<String> getPlayersName(){
        return this.currentGame.getPlayersName();
    }

    public void firstPlayerSetUp(String name){
        Player player = currentGame.getPlayerByName(name);
        currentGame.setCurrentPlayer(player);
    }

    public Tile getTile(int x, int y){
        return currentGame.getBoard().getTile(x, y);
    }

    public void sendToPlayer(Player player, Request message){
        messageManager.sendToPlayer(player, message);
    }

    public void sendToAll(){};

    /**Sets the next game phase, in order
     * Sends the first request message*/
    public void handleGamePhase(){
        switch (currentGame.getGamePhase()) {
            case SETUP:
                sendToPlayer(this.getCurrentPlayer(), new RequestCardsChoice(getPlayerNumber()));
                break;
            case PLAY:
                sendToPlayer(this.getCurrentPlayer(), new RequestStart(new NextStateInfo(TurnPhase.START)));
            case GAMEOVER:
                //chiama chi gestisce la partita
        }
    }

    public void setNextPlayer(){
        currentGame.setNextPlayer();
    }

    public void setNextGamePhase(){
        GamePhase myPhase = currentGame.getGamePhase();
        switch (myPhase){
            case SETUP:
                currentGame.setGamePhase(GamePhase.PLAY);
                break;
            case PLAY:
                currentGame.setGamePhase(GamePhase.GAMEOVER);
                break;
        }
    }

    public GamePhase getGamePhase(){
        return currentGame.getGamePhase();
    }

    public Player getCurrentPlayer(){
        return currentGame.getCurrentPlayer();
    }

    public TurnHandler getTurnHandler(){
        return this.turnHandler;
    }

    public boolean isGameOver(){
        Player toBeDeletedPlayer = currentGame.loser();
        if(toBeDeletedPlayer != null) {
            if (currentGame.getPlayerNumber() == 2){
                return true;
            }
            else{
                currentGame.removePlayer(toBeDeletedPlayer);
            }
        }

        return false;
    }

    public void addGod(String name, Player player){
        this.currentGame.addGod(name, player);
    }
}
