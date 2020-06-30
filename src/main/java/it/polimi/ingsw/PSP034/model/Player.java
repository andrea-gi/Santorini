package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

import java.util.ArrayList;

/**
 * Represents an active player in a given game.
 */
public class Player {
    private final String name;
    private GodsRules myGod;
    private ArrayList<Worker> myWorkers;
    private boolean hasWon;
    private boolean hasLost;
    private final PlayerColor color;

    /** Creates a new Player class instantiating a structure for Workers (that needs to be initialized using {@link Player#addWorker(Sex, Tile)}).
     * At the time of instantiation, there is no myGod associated, which has to bet set using {@link Player#setMyGod(GodsRules)}.
     *
     * @param name  Player's name.
     * @param color Player's color.
     */
    public Player(String name, PlayerColor color) {
        this.name = name;
        myGod = null;
        myWorkers = new ArrayList<>();
        hasWon = false;
        hasLost = false;
        this.color = color;
    }

    /**
     * Returns if a player has lost (and has to be eliminated by class contract).
     * @return {@code true} if the player has lost, {@code false} otherwise.
     */
    public boolean hasLost() {
        return hasLost;
    }

    /**
     * Flags a losing player.
     * @param hasLost {@code true} if the player has lost, {@code false} otherwise.
     */
    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    /**
     * Creates a new Worker associated to the Player, given color, sex and player name.
     * Places the Worker in its starting Tile.
     * @param sex       Worker's sex.
     * @param myTile    Starting worker tile.
     * */
    public void addWorker(Sex sex, Tile myTile){
        if (myWorkers.size() >= 2 || (myWorkers.get(0) != null && myWorkers.get(0).getSex() == sex)){
            throw new IllegalArgumentException("Cannot add the same worker twice.");
        }
        Worker newWorker = new Worker(sex, name, this.color, myTile);
        myWorkers.add(newWorker);
        myTile.setWorker(newWorker);
    }

    /**
     * Returns the reference of the two workers.
     * @return List containing the player's workers.
     */
    public ArrayList<Worker> getMyWorkers(){
        return myWorkers;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the player's worker of given sex.
     * @param sex   Worker's sex.
     * @return      Chosen sex worker, owned by the player.
     * */
    public Worker getWorker(Sex sex) {
        Worker result = null;
        for (Worker myWorker : myWorkers) {
            if (myWorker.getSex() == sex) {
                result = myWorker;
                break;
            }
        }
        return result;
    }

    /**
     * Returns god associated to player.
     * @return Class representing god's rules.
     */
    public GodsRules getMyGod() {
        return myGod;
    }

    /**
     * Associates a given god to a player.
     * @param myGod God to be associated to the player.
     * */
    public void setMyGod(GodsRules myGod) {
        this.myGod = myGod;
    }

    /**
     * Check ownership of worker.
     * @param worker Reference to the worker to be checked.
     * @return Returns {@code true} if player is owner of the given worker, {@code false} otherwise.
     */
    public boolean isOwner(Worker worker){
        return worker.getOwner().equals(name);
    }

    /**
     * Returns player's color.
     * @return Player's color.
     */
    public PlayerColor getColor() {
        return color;
    }
}
