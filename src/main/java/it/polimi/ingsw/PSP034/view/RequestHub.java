package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.view.scenes.ColorChoice;
import it.polimi.ingsw.PSP034.view.scenes.NameChoice;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class RequestHub {
    private Request lastRequest;
    private Scene currScene;
    private AnswerComposer answerComposer;

    public RequestHub(){
        lastRequest = null;
        currScene = null;
        answerComposer = null;
    }

    public Answer newRequest(Request request){
        if(request instanceof RequestServerConfig) {
            return newRequestServerConfig((RequestServerConfig) request);
        }
    }

    private Answer newRequestServerConfig(RequestServerConfig request){
        switch (request.getInfo()){
            case REQUEST_NAME_COLOR:
                RequestNameColor castRequest = (RequestNameColor) request;
                currScene = new NameChoice(castRequest.getAlreadyChosenNames());
                String[] answers = new String[2];
                answers[0] = currScene.show();
                currScene = new ColorChoice(castRequest.getAvailableColors());
                answers[1] = currScene.show();
                answerComposer = new AnswerComposer(castRequest);
                return answerComposer.packetAnswer(answers);
            default:
                //TODO -- decidere cosa ritornare
                return null;
        }
    }

}
