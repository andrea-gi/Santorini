package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    private Board board;
    private Worker myWorker;
    private int difference;

    @Before
    public void setUp(){
        board = new Board();
        myWorker = new Worker(Sex.MALE, "Veronica", Color.BLUE, board.getTile(1,1));
        board.getTile(1,1).setWorker(myWorker);
    }

    @Test
    public void heightDifferenceSameLevel() {
        difference = myWorker.heightDifference(board.getTile(2,1));
        assertEquals(difference, 0);
    }

    @Test
    public void heightDifferenceUpperLevel() {
        board.getTile(2,1).setBuilding(2);
        board.getTile(1,1).setBuilding(1);
        difference = myWorker.heightDifference(board.getTile(2,1));
        assertEquals(difference, 1);
    }

    @Test
    public void heightDifferenceLowerLevel() {
        board.getTile(2,1).setBuilding(2);
        board.getTile(1,1).setBuilding(3);
        difference = myWorker.heightDifference(board.getTile(2,1));
        assertEquals(difference, -1);
    }
}