package it.polimi.ingsw.PSP034.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    Worker test = null;

    @Before
    public void setUp() throws Exception {
        test = new Worker('f', "Pippo");
    }

    @Test
    public void setMyTile() {
        Tile myTile = new Tile(2,3);
        test.setMyTile(myTile);
        assertEquals(test.getMyTile(), myTile);
    }

    @Test
    public void getMyTile() {
        Tile myTile = new Tile(2,3);
        test.setMyTile(myTile);
        Tile newTile = test.getMyTile();
        assertEquals(newTile, myTile);
    }


    @Test
    public void getSex() {
        Worker myWorker = new Worker('m',"Pluto");
        assertEquals(test.getSex(), 'f');
        assertEquals(myWorker.getSex(), 'm');
    }

    @Test
    public void getOwner() {
        assertEquals(test.getOwner(), "Pippo");
    }
}