package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.controller.Controller;
import it.polimi.ingsw.PSP034.controller.IController;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.EndByDisconnection;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.GameOverAnswer;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayAnswer;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.*;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupAnswer;
import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

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
    private IClientConnection firstPlayerConnected;

    private final ArrayList<IClientConnection> activeConnections = new ArrayList<>();

    private final ArrayList<PlayerColor> chosenColors = new ArrayList<>();
    private final ArrayList<String> chosenNames = new ArrayList<>();

    private boolean gameStarted = false;
    private boolean gameEnded = false; // true if game ended correctly (with a winner)
    private boolean firstConnection = true;
    private boolean canStartSetup = false;
    private int chosenPlayerNumber = Constant.MAXPLAYERS;
    private int port;

    private IController controller;

    private final BlockingQueue<AnswerEncapsulated> queue = new LinkedBlockingQueue<>();

    private final Object firstConnectionLock = new Object();

    private final ServerLogger logger = ServerLogger.getInstance();

    /**
     * Instantiates a new server listening to a given port. It also creates a new {@link IController}.
     * @param port A valid port (0 to 65535). For best results, be aware of TCP well-known ports.
     */
    public Server(int port){
        IController temporaryController;
        try {
            this.port = port;
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
    protected synchronized boolean setPlayerNumber(int chosenPlayerNumber){
        if (playerNumberFlag) {
            playerNumberFlag = false;
            this.chosenPlayerNumber = chosenPlayerNumber;
            setCanStartSetup(true);
            checkAndBeginSetup();
            return true;
        }
        return false;
    }

    /**
     * Synchronously checks if player number has already been set and setup can start
     * @return {@code true} if setup can start, {@code false} otherwise.
     */
    protected synchronized boolean canStartSetup() {
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

    /**
     * Synchronously checks if game has already started
     * @return {@code true} if game has started, {@code false} otherwise.
     */
    protected synchronized boolean isGameStarted(){
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
            waitingConnections.removeAll(activeConnections);

            for(IClientConnection connection : activeConnections){
                controller.addModelObserver(connection);
                if (connection.equals(activeConnections.get(0))) {
                    PlayerColor[] alreadyChosenColors = chosenColors.toArray(new PlayerColor[0]);
                    connection.asyncSend(new RequestNameColor(chosenNames.toArray(new String[0]),
                            PlayerColor.getRemainingColors(alreadyChosenColors), alreadyChosenColors));
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
     * Deregisters a connection previously registered using {@link Server#registerConnection(IClientConnection)}
     * @param connection Reference to the connection being deregistered.
     */
    protected synchronized void deregisterConnection(IClientConnection connection){
        if (connection == null)
            return;
        if (waitingConnections.contains(connection)){
            if (isGameStarted()){
                connection.asyncSend(new RequestServerConfig(ServerInfo.ALREADY_STARTED));
                waitingConnections.remove(connection);
            }
            else if (!canStartSetup() && connection == firstPlayerConnected){
                waitingConnections.remove(connection);
                synchronized (firstConnectionLock) {
                    if (!waitingConnections.isEmpty()) {
                        waitingConnections.get(0).asyncSend(new RequestServerConfig(ServerInfo.REQUEST_PLAYER_NUMBER));
                        firstPlayerConnected = waitingConnections.get(0);
                    } else
                        firstConnection = true;
                }
            }
            else {
                waitingConnections.remove(connection);
            }
        } else if (activeConnections.contains(connection)){
            if (connection.isExternalViewer()) {
                connection.closeConnection();
                controller.removeModelObserver(connection);
            }
            else{
                //activeConnections.remove(connection);
                String disconnectedName;
                if (connection.getDebugColor().equals(ANSI.reset)) //ANSI.reset is default color prior to registration
                    disconnectedName = "An opponent";
                else
                    disconnectedName = connection.getName();
                for(IClientConnection deregister : activeConnections) {
                    deregister.asyncSend(new EndByDisconnection(disconnectedName));
                    controller.removeModelObserver(connection);
                }
                restart();
            }
        }
    }

    private synchronized void deregisterWaitingList(){
        ArrayList<IClientConnection> waitingCopy = new ArrayList<>(waitingConnections);
        for(IClientConnection deregistered : waitingCopy) {
            deregisterConnection(deregistered);
        }
        waitingConnections.clear();
    }

    private synchronized void restart(){
        logger.printString(ANSI.underline + ANSI.FG_red + "Game ended. Preparing server for a new game" + ANSI.reset);
        queue.clear();
        synchronized (firstConnectionLock){
            firstConnection = true;
            firstPlayerConnected = null;
            activeConnections.clear();
            waitingConnections.clear();
        }
        controller = new Controller(this);
        chosenPlayerNumber = Constant.MAXPLAYERS;
        playerNumberFlag = true;
        setGameStarted(false);
        setCanStartSetup(false);
        chosenNames.clear();
        chosenColors.clear();
        logger.printString(ANSI.underline + ANSI.FG_red + "New game ready. Waiting for new client connections." + ANSI.reset);
    }

    private void acceptConnections(){
        executor.execute(() -> {
            while (true) {
                try {
                    Socket newSocket = serverSocket.accept();
                    ClientHandler socketConnection;
                    synchronized (firstConnectionLock) {
                        if (firstConnection) {
                            socketConnection = new ClientHandler(newSocket, this, true);
                            firstPlayerConnected = socketConnection;
                            firstConnection = false;
                        } else {
                            socketConnection = new ClientHandler(newSocket, this, false);
                        }
                    }
                    executor.submit(socketConnection);
                    logger.printString("Added new player, temporary ID is: " + socketConnection.getName());
                } catch (IOException e) {
                    System.err.println("Connection Error!");
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

    /**
     * Tries to register a new player, given unique name and color.
     * @param connection Connection to be registered
     * @param name Player name
     * @param color Player color
     * @return {@code true} if registered successfully, {@code false} otherwise (caused by already existing name or color)
     */
    private synchronized boolean registerPlayer(IClientConnection connection, String name, PlayerColor color){
        logger.printString(connection.getName() + " wants to register as " + name + ", using "
                + connection.getDebugColor() + color + ANSI.reset +" workers.");
        if (!chosenNames.contains(name) && !chosenColors.contains(color)) {
            controller.addPlayer(name, color);
            chosenNames.add(name);
            chosenColors.add(color);
            connection.setName(name);
            connection.setDebugColor(color);
            logger.printString("Successfully registered "+name+", using "+color+" workers.");

            int indexPlayer = activeConnections.indexOf(connection);
            if (indexPlayer < getChosenPlayerNumber() - 1) {
                connection.asyncSend(new RequestServerConfig(ServerInfo.SUCCESSFULLY_ADDED));
                PlayerColor[] alreadyChosenColors = chosenColors.toArray(new PlayerColor[0]);
                activeConnections.get(indexPlayer + 1).asyncSend(new RequestNameColor(chosenNames.toArray(new String[0]),
                        PlayerColor.getRemainingColors(alreadyChosenColors), alreadyChosenColors));
            } else {
                controller.handleGamePhase();
            }
            return true;
        }
        logger.printString("Either the name or color requested were not available.");
        return false;
    }

    /**
     * Manages a message received by a client. If the received message is a {@link AnswerServerConfig},
     * it will be managed directly in class by {@link Server#manageServerConfig(AnswerServerConfig, IClientConnection)},
     * otherwise it will call a controller method.
     * @param message Message to be managed
     * @param connection Message sender
     */
    private synchronized void manageMessage(Answer message, IClientConnection connection){
        logger.printAnswerMessage(message, connection);

        if (message instanceof AnswerServerConfig){
            manageServerConfig((AnswerServerConfig) message, connection);
        }
        else if (message instanceof PlayAnswer || message instanceof SetupAnswer || message instanceof GameOverAnswer){
            controller.getMessageManager().handleMessage(message, connection.getName());
        }
    }


    private synchronized void manageServerConfig(AnswerServerConfig message, IClientConnection connection){
        boolean validMessage;
        if (message instanceof AnswerNumber) {
            if(setPlayerNumber(((AnswerNumber) message).getPlayerNumber())){
                if (canStartSetup() && !isGameStarted()){
                    connection.asyncSend(new RequestServerConfig(ServerInfo.LOBBY)); //TODO -- messaggio di attesa altri giocatori
                }
            } else{
                // TODO -- gestire messaggio player number errato (?) vedi setPlayerNumber
            }
        }
        else if (message instanceof AnswerNameColor) {
            AnswerNameColor answerNameColor = (AnswerNameColor) message;
            validMessage = registerPlayer(connection, answerNameColor.getName(), answerNameColor.getColor());
            if (!validMessage){
                // TODO -- come gestisco l'errore? dovrei inviare una notifica di errore
                PlayerColor[] alreadyChosenColors = chosenColors.toArray(new PlayerColor[0]);
                connection.asyncSend(new RequestNameColor(chosenNames.toArray(new String[0]),
                        PlayerColor.getRemainingColors(alreadyChosenColors), alreadyChosenColors));
            }
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
        logger.printString("Server started. Listening to socket connections on port: " + port);
        AnswerEncapsulated message;
        while(true){
            try {
                message = queue.take();
                manageMessage(message.message, message.connection);
            } catch (InterruptedException e){
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                    logger.printString("Error closing server socket.");
                }
            }
        }
    }
}
