package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Manages the server socket connection to a single client.
 */
class ClientHandler implements IClientConnection, Runnable{
    private final Socket socket;
    private final Server server;
    ObjectInputStream in;
    ObjectOutputStream out;

    boolean active;
    private final Object activeLock = new Object();

    boolean firstConnected;
    String playerName = "none";


    /**
     * Creates a new ClientHandler instance with the given parameters.
     * The method registers the instance in the {@link Server}.
     * @param socket Reference to the socket
     * @param server Reference to the server which instantiates the class
     * @param firstConnected True if creating the ClientHandler associated to the first player connected.
     */
    public ClientHandler(Socket socket, Server server, boolean firstConnected){
        this.socket = socket;
        this.server = server;
        this.firstConnected = firstConnected;
        this.active = true;
        server.registerConnection(this);
    }

    /**
     * Synchronously returns the state of the socket connection.
     * @return {@code true} if the socket connection is still active and usable, {@code false} otherwise
     */
    private boolean isActive(){
        synchronized (activeLock) {
            return active;
        }
    }

    /**
     * Synchronously saves the state of the socket connection.
     * @param active {@code true} if the socket connection is still active and usable, {@code false} otherwise
     */
    private void setActive(boolean active){
        synchronized (activeLock){
            this.active = active;
        }
    }

    /**
     * Sends a synchronous {@link Request} message through the {@link ObjectOutputStream} associated
     * to the socket.
     * @param message {@link java.io.Serializable} message being sent
     */
    private synchronized void send(Request message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e){
            // TODO -- disconnetto
            e.printStackTrace();
        }
    }

    public void asyncSend(Request message){
        if (isActive())
            new Thread(() -> send(message)).start();
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
        } catch (IOException e) {
            System.err.println("Cannot open input/output stream.");
            // TODO -- disconnetto
            e.printStackTrace();
        }
        if (firstConnected)
            send(new RequestServerConfig(ServerInfo.REQUEST_PLAYER_NUMBER));
        else {
            send(new RequestServerConfig(ServerInfo.LOBBY));
            server.checkAndBeginSetup();
        }
        while(isActive()){
            try{
                Answer message = (Answer) in.readObject();
                server.addMessage(message, this);
            } catch (IOException | ClassNotFoundException e) {
                setActive(false);
                close();
                // TODO -- disconnetto
            }
        }
    }


    @Override
    public synchronized void closeConnection() {
        // TODO -- inviare messaggio chiusura socket
        try {
            if(!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
    }

    private void close(){
        setActive(false);
        closeConnection();
        System.out.println("Deregistering: " + playerName);
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public synchronized String getName() {
        return playerName;
    }

    @Override
    public synchronized void setName(String name){
        this.playerName = name;
    }

    @Override
    public void update(ModelUpdate message) {
        asyncSend(message);
    }
}
