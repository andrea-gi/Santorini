package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoCloseAnswer;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoCloseRequest;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;
import it.polimi.ingsw.PSP034.view.GameException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class ClientGameHandler implements Runnable{
    private final BlockingQueue<Request> queue;
    private final RequestManager requestManager; //CLI OR GUI
    private final ObjectOutputStream out;
    private boolean isActive;
    private boolean silentEnded = false;

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

    synchronized void setSilentEnded(boolean silentEnded){
        this.silentEnded = silentEnded;
    }

    synchronized boolean isSilentEnded(){
        return this.silentEnded;
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
            closeStream();
            if (!isSilentEnded())
                requestManager.showError(new ErrorMessage("C001", "Connection error. Could not send message"));
        }
    }

    /**
     * Handles a single answer
     * @param message Answer to the given request, {@code null} if the answer is not needed.
     */
    public void send(Answer message){
        if (message instanceof AutoCloseAnswer) {
            setActive(false);
            closeStream();
            System.exit(0);
        }
        else if (message != null)
            sendAnswer(message);
        // if (message instanceof RequestServerConfig) controllo la tipologia di messaggio ed eventualmente chiudo tutto

    }

    private final Object waitLock = new Object();

    @Override
    public void run() {
        while(isActive() || !queue.isEmpty()){
            try{
                Request message = null;
                if(requestManager.canHandleRequest())
                    message = queue.take();
                else{
                    synchronized (waitLock) {
                        waitLock.wait(5);
                    }
                }

                if(Request.isSilentCloseRequest(message)){
                    queue.clear();
                }

                else if (message instanceof AutoCloseRequest) {
                    closeStream();
                    throw new GameException("C002", "Connection error. Could not receive message.");
                }
                if (message != null)
                    requestManager.handleRequest(message);

            } catch (InterruptedException e) {
                setActive(false);
                if (!isSilentEnded())
                    requestManager.showError(new ErrorMessage("C003", "Fatal error."));
            } catch (GameException e){
                setActive(false);
                if (!isSilentEnded())
                    requestManager.showError(new ErrorMessage(e.getCode(), e.getMessage()));
            }
        }
    }
}
