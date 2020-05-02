package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class RequestHub {
    private Request lastRequest;
    private Scene scene;
    private AnswerHandler answerHandler;

    public RequestHub(){
        lastRequest = null;
        scene = new Scene();
    }

    public Answer newRequest(Request request){
        switch(request.getClass().toString()){
            case "SlimBoard":
                scene = new PlayTable();
                scene.update(SlimBoard board, null);
        }
    }

}
