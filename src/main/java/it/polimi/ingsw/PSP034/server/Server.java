package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.controller.Controller;
import it.polimi.ingsw.PSP034.controller.IController;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.GameOverAnswer;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayAnswer;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.*;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupAnswer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class managing the server. Its main thread (started using {@link Server#run()}, creates a new thread
 * listening to incoming socket connections. Then, it manages the messages received by clients sequentially.
 */
public class Server implements Runnable{
    private static class AnswerEncapsulated{
        private final Answer message;
        private final IClientConnection connection;

        AnswerEncapsulated(Answer message, IClientConnection connection) {
            this.message = message;
            this.connection = connection;
        }
    }

    private ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(12);

    private final ArrayList<IClientConnection> waitingConnections = new ArrayList<>();

    private final ArrayList<IClientConnection> activeConnections = new ArrayList<>();

    private final ArrayList<Color> chosenColors = new ArrayList<>();
    private final ArrayList<String> chosenNames = new ArrayList<>();

    private boolean gameStarted = false;
    private boolean gameEnded = false; // true if game ended correctly (with a winner)
    private boolean firstConnection = true;
    private boolean canStartSetup = false;
    private int chosenPlayerNumber = Constant.MAXPLAYERS;

    private final IController controller;

    private final BlockingQueue<AnswerEncapsulated> queue = new ArrayBlockingQueue<>(16);

    /**
     * Instantiates a new server listening to a given port. It also creates a new {@link Controller}.
     * @param port A valid port (0 to 65535). For best results, be aware of TCP well-known ports.
     */
    public Server(int port){
        IController temporaryController;
        try {
            this.serverSocket = new ServerSocket(port);
            temporaryController = new Controller(this);
        } catch (IOException e){
            temporaryController = null;
            System.err.println("Cannot open server socket");
            System.exit(1);
        }
        controller = temporaryController;
    }

    boolean playerNumberFlag = true;
    /**
     * Sets the number of player synchronously. Can be done only once.
     * @param chosenPlayerNumber Player number chosen by the selected client (2 or 3).
     */
    protected synchronized void setPlayerNumber(int chosenPlayerNumber){
        if (playerNumberFlag) {
            playerNumberFlag = false;
            this.chosenPlayerNumber = chosenPlayerNumber;
            setCanStartSetup(true);
            checkAndBeginSetup();
        }
    }

    private synchronized boolean canStartSetup() {
        return canStartSetup;
    }

    private synchronized int getChosenPlayerNumber(){
        return chosenPlayerNumber;
    }

    private synchronized void setCanStartSetup(boolean canStartSetup){
        this.canStartSetup = canStartSetup;
    }

    private synchronized void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    private synchronized boolean isGameStarted(){
        return this.gameStarted;
    }

    /**
     * Checks if player number has already been set and, if there are enough players, starts the registration.
     */
    protected synchronized void checkAndBeginSetup(){
        if (isGameStarted()) {
            deregisterWaitingList();
            return; // in case the function is called more than once
        }

        if (waitingConnections.size() >= getChosenPlayerNumber() && canStartSetup()) {

            setGameStarted(true);

            activeConnections.addAll(waitingConnections.subList(0, chosenPlayerNumber));

            for(IClientConnection connection : activeConnections){
                if (connection.equals(activeConnections.get(0))) {
                    controller.addModelObserver(connection);
                    connection.asyncSend(new RequestNameColor(chosenNames.toArray(new String[0]),
                            Color.getRemainingColors(chosenColors.toArray(new Color[0]))));
                }
                else
                    connection.asyncSend(new RequestServerConfig(ServerInfo.WELCOME_WAIT));
            }

            deregisterWaitingList();
        }
    }

    /**
     * Registers a new connection to the waiting list.
     * @param connection Reference to the connection being registered.
     */
    protected synchronized void registerConnection(IClientConnection connection){
        waitingConnections.add(connection);
    }

    /**
     * Deregisters a connection previously registered using {@link Server#deregisterConnection(IClientConnection)}
     * @param connection Reference to the connection being deregistered.
     */
    protected synchronized void deregisterConnection(IClientConnection connection){
        if(isGameStarted()){
            // TODO -- gestione termine della partita
            // thread del client aggiunge un messaggio di disconnessione al thread del server
        }
        else{
            if (!canStartSetup() && waitingConnections.indexOf(connection) == 0){
                // TODO -- rimuovo altre cose / chiudo connessioni se necessario
                waitingConnections.remove(connection);
                waitingConnections.get(0).asyncSend(new RequestServerConfig(ServerInfo.REQUEST_PLAYER_NUMBER));
            } else {
                waitingConnections.remove(connection);
            }
        }
    }

    private void deregisterWaitingList(){
        for(IClientConnection deregistered : waitingConnections) {
            // TODO -- invio messaggio
            deregisterConnection(deregistered);
        }
    }


    private void acceptConnections(){
        executor.execute(() -> {
            while (true) {
                try {
                    Socket newSocket = serverSocket.accept();
                    // TODO -- gestire connessioni in eccesso
                    ClientHandler socketConnection;
                    if (firstConnection) {
                        socketConnection = new ClientHandler(newSocket, this, true);
                        firstConnection = false;
                    } else {
                        socketConnection = new ClientHandler(newSocket, this, false);
                    }
                    executor.submit(socketConnection);
                } catch (IOException e) {
                    System.out.println("Connection Error!");
                    e.printStackTrace();
                    break;
                }
            }
        });
    }

    /**
     * Synchronously adds a message to the {@link BlockingQueue}
     * @param message Message being added
     * @param connection Connection that added the message
     */
    protected synchronized void addMessage(Answer message, IClientConnection connection){
        queue.offer(new AnswerEncapsulated(message, connection));
    }

    private synchronized void registerPlayer(IClientConnection connection, String name, Color color){
        if (!chosenNames.contains(name) && !chosenColors.contains(color)) {
            controller.addPlayer(name, color);
            chosenNames.add(name);
            chosenColors.add(color);
            connection.setName(name);

            int indexPlayer = activeConnections.indexOf(connection);
            if (indexPlayer < getChosenPlayerNumber() - 1) {
                connection.asyncSend(new RequestServerConfig(ServerInfo.SUCCESSFULLY_ADDED));
                activeConnections.get(indexPlayer + 1).asyncSend(new RequestNameColor(chosenNames.toArray(new String[0]),
                        Color.getRemainingColors(chosenColors.toArray(new Color[0]))));
            } else {
                controller.handleGamePhase();
            }
        }
    }

    private synchronized void manageMessage(Answer message, IClientConnection connection){
        if (message instanceof AnswerServerConfig){
            if (message instanceof AnswerNumber) {
                setPlayerNumber(((AnswerNumber) message).getPlayerNumber());
            }
            else if (message instanceof AnswerNameColor) {
                AnswerNameColor answerNameColor = (AnswerNameColor) message;
                registerPlayer(connection, answerNameColor.getName(), answerNameColor.getColor());
            }
        } else if (message instanceof PlayAnswer || message instanceof SetupAnswer || message instanceof GameOverAnswer){
            controller.handleMessage(message, connection.getName());
        }
    }

    /**
     * Sends a message to a given player. See {@link IClientConnection#asyncSend(Request)} for further information.
     * Should be executed by the same thread that manages the message.
     * If either the player does not exists or the message in {@code null}, method does nothing.
     * @param player Player name
     * @param message Message being sent
     */
    public synchronized void asyncSendTo(String player, Request message){
        if (message == null)
            return;
        for (IClientConnection connection : activeConnections){
            if(connection.getName().equals(player))
                connection.asyncSend(message);
        }
    }
    
    @Override
    public void run() {
        acceptConnections();
        AnswerEncapsulated message;
        while(true){
            try {
                message = queue.take();
                manageMessage(message.message, message.connection);
            } catch (InterruptedException e){
                // TODO -- interrupted
            }
        }
    }
}
