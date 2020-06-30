package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultRulesTest {
    private Game myGame;
    private Board myBoard;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;

    private DefaultRules defaultRules;

    @Before
    public void setUp() {
        myBoard = new Board();
        myGame = new Game(myBoard);
        defaultRules = new DefaultRules(myGame);
        firstPlayer = new Player("Veronica", PlayerColor.RED);
        secondPlayer = new Player("Andrea", PlayerColor.BLUE);
        thirdPlayer = new Player("Lorenzo", PlayerColor.MAGENTA);
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.addPlayer(thirdPlayer);
        myGame.setCurrentPlayerByName("Veronica");
        myGame.addWorker(firstPlayer, Sex.MALE, 0,0);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 1,1);
        myGame.addWorker(secondPlayer, Sex.MALE, 2,2);
        myGame.addWorker(secondPlayer, Sex.FEMALE, 3,3);
        myGame.addWorker(thirdPlayer, Sex.MALE, 4,4);
        myGame.addWorker(thirdPlayer, Sex.FEMALE, 3,2);
    }

    @Test
    public void anyValidMove() {
        Player currentPlayer = myGame.getCurrentPlayer();
        assertTrue(defaultRules.anyValidMove(currentPlayer.getWorker(Sex.MALE)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        assertTrue(defaultRules.anyValidMove(currentPlayer.getWorker(Sex.FEMALE)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        assertTrue(defaultRules.anyValidMove(currentPlayer.getWorker(Sex.MALE)));
    }

    @Test
    public void checkMoveLostTrue() {
        myBoard.getTile(1,0).setBuilding(2);
        myBoard.getTile(0,1).setBuilding(2);
        myBoard.getTile(2,0).setBuilding(2);
        myBoard.getTile(0,2).setBuilding(2);
        myBoard.getTile(1,2).setBuilding(2);
        myBoard.getTile(2,1).setBuilding(2);
        assertTrue(defaultRules.checkMoveLost(firstPlayer));
    }

    @Test
    public void checkMoveLostFalse() {
        myBoard.getTile(2,0).setBuilding(2);
        myBoard.getTile(0,2).setBuilding(2);
        myBoard.getTile(1,2).setBuilding(2);
        myBoard.getTile(2,1).setBuilding(2);
        assertFalse(defaultRules.checkMoveLost(firstPlayer));
    }

    @Test
    public void anyValidBuild() {
        Player currentPlayer = myGame.getCurrentPlayer();
        defaultRules.setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertTrue(defaultRules.anyValidBuild(currentPlayer.getWorker(Sex.MALE)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        defaultRules.setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        assertTrue(defaultRules.anyValidBuild(currentPlayer.getWorker(Sex.FEMALE)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        defaultRules.setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertTrue(defaultRules.anyValidBuild(currentPlayer.getWorker(Sex.MALE)));
    }

    @Test
    public void checkBuildLostTrue() {
        myBoard.getTile(1,0).setDome(true);
        myBoard.getTile(0,1).setDome(true);
        myBoard.getTile(2,0).setDome(true);
        myBoard.getTile(0,2).setDome(true);
        myBoard.getTile(1,2).setDome(true);
        myBoard.getTile(2,1).setDome(true);
        Player currentPlayer = myGame.getCurrentPlayer();
        defaultRules.setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertTrue(defaultRules.checkBuildLost(currentPlayer));
    }

    @Test
    public void checkBuildLostFalse() {
        myBoard.getTile(1,0).setDome(true);
        myBoard.getTile(2,0).setDome(true);
        myBoard.getTile(0,2).setDome(true);
        myBoard.getTile(1,2).setDome(true);
        myBoard.getTile(2,1).setDome(true);
        Player currentPlayer = myGame.getCurrentPlayer();
        defaultRules.setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(defaultRules.checkBuildLost(currentPlayer));
    }

    @After
    public void tearDown() {
        myBoard = null;
        myGame = null;
        firstPlayer = null;
        secondPlayer = null;
        thirdPlayer = null;
    }
}