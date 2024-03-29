package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoCloseAnswer;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;
import it.polimi.ingsw.PSP034.view.GameException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

/**
 * Message handler and sender. Communicates directly with {@link RequestManager}.
 */
public class ClientGameHandler implements Runnable{
    private final BlockingQueue<Request> queue;
    private final RequestManager requestManager; //CLI OR GUI
    private final ObjectOutputStream out;
    private boolean isActive;
    private boolean silentEnded = false;

    /**
     * Creates a new ClientGameHandler able to manage incoming request and to send them.
     *
     * @param requestManager        CLI or GUI request manager.
     * @param requestBlockingQueue  Incoming messages queue.
     * @param out                   Output message stream.
     */
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

    /**
     * Sets synchronously if client has ended silently (e.g. win/lose condition), therefore its disconnection should not be
     * seen as an error.
     *
     * @param silentEnded {@code true} if client has ended silently, {@code false} otherwise.
     */
    synchronized void setSilentEnded(boolean silentEnded){
        this.silentEnded = silentEnded;
    }

    /**
     * Returns synchronously if client has ended (e.g. win/lose condition), therefore its disconnection should not be
     * seen as an error.
     *
     * @return {@code true} if client has ended silently, {@code false} otherwise.
     */
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
     *
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
    }

    private final Object waitLock = new Object();

    /**
     * Continuously takes message from queue and forwards them to associated {@link RequestManager}.
     * Manages possible {@link GameException} or generic Exceptions.
     */
    @Override
    public void run() {
        while(isActive() || !queue.isEmpty()){
            try{
                Request message = null;
                if(requestManager.canHandleRequest())
                    message = queue.take();
                else{
                    synchronized (waitLock) {
                        waitLock.wait(100);
                    }
                }

                if(Request.isSilentCloseRequest(message)){
                    queue.clear();
                }

                if (message != null)
                    requestManager.handleRequest(message);

            } catch (GameException e){
                setActive(false);
                if (!isSilentEnded())
                    requestManager.showError(new ErrorMessage(e.getCode(), e.getMessage()));
            } catch (Exception e) {
                setActive(false);
                if (!isSilentEnded())
                    requestManager.showError(new ErrorMessage("C003", "Fatal error."));
            }
        }
    }
}
