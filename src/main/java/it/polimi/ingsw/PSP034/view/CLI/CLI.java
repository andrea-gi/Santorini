package it.polimi.ingsw.PSP034.view.CLI;

import it.polimi.ingsw.PSP034.client.Client;
import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.*;

public class CLI extends RequestManager implements Runnable {
    private final CLIRequestHub CLIRequestHub;

    public CLI(){
        CLIRequestHub = new CLIRequestHub();
        //TODO
    }

    @Override
    public void handleRequest(Request request) {
        Answer answer = CLIRequestHub.newRequest(request);
        if (answer instanceof RestartClient){
            startConnection();
        } else {
            sendAnswer(answer);
        }
    }

    @Override
    public void showError(ErrorMessage error) {
        CLIRequestHub.newRequest(error);
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
