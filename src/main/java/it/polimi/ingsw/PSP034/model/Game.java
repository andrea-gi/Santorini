package it.polimi.ingsw.PSP034.model;

import java.util.ArrayList;

public class Game {
    private final Board board;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<String> godsList;

    /**
     * Creates a new Game class, instantiating a new Board and a structure for Players. 
     * At the time of instantiation, there is no currentPlayer, which has to bet set using {@link Game#setCurrentPlayer(Player)}
     */
    public Game(){
        this.players = new ArrayList<Player>();
        this.board = new Board();
        this.currentPlayer = null;
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
}
