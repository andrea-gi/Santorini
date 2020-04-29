package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;

import java.util.concurrent.BlockingQueue;

public class ClientGameHandler implements Runnable{
    private final BlockingQueue<Request> queue;
    private final RequestManager requestManager; //CLI OR GUI

    public ClientGameHandler(RequestManager requestManager, BlockingQueue<Request> requestBlockingQueue){
        this.requestManager = requestManager;
        this.queue = requestBlockingQueue;
    }


    @Override
    public void run() {
        while(true){
            try{
                Request message = queue.take();
                requestManager.manageRequest(message);
                // if (message instanceof RequestServerConfig) controllo la tipologia di messaggio ed eventualmente chiudo tutto
            } catch (InterruptedException e) {
                e.printStackTrace();
                // cosa faccio?
            }
        }
    }
}
