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

/**
 * It controls the unfolding of the game, checking the GamePhase, giving control of the TurnPhase to the TurnHandler
 * and decorating the Gods in the right order, choosing their right moves.
 */
public class Controller implements IController{
    private final Game currentGame;
    private final TurnHandler turnHandler;
    private final SetupHandler setup;
    private final MessageManager messageManager;

    /**
     * Creates the controller associated to a Game. It builds itself the setupHandler, the TurnHandler and
     * the MessageManager.
     * @param server Server associated with the controller.
     */
    public Controller(Server server) {
        this.currentGame = new Game(new Board());
        this.turnHandler = new TurnHandler(this);
        this.setup = new SetupHandler(this);
        this.messageManager = new MessageManager(this, server);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addModelObserver(ModelObserver observer){
        currentGame.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeModelObserver(ModelObserver observer){
        currentGame.removeObserver(observer);
    }

    SlimBoard getSlimBoard(){
        return currentGame.generateSlimBoard();
    }

    /**
     * Forwards a player add request to the current game.
     * @param name Name of the player to be added.
     * @param color Color of the player to be added.
     */
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

    /**
     * Adds a given god to the list of possible gods, for the next player to chose from.
     *
     * @param god God chosen to play in the current game.
     */
    public void addRemainingGod(String god){
        this.currentGame.addRemainingGod(god);
    }

    /**
     * Returns the number of active players.
     *
     * @return Number of players currently in the game.
     */
    public int getPlayerNumber(){
        return this.currentGame.getPlayerNumber();
    }

    public ArrayList<String> getPlayersName(){
        return this.currentGame.getPlayersName();
    }

    /**
     * Forwards the setting of the first player to the current game.
     *
     * @param name Name of the first player.
     */
    public void firstPlayerSetUp(String name){
        currentGame.setCurrentPlayerByName(name);
    }

    /**
     * Adds a new worker, associated to the current player.
     *
     * @param sex       Worker's sex.
     * @param x         x coordinate.
     * @param y         y coordinate.
     */
    void addWorker(Sex sex, int x, int y){
        currentGame.addWorker(getCurrentPlayer(), sex, x, y);
    }

    /**
     * Sends to a single player a given message.
     * @param player Player recipient of the message.
     * @param message Message sent to the player.
     */
    void sendToPlayer(String player, Request message){
        messageManager.sendTo(message, player);
    }

    /**
     * Sends to all the players a given message.
     * @param message Message sent to all the players.
     * @param toEliminatedPlayer Flag to specify if the message should be sent also to the eliminated player.
     */
    void sendToAll(Request message, boolean toEliminatedPlayer){
        ArrayList<String> playersList = currentGame.getPlayersName();
        if(toEliminatedPlayer && currentGame.getEliminatedPlayerName().length() > 0)
            playersList.add(currentGame.getEliminatedPlayerName());
        String[] players = playersList.toArray(new String[0]);
        messageManager.sendTo(message, players);
    }

    /**
     * Sends to all the players except one a given message.
     * @param player Player excluded from receiving the message.
     * @param message Message sent to all due players.
     * @param toEliminatedPlayer Flag to specify if the message should be sent also to the eliminated player.
     */
    void sendToAllExcept(String player, Request message, boolean toEliminatedPlayer){
        ArrayList<String> playersList = currentGame.getPlayersName();
        playersList.remove(player);
        if(toEliminatedPlayer && currentGame.getEliminatedPlayerName().length() > 0)
            playersList.add(currentGame.getEliminatedPlayerName());
        messageManager.sendTo(message, playersList.toArray(new String[0]));
    }

    /**
     * Returns the current player's associated god.
     *
     * @return Reference to player's god.
     */
    public IStateManager getCurrentGod() {
        return getCurrentPlayer().getMyGod();
    }

    /**
     * Executes the content of a given message.
     *
     * @param message Play message to be handled.
     */
    void executeSelectedState(PlayAnswer message){
        turnHandler.executeSelectedState(message);
    }

    /**
     * Executes the content of a given message.
     *
     * @param message Setup message to be handled.
     */
    void executeSelectedState(SetupAnswer message){
        setup.executeSelectedState(message);
    }

    /**
     * {@inheritDoc}
     */
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
        }
    }

    /**
     * Sets the god-like player with a random function.
     */
    private void godLikePlayerChoice(){
        currentGame.setRandomPlayer();
    }

    /**
     * Sets the next player, in order.
     */
    void setNextPlayer(){
        currentGame.setNextPlayer();
    }

    /**
     * Sets the next game phase, in order.
     */
    void setNextGamePhase(){
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

    GamePhase getGamePhase(){
        return currentGame.getGamePhase();
    }

    Player getCurrentPlayer(){
        return currentGame.getCurrentPlayer();
    }

    /**
     * Checks if a player lost and manages the possible game end.
     *
     * @return {@code true} if the game has ended; {@code false} if only one out of three players has lost.
     */
    boolean isGameOver(){
        Player toBeDeletedPlayer = currentGame.loser();
        if(toBeDeletedPlayer != null) {
            if (currentGame.getPlayerNumber() == 2){
                String loser = toBeDeletedPlayer.getName();
                int indexLoser = currentGame.getPlayersName().indexOf(toBeDeletedPlayer.getName());
                String winner = currentGame.getPlayersName().get((indexLoser + 1)% 2);

                sendToAllExcept(winner, new PersonalDefeatRequest(winner, loser), true);
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

    /**
     * Associates a god to the current player.
     *
     * @param name Name of the god.
     */
    void addGod(String name){
        this.currentGame.addGod(name, getCurrentPlayer());
    }
}
