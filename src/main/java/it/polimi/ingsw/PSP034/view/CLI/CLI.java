package it.polimi.ingsw.PSP034.view.CLI;

import it.polimi.ingsw.PSP034.client.Client;
import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.TitleRequest;

public class CLI extends RequestManager implements Runnable {
    private final CLIRequestHub CLIRequestHub;

    public CLI(){
        CLIRequestHub = new CLIRequestHub();
        //TODO
    }

    public void handleRequest(Request request) {
        Answer answer = CLIRequestHub.newRequest(request);
        sendAnswer(answer);
    }

    @Override
    public void run() {
        handleRequest(new TitleRequest());
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
}
