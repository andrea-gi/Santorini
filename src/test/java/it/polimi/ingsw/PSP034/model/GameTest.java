package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game myGame;
    private Player firstPlayer;
    private Player secondPlayer;
    private Board myBoard;

    @Before
    public void setUp() {
        myBoard = new Board();
        myGame = new Game(myBoard);
        firstPlayer = new Player("Veronica", Color.MAGENTA);
        secondPlayer = new Player("Sara", Color.RED);
    }

    @Test
    public void addPlayer() {
        ArrayList<String> names = new ArrayList<>();
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        names.add(firstPlayer.getName());
        names.add(secondPlayer.getName());
        assertEquals(myGame.getPlayerNumber(), 2);
        assertEquals(myGame.getPlayersName(), names);
    }

    @Test
    public void loser() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        firstPlayer.setHasLost(true);
        assertEquals(myGame.loser(), firstPlayer);
    }

    @Test
    public void addWorker() {
        myGame.addPlayer(firstPlayer);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 3, 3);
        assertEquals(firstPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,3).getWorker());
    }

    @After
    public void tearDown() {
        myGame = null;
        firstPlayer = null;
        secondPlayer = null;
    }
}