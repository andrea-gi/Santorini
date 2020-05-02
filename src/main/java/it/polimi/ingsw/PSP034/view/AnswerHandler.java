package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.view.printables.ANSI;

import java.util.Scanner;

public class AnswerHandler {
    private final Request request;
    private String answer;

    public AnswerHandler(Request request){
        this.request = request;
        answer = null;
    }


    public void packetAnswer(){
        if(answer != null  &&  request != null) {
            switch (request.getClass().getName()) {
                case "":
                    //Impacchettamento della rispsota
                    break;
            }
        }
    }
}