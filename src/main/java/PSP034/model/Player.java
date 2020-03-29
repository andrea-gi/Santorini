package PSP034.model;

import java.util.ArrayList;

public class Player {
    private String name;
    private String myGod;
    private ArrayList<Worker> worker;


    public Player(String name) {
        this.name = name;
        myGod = null;
        worker = new ArrayList<Worker>();
    }

    public String getName() {
        return name;
    }

    public Worker getWorker(char sex) {
        for (Worker myWorker : worker) {
            if (myWorker.getSex() == sex)
                return myWorker;
        }
    }

    public void setWorker(char team) {
        worker.add(new Worker('m', team));
        worker.add(new Worker('f', team));
    }

    public String getMyGod() {
        return myGod;
    }

    public void setMyGod(String myGod) {
        this.myGod = myGod;
    }
}
