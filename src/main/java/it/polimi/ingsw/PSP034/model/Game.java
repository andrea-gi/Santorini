package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.*;

import java.util.ArrayList;

public class Game {
    private final Board board;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private GamePhase gamePhase;
    private ArrayList<String> godsList;

    /**
     * Creates a new Game class, instantiating a new Board and a structure for Players. 
     * At the time of instantiation, there is no currentPlayer, which has to bet set using {@link Game#setCurrentPlayer(Player)}
     */
    public Game(){
        this.players = new ArrayList<Player>();
        this.board = new Board();
        this.currentPlayer = null;
        this.gamePhase = GamePhase.SETUP;
        // godsList ???
    }

    /**
     * @return Number of players currently in the game
     */
    public int getPlayerNumber(){
        return players.size();
    }

    private void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /**
     * Adds a Player to the structure of active players.
     * @param player - Existing player to be added to the game.
     */
    public void addPlayer(Player player){
        if(player == null)
            throw new IllegalArgumentException("Added player cannot be null!");
        players.add(player);
        if (players.size() == 1){
            setCurrentPlayer(player);
        }
    }

    /**
     * Sets the next player among the data structure of players, which is already in the right turn order.
     * */
    public void setNextPlayer(){
        int index = players.indexOf(currentPlayer);
        int nextIndex = ((index + 1) % players.size());
        setCurrentPlayer(players.get(nextIndex));
    }


    public Board getBoard(){
        return board;
    }

    public void setGamePhase(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase(){
        return gamePhase;
    }
}
