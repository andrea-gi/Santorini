package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.PersonalDefeatRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.SingleLoserInfo;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.WinnerRequest;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerCardsChoice;
import it.polimi.ingsw.PSP034.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ControllerAndMessageManagerTest {
    Controller controller;
    ArgumentCaptor<String> name;
    ArgumentCaptor<Request> request;
    Server serverMock;

    @Before
    public void setUp() {
        serverMock = mock(Server.class);
        name = ArgumentCaptor.forClass(String.class);
        request = ArgumentCaptor.forClass(Request.class);
        doNothing().when(serverMock).asyncSendTo(name.capture(), request.capture());
        controller = new Controller(serverMock);
    }

    @Test
    public void addPlayer() {
        controller.addPlayer("Andrea", PlayerColor.BLUE);
        assertEquals(1, controller.getPlayerNumber());
        assertEquals("Andrea", controller.getPlayersName().get(0));
        controller.addPlayer("Lorenzo", PlayerColor.RED);
        assertEquals(2, controller.getPlayerNumber());
        assertEquals("Lorenzo", controller.getPlayersName().get(1));
    }

    @Test
    public void addRemainingGod() {
        controller.addRemainingGod("Apollo");
        assertEquals(1, controller.getRemainingGods().size());
        assertEquals("Apollo", controller.getRemainingGods().get(0));
    }

    @Test
    public void sendToPlayerSuccess() {
        controller.sendToPlayer("Veronica", new RequestStart(new NextStateInfo(TurnPhase.START)));
        assertEquals("Veronica", name.getValue());
        assertEquals(TurnPhase.START, request.getValue() instanceof RequestStart ? ((RequestStart) request.getValue()).getNextPhase() : null);
    }

    @Test
    public void sendToFail() {
        controller.sendToAll(new RequestStart(new NextStateInfo(TurnPhase.START)), true);
        assertEquals(0, name.getAllValues().size());
    }

    @Test
    public void sendToAll() {
        controller.addPlayer("Lorenzo", PlayerColor.RED);
        controller.addPlayer("Andrea", PlayerColor.BLUE);
        controller.sendToAll(new RequestStart(new NextStateInfo(TurnPhase.START)), true);
        assertTrue(name.getAllValues().contains("Lorenzo") && name.getAllValues().contains("Andrea"));
        assertEquals(2, name.getAllValues().size());
        assertEquals(TurnPhase.START, request.getValue() instanceof RequestStart ? ((RequestStart) request.getValue()).getNextPhase() : null);
    }

    @Test
    public void sendToAllExcept() {
        controller.addPlayer("Lorenzo", PlayerColor.RED);
        controller.addPlayer("Andrea", PlayerColor.BLUE);
        controller.sendToAllExcept("Lorenzo", new RequestBooleanChoice(new NextStateInfo(TurnPhase.POWER)), true);
        assertEquals("Andrea", name.getValue());
        assertTrue(request.getValue() instanceof RequestBooleanChoice);
    }

    @Test
    public void sendToEliminated() {
        isGameOverThreePlayers();
        String nameOne = controller.getCurrentPlayer().getName();
        controller.sendToAll(controller.getSlimBoard(), true);
        assertTrue(name.getAllValues().subList(0, name.getAllValues().size()-4).contains("Andrea"));
        controller.sendToAllExcept(nameOne, controller.getSlimBoard(), true);
        assertTrue(name.getAllValues().subList(0, name.getAllValues().size()-3).contains("Andrea"));
    }

    @Test
    public void isGameOver() {
        handleMessagePlayValid();
        controller.getCurrentPlayer().setHasLost(true);
        String loser = controller.getCurrentPlayer().getName();
        int indexLoser = controller.getPlayersName().indexOf(loser);
        String winner = controller.getPlayersName().get((indexLoser + 1) % 2);
        controller.isGameOver();
        List<String> names = name.getAllValues();
        List<Request> requests = request.getAllValues();
        assertEquals(loser, names.get(names.size() -2));
        assertTrue(requests.get(requests.size() -2) instanceof PersonalDefeatRequest);
        assertEquals(winner, names.get(names.size() -1));
        assertTrue(requests.get(requests.size() -1) instanceof WinnerRequest);
    }

    @Test
    public void isGameOverThreePlayers() {
        MessageManager messageManager = controller.getMessageManager();
        controller.addPlayer("Veronica", PlayerColor.MAGENTA);
        controller.addPlayer("Andrea", PlayerColor.RED);
        controller.addPlayer("Lorenzo", PlayerColor.BLUE);
        controller.handleGamePhase();
        controller.addRemainingGod("Apollo");
        controller.addRemainingGod("Artemis");
        controller.addRemainingGod("Atlas");
        controller.addGod("Apollo");
        controller.setNextPlayer();
        controller.addGod("Artemis");
        controller.setNextPlayer();
        controller.addGod("Atlas");
        controller.firstPlayerSetUp("Andrea");
        if (controller.getCurrentPlayer().getName().equals("Veronica"))
            controller.setNextPlayer();
        else if (controller.getCurrentPlayer().getName().equals("Lorenzo")){
            controller.setNextPlayer();
            controller.setNextPlayer();
        }
        controller.addWorker(Sex.MALE, 1, 1);
        controller.addWorker(Sex.FEMALE, 0, 1);
        controller.setNextPlayer();
        controller.addWorker(Sex.MALE, 1, 0);
        controller.addWorker(Sex.FEMALE, 2, 4);
        controller.setNextPlayer();
        controller.addWorker(Sex.MALE, 3, 0);
        controller.addWorker(Sex.FEMALE, 3, 4);
        controller.setNextPlayer();
        controller.setNextGamePhase();
        controller.handleGamePhase();
        controller.getCurrentPlayer().setHasLost(true);
        String loser = controller.getCurrentPlayer().getName();
        controller.isGameOver();
        List<String> names = name.getAllValues();
        List<Request> requests = request.getAllValues();
        assertEquals(loser, names.get(names.size() -3));
        assertTrue(requests.get(requests.size() -3) instanceof PersonalDefeatRequest);
        assertEquals(controller.getPlayersName().get(0), names.get(names.size() -2));
        assertTrue(requests.get(requests.size() -2) instanceof SingleLoserInfo);
        assertEquals(controller.getPlayersName().get(1), names.get(names.size() -1));
        assertTrue(requests.get(requests.size() -1) instanceof SingleLoserInfo);
        controller.setNextGamePhase();
        assertSame(controller.getGamePhase(), GamePhase.GAMEOVER);
    }

    @Test
    public void isNotGameOver() {
        handleMessagePlayValid();
        assertFalse(controller.isGameOver());
    }

    @Test
    public void handleMessageSetup() {
        MessageManager messageManager = controller.getMessageManager();
        controller.addPlayer("Veronica", PlayerColor.MAGENTA);
        controller.addPlayer("Andrea", PlayerColor.RED);
        controller.handleGamePhase();
        assertTrue(controller.getCurrentPlayer().getName().equals("Andrea")
                || controller.getCurrentPlayer().getName().equals("Veronica"));
        messageManager.handleMessage(new AnswerCardsChoice(new String[]{"Apollo", "Artemis"}), controller.getCurrentPlayer().getName());
        assertTrue(controller.getRemainingGods().containsAll(Arrays.asList("Apollo", "Artemis")));
    }

    @Test
    public void handleMessageSetupNotValid() {
        MessageManager messageManager = controller.getMessageManager();
        controller.addPlayer("Veronica", PlayerColor.MAGENTA);
        controller.addPlayer("Andrea", PlayerColor.RED);
        controller.handleGamePhase();
        assertTrue(controller.getCurrentPlayer().getName().equals("Andrea")
                || controller.getCurrentPlayer().getName().equals("Veronica"));
        String current = controller.getCurrentPlayer().getName().equals("Andrea") ? "Veronica" : "Andrea";
        messageManager.handleMessage(new AnswerCardsChoice(new String[]{"Apollo", "Artemis"}), current);
        assertEquals(0, controller.getRemainingGods().size());
    }

    @Test
    public void handleMessagePlayValid() {
        MessageManager messageManager = controller.getMessageManager();
        controller.addPlayer("Veronica", PlayerColor.MAGENTA);
        controller.addPlayer("Andrea", PlayerColor.RED);
        controller.handleGamePhase();
        controller.addRemainingGod("Apollo");
        controller.addRemainingGod("Artemis");
        controller.addGod("Apollo");
        controller.setNextPlayer();
        controller.addGod("Artemis");
        controller.firstPlayerSetUp("Andrea");
        if (controller.getCurrentPlayer().getName().equals("Veronica"))
            controller.setNextPlayer();
        controller.addWorker(Sex.MALE, 1, 1);
        controller.addWorker(Sex.FEMALE, 0, 1);
        controller.setNextPlayer();
        controller.addWorker(Sex.MALE, 1, 0);
        controller.addWorker(Sex.FEMALE, 2, 4);
        controller.setNextPlayer();
        controller.setNextGamePhase();
        controller.handleGamePhase();
        messageManager.handleMessage(new AnswerStart(), controller.getCurrentPlayer().getName());
        assertTrue(request.getValue() instanceof RequestAction);
    }


}