package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;
import it.polimi.ingsw.PSP034.view.GameException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Abstract class extended by each Request Manager (CLI or GUI)
 */
public abstract class RequestManager {
    private ClientGameHandler sender;

    /**
     * Handles a single request (synchronously).
     * @param message           Request to be handled.
     * @throws GameException    Thrown if there has been a client error.
     */
    public abstract void handleRequest(Request message) throws GameException;

    /**
     * Displays an error. Can be called asynchronously.
     *
     * @param error Error to be shown.
     */
    public abstract void showError(ErrorMessage error);

    /**
     * Sets instance used to send answers.
     *
     * @param sender Reference to ClientGameHandler used by a client
     */
    public void setSender(ClientGameHandler sender) {
        this.sender = sender;
    }

    /**
     * Sends an answer through a previously set {@link ClientGameHandler}.
     * @param answer Answer to be sent.
     */
    public synchronized void sendAnswer(Answer answer){
        if (sender != null)
            new Thread(() -> {
                sender.send(answer);
            }).start();
    }

    /**
     * Returns message handling availability.
     *
     * @return {@code true} if this instance of the RequestHub can manage a new request
     * ({@link RequestManager#handleRequest(Request)}), {@code false} otherwise.
     */
    public abstract boolean canHandleRequest();

    private final ExecutorService executorConnection = Executors.newSingleThreadExecutor();

    /** Creates the connection between the client and the server.
     *
     *  @param answer   Answer containing IP address and port.
     *  @return         {@link Future} representing the result of the connection attempt.
     */
    public Future<Boolean> createConnection(AnswerIP answer){
        return executorConnection.submit(() ->{
            Client client = new Client(this, answer.getIp(), answer.getPort());
            if (client.startConnection()){
                new Thread(client).start();
                return true;
            } else {
                return false;
            }
        });
    }
}
