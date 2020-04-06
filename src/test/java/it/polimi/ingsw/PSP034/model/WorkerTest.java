package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    Worker test = null;

    @Before
    public void setUp() throws Exception {
        Tile myTile = new Tile(2,3);
        test = new Worker(Sex.FEMALE, "Pippo", Color.BLUE, myTile);
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
        Tile myTile = new Tile(2,3);
        Worker myWorker = new Worker(Sex.MALE, "Pippo", Color.GREY, myTile);
        assertEquals(test.getSex(), Sex.FEMALE);
        assertEquals(myWorker.getSex(), Sex.MALE);
    }

    @Test
    public void getOwner() {
        assertEquals(test.getOwner(), "Pippo");
    }
}