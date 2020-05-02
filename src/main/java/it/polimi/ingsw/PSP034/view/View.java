package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;

public class View {
    RequestHub requestHub;

    public View(){
        requestHub = new RequestHub();
        //TODO
    }

    public Answer HandleRequest(Request request) {
        requestHub.newRequest(request);
        return requestHub.
    }

}
