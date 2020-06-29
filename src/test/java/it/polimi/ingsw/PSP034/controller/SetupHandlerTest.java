package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.ServerInfo;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import it.polimi.ingsw.PSP034.model.Board;
import it.polimi.ingsw.PSP034.model.Game;
import it.polimi.ingsw.PSP034.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class SetupHandlerTest {
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

    private SetupHandler setupHandler;

    @Before
    public void setUp() {
        controller = mock(Controller.class);
        myBoard = new Board();
        myGame = new Game(myBoard);
        firstPlayer = new Player("Veronica", PlayerColor.RED);
        secondPlayer = new Player("Andrea", PlayerColor.BLUE);
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.setCurrentPlayerByName("Veronica");

        sendToSingleName = ArgumentCaptor.forClass(String.class);
        sendToSingleRequest = ArgumentCaptor.forClass(Request.class);
        sendToAllExceptName = ArgumentCaptor.forClass(String.class);
        sendToAllExceptRequest = ArgumentCaptor.forClass(Request.class);
        sendToAllRequest = ArgumentCaptor.forClass(Request.class);
        first = true;

        when(controller.getPlayersName()).thenReturn(myGame.getPlayersName());
        when(controller.getPlayerNumber()).thenReturn(myGame.getPlayerNumber());
        when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);

        doAnswer(invocation -> {
            first = !first;
            when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);
            when(controller.getCurrentGod()).thenReturn(first ? firstPlayer.getMyGod() : secondPlayer.getMyGod());
            return null;
        }).when(controller).setNextPlayer();

        ArgumentCaptor<String> remainingGod = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            myGame.addRemainingGod(remainingGod.getValue());
            when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
            return null;
        }).when(controller).addRemainingGod(remainingGod.capture());

        ArgumentCaptor<String> god = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            myGame.addGod(god.getValue(), controller.getCurrentPlayer());
            when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
            return null;
        }).when(controller).addGod(god.capture());



        doNothing().when(controller).sendToPlayer(sendToSingleName.capture(), sendToSingleRequest.capture());
        doNothing().when(controller).sendToAllExcept(sendToAllExceptName.capture(), sendToAllExceptRequest.capture(), anyBoolean());
        doNothing().when(controller).sendToAll(sendToAllRequest.capture(), anyBoolean());
        doNothing().when(controller).handleGamePhase();
        doNothing().when(controller).setNextGamePhase();

        setupHandler = new SetupHandler(controller);
    }

    @Test
    public void executeSelectedStateCardsChoice() {
        String[] gods = new String[]{"Apollo", "Zeus"};
        setupHandler.executeSelectedState(new AnswerCardsChoice(gods));
        List<String> singleValues = sendToSingleName.getAllValues();
        List<Request> singleRequests = sendToSingleRequest.getAllValues();
        assertTrue(myGame.getRemainingGods().contains("Apollo") && myGame.getRemainingGods().contains("Zeus"));
        assertEquals("Veronica", singleValues.get(singleValues.size() - 2));
        assertEquals("Andrea", singleValues.get(singleValues.size() - 1));
        assertTrue(singleRequests.get(singleRequests.size() - 2) instanceof RequestServerConfig);
        assertTrue(singleRequests.get(singleRequests.size() - 1) instanceof RequestPersonalGod);
    }

    @Test
    public void executeSelectedStateCardsChoiceNotValid() {
        String[] gods = new String[]{"Appolllo", "Zzzzeusss"};
        setupHandler.executeSelectedState(new AnswerCardsChoice(gods));
        assertEquals(0, myGame.getRemainingGods().size());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestCardsChoice);
        assertSame("Veronica", sendToSingleName.getValue());

    }

    @Test
    public void executeSelectedStatePersonalGod() {
        String[] gods = new String[]{"Apollo", "Zeus"};
        setupHandler.executeSelectedState(new AnswerCardsChoice(gods)); //Veronica chose the gods
        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Zeus")); // Andrea chose Zeus
        List<String> singleValues = sendToSingleName.getAllValues();
        List<Request> singleRequests = sendToSingleRequest.getAllValues();
        assertEquals("Andrea", singleValues.get(singleValues.size() - 2));
        assertTrue(singleRequests.get(singleRequests.size() - 2) instanceof RequestServerConfig);
        assertEquals("Veronica", singleValues.get(singleValues.size() - 1));
        assertTrue(singleRequests.get(singleRequests.size() - 1) instanceof RequestPersonalGod);

        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        ArgumentCaptor<String> godName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            myGame.addGod(godName.getValue(), controller.getCurrentPlayer());
            when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
            return null;
        }).when(controller).addGod(godName.capture());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Apollo")); //Veronica chose Apollo
        singleValues = sendToSingleName.getAllValues();
        singleRequests = sendToSingleRequest.getAllValues();
        assertEquals("Veronica", singleValues.get(singleValues.size() - 1));
        assertTrue(singleRequests.get(singleRequests.size() - 1) instanceof RequestFirstPlayer);
    }

    @Test
    public void executeSelectedStateFirstPlayer() {
        String[] gods = new String[]{"Apollo", "Zeus"};
        setupHandler.executeSelectedState(new AnswerCardsChoice(gods)); //Veronica chose the gods
        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Zeus")); // Andrea chose Zeus
        List<String> singleValues = sendToSingleName.getAllValues();
        List<Request> singleRequests = sendToSingleRequest.getAllValues();

        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        ArgumentCaptor<String> godName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            myGame.addGod(godName.getValue(), controller.getCurrentPlayer());
            when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
            return null;
        }).when(controller).addGod(godName.capture());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Apollo")); //Veronica chose Apollo
        singleValues = sendToSingleName.getAllValues();
        singleRequests = sendToSingleRequest.getAllValues();

        ArgumentCaptor<String> firstName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            first = firstPlayer.getName().equals(firstName.getValue());
            when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);
            when(controller.getCurrentGod()).thenReturn(first ? firstPlayer.getMyGod() : secondPlayer.getMyGod());
            return null;
        }).when(controller).firstPlayerSetUp(firstName.capture());

        setupHandler.executeSelectedState(new AnswerFirstPlayer("Andrea"));
        singleValues = sendToSingleName.getAllValues();
        singleRequests = sendToSingleRequest.getAllValues();
        assertTrue(sendToAllRequest.getValue() instanceof InitializeBoard);
        assertEquals("Andrea", singleValues.get(singleValues.size() - 1));
        assertTrue(singleRequests.get(singleRequests.size() - 1) instanceof RequestPlaceWorker);
        assertEquals("Andrea", sendToAllExceptName.getValue());
        assertTrue(sendToAllExceptRequest.getValue() instanceof InfoIsPlacing);
    }

    @Test
    public void executeSelectedStatePlaceWorker() {
        String[] gods = new String[]{"Apollo", "Zeus"};
        setupHandler.executeSelectedState(new AnswerCardsChoice(gods)); //Veronica chose the gods
        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Zeus")); // Andrea chose Zeus
        List<String> singleValues;
        List<Request> singleRequests;

        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        ArgumentCaptor<String> godName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            myGame.addGod(godName.getValue(), controller.getCurrentPlayer());
            when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
            return null;
        }).when(controller).addGod(godName.capture());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Apollo")); //Veronica chose Apollo

        ArgumentCaptor<String> firstName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            first = firstPlayer.getName().equals(firstName.getValue());
            when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);
            when(controller.getCurrentGod()).thenReturn(first ? firstPlayer.getMyGod() : secondPlayer.getMyGod());
            return null;
        }).when(controller).firstPlayerSetUp(firstName.capture());

        setupHandler.executeSelectedState(new AnswerFirstPlayer("Andrea"));
        setupHandler.executeSelectedState(new AnswerPlaceWorker(Sex.MALE, 0, 2));
        setupHandler.executeSelectedState(new AnswerPlaceWorker(Sex.FEMALE, 2, 3));
        singleValues = sendToSingleName.getAllValues();
        singleRequests = sendToSingleRequest.getAllValues();
        assertEquals("Andrea", singleValues.get(singleValues.size() - 2));
        assertTrue(singleRequests.get(singleRequests.size() - 2) instanceof ReceivedWorkerChoice);
        assertEquals("Veronica", singleValues.get(singleValues.size() - 1));
        assertTrue(singleRequests.get(singleRequests.size() - 1) instanceof RequestPlaceWorker);
        setupHandler.executeSelectedState(new AnswerPlaceWorker(Sex.MALE, 0, 2));
        setupHandler.executeSelectedState(new AnswerPlaceWorker(Sex.FEMALE, 2, 3));
        singleValues = sendToSingleName.getAllValues();
        singleRequests = sendToSingleRequest.getAllValues();
        assertEquals("Veronica", singleValues.get(singleValues.size() - 2));
        assertEquals("Veronica", singleValues.get(singleValues.size() - 1));
        assertTrue(singleRequests.get(singleRequests.size() - 2) instanceof RequestPlaceWorker);
        assertTrue(singleRequests.get(singleRequests.size() - 1) instanceof ReceivedWorkerChoice);
    }

    @Test
    public void executeSelectedStatePlaceWorkerNotValid() {
        String[] gods = new String[]{"Apollo", "Zeus"};
        setupHandler.executeSelectedState(new AnswerCardsChoice(gods)); //Veronica chose the gods
        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Zeus")); // Andrea chose Zeus
        List<String> singleValues;
        List<Request> singleRequests;

        when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
        ArgumentCaptor<String> godName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            myGame.addGod(godName.getValue(), controller.getCurrentPlayer());
            when(controller.getRemainingGods()).thenReturn(myGame.getRemainingGods());
            return null;
        }).when(controller).addGod(godName.capture());
        setupHandler.executeSelectedState(new AnswerPersonalGod("Apollo")); //Veronica chose Apollo

        ArgumentCaptor<String> firstName = ArgumentCaptor.forClass(String.class);
        doAnswer(invocation -> {
            first = firstPlayer.getName().equals(firstName.getValue());
            when(controller.getCurrentPlayer()).thenReturn(first ? firstPlayer : secondPlayer);
            when(controller.getCurrentGod()).thenReturn(first ? firstPlayer.getMyGod() : secondPlayer.getMyGod());
            return null;
        }).when(controller).firstPlayerSetUp(firstName.capture());

        setupHandler.executeSelectedState(new AnswerFirstPlayer("Andrea"));
        setupHandler.executeSelectedState(new AnswerPlaceWorker(Sex.MALE, 0, 2));
        assertEquals("Andrea", sendToSingleName.getValue());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestPlaceWorker);
        assertSame(((RequestPlaceWorker) sendToSingleRequest.getValue()).getSex(), Sex.FEMALE);
        setupHandler.executeSelectedState(new AnswerPlaceWorker(Sex.FEMALE, -2, 5));
        assertEquals("Andrea", sendToSingleName.getValue());
        assertTrue(sendToSingleRequest.getValue() instanceof RequestPlaceWorker);
        assertSame(((RequestPlaceWorker) sendToSingleRequest.getValue()).getSex(), Sex.FEMALE);
    }

}