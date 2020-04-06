package it.polimi.ingsw.PSP034.model;

import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.PSP034.constants.*;

import static org.junit.Assert.*;

public class PlayerTest {
    Player test = null;
    String myGod = null;
    Worker myWorker = null;

    @Before
    public void setUp() throws Exception {
        test = new Player("Pippo");
    }

    @Test
    public void getName() {
        assertEquals(test.getName(), "Pippo");
    }

    @Test
    public void getWorker_f() {
        Sex sex = Sex.FEMALE;
        myWorker = test.getWorker(sex);
        assertNotNull(myWorker);
        assertEquals(myWorker.getSex(), sex);
    }

    @Test
    public void getWorker_m() {
        Sex sex = Sex.MALE;
        myWorker = test.getWorker(sex);
        assertNotNull(myWorker);
        assertEquals(myWorker.getSex(), sex);
    }


    @Test
    public void setMyGod() {
        myGod = "Athena";
        test.setMyGod(myGod);
        assertEquals(test.getMyGod(), "Athena");
    }

    @Test
    public void getMyGod() {
        myGod = "Apollo";
        test.setMyGod(myGod);
        assertEquals(test.getMyGod(), "Apollo");
    }

}