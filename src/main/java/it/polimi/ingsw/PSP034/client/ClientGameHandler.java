package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;

import java.util.concurrent.BlockingQueue;

public class ClientGameHandler implements Runnable{
    private final BlockingQueue<Request> queue;

    private boolean active = true;

    public ClientGameHandler(BlockingQueue<Request> requestBlockingQueue){
        this.queue = requestBlockingQueue;
    }

    // public manageRequest(Request message);

    @Override
    public void run() {
        while(true){
            if(!active)
                return;
            try{
                Request message = queue.take();
                manageRequest(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // cosa faccio?
            }
        }
    }
}
