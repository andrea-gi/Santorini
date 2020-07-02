package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.messages.gameOverPhase.EndByDisconnection;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.PersonalDefeatRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.WinnerRequest;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.ServerInfo;

import java.io.Serializable;

/**
 * Answer message sent by server to client.
 */
public abstract class Request implements Serializable {

    /**
     * Returns if a message is a silent close request, meaning that it will not generate a connection error after being handled.
     * @param message Message to be checked.
     * @return {@code true} if message is a silent close request, {@code false} otherwise.
     */
    public static boolean isSilentCloseRequest(Request message){
        return (message instanceof RequestServerConfig && ((RequestServerConfig) message).getInfo() == ServerInfo.ALREADY_STARTED)
                || (message instanceof PersonalDefeatRequest && !((PersonalDefeatRequest) message).getWinner().equals(""))
                || message instanceof WinnerRequest
                || message instanceof EndByDisconnection;
    }
}
