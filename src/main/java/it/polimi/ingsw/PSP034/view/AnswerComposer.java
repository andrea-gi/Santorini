package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestClientConfig;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
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
            }else if(request instanceof SetupRequest){
                return answerSetup(params);
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
            answer = new AnswerIP(ip, Integer.parseInt(params[1]));
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
                gods[i] = GodDescription.values()[numbers[i]].getName();
            }
            answer = new AnswerCardsChoice(gods);
        }
        else if (request instanceof RequestFirstPlayer) {
            // TODO -- gestire first player
        }
        else if (request instanceof RequestPersonalGod) {
            answer = new AnswerPersonalGod(((RequestPersonalGod) request).getPossibleGods()[Integer.parseInt(params[0])]);
        }
        return answer;
    }
}