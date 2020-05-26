package it.polimi.ingsw.PSP034.controller;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.*;
import it.polimi.ingsw.PSP034.messages.playPhase.InfoIsStarting;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayAnswer;
import it.polimi.ingsw.PSP034.messages.playPhase.RequestStart;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.setupPhase.GodLikeInfo;
import it.polimi.ingsw.PSP034.messages.setupPhase.RequestCardsChoice;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupAnswer;
import it.polimi.ingsw.PSP034.model.*;
import it.polimi.ingsw.PSP034.observer.ModelObserver;
import it.polimi.ingsw.PSP034.server.Server;

import java.util.ArrayList;

/**It controls the unfolding of the game, checking the GamePhase, giving control of the TurnPhase to the TurnHandler
 * and decorating the Gods in the right order, choosing their right moves*/
public class Controller implements IController{
    private final Game currentGame;
    private final TurnHandler turnHandler;
    private final SetupHandler setup;
    private final GameOverPhase gameOver;
    private final MessageManager messageManager;

    /**Creates the controller associated to a Game. It builds itself the setupPhase, the TurnHandler and the gameOverPhase
     * It creates also the DefaultRules in order to have ready all the Gods cards */
    public Controller(Server server) {
        this.currentGame = new Game(new Board());
        this.turnHandler = new TurnHandler(this);
        this.setup = new SetupHandler(this);
        this.gameOver = new GameOverPhase(this, false);
        this.messageManager = new MessageManager(this, server);
    }

    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }

    public void addModelObserver(ModelObserver observer){
        currentGame.addObserver(observer);
    }

    public void removeModelObserver(ModelObserver observer){
        currentGame.removeObserver(observer);
    }

    SlimBoard getSlimBoard(){
        return currentGame.generateSlimBoard();
    }

    @Override
    public void addPlayer(String name, PlayerColor color){
        currentGame.addPlayer(new Player(name, color));
    }

    public ArrayList<String> getRemainingGods() {
        return currentGame.getRemainingGods();
    }

    public ArrayList<String> getAlreadyChosenGods(){
        return currentGame.getAlreadyChosenGods();
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
        currentGame.setCurrentPlayerByName(name);
    }

    void addWorker(Sex sex, int x, int y){
        currentGame.addWorker(getCurrentPlayer(), sex, x, y);
    }

    void sendToPlayer(String player, Request message){
        messageManager.sendTo(message, player);
    }

    void sendToAll(Request message, boolean toEliminatedPlayer){
        ArrayList<String> playersList = currentGame.getPlayersName();
        if(toEliminatedPlayer)
            playersList.add(currentGame.getEliminatedPlayerName());
        String[] players = playersList.toArray(new String[0]);
        messageManager.sendTo(message, players);
    }

    void sendToAllExcept(String player, Request message, boolean toEliminatedPlayer){
        ArrayList<String> playersList = currentGame.getPlayersName();
        playersList.remove(player);
        if(toEliminatedPlayer)
            playersList.add(currentGame.getEliminatedPlayerName());
        messageManager.sendTo(message, playersList.toArray(new String[0]));
    }

    public IStateManager getCurrentGod() {
        return getCurrentPlayer().getMyGod();
    }

    void executeSelectedState(PlayAnswer message){
        turnHandler.executeSelectedState(message);
    }

    void executeSelectedState(SetupAnswer message){
        setup.executeSelectedState(message);
    }

    void executeSelectedState(GameOverAnswer message){
        gameOver.executeSelectedState(message);
    }

    /**Sets the next game phase, in order
     * Sends the first request message*/
    @Override
    public void handleGamePhase(){
        switch (currentGame.getGamePhase()) {
            case SETUP:
                godLikePlayerChoice();
                String name = this.getCurrentPlayer().getName();
                sendToAllExcept(name, new GodLikeInfo(name), false);
                sendToPlayer(name, new RequestCardsChoice(getPlayerNumber()));
                break;
            case PLAY:
                turnHandler.setCurrentGod(getCurrentPlayer().getMyGod());
                sendToPlayer(this.getCurrentPlayer().getName(), new RequestStart(new NextStateInfo(TurnPhase.START)));
                sendToAllExcept(this.getCurrentPlayer().getName(), new InfoIsStarting(this.getCurrentPlayer().getName()), false);
                break;
            case GAMEOVER:
                //Player loser = this.getCurrentPlayer();
                //sendToPlayer(loser.getName(), new SendGameOver(loser));
                //sendToOthers(others, new SendGameOver(loser));
        }
    }

    private void godLikePlayerChoice(){
        currentGame.setRandomPlayer();
    }

    void setNextPlayer(){
        currentGame.setNextPlayer();
    }

    void setNextGamePhase(){
        GamePhase myPhase = currentGame.getGamePhase();
        switch (myPhase){
            case SETUP:
                currentGame.setGamePhase(GamePhase.PLAY);
                break;
            case PLAY:
                currentGame.setGamePhase(GamePhase.GAMEOVER);
                break;
            case GAMEOVER:
                currentGame.setGamePhase(GamePhase.SETUP);
        }
    }

    GamePhase getGamePhase(){
        return currentGame.getGamePhase();
    }

    Player getCurrentPlayer(){
        return currentGame.getCurrentPlayer();
    }



    boolean isGameOver(){
        Player toBeDeletedPlayer = currentGame.loser();
        if(toBeDeletedPlayer != null) {
            if (currentGame.getPlayerNumber() == 2){
                String loser = toBeDeletedPlayer.getName();
                int indexLoser = currentGame.getPlayersName().indexOf(toBeDeletedPlayer.getName());
                String winner = currentGame.getPlayersName().get((indexLoser + 1)% 2);

                sendToPlayer(loser, new PersonalDefeatRequest(winner, loser));
                sendToPlayer(winner, new WinnerRequest(loser, winner));
                return true;
            }
            else{
                sendToPlayer(toBeDeletedPlayer.getName(), new PersonalDefeatRequest("", toBeDeletedPlayer.getName())); // empty string --> no winner
                sendToAllExcept(toBeDeletedPlayer.getName(), new SingleLoserInfo(toBeDeletedPlayer.getName(), toBeDeletedPlayer.getColor()), false);
                currentGame.removePlayer(toBeDeletedPlayer);
            }
        }

        return false;
    }

    void addGod(String name){
        this.currentGame.addGod(name, getCurrentPlayer());
    }
}
