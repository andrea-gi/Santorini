package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;

public class AnswerComposer {
    private final Request request;
    private Answer answer;

    public AnswerComposer(Request request){
        this.request = request;
    }


    public Answer packetAnswer(String[] params){
        if(request != null) {
            switch (request.getClass().getName()) {
                case "RequestNameColor":
                    RequestNameColor castRequest = (RequestNameColor) request;
                    String name = params[0];
                    Color color = castRequest.getAvailableColors()[Integer.parseInt(params[1])-1];
                    answer = new AnswerNameColor(name, color);
                    break;
            }
            return answer;
        }else{
            throw new NullPointerException("It is not possible to get an answer without a request");
        }
    }
}