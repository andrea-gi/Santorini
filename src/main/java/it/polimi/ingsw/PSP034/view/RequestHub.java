package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestClientConfig;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.setupPhase.RequestCardsChoice;
import it.polimi.ingsw.PSP034.messages.setupPhase.RequestPersonalGod;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupRequest;
import it.polimi.ingsw.PSP034.view.scenes.*;
import it.polimi.ingsw.PSP034.view.scenes.clientConfiguration.ServerAddress;
import it.polimi.ingsw.PSP034.view.scenes.clientConfiguration.ServerPort;
import it.polimi.ingsw.PSP034.view.scenes.clientConfiguration.Title;
import it.polimi.ingsw.PSP034.view.scenes.serverConfiguration.*;
import it.polimi.ingsw.PSP034.view.scenes.setupPhase.CardsChoice;
import it.polimi.ingsw.PSP034.view.scenes.setupPhase.PersonalGodChoice;

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
        if(request instanceof RequestClientConfig)
            return newRequestClientConfig((RequestClientConfig) request);
        else if(request instanceof RequestServerConfig)
            return newRequestServerConfig((RequestServerConfig) request);
        else if(request instanceof SetupRequest)
            return newSetupRequest((SetupRequest) request);
        else{
            //TODO -- decidere se va bene
            return null;
        }
    }

    private Answer newRequestClientConfig(RequestClientConfig request){
        switch (request.getClass().toString()){
            case "Title":
                currScene = new Title();
                currScene.show();
                lastRequest = request;
                return null;

            case "RequestIP":
                String[] answers = new String[2];
                currScene = new ServerAddress();
                answers[0] = currScene.show();
                currScene = new ServerPort();
                answers[1] = currScene.show();
                answerComposer = new AnswerComposer((RequestIP) request);
                lastRequest = request;
                return answerComposer.packetAnswer(answers);
        }
        //TODO -- decidere se va bene
        return null;
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
                lastRequest = request;
                return answerComposer.packetAnswer(answers);

            case REQUEST_PLAYER_NUMBER:
                currScene = new PlayerNumber();
                String answer = currScene.show();
                answerComposer = new AnswerComposer(request);
                lastRequest = request;
                return answerComposer.packetAnswer(answer);

            case LOBBY:
                currScene = new Lobby();
                currScene.show();
                lastRequest = request;
                return null;

            case WELCOME_WAIT:
                currScene = new WelcomeWait();
                currScene.show();
                lastRequest = request;
                return null;

            case SUCCESSFULLY_ADDED:
                currScene = new SuccessfullyAdded();
                currScene.show();
                lastRequest = request;
                return null;
        }
        //TODO -- decidere se va bene
        return null;
    }

    private Answer newSetupRequest(SetupRequest request){
        String answer;
        String[] answers;
        switch (request.getClass().toString()){
            case "RequestCardsChoice":
                currScene = new CardsChoice(((RequestCardsChoice) request).getPlayerNumber());
                answer = currScene.show();
                answerComposer = new AnswerComposer(request);
                lastRequest = request;
                return answerComposer.packetAnswer(answer);

            case "RequestFirstPlayer":
                //TODO
                break;

            case "RequestPersonalGod":
                currScene = new PersonalGodChoice(((RequestPersonalGod) request).getPossibleGods());
                answer = currScene.show();
                answerComposer.packetAnswer(answer);
                lastRequest = request;
                return answerComposer.packetAnswer(answer);

            case "RequestPlaceWorker":
                //TODO
                break;
        }
        //TODO -- decidere se va bene
        return null;
    }
}
