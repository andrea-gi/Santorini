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


    @Override
    public void run() {
        while(true){
            try{
                Request message = queue.take();
                if (message instanceof AutoClose) {
                    setActive(false);
                    closeStream();
                }
                Answer answer = requestManager.handleRequest(message);
                if (answer != null)
                    sendAnswer(answer);
                // if (message instanceof RequestServerConfig) controllo la tipologia di messaggio ed eventualmente chiudo tutto
            } catch (InterruptedException e) {
                setActive(false);
                e.printStackTrace();
                // cosa faccio?
            }
        }
    }
}
