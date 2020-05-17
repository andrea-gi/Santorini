package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.*;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.*;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import it.polimi.ingsw.PSP034.view.printables.ANSI;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Singleton logger. Used to log useful infos from the server.
 * To function correctly, {@link PrintStream} must be set using {@link ServerLogger#setPrintStreams(PrintStream, PrintStream...)}.
 * This class does not close any {@link PrintStream}.
 */
public class ServerLogger {
    //TODO -- AGGIUNGERE ANCHE MESSAGGI DI ERRORE
    private static ServerLogger singleton;
    private final ArrayList<PrintStream> out = new ArrayList<>();

    private final static String sendColor = ANSI.FG_bright_green;
    private final static String receivedColor = ANSI.FG_bright_blue;

    private ServerLogger(){}

    /**
     * Sets the {@link PrintStream} (at least one) where each info will be print to.
     * @param required Required one
     * @param others Optional ones
     */
    public void setPrintStreams(PrintStream required, PrintStream... others){
        if (this.out.size() == 0) {
            this.out.add(required);
            this.out.addAll(Arrays.asList(others));
        }
    }

    public static ServerLogger getInstance() {
        if(singleton == null){
            singleton = new ServerLogger();
        }
        return singleton;
    }

    /**
     * Prints necessary logs about {@link Answer} messages. It includes ANSI color.
     * Details about the content of the message are included too.
     * @param message Message to be logged
     * @param connection Sender of the message
     */
    public void printAnswerMessage(Answer message, IClientConnection connection){
        String messageDetailedInfo = "";
        if (message instanceof PlayAnswer)
            messageDetailedInfo = detailedPlayAnswer((PlayAnswer) message);
        else if (message instanceof SetupAnswer)
            messageDetailedInfo = detailedSetupAnswer((SetupAnswer) message);
        else if (message instanceof AnswerServerConfig)
            messageDetailedInfo = detailedAnswerServerConfig((AnswerServerConfig) message);


        printInfoConsole(receivedColor + "Received: " + ANSI.reset + message.getClass().getSimpleName() + messageDetailedInfo
                + " by: " + connection.getDebugColor() + connection.getName() + ANSI.reset);
    }

    private String detailedPlayAnswer(PlayAnswer message){
        String result = "";
        if (message instanceof AnswerAction)
            result = result + " (Worker: " + ((AnswerAction) message).getWorkerSex() + "; Direction: " + ((AnswerAction) message).getDirection() + ")";
        else if (message instanceof AnswerBooleanChoice)
            result = result + " (Power choice: " + ((AnswerBooleanChoice) message).getChoice() +")";

        return result;
    }

    private String detailedSetupAnswer(SetupAnswer message){
        String result = "";
        if (message instanceof AnswerCardsChoice)
            result = result + " (Gods chosen: " + Arrays.toString(((AnswerCardsChoice) message).getChoice()) + ")";
        else if (message instanceof AnswerFirstPlayer)
            result = result + " (First player: " + ((AnswerFirstPlayer) message).getFirstPlayer() + ")";
        else if (message instanceof AnswerPersonalGod)
            result = result + " (Chosen: " + ((AnswerPersonalGod) message).getMyGod() + ")";
        else if (message instanceof AnswerPlaceWorker)
            result = result + " (Placed: " + ((AnswerPlaceWorker) message).getSex() + " in "
                    + ((AnswerPlaceWorker) message).getX() + "," + ((AnswerPlaceWorker) message).getY() + ")";

        return result;
    }

    private String detailedAnswerServerConfig(AnswerServerConfig message){
        String result = "";
        if (message instanceof AnswerNameColor)
            result = result + " (Name: " + ((AnswerNameColor) message).getName() + ", color: " + ((AnswerNameColor) message).getColor() + ")";
        if (message instanceof AnswerNumber)
            result = result + " (" + ((AnswerNumber) message).getPlayerNumber() + " players)";
        return result;
    }

    public void printString(String string){
        printInfoConsole(string);
    }

    /**
     * Prints necessary logs about {@link Request} messages. It includes ANSI color.
     * Details about the content of the message are included too.
     * @param message Message to be logged
     * @param playerName Name of the message recipient
     * @param color Color of the message recipient (ANSI)
     */
    public void printRequestMessage(Request message, String playerName, String color){
        String messageDetailedInfo = "";
        if (message instanceof PlayRequest)
            messageDetailedInfo = detailedPlayRequest((PlayRequest) message);
        else if (message instanceof SetupRequest)
            messageDetailedInfo = detailedSetupRequest((SetupRequest) message);
        else if (message instanceof RequestServerConfig)
            messageDetailedInfo = detailedRequestServerConfig((RequestServerConfig) message);
        else if (message instanceof GameOverRequest)
            messageDetailedInfo = detailedGameOverRequest((GameOverRequest) message);

        printInfoConsole(sendColor + "Sent message: " + ANSI.reset +
                message.getClass().getSimpleName() + messageDetailedInfo + " to " + color + playerName + ANSI.reset);
    }

    private String detailedPlayRequest(PlayRequest message){
        String result = " (Actions required: " + Arrays.toString(message.getRequiredActions());
        if (message instanceof RequestAction) {
            RequestAction castRA = (RequestAction) message;
            result = result + "; Male: " + Arrays.toString(castRA.getPossibleMaleDirections())
                    + "; Female: " + Arrays.toString(castRA.getPossibleFemaleDirections());
        }
        return result + ")";
    }

    private String detailedSetupRequest(SetupRequest message){
        String result = "";
        if (message instanceof RequestPersonalGod) {
            RequestPersonalGod cast = (RequestPersonalGod) message;
            result = result + " (Possible Gods: " + Arrays.toString(cast.getPossibleGods()) + "; already chosen Gods: " +
                    Arrays.toString(cast.getAlreadyChosenGods()) + ")";
        }
        return result;
    }

    private String detailedRequestServerConfig(RequestServerConfig message){
        String result = " (" + message.getInfo();
        if (message instanceof RequestNameColor) {
            RequestNameColor cast = (RequestNameColor) message;
            result = result + "; names already chosen: " + Arrays.toString(cast.getAlreadyChosenNames()) +
                    "; colors available: " + Arrays.toString(cast.getAvailableColors());
        }
        return result + ")";
    }

    private String detailedGameOverRequest(GameOverRequest message){
        String result = "";
        if (message instanceof SingleLoserInfo)
            result = result + " (Loser: " + ((SingleLoserInfo) message).getLoser() ;
        else if (message instanceof WinnerRequest)
            result = result + " (Loser: " + ((WinnerRequest) message).getLoser();
        else if (message instanceof PersonalDefeatRequest)
            result = result + " (Winner: " + ((PersonalDefeatRequest) message).getWinner() + "; Losers: " + Arrays.toString(((PersonalDefeatRequest) message).getLosers());

        return result + ")";
    }

    /**
     * Prints synchronously a debug info in the console.
     * @param string Info to be printed
     */
    private void printInfoConsole(String string){
        for (PrintStream stream : out)
            if (stream != null)
                stream.println(string);
    }
}
