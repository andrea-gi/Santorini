package it.polimi.ingsw.PSP034.model;

import java.util.ArrayList;

public class Player {
    private final String name;
    private String myGod;
    private ArrayList<Worker> myWorkers;

    /** Creates a new Player class
     * @param name Player's name
     * instantiating a structure for Workers and initializing
     * it with the two Workers (male and female) associated to the Player
     * At the time of instantiation, there is no myGod associated, which has to bet set using
     * {@link Player#setMyGod(String)}
     * */
    public Player(String name) {
        this.name = name;
        myGod = null;
        myWorkers = new ArrayList<Worker>();
        myWorkers.add(new Worker('m', name));
        myWorkers.add(new Worker('f', name));
    }

    public String getName() {
        return name;
    }

    /**@param sex to select the right Worker
     * @return the right Worker associated to the Player
     * The function always satisfies the if condition*/
    public Worker getWorker(char sex) {
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
