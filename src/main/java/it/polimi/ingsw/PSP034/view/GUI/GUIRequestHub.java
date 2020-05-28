package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.client.Client;
import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIRequestHub extends RequestManager {
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

    private void craftRequestIP(RequestIP request){
        Platform.runLater(()->{ScenePath.setNextScene(currentController.getPane().getScene(), ScenePath.SERVER_LOGIN);});
    }

    private void craftRequestServer(RequestServerConfig request){
        Scene scene = currentController.getPane().getScene();
        switch (request.getInfo()){
            case REQUEST_NAME_COLOR:
                RequestNameColor requestNameColor = (RequestNameColor) request;
                Platform.runLater(()->{
                    ScenePath.setNextScene(scene, ScenePath.LOGIN);
                    ((LoginController) currentController).update(requestNameColor.getAlreadyChosenNames(), requestNameColor.getAvailableColors());
                });
                break;
            case REQUEST_PLAYER_NUMBER:
                Platform.runLater(()->{ScenePath.setNextScene(scene, ScenePath.NUMBER_OF_PLAYERS);});
                break;
            case LOBBY:
                Platform.runLater(()->{ScenePath.setNextScene(scene, ScenePath.WAITING);});
                break;
                //TODO -- waiting di lobby

            case WELCOME_WAIT:
                Platform.runLater(()->{ScenePath.setNextScene(scene, ScenePath.WAITING);});
                //TODO -- waiting di lobby
                break;
            case SUCCESSFULLY_ADDED:
                Platform.runLater(()->{ScenePath.setNextScene(scene, ScenePath.WAITING);}); //TODO -- waiting di lobby
                break;
            case CARDS_CHOICE_WAIT:
                Platform.runLater(()->{ScenePath.setNextScene(scene, ScenePath.WAITING);}); //TODO -- waiting di lobby
                break;
            case ALREADY_STARTED:
                Platform.runLater(()->{ScenePath.setDialog((Stage)scene.getWindow(),"Game Started",
                        "Oops, seems like the game has already started without you, sorry! :(");});
                //TODO -- chi eccede e non può registrarsi


        }

    }

    private void craftRequestSetup(SetupRequest request){
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

    //TODO -- unificare con cli?
    void createConnection(AnswerIP answer){
        new Thread(() -> {
            Client client = new Client(this, answer.getIp(), answer.getPort());
            client.startConnection(); // TODO -- assicurarsi che la connessione sia stata creata
            Thread runClient = new Thread(client);
            runClient.start();
        }
        ).start();
    }

    @Override
    public void handleRequest(Request message) {
        if (message instanceof RequestIP)
            craftRequestIP((RequestIP) message);
        else if (message instanceof RequestServerConfig)
            craftRequestServer((RequestServerConfig) message);
        else if (message instanceof SetupRequest)
            craftRequestSetup((SetupRequest) message);
    }

    @Override
    public void showError(ErrorMessage error) {
        //TODO
    }
}
