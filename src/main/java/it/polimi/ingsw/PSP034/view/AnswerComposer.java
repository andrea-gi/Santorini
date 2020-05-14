package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestClientConfig;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerNumber;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import it.polimi.ingsw.PSP034.view.printables.godcards.GodDescription;

public class AnswerComposer {
    private final Request request;
    private Answer answer;

    public AnswerComposer(Request request){
        this.request = request;
    }

    public Answer packetAnswer(String...params){
        if(request != null) {
            if(request instanceof RequestClientConfig) {
                return answerClientConfig(params);
            }else if (request instanceof RequestServerConfig) {
                return answerServerConfig(params);
            }else if (request instanceof SetupRequest) {
                return answerSetup(params);
            }else if (request instanceof PlayRequest) {
                return answerPlay(params);
            }else
                //TODO decidere testo
                throw new IllegalArgumentException();
        }else{
            throw new NullPointerException("It is not possible to get an answer without a request");
        }
    }

    private Answer answerClientConfig(String...params){
        if (request instanceof RequestIP){
            String ip = params[0].equals("") ? "localhost" : params[0];
            int port = params[1].equals("") ? 2020 : Integer.parseInt(params[1]);
            answer = new AnswerIP(ip, port);
        }
        return answer;
    }

    private Answer answerServerConfig(String...params){
        switch (((RequestServerConfig) request).getInfo()) {
            case REQUEST_NAME_COLOR:
                RequestNameColor castRequest = (RequestNameColor) request;
                String name = params[0];
                Color color = castRequest.getAvailableColors()[Integer.parseInt(params[1])-1];
                answer = new AnswerNameColor(name, color);
                break;

            case REQUEST_PLAYER_NUMBER:
                answer = new AnswerNumber(Integer.parseInt(params[0]));
                break;
        }
        return answer;
    }

    private Answer answerSetup(String...params){
        if (request instanceof RequestCardsChoice) {
            String[] stringNumbers = params[0].split(",");
            int[] numbers = new int[((RequestCardsChoice) request).getPlayerNumber()];
            for (int i = 0; i < ((RequestCardsChoice) request).getPlayerNumber(); i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i]);
            }
            String[] gods = new String[((RequestCardsChoice) request).getPlayerNumber()];
            for (int i = 0; i < ((RequestCardsChoice) request).getPlayerNumber(); i++) {
                gods[i] = GodDescription.values()[numbers[i]-1].getName();
            }
            answer = new AnswerCardsChoice(gods);
        }
        else if (request instanceof RequestFirstPlayer) {
            answer = new AnswerFirstPlayer(((RequestFirstPlayer) request).getPlayers()[Integer.parseInt(params[0])-1]);
        }
        else if (request instanceof RequestPersonalGod) {
            answer = new AnswerPersonalGod(((RequestPersonalGod) request).getPossibleGods()[Integer.parseInt(params[0])-1]);
        }else if(request instanceof RequestPlaceWorker) {
            SlimBoard slimBoard = ((RequestPlaceWorker) request).getSlimBoard();
            int tile = Integer.parseInt(params[0]);
            int x = 0;
            int y;
            int counter = 0;
            cycle:
            for (y = 0; y < Constant.DIM; y++){
                for (x = 0; x < Constant.DIM; x++){
                    if (!slimBoard.getDome()[x][y] && slimBoard.getSex()[x][y] == null){
                        counter++;
                        if (counter == tile)
                            break cycle;
                    }
                }
            }
            answer = new AnswerPlaceWorker(((RequestPlaceWorker) request).getSex(), x, Constant.DIM - 1 - y );
        }
        return answer;
    }

    private Answer answerPlay(String...params){
        if(request instanceof RequestAction){
            Sex sex = params[0].equals("1") ? Sex.MALE : Sex.FEMALE;
            Directions[] directions = sex == Sex.MALE ? ((RequestAction) request).getPossibleMaleDirections() : ((RequestAction) request).getPossibleFemaleDirections();
            Directions direction = directions[Integer.parseInt(params[1])-1];
            answer = new AnswerAction(sex, direction);
        }else if(request instanceof RequestBooleanChoice) {
            boolean choice = params[0].equals("1");
            answer = new AnswerBooleanChoice(choice);
        }
        return answer;
    }
}