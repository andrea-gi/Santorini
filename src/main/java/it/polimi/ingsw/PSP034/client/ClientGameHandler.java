package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoClose;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class ClientGameHandler implements Runnable{
    private final BlockingQueue<Request> queue;
    private final RequestManager requestManager; //CLI OR GUI
    private final ObjectOutputStream out;
    private boolean isActive;

    public ClientGameHandler(RequestManager requestManager, BlockingQueue<Request> requestBlockingQueue, ObjectOutputStream out){
        this.requestManager = requestManager;
        this.queue = requestBlockingQueue;
        this.out = out;
        requestManager.setSender(this);
        setActive(true);
    }

    private synchronized boolean isActive(){
        return isActive;
    }

    private synchronized void setActive(boolean active){
        this.isActive = active;
    }

    private synchronized void closeStream(){
        try {
            out.close();
        } catch (IOException ignored){
        }
    }

    private synchronized void sendAnswer(Answer message){
        try {
            if (isActive()) {
                out.reset();
                out.writeObject(message);
                out.flush();
            }
        } catch(IOException e){
            setActive(false);
            e.printStackTrace();
            //TODO -- gestire eccezione. dove va gestita? Prob nel client
        }
    }

    /**
     * Handles a single answer
     * @param message Answer to the given request, {@code null} if the answer is not needed.
     */
    public void send(Answer message){
        if (message instanceof AutoClose) {
            setActive(false);
            closeStream();
        }
        else if (message != null)
            sendAnswer(message);
        // if (message instanceof RequestServerConfig) controllo la tipologia di messaggio ed eventualmente chiudo tutto

    }

    @Override
    public void run() {
        while(isActive()){
            try{
                Request message = queue.take();
                requestManager.handleRequest(message);
            } catch (InterruptedException e) {
                setActive(false);
                e.printStackTrace();
                // cosa faccio?
            }
        }
    }
}
