package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.observer.ServerObservable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends ServerObservable implements IClientConnection, Runnable{
    private final Socket socket;
    private final Server server; // lo userò per chiudere la connessione
    boolean active;
    String player;

    public ClientHandler(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;
    }

    private synchronized boolean isActive(){
        return active;
    }


    @Override
    public void run() {
        ObjectInputStream in;
        ObjectOutputStream out;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            //DEVO SALVARE IL NOME DEL PLAYER
            while(isActive()){
                // TODO -- controllo tipo messaggio
                Answer message = (Answer) in.readObject();
                notifyObservers(message, player);
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e){
            e.printStackTrace();
        }
    }
}
