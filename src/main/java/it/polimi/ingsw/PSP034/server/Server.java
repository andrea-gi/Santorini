package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.controller.Controller;
import it.polimi.ingsw.PSP034.observer.ModelObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(12);
    private final Map<String, IClientConnection> waitingConnections = new HashMap<>();
    private final Map<String, Color> waitingColors = new HashMap<>();
    private final ArrayList<IClientConnection> activeConnections = new ArrayList<>();
    private boolean gameStarted = false;
    private boolean gameEnded = false; // true if game ended correctly (with a winner)
    private boolean firstConnection = true;
    private boolean startSetup = false;
    private int chosenPlayerNumber = Constant.MAXPLAYERS;

    Controller controller;

    public Server(int port){
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e){
            System.out.println("Cannot open server socket");
            System.exit(1);
        }
    }

    protected synchronized void setPlayerNumber(final int chosenPlayerNumber){
        this.chosenPlayerNumber = chosenPlayerNumber;
        this.notifyAll();
    }

    protected synchronized boolean registerName(String name, IClientConnection connection){
        if(waitingConnections.containsValue(connection))
            return false;
        for(String registeredName : waitingConnections.keySet()) {
            if (registeredName.equals(name))
                return false;
        }
        waitingConnections.put(name, connection);
        return true;
    }

    protected synchronized boolean registerColor(String name, Color color){
        if(waitingColors.containsValue(color))
            return false;
        if(!waitingColors.containsKey(name)) {
            waitingColors.put(name, color);
            return true;
        }
        return false;
    }

    protected synchronized void checkAndBeginGame(){
        if (waitingColors.size() == chosenPlayerNumber && waitingConnections.size() == chosenPlayerNumber){
            startSetup = true;
            this.notifyAll();
        }
    }

    private synchronized void createMatch(){
        gameStarted = true;
        controller = new Controller();
        int playersAdded = 0;
        for(String client : waitingConnections.keySet()) {
            IClientConnection connection = waitingConnections.get(client);
            if(playersAdded < chosenPlayerNumber) {
                controller.addPlayer(client, waitingColors.get(client));
                activeConnections.add(connection);
                connection.addObserver(controller.getMessageManager()); //adding message manager to connection observers
                controller.addModelObserver((ModelObserver) connection); //adding connection to model observers
                playersAdded++;
            }
            else{
                if(connection != null)
                    // TODO -- inviare messaggio "superato numero massimo di giocatori"
                    connection.closeConnection();
            }
        }

        controller.handleGamePhase();
        waitingConnections.clear();
        waitingColors.clear();
    }

    protected synchronized void removeClient(IClientConnection connection){
        if (waitingConnections.containsValue(connection)){
            String name = "";
            for (String user : waitingConnections.keySet()){
                if (waitingConnections.get(user) == connection) {
                    name = user;
                    break;
                }
            }
            waitingConnections.remove(name);
            waitingColors.remove(name);
        }
        // Rimozione observer?
        controller.removeModelObserver((ModelObserver) connection);

        activeConnections.remove(connection);
        if(!startSetup) {
            notifyAll();
        } else if(!gameEnded){
            //TODO -- partita terminata, e se sono 3 giocatori e si disconnette chi ha perso?
        }
    }

    public void run(){
        while(true){
            try {
                boolean isGameStarted;
                int playerNumber;
                synchronized(this){
                    isGameStarted = gameStarted;
                    playerNumber = chosenPlayerNumber;
                }
                if (!isGameStarted && waitingConnections.size() < Math.min(playerNumber, Constant.MAXPLAYERS)) {
                    Socket newSocket = serverSocket.accept();
                    ClientHandler socketConnection;
                    if(firstConnection) {
                        socketConnection = new ClientHandler(newSocket, this, true);
                        firstConnection = false;
                    }
                    else{
                        socketConnection = new ClientHandler(newSocket, this);
                    }
                    executor.submit(socketConnection);
                } else {
                    this.wait();
                    if(startSetup)
                        createMatch();
                }
            } catch (IOException e) {
                System.out.println("Connection Error!");
                e.printStackTrace();
            } catch (InterruptedException e){
                System.out.println("InterruptedException Error");
                e.printStackTrace();
            }
        }
    }
}
