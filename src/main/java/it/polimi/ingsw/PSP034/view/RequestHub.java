package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestClientConfig;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.TitleRequest;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import it.polimi.ingsw.PSP034.view.scenes.*;
import it.polimi.ingsw.PSP034.view.scenes.clientConfiguration.ServerAddress;
import it.polimi.ingsw.PSP034.view.scenes.clientConfiguration.ServerPort;
import it.polimi.ingsw.PSP034.view.scenes.clientConfiguration.TitleScene;
import it.polimi.ingsw.PSP034.view.scenes.playPhase.Table;
import it.polimi.ingsw.PSP034.view.scenes.serverConfiguration.*;
import it.polimi.ingsw.PSP034.view.scenes.setupPhase.*;

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
        else if(request instanceof PlayRequest){
            return newPlayRequest((PlayRequest) request);
        }else if(request instanceof SlimBoard){
            SlimBoard slimBoard = (SlimBoard) request;
            ((Table) currScene).updateBoard(slimBoard.getDome(), slimBoard.getBuilding(), slimBoard.getColor(), slimBoard.getSex(), false, slimBoard.getCurrentPlayer());
            currScene.show();
            lastRequest = request;
            return null;
        }else{
            //TODO -- decidere se va bene
            return null;
        }
    }

    private Answer newRequestClientConfig(RequestClientConfig request){
        if (request instanceof TitleRequest) {
            currScene = new TitleScene();
            currScene.show();
            lastRequest = request;
            return null;
        }
        else if (request instanceof RequestIP){
            String[] answers = new String[2];
            currScene = new ServerAddress();
            answers[0] = currScene.show();
            currScene = new ServerPort();
            answers[1] = currScene.show();
            answerComposer = new AnswerComposer(request);
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

            case CARDS_CHOICE_WAIT:
                currScene = new CardsChoiceWait();
                currScene.show();
                lastRequest = request;
                return null;

            case ALREADY_STARTED:
                currScene = new AlreadyStarted();
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
        if (request instanceof  GodLikeInfo){
            currScene = new GodLikeChosen(((GodLikeInfo) request).getGodLikePlayer());
            currScene.show();
            lastRequest = request;
            return null;
        }

        else if(request instanceof RequestCardsChoice) {
            currScene = new CardsChoice(((RequestCardsChoice) request).getPlayerNumber());
            answer = currScene.show();
            answerComposer = new AnswerComposer(request);
            lastRequest = request;
            return answerComposer.packetAnswer(answer);
        }

        else if (request instanceof RequestFirstPlayer) {
            currScene = new FirstPlayer(((RequestFirstPlayer) request).getPlayers());
            answer = currScene.show();
            answerComposer = new AnswerComposer(request);
            lastRequest = request;
            return answerComposer.packetAnswer(answer);
        }

        else if (request instanceof FirstPlayerInfo){
            currScene = new FirstPlayerChosen(((FirstPlayerInfo) request).getFirstPlayer());
            currScene.show();
            lastRequest = request;
            return null;
        }

        else if (request instanceof RequestPersonalGod) {
            currScene = new PersonalGodChoice(((RequestPersonalGod) request).getPossibleGods());
            answer = currScene.show();
            answerComposer = new AnswerComposer(request);
            lastRequest = request;
            return answerComposer.packetAnswer(answer);
        }

        else if (request instanceof RequestPlaceWorker) {
            SlimBoard slimBoard = ((RequestPlaceWorker) request).getSlimBoard();
            ((Table) currScene).updatePlaceWorker(slimBoard.getDome(), slimBoard.getBuilding(), slimBoard.getColor(), slimBoard.getSex(), slimBoard.getCurrentPlayer());
            answer = currScene.show();
            answerComposer = new AnswerComposer(request);
            answerComposer.packetAnswer(answer);
            lastRequest = request;
            return answerComposer.packetAnswer(answer);
        }

        else if (request instanceof  ReceivedWorkerChoice){
            ((Table) currScene).updateClearRequest();
            currScene.show();
            return null;
        }

        else if(request instanceof InitializeBoard){
            SlimBoard slimBoard = ((InitializeBoard) request).getSlimBoard();
            currScene = new Table(slimBoard.getGodsList());
            currScene.show();
            return null;
        }

        //TODO -- decidere se va bene
        return null;
    }

    private Answer newPlayRequest(PlayRequest request){
        String answer;
        String[] answers =  new String[2];
        if(request instanceof RequestAction){
            RequiredActions[] actions = request.getRequiredActions();
            Sex requiredSex = null;
            boolean hasChoice = false;
            for (RequiredActions action : actions) {
                switch (action) {
                    case REQUEST_WORKER:
                        ((Table) currScene).updateSelectWorker();
                        answers[0] = currScene.show();
                        lastRequest = request;
                        requiredSex = answers[0].equals("1") ? Sex.MALE : Sex.FEMALE;
                        hasChoice = true;
                        break;
                    case REQUIRED_MALE:
                        requiredSex = Sex.MALE;
                        answers[0] = "1";
                        hasChoice = false;
                        lastRequest = request;
                        break;
                    case REQUIRED_FEMALE:
                        requiredSex = Sex.FEMALE;
                        answers[0] = "2";
                        hasChoice = false;
                        lastRequest = request;
                        break;
                    case REQUEST_MOVE:
                        Directions[] moveDirections = requiredSex == Sex.MALE ? ((RequestAction) request).getPossibleMaleDirections() : ((RequestAction) request).getPossibleFemaleDirections();
                        ((Table) currScene).updateMove(requiredSex, moveDirections, hasChoice);
                        answers[1] = currScene.show();
                        answerComposer = new AnswerComposer(request);
                        lastRequest = request;
                        return answerComposer.packetAnswer(answers);
                    case REQUEST_BUILD:
                        Directions[] buildDirections = requiredSex == Sex.MALE ? ((RequestAction) request).getPossibleMaleDirections() : ((RequestAction) request).getPossibleFemaleDirections();
                        ((Table) currScene).updateBuild(requiredSex, buildDirections, hasChoice);
                        answers[1] = currScene.show();
                        answerComposer = new AnswerComposer(request);
                        lastRequest = request;
                        return answerComposer.packetAnswer(answers);
                }
            }
        }

        else if(request instanceof RequestBooleanChoice){
            RequiredActions[] actions = request.getRequiredActions();
            for(RequiredActions action : actions){
                if (action == RequiredActions.REQUEST_POWER) {
                    ((Table) currScene).updatePower();
                    answer = currScene.show();
                    answerComposer = new AnswerComposer(request);
                    lastRequest = request;
                    return answerComposer.packetAnswer(answer);
                }
            }
        }

        else if(request instanceof RequestStart){
            ((Table) currScene).updateStart();
            currScene.show();
            return new AnswerStart();
        }

        else if(request instanceof RequestEnd){
            ((Table) currScene).updateClearRequest();
            currScene.show();
            return null;
        }

        //TODO -- decidere se va bene
        return null;
    }
}
