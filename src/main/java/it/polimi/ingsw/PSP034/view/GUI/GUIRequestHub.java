package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import javafx.scene.Scene;

import java.lang.reflect.Method;

public class GUIRequestHub implements RequestManager {
    private GUIController currentController;
    private static GUIRequestHub instance;


    private GUIRequestHub(){
    }

    public static GUIRequestHub getInstance(){
        if (instance == null){
            instance = new GUIRequestHub();
        }
        return instance;
    }

    public void setCurrentController(GUIController currentController) {
        this.currentController = currentController;
    }

    public GUIController getCurrentController() {
        return currentController;
    }

    private void craftRequest(RequestIP request){
        ScenePath.setNextScene(currentController.getPane().getScene(), ScenePath.SERVER_LOGIN);
    }

    private void craftRequest(RequestServerConfig request){
        Scene scene = currentController.getPane().getScene();
        switch (request.getInfo()){
            //TODO -- break
            case REQUEST_NAME_COLOR:
                ScenePath.setNextScene(scene, ScenePath.LOGIN);

            case REQUEST_PLAYER_NUMBER:
                ScenePath.setNextScene(scene, ScenePath.NUMBER_OF_PLAYERS);

            case LOBBY:
                ScenePath.setNextScene(scene, ScenePath.WAITING);

                //TODO -- waiting di lobby

            case WELCOME_WAIT:
                ScenePath.setNextScene(scene, ScenePath.WAITING); //TODO -- waiting di lobby

            case SUCCESSFULLY_ADDED:
                ScenePath.setNextScene(scene, ScenePath.WAITING); //TODO -- waiting di lobby

            case CARDS_CHOICE_WAIT:
                ScenePath.setNextScene(scene, ScenePath.WAITING); //TODO -- waiting di lobby

            case ALREADY_STARTED:
                //TODO -- chi eccede e non può registrarsi
        }

    }

    private void craftRequest(SetupRequest request){
        Scene scene = currentController.getPane().getScene();
        if (request instanceof GodLikeInfo){

        }

        else if(request instanceof RequestCardsChoice) {
            ScenePath.setNextScene(scene, ScenePath.CHOOSE_GODS);
        }

        else if (request instanceof RequestFirstPlayer) {

        }

        else if (request instanceof FirstPlayerInfo){

        }

        else if (request instanceof RequestPersonalGod) {
            ScenePath.setNextScene(scene, ScenePath.PERSONAL_GOD);
        }

        else if (request instanceof RequestPlaceWorker) {

        }

        else if (request instanceof  ReceivedWorkerChoice){

        }

        else if(request instanceof InitializeBoard){

        }

        //TODO -- decidere se va bene
    }

    public void sendAnswer(Answer answer){


    }

    @Override
    public Answer handleRequest(Request message) {
        try {
            Method method = getClass().getMethod("craftRequest", message.getClass());
            method.invoke(this, message);
        } catch (Exception ignored){
        }
        return null; //TODO -- void
    }
}
