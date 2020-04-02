package it.polimi.ingsw.PSP034.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {
    private Tile tile = null;
    private Worker worker1 = null;

    @Before
    public void setUp(){
        tile = new Tile(1, 2);
        worker1 = new Worker('m', "Pippo");
    }

    @Test
    public void getWorker_correctInput_correctOutput() {
        tile.setWorker(worker1);
        assertEquals(worker1, tile.getWorker());
    }

    @Test
    public void setWorker_correctInput_correctOutput() {
        tile.setWorker(worker1);
        assertEquals(worker1, tile.getWorker());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setWorker_nullInput_throwsIllegalInputException() {
        tile.setWorker(null);
    }

    @Test
    public void removeWorker_noInput_correctOutput() {
        tile.setWorker(worker1);
        tile.removeWorker();
        assertNull(tile.getWorker());
    }

    @Test
    public void getBuilding_noInput_correctOutput() {
        assertEquals(0, tile.getBuilding());
    }

    @Test
    public void setBuilding_correctInput_correctOutput() {
        tile.setBuilding(1);
        assertEquals(1, tile.getBuilding());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setBuilding_invalidInput_throwsIllegaInputException() {
        tile.setBuilding(-1);
    }

    @Test
    public void hasDome_noInput_CorrectOutput() {
        assertFalse(tile.hasDome());
    }

    @Test
    public void setDome_correctInput_correctOutput() {
        tile.setDome(true);
        assertTrue(tile.hasDome());
    }

    @Test
    public void getX_noInput_CorrectOutput() {
        assertEquals(1, tile.getX());
    }

    @Test
    public void getY_noInput_CorrectOutput() {
        assertEquals(2, tile.getY());
    }
}