package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;
import it.polimi.ingsw.PSP034.view.GodDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GodsRulesTest {
    private Game myGame;
    private Board myBoard;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;

    @Before
    public void setUp() {
        myBoard = new Board();
        myGame = new Game(myBoard);
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

    //Metodo ausiliario
    private void setGods(String firstGod, String secondGod, String thirdGod){
        myGame.addRemainingGod(firstGod);
        myGame.addRemainingGod(secondGod);
        myGame.addRemainingGod(thirdGod);
        myGame.addGod(firstGod, firstPlayer);
        myGame.addGod(secondGod, secondPlayer);
        myGame.addGod(thirdGod, thirdPlayer);
    }

    private void setBoardBuildings(int[][] buildings){
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                myBoard.getTile(i, j).setBuilding(buildings[i][j]);
            }
        }
    }

    @Test
    public void getPlayerSuccessAllGods(){
        String[][] gods = {{"Apollo", "Artemis", "Athena"},
                {"Atlas", "Demeter", "Hephaestus"},
                {"Minotaur", "Pan", "Prometheus"},
                {"Limus", "Hestia", "Hera"}, //NOTE: for the test to work properly Limus has to be the first god
                {"Triton", "Zeus", "Apollo"}};
        for(String[] group : gods){
            setUp();

            setGods(group[0], group[1], group[2]);
            Player currentPlayer = myGame.getCurrentPlayer();
            GodsRules currentGod = currentPlayer.getMyGod();
            assertEquals(currentPlayer, currentGod.getPlayer());

            myGame.setNextPlayer();
            currentPlayer = myGame.getCurrentPlayer();
            currentGod = currentPlayer.getMyGod();
            assertEquals(currentPlayer, currentGod.getPlayer());

            myGame.setNextPlayer();
            currentPlayer = myGame.getCurrentPlayer();
            currentGod = currentPlayer.getMyGod();
            assertEquals(currentPlayer, currentGod.getPlayer());

            tearDown();
        }
    }

    @Test
    public void normalValidMoveSuccessAtlasDemeterHephaestus() {
        setGods("Atlas", "Demeter", "Hephaestus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3)));
    }

    @Test
    public void normalValidMoveFailAtlasDemeterHephaestus() {
        setGods("Atlas", "Demeter", "Hephaestus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,0)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,2)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,4)));
    }

    @Test
    public void normalValidMoveSuccessHeraHestiaLimus() {
        setGods("Hera", "Hestia", "Limus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3)));
    }

    @Test
    public void normalValidMoveFailHeraHestiaLimus() {
        setGods("Hera", "Hestia", "Limus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,0)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,2)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,4)));
    }

    @Test
    public void normalValidMoveSuccessPanPrometheusTriton() {
        setGods("Pan", "Prometheus", "Triton");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(3,4)));
    }

    @Test
    public void normalValidMoveFailPanPrometheusTriton() {
        setGods("Pan", "Prometheus", "Triton");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,2)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,4)));
    }

    @Test
    public void normalValidMoveSuccessZeusApolloArtemisOneLevelUp() {
        setGods("Zeus", "Apollo", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(1,0).setBuilding(1);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        myBoard.getTile(2,2).setBuilding(1);
        myBoard.getTile(2,3).setBuilding(2);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        myBoard.getTile(4,4).setBuilding(2);
        myBoard.getTile(4,3).setBuilding(3);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3)));
    }

    @Test
    public void normalValidMoveFailZeusTwoLevelsUp() {
        setGods("Zeus", "Prometheus", "Triton");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(1,0).setBuilding(2);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));
    }

    @Test
    public void normalValidMoveFailZeusDome() {
        setGods("Zeus", "Apollo", "Triton");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(0,0).setBuilding(2);
        myBoard.getTile(1,0).setBuilding(3);
        myBoard.getTile(1,0).setDome(true);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));
    }


    @Test
    public void validMoveSuccessApollo() {
        setGods("Apollo", "Athena", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(2,2).setBuilding(1);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,2)));
        myBoard.getTile(2,2).setBuilding(0);
        myBoard.getTile(1,1).setBuilding(2);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,2)));
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));
    }

    @Test
    public void validMoveFailApolloSameOwnerSwap() {
        setGods("Apollo", "Athena", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,1)));
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,0)));
    }

    @Test
    public void validMoveFailApolloInaccessibleTile() {
        setGods("Apollo", "Athena", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(2,2).setBuilding(2);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,2)));
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,3)));
    }

    @Test
    public void validMoveSuccessArtemisSecondMove() {
        setGods("Artemis", "Athena", "Zeus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(1,0), false);
        currentGod.executeState(TurnPhase.POWER, null, null, true);
        myBoard.getTile(1,1).setBuilding(2);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,0)));
    }

    @Test
    public void validMoveFailArtemisSecondMove() {
        setGods("Artemis", "Limus", "Triton");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(1,0), false);
        currentGod.executeState(TurnPhase.POWER, null, null, true);
        myBoard.getTile(1,1).setBuilding(1);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(1,1)));
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,3)));
    }

    @Test
    public void validMoveSuccessAthenaMovingUp() {
        setGods("Athena", "Hera", "Zeus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(2,1).setBuilding(1);
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,1), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE).getMyTile(), myBoard.getTile(2, 1));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        myBoard.getTile(4,3).setBuilding(1);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        myBoard.getTile(3,1).setBuilding(1);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,1)));
    }

    @Test
    public void validMoveFailAthenaNotMovingUp() {
        setGods("Athena", "Minotaur", "Triton");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,1), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE).getMyTile(), myBoard.getTile(2, 1));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        myBoard.getTile(4,3).setBuilding(1);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,3)));
        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        myBoard.getTile(3,1).setBuilding(1);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,1)));
    }

    @Test
    public void validMoveSuccessMinotaur() {
        setGods("Zeus", "Minotaur", "Hera");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0), false);
        assertEquals(currentPlayer.getWorker(Sex.MALE).getMyTile(), myBoard.getTile(1, 0));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();

        //Diagonal push (>1 level up)
        myBoard.getTile(0,0).setBuilding(3);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,1)));

        //Vertical push
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,2)));
    }

    @Test
    public void validMoveFailMinotaurInvalidPush() {
        setGods("Zeus", "Minotaur", "Hera");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0), false);
        assertEquals(currentPlayer.getWorker(Sex.MALE).getMyTile(), myBoard.getTile(1, 0));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();

        //Diagonal dome push
        myBoard.getTile(0,0).setBuilding(3);
        myBoard.getTile(0,0).setDome(true);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,1)));

        //Same owner push
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,2)));

        //Out of board push
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,4)));

        //>1 level move
        myBoard.getTile(2,1).setBuilding(2);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,1)));

        //dome move
        myBoard.getTile(2,1).setBuilding(0);
        myBoard.getTile(2,1).setDome(true);
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,1)));

        //not neighbouring tile
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3)));

        //same tile
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,2)));
    }


    @Test
    public void validMoveSuccessTritonPerimeter() {
        setGods("Triton", "Athena", "Zeus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(1,0), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE).getMyTile(), myBoard.getTile(1, 0));
        currentGod.executeState(TurnPhase.POWER, null, null, true);
        myBoard.getTile(2,0).setBuilding(1);
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,0), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE).getMyTile(), myBoard.getTile(2, 0));
        currentGod.executeState(TurnPhase.POWER, null, null, true);
        myBoard.getTile(3,0).setBuilding(2);
        assertTrue(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(3,0)));
    }

    @Test
    public void validMoveFailTritonPerimeter() {
        setGods("Triton", "Limus", "Hestia");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(0,1), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE).getMyTile(), myBoard.getTile(0, 1));
        currentGod.executeState(TurnPhase.POWER, null, null, true);
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(1,2), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE).getMyTile(), myBoard.getTile(1, 2));
        assertFalse(currentGod.getCompleteRules().validMove(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,3)));
    }

    @Test
    public void anyValidMoveSuccess() {
        setGods("Triton", "Limus", "Hestia");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        assertTrue(currentGod.anyValidMove(currentPlayer.getWorker(Sex.MALE)));
    }

    @Test
    public void anyValidMoveEmpty() {
        setGods("Apollo", "Demeter", "Prometheus");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(0,1).setDome(true);
        myBoard.getTile(1,0).setBuilding(2);
        assertFalse(currentGod.anyValidMove(currentPlayer.getWorker(Sex.MALE)));
    }

    @Test
    public void availableMovingTilesSuccess() {
        setGods("Triton", "Limus", "Hestia");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        ArrayList<Tile> tiles = GodsRules.availableMovingTiles(currentPlayer.getWorker(Sex.MALE));
        assertEquals(2, tiles.size());
        assertTrue(tiles.contains(myBoard.getTile(0,1)));
        assertTrue(tiles.contains(myBoard.getTile(1,0)));
    }

    @Test
    public void availableMovingTilesNoTile() {
        setGods("Triton", "Limus", "Hestia");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(0,1).setDome(true);
        myBoard.getTile(1,0).setBuilding(2);
        ArrayList<Tile> tiles = GodsRules.availableMovingTiles(currentPlayer.getWorker(Sex.MALE));
        assertEquals(0, tiles.size());
    }

    @Test
    public void checkMoveLostTrue() {
        setGods("Triton", "Limus", "Hestia");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(0,1).setDome(true);
        myBoard.getTile(1,0).setBuilding(2);
        myBoard.getTile(2,0).setDome(true);
        myBoard.getTile(0,2).setDome(true);
        myBoard.getTile(2,1).setBuilding(2);
        myBoard.getTile(1,2).setBuilding(3);
        assertTrue(currentGod.checkMoveLost(currentPlayer));
    }

    @Test
    public void checkMoveLostFalse() {
        setGods("Triton", "Limus", "Hestia");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        myBoard.getTile(0,1).setDome(true);
        myBoard.getTile(1,0).setBuilding(2);
        myBoard.getTile(2,0).setDome(true);
        myBoard.getTile(2,1).setBuilding(2);
        myBoard.getTile(1,2).setBuilding(3);
        assertFalse(currentGod.checkMoveLost(currentPlayer));
    }

    @Test
    public void normalMoveSuccessAllGods(){
        String[][] gods = {{"Apollo", "Artemis", "Athena"},
                {"Atlas", "Demeter", "Hephaestus"},
                {"Minotaur", "Pan", "Prometheus"},
                {"Limus", "Hestia", "Hera"}, //NOTE: for the test to work properly Limus has to be the first god
                {"Triton", "Zeus", "Apollo"}};
        for(String[] group : gods){
            setUp();
            System.out.println(group[0] + " " + group[1] + " " + group[2]);
            normalMoveSuccess(group[0], group[1], group[2]);
            tearDown();
        }
    }

    private void normalMoveSuccess(String firstGod, String secondGod, String thirdGod){
        setGods(firstGod, secondGod, thirdGod);

        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);
        assertEquals(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1).getWorker());

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,3), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,3).getWorker());

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3), false);
        assertEquals(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3).getWorker());
    }

    @Test
    public void specialMoveApolloSuccess(){
        setGods("Apollo","Athena","Artemis");

        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,2), false);
        assertEquals(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,2).getWorker());

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        assertEquals(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,1).getWorker());
    }

    @Test
    public void specialMoveApolloFail(){
        setGods("Apollo","Athena","Artemis");

        //Trying to swap with own worker
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,1), false);
        assertEquals(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,0).getWorker());
        assertEquals(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(1,1).getWorker());
    }


    @Test
    public void normalValidBuildSuccessAllGods() {
        String[][] gods = {{"Apollo", "Artemis", "Athena"},
                          {"Atlas", "Demeter", "Hephaestus"},
                          {"Minotaur", "Pan", "Prometheus"},
                          {"Limus", "Hestia", "Hera"}, //NOTE: for the test to work properly Limus has to be the first god
                          {"Triton", "Zeus", "Apollo"}};
        for(String[] group : gods){
            setUp();
            System.out.println(group[0] + " " + group[1] + " " + group[2]);
            normalValidBuildSuccess(group[0], group[1], group[2]);
            tearDown();
        }
    }

    private void normalValidBuildSuccess(String firstGod, String secondGod, String thirdGod) {
        setGods(firstGod, secondGod, thirdGod);
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();

        // Valid build requires the correct worker (the one who has moved before)
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0, 1)));
        myGame.setNextPlayer();

        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,3)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3)));
    }

    @Test
    public void normalValidBuildFailAllGods(){
        String[][] gods = {{"Apollo", "Artemis", "Athena"},
                {"Atlas", "Demeter", "Hephaestus"},
                {"Minotaur", "Pan", "Prometheus"},
                {"Limus", "Hestia", "Hera"}, //NOTE: for the test to work properly Limus has to be the first god
                {"Triton", "Zeus", "Apollo"}};
        for(String[] group : gods){
            setUp();
            System.out.println(group[0] + " " + group[1] + " " + group[2]);
            normalValidBuildFail(group[0], group[1], group[2]);
            tearDown();
        }
    }

    private void normalValidBuildFail(String firstGod, String secondGod, String thirdGod) {
        setGods(firstGod, secondGod, thirdGod);
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();

        //trying to build with the wrong worker
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(0,1)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(2,3)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,3)));

        //trying to build on other worker
        myGame.setNextPlayer();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,1)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(4,4)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(3,3)));

        //trying to build on dome
        myBoard.getTile(0,1).setBuilding(3);
        myBoard.getTile(0,1).setDome(true);
        myBoard.getTile(2,3).setBuilding(2);
        myBoard.getTile(2,3).setDome(true);
        myBoard.getTile(4,3).setBuilding(3);
        myBoard.getTile(4,3).setDome(true);

        myGame.setNextPlayer();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.FEMALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.FEMALE), myBoard.getTile(2,3)));

        myGame.setNextPlayer();
        currentPlayer = myGame.getCurrentPlayer();
        currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(4,3)));
    }

    @Test
    public void normalBuildSuccessAllGods(){
        String[][] gods = {{"Apollo", "Artemis", "Athena"},
                {"Atlas", "Demeter", "Hephaestus"},
                {"Minotaur", "Pan", "Prometheus"},
                {"Limus", "Hestia", "Hera"}, //NOTE: for the test to work properly Limus has to be the first god
                {"Triton", "Zeus", "Apollo"}};
        for(String[] group : gods){
            setUp();
            System.out.println(group[0] + " " + group[1] + " " + group[2]);
            normalBuildSuccess(group[0], group[1], group[2]);
            tearDown();
        }
    }

    private void normalBuildSuccess(String firstGod, String secondGod, String thirdGod){
        setGods(firstGod, secondGod, thirdGod);

        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);
        assertEquals(1, myBoard.getTile(0,1).getBuilding());

        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);
        assertEquals(2, myBoard.getTile(0,1).getBuilding());

        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);
        assertEquals(3, myBoard.getTile(0,1).getBuilding());

        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);
        assertEquals(3, myBoard.getTile(0,1).getBuilding());
        assertTrue(myBoard.getTile(0,1).hasDome());
    }

    @Test
    public void specialDomeBuildAtlasSuccess(){
        setGods("Atlas", "Apollo", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));

        //Building dome on level 0
        currentGod.executeState(TurnPhase.POWER, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), true);

        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));

        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        assertEquals(0, myBoard.getTile(0,1).getBuilding());
        assertTrue(myBoard.getTile(0,1).hasDome());
    }

    @Test
    public void secondBuildDemeterSuccess(){
        setGods("Demeter", "Apollo", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));

        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        currentGod.executeState(TurnPhase.POWER, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0), true);
        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));

        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0), false);

        assertEquals(1, myBoard.getTile(0,1).getBuilding());
        assertEquals(1, myBoard.getTile(1,0).getBuilding());
    }

    @Test
    public void secondBuildDemeterFail(){
        setGods("Demeter", "Apollo", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));

        //Trying to build on previous tile
        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        currentGod.executeState(TurnPhase.POWER, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), true);
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));

        assertEquals(1, myBoard.getTile(0,1).getBuilding());
    }

    @Test
    public void secondBuildHephaestusSuccess(){
        setGods("Hephaestus", "Apollo", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));

        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        currentGod.executeState(TurnPhase.POWER, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), true);
        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));

        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        assertEquals(2, myBoard.getTile(0,1).getBuilding());
    }

    @Test
    public void secondBuildHephaestusFail(){
        setGods("Hephaestus", "Apollo", "Artemis");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();
        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));

        assertTrue(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        //Trying to build on different tile
        currentGod.executeState(TurnPhase.POWER, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0), true);
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(1,0)));
        assertEquals(0, myBoard.getTile(1,0).getBuilding());

        //Trying to build a dome
        myBoard.getTile(0,1).setBuilding(3);
        currentGod.executeState(TurnPhase.POWER, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), true);
        assertFalse(currentGod.getCompleteRules().validBuild(currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1)));
        assertEquals(3, myBoard.getTile(0,1).getBuilding());
        assertFalse(myBoard.getTile(0,1).hasDome());
    }







    @Test
    public void nextStateSuccessArtemisAthena(){
        setGods( "Apollo", "Artemis", "Athena");
        Player currentPlayer = myGame.getCurrentPlayer();
        GodsRules currentGod = currentPlayer.getMyGod();

        //////////
        //Apollo//
        //////////
        //START
        currentGod.executeState(TurnPhase.START, null, null, false);

        NextStateInfo expected = new NextStateInfo(TurnPhase.MOVE, RequiredActions.REQUEST_WORKER, RequiredActions.REQUEST_MOVE);
        NextStateInfo actual = currentGod.nextState(TurnPhase.START);
        assertEquals(expected.getNextPhase(), actual.getNextPhase());
        assertEquals(expected.getRequiredActions(), actual.getRequiredActions());


        //MOVE
        currentGod.executeState(TurnPhase.MOVE, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,1), false);

        currentGod.getDefaultRules().setChosenSex(currentPlayer.getWorker(Sex.MALE));
        expected = new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(Sex.MALE), RequiredActions.REQUEST_BUILD);
        actual = currentGod.nextState(TurnPhase.MOVE);
        assertEquals(expected.getNextPhase(), actual.getNextPhase());
        assertEquals(expected.getRequiredActions(), actual.getRequiredActions());


        //BUILD
        currentGod.executeState(TurnPhase.BUILD, currentPlayer.getWorker(Sex.MALE), myBoard.getTile(0,2), false);

        expected = new NextStateInfo(TurnPhase.END);
        actual = currentGod.nextState(TurnPhase.BUILD);
        assertEquals(expected.getNextPhase(), actual.getNextPhase());
        assertEquals(expected.getRequiredActions(), actual.getRequiredActions());
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