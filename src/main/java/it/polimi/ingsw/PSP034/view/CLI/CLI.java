package it.polimi.ingsw.PSP034.view.CLI;

import it.polimi.ingsw.PSP034.client.Client;
import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.*;

/**
 * CLI entry point. Starts the CLI interface and works as a proxy for messages or error to be handled.
 */
public class CLI extends RequestManager implements Runnable {
    private final CLIRequestHub CLIRequestHub;

    /**
     * Creates a new {@link CLIRequestHub}.
     */
    public CLI(){
        CLIRequestHub = new CLIRequestHub();
    }

    /**
     * Forwards the request message to the CLIRequestHub and receives the answer back. If the answer is a {@link RestartClient} instance,
     * the {@link Client} is created again, otherwise the correct answer is sent to the game server.
     *
     * @param request The request message.
     */
    @Override
    public void handleRequest(Request request) {
        Answer answer = CLIRequestHub.newRequest(request);
        if (answer instanceof RestartClient){
            startConnection();
        } else {
            sendAnswer(answer);
        }
    }

    /**
     * Asks the {@link CLIRequestHub} to print an error and then closes the application.
     *
     * @param error Error to be printed.
     */
    @Override
    public void showError(ErrorMessage error) {
        CLIRequestHub.newRequest(error);
        System.exit(0);
    }

    /**
     * Returns message handling availability.
     *
     * @return always {@code true}, because CLI is input blocking.
     */
    @Override
    public boolean canHandleRequest() {
        return true;
    }

    private void startConnection(){
        Client client;
        boolean connected = true;
        do{
            AnswerIP ip = (AnswerIP) CLIRequestHub.newRequest(new RequestIP(!connected));
            client = new Client(this, ip.getIp(), ip.getPort());
            connected = client.startConnection();
        } while (!connected);
        Thread runClient = new Thread(client);
        runClient.start();
    }


    @Override
    public void run() {
        handleRequest(new TitleRequest());
        startConnection();
    }
}
