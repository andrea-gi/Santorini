package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

import java.util.ArrayList;

public class Player {
    private final String name;
    private String myGod;
    private ArrayList<Worker> myWorkers;

    /** Creates a new Player class
     * @param name Player's name
     * instantiating a structure for Workers, that needs to be initialised using {@link Player#setMyWorkers(Color, Tile)}
     * At the time of instantiation, there is no myGod associated, which has to bet set using
     * {@link Player#setMyGod(String)}
     * */
    public Player(String name) {
        this.name = name;
        myGod = null;
        myWorkers = new ArrayList<Worker>();
    }

    /** Initialises the structure for the Workers associated to the Player, with his name, a color and a sex. It also
     * already places the Workers in their first position
     * @param sex is the sex of the Worker
     * @param myColor is the color of my Workers
     * @param myTile is the tile where the Player places the first time his Worker*/
    public void setMyWorkers(Sex sex, Color myColor, Tile myTile){
        myWorkers.add(new Worker(sex, name, myColor, myTile));
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

    public String getMyGod() {
        return myGod;
    }

    /** @param myGod the first turn a God is associated with the Player, for the entire game*/
    public void setMyGod(String myGod) {
        this.myGod = myGod;
    }

    /*TODO --*/
    /*
    public void remove(){

        myWorkers.remove();    //devo toglierli davvero non solo dalla lista
        myWorkers.remove();
    }*/

}
