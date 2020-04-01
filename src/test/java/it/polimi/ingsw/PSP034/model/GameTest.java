package it.polimi.ingsw.PSP034.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    Game test = null;
    Player player1, player2, player3;

    @Before
    public void setUp() throws Exception {
        test = new Game();
        player1 = new Player("Pippo");
        player2 = new Player("Pluto");
        player3 = new Player("Paperino");
    }

    @Test
    public void addPlayer_validInput() {
        test.addPlayer(player1);
        // assertEquals(test.players, player); ??????????????????????
    }

    @Test
    public void addPlayer_notValidInput() {
        test.addPlayer(null);
        // ??????????????????????
    }

    @Test
    public void getPlayerNumber_1P() {
        test.addPlayer(player1);
        assertEquals(test.getPlayerNumber(), 1);
    }

    @Test
    public void getPlayerNumber_2P() {
        test.addPlayer(player1);
        test.addPlayer(player2);
        assertEquals(test.getPlayerNumber(), 2);
    }

    @Test
    public void getPlayerNumber_3P() {
        test.addPlayer(player1);
        test.addPlayer(player2);
        test.addPlayer(player3);
        assertEquals(test.getPlayerNumber(), 3);
    }

    @Test
    public void getCurrentPlayer_FirstPlayer() {
        test.addPlayer(player1);
        assertEquals(test.getCurrentPlayer(), player1);
    }

    @Test
    public void getCurrentPlayer_2Players() {
        test.addPlayer(player1);
        test.addPlayer(player2);
        assertEquals(test.getCurrentPlayer(), player1);
    }

    @Test
    public void setNextPlayer_2Players() {
        test.addPlayer(player1);
        test.addPlayer(player2);
        test.setNextPlayer();
        assertEquals(test.getCurrentPlayer(), player2);
    }

    @Test
    public void setNextPlayer_2Players_Twice() {
        test.addPlayer(player1);
        test.addPlayer(player2);
        test.setNextPlayer();
        test.setNextPlayer();
        assertEquals(test.getCurrentPlayer(), player1);
    }
}