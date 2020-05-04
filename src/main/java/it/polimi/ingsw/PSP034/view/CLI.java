package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.client.Client;
import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.TitleRequest;

public class CLI implements RequestManager, Runnable {
    RequestHub requestHub;

    public CLI(){
        requestHub = new RequestHub();
        //TODO
    }

    @Override
    public Answer handleRequest(Request request) {
        return requestHub.newRequest(request);
    }

    @Override
    public void run() {
        handleRequest(new TitleRequest());
        AnswerIP ip = (AnswerIP) handleRequest(new RequestIP());
        Client client = new Client(this, ip.getIp(), ip.getPort());
        Thread runClient = new Thread(client);
        runClient.start();
    }
}
