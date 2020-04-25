package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequiredServerConfig;
import it.polimi.ingsw.PSP034.observer.ModelObserver;
import it.polimi.ingsw.PSP034.observer.ServerObservable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends ServerObservable implements IClientConnection, ModelObserver, Runnable{
    private final Socket socket;
    private final Server server; // lo userò per chiudere la connessione
    ObjectInputStream in;
    ObjectOutputStream out;
    boolean active;
    boolean firstConnected;
    String player = "none";


    public ClientHandler(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.console().printf("Cannot open input/output stream.");
            e.printStackTrace();
        }
    }

    public ClientHandler(Socket socket, Server server, boolean firstConnected){
        this(socket, server);
        this.firstConnected = firstConnected;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Request message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void asyncSend(Request message){
        new Thread(() -> send(message)).start();
    }

    @Override
    public void run() {
        //DEVO SALVARE IL NOME DEL PLAYER
        boolean register = true;
        while(isActive()){
            try{
                if (register){
                    manageRegistration();
                    if(firstConnected)
                        manageFirstPlayer();
                    register = false;
                }

                // TODO -- controllo tipo messaggio
                Answer message = (Answer) in.readObject();
                notifyObservers(message, player);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                active = false;
            } finally{
                close();
            }

        }
    }

    private void manageRegistration(){
        boolean ended = false;
        boolean validSubAction;
        boolean nameRegistration = true;
        AnswerServerConfig message;
        Object serverObject;
        while(!ended){
            try {
                if (nameRegistration) {
                    send(new RequestServerConfig(RequiredServerConfig.REQUEST_NAME));
                    serverObject = in.readObject();
                    if (serverObject instanceof AnswerServerConfig) {
                        message = (AnswerServerConfig) serverObject;
                        String name = message.getName();
                        if (name != null) {
                            validSubAction = server.registerName(name, this);
                            if (validSubAction) {
                                nameRegistration = false;
                                this.player = name;
                            }
                            // TODO -- notificare eventuale errore
                        }
                    }
                }
                else {
                    send(new RequestServerConfig(RequiredServerConfig.REQUEST_COLOR));
                    serverObject = in.readObject();
                    if (serverObject instanceof AnswerServerConfig) {
                        message = (AnswerServerConfig) serverObject;
                        Color color = message.getColor();
                        if (color != null) {
                            validSubAction = server.registerColor(player, color);
                            if (validSubAction)
                                ended = true;
                            // TODO -- notificare eventuale errore
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        server.checkAndBeginGame();
    }

    private void manageFirstPlayer(){
        boolean ended = false;
        while(!ended)
        try{
            Object serverObject = in.readObject();
            if (serverObject instanceof AnswerServerConfig){
                AnswerServerConfig message = (AnswerServerConfig) serverObject;
                int playerNumber = message.getPlayerNumber();
                if (playerNumber >= Constant.MINPLAYERS && playerNumber <= Constant.MAXPLAYERS){
                    server.setPlayerNumber(playerNumber);
                    ended = true;
                }
                // TODO -- notificare eventuale errore

            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void closeConnection() {
        // TODO -- inviare messaggio chiusura socket
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Closing connection with " + player);
        server.removeClient(this);
        System.out.println("Done!");
    }

    @Override
    public void update(ModelUpdate message) {
        asyncSend(message);
    }
}
