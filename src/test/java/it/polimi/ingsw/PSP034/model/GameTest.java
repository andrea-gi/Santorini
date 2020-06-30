package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
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
        firstPlayer = new Player("Veronica", PlayerColor.MAGENTA);
        secondPlayer = new Player("Sara", PlayerColor.RED);
        myGame.addRemainingGod("Apollo");
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
        boolean exceptionThrown = false;
        try{
            myGame.addPlayer(null);
        } catch (IllegalArgumentException e){
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }
    }

    @Test
    public void loser() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        firstPlayer.setHasLost(true);
        assertEquals(myGame.loser(), firstPlayer);
        firstPlayer.setHasLost(false);
        assertNull(myGame.loser());
    }

    @Test
    public void addWorker() {
        myGame.addPlayer(firstPlayer);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 3, 3);
        assertEquals(firstPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,3).getWorker());
    }

    @Test
    public void addWorkerNull() {
        myGame.addPlayer(firstPlayer);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 3, 3);
        boolean exceptionThrown = false;
        try{
            myGame.addWorker(firstPlayer, Sex.FEMALE, 2, 2);
        } catch (IllegalArgumentException e){
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }
    }

    @Test
    public void invalidTile() {
        assertNull(myBoard.getTile(-1,3));
    }

    @Test
    public void setRandomPlayer() {
        assertNull(myGame.getCurrentPlayer());
        myGame.addPlayer(firstPlayer);
        assertEquals(myGame.getCurrentPlayer(), firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.setRandomPlayer();
        assertTrue(myGame.getCurrentPlayer().equals(firstPlayer)
                || myGame.getCurrentPlayer().equals(secondPlayer));
    }

    @Test
    public void remainingAndChosenGods() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.addRemainingGod("Artemis");
        assertTrue(myGame.getRemainingGods().contains("Apollo"));
        assertTrue(myGame.getRemainingGods().contains("Artemis"));
        assertEquals(2, myGame.getRemainingGods().size());
        myGame.addRemainingGod(null);
        myGame.addRemainingGod("");
        assertTrue(myGame.getRemainingGods().contains("Apollo"));
        assertTrue(myGame.getRemainingGods().contains("Artemis"));
        assertEquals(2, myGame.getRemainingGods().size());
        assertEquals(0, myGame.getAlreadyChosenGods().size());
        myGame.addGod("Apollo", firstPlayer);
        assertEquals(1, myGame.getAlreadyChosenGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Apollo"));
        myGame.addGod("Artemis", secondPlayer);
        assertEquals(2, myGame.getAlreadyChosenGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Artemis"));
    }

    @Test
    public void playerByName() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player previousCurrentPlayer = myGame.getCurrentPlayer();
        myGame.setCurrentPlayerByName("Giorgio");
        assertEquals(previousCurrentPlayer, myGame.getCurrentPlayer());
        myGame.setCurrentPlayerByName("Sara");
        assertEquals(secondPlayer, myGame.getCurrentPlayer());
    }

    @Test
    public void setNextPlayer() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.setCurrentPlayerByName(firstPlayer.getName());
        myGame.setNextPlayer();
        assertEquals(secondPlayer, myGame.getCurrentPlayer());
        myGame.setNextPlayer();
        assertEquals(thirdPlayer, myGame.getCurrentPlayer());
        myGame.setNextPlayer();
        assertEquals(firstPlayer, myGame.getCurrentPlayer());
    }

    @Test
    public void removePlayer() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.addRemainingGod("Hera");
        myGame.addRemainingGod("Zeus");
        myGame.addGod("Hera", secondPlayer);
        myGame.addGod("Zeus", firstPlayer);
        myGame.addWorker(firstPlayer, Sex.MALE, 1, 1);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 0, 1);
        myGame.addWorker(secondPlayer, Sex.MALE, 1, 3);
        myGame.addWorker(secondPlayer, Sex.FEMALE, 0, 3);
        myGame.removePlayer(firstPlayer);
        assertEquals(1, myGame.getPlayerNumber());
        assertEquals(secondPlayer, myGame.getCurrentPlayer());
        assertEquals("Veronica", myGame.getEliminatedPlayerName());
        boolean exceptionThrown = false;
        try{
            myGame.removePlayer(secondPlayer);
        } catch (IllegalStateException e){
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

    }

    @Test
    public void removeNotCurrentPlayer() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.addRemainingGod("Hera");
        myGame.addRemainingGod("Zeus");
        myGame.addGod("Hera", secondPlayer);
        myGame.addGod("Zeus", firstPlayer);
        myGame.addWorker(firstPlayer, Sex.MALE, 1, 1);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 0, 1);
        myGame.addWorker(secondPlayer, Sex.MALE, 1, 3);
        myGame.addWorker(secondPlayer, Sex.FEMALE, 0, 3);
        myGame.setCurrentPlayerByName("Sara");
        myGame.removePlayer(firstPlayer);
        assertEquals(1, myGame.getPlayerNumber());
        assertEquals(secondPlayer, myGame.getCurrentPlayer());
        assertEquals("Veronica", myGame.getEliminatedPlayerName());
        boolean exceptionThrown = false;
        try{
            myGame.removePlayer(secondPlayer);
        } catch (IllegalStateException e){
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }
    }

    @Test
    public void addGodAtlasDemeter() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.addRemainingGod("Atlas");
        myGame.addRemainingGod("Demeter");
        myGame.addGod("Atlas", firstPlayer);
        myGame.addGod("Demeter", secondPlayer);
        myGame.addGod("Apollo", thirdPlayer);
        assertEquals(3, myGame.getAlreadyChosenGods().size());
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Atlas"));
        assertTrue(myGame.getAlreadyChosenGods().contains("Demeter"));
    }

    @Test
    public void addGodHephaestusHera() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.addRemainingGod("Hephaestus");
        myGame.addRemainingGod("Hera");
        myGame.addGod("Hephaestus", firstPlayer);
        myGame.addGod("Hera", secondPlayer);
        myGame.addGod("Apollo", thirdPlayer);
        assertEquals(3, myGame.getAlreadyChosenGods().size());
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Hephaestus"));
        assertTrue(myGame.getAlreadyChosenGods().contains("Hera"));
    }

    @Test
    public void addGodHestiaLimus() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.addRemainingGod("Hestia");
        myGame.addRemainingGod("Limus");
        myGame.addGod("Hestia", firstPlayer);
        myGame.addGod("Limus", secondPlayer);
        myGame.addGod("Apollo", thirdPlayer);
        assertEquals(3, myGame.getAlreadyChosenGods().size());
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Hestia"));
        assertTrue(myGame.getAlreadyChosenGods().contains("Limus"));
    }

    @Test
    public void addGodMinotaurPan() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.addRemainingGod("Minotaur");
        myGame.addRemainingGod("Pan");
        myGame.addGod("Minotaur", firstPlayer);
        myGame.addGod("Pan", secondPlayer);
        myGame.addGod("Apollo", thirdPlayer);
        assertEquals(3, myGame.getAlreadyChosenGods().size());
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Minotaur"));
        assertTrue(myGame.getAlreadyChosenGods().contains("Pan"));
    }

    @Test
    public void addGodPrometheusTriton() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.addRemainingGod("Prometheus");
        myGame.addRemainingGod("Triton");
        myGame.addGod("Prometheus", firstPlayer);
        myGame.addGod("Triton", secondPlayer);
        myGame.addGod("Apollo", thirdPlayer);
        assertEquals(3, myGame.getAlreadyChosenGods().size());
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Prometheus"));
        assertTrue(myGame.getAlreadyChosenGods().contains("Triton"));
    }

    @Test
    public void addGodZeusAthena() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        Player thirdPlayer = new Player("Beatrice", PlayerColor.BLUE);
        myGame.addPlayer(thirdPlayer);
        myGame.addRemainingGod("Zeus");
        myGame.addRemainingGod("Athena");
        myGame.addGod("Zeus", firstPlayer);
        myGame.addGod("Athena", secondPlayer);
        myGame.addGod("Apollo", thirdPlayer);
        assertEquals(3, myGame.getAlreadyChosenGods().size());
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(myGame.getAlreadyChosenGods().contains("Zeus"));
        assertTrue(myGame.getAlreadyChosenGods().contains("Athena"));
    }

    @Test
    public void slimBoard() {
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.addRemainingGod("Zeus");
        myGame.addGod("Zeus", firstPlayer);
        myGame.addGod("Apollo", secondPlayer);
        myGame.addWorker(firstPlayer, Sex.MALE, 1, 1);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 0, 1);
        myGame.addWorker(secondPlayer, Sex.MALE, 1, 3);
        myGame.addWorker(secondPlayer, Sex.FEMALE, 0, 3);
        myBoard.getTile(3, 2).setBuilding(2);
        myBoard.getTile(1, 0).setBuilding(3);
        myBoard.getTile(3, 0).setDome(true);
        myBoard.getTile(0, 0).setDome(true);
        SlimBoard slimBoard = myGame.generateSlimBoard();
        assertEquals(slimBoard.getCurrentPlayer(), myGame.getCurrentPlayer().getName());
        Sex[][] boardSex = slimBoard.getSex();
        PlayerColor[][] boardColor = slimBoard.getColor();
        int[][] boardBuildings = slimBoard.getBuilding();
        boolean[][] boardDome = slimBoard.getDome();
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                if (myBoard.getTile(i, j).hasDome())
                    assertTrue(boardDome[i][j]);
                assertEquals(myBoard.getTile(i, j).getBuilding(), boardBuildings[i][j]);
                if (myBoard.getTile(i, j).getWorker() == null) {
                    assertNull(boardColor[i][j]);
                    assertNull(boardSex[i][j]);
                }
                else {
                    assertEquals(myBoard.getTile(i, j).getWorker().getColor(), boardColor[i][j]);
                    assertEquals(myBoard.getTile(i, j).getWorker().getSex(), boardSex[i][j]);
                }
            }
        }
    }


    @After
    public void tearDown() {
        myGame = null;
        firstPlayer = null;
        secondPlayer = null;
    }
}