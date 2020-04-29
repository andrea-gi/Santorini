package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements IClientConnection, Runnable{
    private final Socket socket;
    private final Server server; // lo userò per chiudere la connessione
    ObjectInputStream in;
    ObjectOutputStream out;

    boolean active;
    private final Object activeLock = new Object();

    boolean firstConnected;
    String playerName = "none";



    public ClientHandler(Socket socket, Server server, boolean firstConnected){
        this.socket = socket;
        this.server = server;
        this.firstConnected = firstConnected;
        this.active = true;
        server.registerConnection(this);
    }

    private boolean isActive(){
        synchronized (activeLock) {
            return active;
        }
    }

    private void setActive(boolean active){
        synchronized (activeLock){
            this.active = active;
        }
    }

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
        new Thread(() -> send(message)).start();
    }

    @Override
    public void run() {
        //DEVO SALVARE IL NOME DEL PLAYER
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
                e.printStackTrace();
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
        active = false;
    }

    private void close(){
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
}
