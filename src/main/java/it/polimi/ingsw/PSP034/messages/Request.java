package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.messages.gameOverPhase.EndByDisconnection;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.PersonalDefeatRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.WinnerRequest;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.ServerInfo;

import java.io.Serializable;

public abstract class Request implements Serializable {
    public static boolean isSilentCloseRequest(Request message){
        return (message instanceof RequestServerConfig && ((RequestServerConfig) message).getInfo() == ServerInfo.ALREADY_STARTED)
                || (message instanceof PersonalDefeatRequest && !((PersonalDefeatRequest) message).getWinner().equals(""))
                || message instanceof WinnerRequest
                || message instanceof EndByDisconnection;
    }
}
