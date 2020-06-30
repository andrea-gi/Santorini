package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.PersonalDefeatRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.WinnerRequest;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.model.Board;
import it.polimi.ingsw.PSP034.model.Game;
import it.polimi.ingsw.PSP034.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TurnHandlerTest {
    private Controller controller;
    private ArgumentCaptor<String> sendToSingleName;
    private ArgumentCaptor<Request> sendToSingleRequest;
    private ArgumentCaptor<String> sendToAllExceptName;
    private ArgumentCaptor<Request> sendToAllExceptRequest;
    private ArgumentCaptor<Request> sendToAllRequest;
    private Player firstPlayer;
    private Player secondPlayer;
    private Board myBoard;
    private Game myGame;
    private boolean first;

    private TurnHandler turnHandler;

    @Before
    public void setUp() {
        /*
         * Creating a new game and initializing a board (tested in model)
         */
        myBoard = new Board();
        myGame = new Game(myBoard);
        firstPlayer = new Player("Veronica", PlayerColor.RED);
        secondPlayer = new Player("Andrea", PlayerColor.BLUE);
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.setCurrentPlayerByName("Veronica");
        myGame.addWorker(firstPlayer, Sex.MALE, 0,0);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 1,1);
        myGame.addWorker(secondPlayer, Sex.MALE, 2,2);
        myGame.addWorker(secondPlayer, Sex.FEMALE, 3,3);

        /*
         * Creating a mock controller
         * */
        controller = mock(Controller.class);
        sendToSingleName = ArgumentCaptor.forClass(String.class);
        sendToSingleRequest = ArgumentCaptor.forClass(Request.class);
        sendToAllExceptName = ArgumentCaptor.forClass(String.class);
        sendToAllExceptRequest = ArgumentCaptor.forClass(Request.class);
        sendToAllRequest = ArgumentCaptor.forClass(Request.class);
        first = true;
        when(controller.getPlayersName()).thenReturn(myGame.getPlayersName());
        when(controller.isGameOver()).thenReturn(true);

        doAnswer(invocation -> {
            first = !first;
            when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);
            when(controller.getCurrentGod()).thenReturn(first ? firstPlayer.getMyGod() : secondPlayer.getMyGod());
            return null;
        }).when(controller).setNextPlayer();

        doNothing().when(controller).sendToPlayer(sendToSingleName.capture(), sendToSingleRequest.capture());
        doNothing().when(controller).sendToAllExcept(sendToAllExceptName.capture(), sendToAllExceptRequest.capture(), anyBoolean());
        doNothing().when(controller).sendToAll(sendToAllRequest.capture(), anyBoolean());
        doNothing().when(controller).handleGamePhase();
        doNothing().when(controller).setNextGamePhase();

        turnHandler = new TurnHandler(controller);
    }

    private void setGods(String firstGod, String secondGod){
        myGame.addRemainingGod(firstGod);
        myGame.addRemainingGod(secondGod);
        myGame.addGod(firstGod, firstPlayer);
        myGame.addGod(secondGod, secondPlayer);
        turnHandler.setCurrentGod(myGame.getCurrentPlayer().getMyGod());
        when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);
        when(controller.getCurrentGod()).thenReturn(first ? firstPlayer.getMyGod() : secondPlayer.getMyGod());
    }


    @Test
    public void executeStartNormal() {
        setGods("Apollo", "Athena");
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.MOVE);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.MOVE);
    }

    @Test
    public void executeStartPower() {
        setGods("Prometheus", "Athena");
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestBooleanChoice && ((RequestBooleanChoice) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.POWER);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.POWER);
    }

    @Test
    public void executeStartNotValid() {
        setGods("Apollo", "Athena");
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.S)); // Wrong answer to START
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.MOVE);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.MOVE); // New MOVE request
    }

    @Test
    public void executeMoveNormal() {
        setGods("Zeus", "Athena");
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.MOVE);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.MOVE);
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.S));
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.BUILD);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.BUILD);
    }

    @Test
    public void executeMoveNotValid() {
        setGods("Zeus", "Athena");
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.MOVE);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.MOVE);
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.N)); // Invalid direction
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.MOVE);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.MOVE);
    }

    @Test
    public void executeBuildNormal() {
        setGods("Pan", "Hera");
        turnHandler.executeSelectedState(new AnswerStart());
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.S));
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.S));
        assertEquals(controller.getCurrentPlayer(), secondPlayer);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.START);
    }

    @Test
    public void executeBuildNotValid() {
        setGods("Hera", "Pan");
        turnHandler.executeSelectedState(new AnswerStart());
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.S));
        turnHandler.executeSelectedState(new AnswerAction(Sex.MALE, Directions.E)); // Invalid direction
        assertEquals(controller.getCurrentPlayer(), firstPlayer);
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.BUILD);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.BUILD);
        turnHandler.executeSelectedState(new AnswerStart()); // Invalid message
        assertEquals(controller.getCurrentPlayer(), firstPlayer);
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.BUILD);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.BUILD);
    }

    @Test
    public void executePower() {
        setGods("Prometheus", "Pan");
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestBooleanChoice && ((RequestBooleanChoice) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.POWER);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.POWER);
        turnHandler.executeSelectedState(new AnswerBooleanChoice(true));
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestAction && ((RequestAction) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.BUILD);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.BUILD);
    }

    @Test
    public void executePowerNotValid() {
        setGods("Prometheus", "Pan");
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestBooleanChoice && ((RequestBooleanChoice) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.POWER);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.POWER);
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestBooleanChoice && ((RequestBooleanChoice) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.POWER);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.POWER);
    }

    @Test
    public void manageWin() {
        setGods("Limus", "Hestia");
        myBoard.getTile(1,1).setBuilding(2);
        myBoard.getTile(1,0).setBuilding(3);
        turnHandler.executeSelectedState(new AnswerStart());
        turnHandler.executeSelectedState(new AnswerAction(Sex.FEMALE, Directions.N));
        assertEquals(sendToSingleName.getValue(), firstPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof WinnerRequest && ((WinnerRequest) sendToSingleRequest.getValue()).getLoser().equals("") &&
                ((WinnerRequest) sendToSingleRequest.getValue()).getWinner().equals(firstPlayer.getName()));
        assertEquals(sendToAllExceptName.getValue(), firstPlayer.getName());
        assertTrue(sendToAllExceptRequest.getValue() instanceof PersonalDefeatRequest);
        assertEquals(((PersonalDefeatRequest) sendToAllExceptRequest.getValue()).getWinner(), firstPlayer.getName());
        assertEquals(((PersonalDefeatRequest) sendToAllExceptRequest.getValue()).getLosers()[0], secondPlayer.getName());
    }

    @Test
    public void manageGameOver() {
        setGods("Limus", "Hestia");
        myBoard.getTile(0,1).setBuilding(2);
        myBoard.getTile(1,0).setBuilding(3);
        myBoard.getTile(2,0).setBuilding(2);
        myBoard.getTile(2,1).setBuilding(2);
        myBoard.getTile(0,2).setBuilding(2);
        myBoard.getTile(1,2).setBuilding(2);
        turnHandler.executeSelectedState(new AnswerStart());

    }

    @Test
    public void manageGameOverNotEndingGame() {
        when(controller.isGameOver()).thenReturn(false);
        setGods("Limus", "Hestia");
        myBoard.getTile(0,1).setBuilding(2);
        myBoard.getTile(1,0).setBuilding(3);
        myBoard.getTile(2,0).setBuilding(2);
        myBoard.getTile(2,1).setBuilding(2);
        myBoard.getTile(0,2).setBuilding(2);
        myBoard.getTile(1,2).setBuilding(2);
        doAnswer(invocation -> {
            controller.setNextPlayer();
            return null;
        }).when(controller).isGameOver();
        turnHandler.executeSelectedState(new AnswerStart());
        assertEquals(sendToSingleName.getValue(), secondPlayer.getName());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestStart && ((RequestStart) sendToSingleRequest.getValue()).getNextPhase() == TurnPhase.START);
        assertSame(turnHandler.getMyTurnPhase(), TurnPhase.START);
        assertEquals(sendToAllExceptName.getValue(), secondPlayer.getName());
        assertTrue(sendToAllExceptRequest.getValue() instanceof InfoIsStarting && ((InfoIsStarting) sendToAllExceptRequest.getValue()).getPlayer().equals(secondPlayer.getName()));
    }

}