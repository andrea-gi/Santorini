package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

import java.util.ArrayList;

public class Player {
    private final String name;
    private GodsRules myGod;
    private ArrayList<Worker> myWorkers;
    private boolean hasWon;
    private boolean hasLost;
    private final PlayerColor color;

    /** Creates a new Player class
     * @param name Player's name
     * instantiating a structure for Workers, that needs to be initialised using {@link Player#addWorker(Sex, Tile)}
     * At the time of instantiation, there is no myGod associated, which has to bet set using
     * {@link Player#setMyGod(GodsRules)}
     * */
    public Player(String name, PlayerColor color) {
        this.name = name;
        myGod = null;
        myWorkers = new ArrayList<>();
        hasWon = false;
        hasLost = false;
        this.color = color;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    /** Initialises the structure for the Workers associated to the Player, with his name, a color and a sex. It also
     * already places the Workers in their first position
     * @param sex is the sex of the Worker
     * @param myTile is the tile where the Player places the first time his Worker*/
    public void addWorker(Sex sex, Tile myTile){
        Worker newWorker = new Worker(sex, name, this.color, myTile);
        myWorkers.add(newWorker);
        myTile.setWorker(newWorker);
    }

    public ArrayList<Worker> getMyWorkers(){
        return myWorkers;
    }

    public String getName() {
        return name;
    }

    /**@param sex to select the right Worker
     * @return the right Worker associated to the Player
     * The function always satisfies the if condition*/
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

    public GodsRules getMyGod() {
        return myGod;
    }

    /** @param myGod the first turn a God is associated with the Player, for the entire game*/
    public void setMyGod(GodsRules myGod) {
        this.myGod = myGod;
    }

    /*TODO --*/
    /*
    public void remove(){

        myWorkers.remove();    //devo toglierli davvero non solo dalla lista
        myWorkers.remove();
    }*/

    /**
     * Check ownership of worker.
     * @param worker Reference to the worker to be checked.
     * @return Returns true if player is owner of the given worker, false otherwise.
     */
    public boolean isOwner(Worker worker){
        return worker.getOwner().equals(name);
    }

    public PlayerColor getColor() {
        return color;
    }
}
