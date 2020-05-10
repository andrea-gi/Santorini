package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class ClientGameHandler implements Runnable{
    private final BlockingQueue<Request> queue;
    private final RequestManager requestManager; //CLI OR GUI
    private final ObjectOutputStream out;

    public ClientGameHandler(RequestManager requestManager, BlockingQueue<Request> requestBlockingQueue, ObjectOutputStream out){
        this.requestManager = requestManager;
        this.queue = requestBlockingQueue;
        this.out = out;
    }

    private synchronized void sendAnswer(Answer message){
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            e.printStackTrace();
            //TODO -- gestire eccezione. dove va gestita? Prob nel client
        }
    }


    @Override
    public void run() {
        while(true){
            try{
                Request message = queue.take();
                Answer answer = requestManager.handleRequest(message);
                if (answer != null)
                    sendAnswer(answer);
                // if (message instanceof RequestServerConfig) controllo la tipologia di messaggio ed eventualmente chiudo tutto
            } catch (InterruptedException e) {
                e.printStackTrace();
                // cosa faccio?
            }
        }
    }
}
