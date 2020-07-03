package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.messages.*;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoCloseRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.PersonalDefeatRequest;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Manages the server socket connection to a single client.
 */
public class ClientHandler implements IClientConnection, Runnable{
    private final Socket socket;
    private final Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean active;
    private final Object activeLock = new Object();

    private boolean firstConnected;
    private String playerName;
    private String debugColor = ANSI.reset;

    private final BlockingQueue<Request> sendQueue = new LinkedBlockingQueue<>();

    private boolean externalViewer = false;

    private final static ServerLogger logger = ServerLogger.getInstance();


    /**
     * Creates a new ClientHandler instance with the given parameters.
     * The method registers the instance in the {@link Server}.
     *
     * @param socket Reference to the socket
     * @param server Reference to the server which instantiates the class
     * @param firstConnected True if creating the ClientHandler associated to the first player connected.
     */
    public ClientHandler(Socket socket, Server server, boolean firstConnected){
        Random randGenID = new Random();
        this.playerName = String.valueOf(randGenID.nextInt(Integer.MAX_VALUE));
        this.socket = socket;
        this.server = server;
        this.firstConnected = firstConnected;
        this.active = true;
        server.registerConnection(this);
    }

    /**
     * Synchronously returns the state of the socket connection.
     *
     * @return {@code true} if the socket connection is still active and usable, {@code false} otherwise
     */
    private boolean isActive(){
        synchronized (activeLock) {
            return active;
        }
    }

    /**
     * Synchronously saves the state of the socket connection.
     *
     * @param active {@code true} if the socket connection is still active and usable, {@code false} otherwise
     */
    private void setActive(boolean active){
        synchronized (activeLock){
            this.active = active;
        }
    }

    /**
     * Synchronously checks if a player is an external viewer of the game (player who has already lost).
     *
     * @return {@code true} if player is an external viewer
     */
    @Override
    public synchronized boolean isExternalViewer() {
        return externalViewer;
    }

    /**
     * Sends a synchronous {@link Request} message through the {@link ObjectOutputStream} associated
     * to the socket.
     */
    private void send(){
        Request message;
        while (isActive()) {
            try {
                message = sendQueue.take();
                if(message instanceof AutoCloseRequest)
                    return;
                else if (!isActive()) {
                    logger.printString("Connection to " + debugColor + this.playerName + ANSI.reset +
                            " has already been closed. Cannot send: " + message.getClass().getSimpleName());
                    return;
                }
                out.reset();
                out.writeObject(message);
                out.flush();
                logger.printRequestMessage(message, this.playerName, this.debugColor);
                manageClosingInfo(message);
            } catch (IOException | InterruptedException e) {
                close();
            }
        }
    }

    private final ScheduledThreadPoolExecutor waitingHeartBeat = new ScheduledThreadPoolExecutor(1);
    private final ExecutorService heartBeatManager = Executors.newSingleThreadExecutor();

    private boolean receivedHeartBeat = false;

    private synchronized boolean hasReceivedHeartBeat(){
        return receivedHeartBeat;
    }

    private synchronized void setReceivedHeartBeat(boolean received){
        this.receivedHeartBeat = received;
    }

    /**
     * Schedules a heartbeat timeout, when it expires, checks if a heartbeat message was received in the meantime
     * and, depending on the result, closes the connection or sends a new heartbeat.
     */
    private void waitHeartBeat(){
        waitingHeartBeat.schedule(()->
        {
            if (!hasReceivedHeartBeat()){
                if (isActive())
                    logger.printString(playerName + " heartbeat missing. Disconnecting.");
                this.close();
            } else{
                heartBeatManager.execute(this::sendHeartBeat);
            }
        }, Constant.HEARTBEAT_PERIOD, TimeUnit.SECONDS);
    }

    /**
     * Sends a heartbeat to the client and calls {@link ClientHandler#waitHeartBeat()}
     */
    private void sendHeartBeat(){
        setReceivedHeartBeat(false);
        asyncSend(new HeartBeatRequest());
        waitHeartBeat();
    }

    /**
     * Manages a message that causes a disconnection.
     * Used in {@link ClientHandler#send()} method, in order to process the info right after the notification of disconnection
     * @param message Message that causes the disconnection.
     */
    private void manageClosingInfo(Request message){
        if (message == null)
            return;

        if (Request.isSilentCloseRequest(message)){
            close();
        }
    }

    /**
     * Manages a message that causes a change in the client status (e.g. it became an external viewer).
     * @param message Message that causes the status change.
     */
    private void manageStatusInfo(Request message){
        if (message == null)
            return;
        if (message instanceof PersonalDefeatRequest){
            synchronized (this){
                externalViewer = true;
            }
        }
    }

    /**
     * Sends an asynchronous {@link Request} message through an {@link java.io.ObjectOutputStream}
     *
     * @param request {@link java.io.Serializable} message being sent
     */
    @Override
    public void asyncSend(Request request){
        manageStatusInfo(request);
        try{
            sendQueue.offer(request);
        } catch (ClassCastException | NullPointerException | IllegalArgumentException ignored){}
    }

    /**
     * Run method (called in a new thread) that creates and manages {@link ObjectInputStream} and {@link ObjectOutputStream}
     * associated to the socket.
     * It reads messages from the client and adds them to a {@link Server} {@link java.util.concurrent.BlockingQueue}
     */
    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(this::send).start();
            heartBeatManager.execute(this::sendHeartBeat);
        } catch (IOException e) {
            logger.printString("Cannot open input/output stream ("+ debugColor + playerName + ANSI.reset + ")");
            close();
            return;
        }
        if (firstConnected) {
            try {
                sendQueue.offer(new RequestServerConfig(ServerInfo.REQUEST_PLAYER_NUMBER));
            } catch (ClassCastException | NullPointerException | IllegalArgumentException ignored){}
        }
        else {
            try {
                sendQueue.offer(new RequestServerConfig(ServerInfo.LOBBY));
            } catch (ClassCastException | NullPointerException | IllegalArgumentException ignored){}
            server.checkAndBeginSetup();
        }
        while(isActive()){
            try{
                Answer message = (Answer) in.readObject();
                if (message instanceof HeartBeatAnswer){
                    setReceivedHeartBeat(true);
                }
                else{
                    server.addMessage(message, this);
                }
            } catch (IOException e) {
                if (isActive())
                    logger.printString("IOException in ClientHandler - run() (" + debugColor + playerName + ANSI.reset + "). Normally caused by disconnection.");
                close();
            } catch (ClassNotFoundException e){
                logger.printString("ClassNotFoundException in ClientHandler - run() (" + debugColor + playerName + ANSI.reset + ").");
                close();
            }
        }
        close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void closeConnection() {
        try {
            if(!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            logger.printString("Error when closing " + debugColor + playerName + ANSI.reset + " socket!" );
        } finally{
            setActive(false);
        }
    }

    /**
     * Closes and deregisters connection from {@link Server}.
     */
    private void close(){
        if (isActive()) {
            closeConnection();
            try{
                sendQueue.offer(new AutoCloseRequest());
            } catch (ClassCastException | NullPointerException | IllegalArgumentException ignored){}
            logger.printString("Deregistering: " + debugColor + playerName + ANSI.reset);
            server.deregisterConnection(this);
            logger.printString(debugColor + playerName + ANSI.reset + " deregistered successfully.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized String getName() {
        return playerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setName(String name){
        this.playerName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDebugColor(PlayerColor color) {
        this.debugColor = color.getFG_color();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDebugColor() {
        return debugColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(ModelUpdate message) {
        asyncSend(message);
    }
}
