package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable{
    private String address;
    private int socketPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    Thread clientGameHandler;

    boolean clientEnded = false;

    private final BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(64);
    private final BlockingQueue<Answer> answerQueue = new ArrayBlockingQueue<>(64);

    public Client(String address, int socketPort){
        this.address = address;
        this.socketPort = socketPort;
    }

    //TODO -- bisogna prima chiamare questo metodo e poi avviare il thread
    public boolean startConnection(){
        try {
            socket = new Socket(address, socketPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            clientGameHandler = new Thread(new ClientGameHandler(requestManager, requestQueue));
            clientGameHandler.start();
            return true;
        } catch (IOException e){
            // devo chiudere stream (no) e socket?
            return false;
        }
    }

    public synchronized void setClientEnded(boolean value){
        this.clientEnded = value;
    }


    @Override
    public void run() {
        while(true){
            try{
                Object receivedMessage = in.readObject();
                if (receivedMessage instanceof Request){
                    requestQueue.add((Request) receivedMessage);
                } //else if PING
                //else if MESSAGGIO DI CHIUSURA ???
            }
            catch (IOException | ClassNotFoundException e){
                //cosa devo fare qui?
            }
            if (clientEnded){
                return;
            }
        }
    }
}
