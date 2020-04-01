package it.polimi.ingsw.PSP034.model;

import java.util.ArrayList;

public class Player {
    private final String name;
    private String myGod;
    private ArrayList<Worker> myWorkers;


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

    public Worker getWorker(char sex) {
        for (Worker myWorker : myWorkers) {
            if (myWorker.getSex() == sex)
                return myWorker;
        }
        return null;    //exception?? Perché devo entrare sempre nell'if
    }

    public String getMyGod() {
        return myGod;
    }

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
