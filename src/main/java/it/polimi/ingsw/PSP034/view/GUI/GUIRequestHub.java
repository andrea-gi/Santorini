package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.client.Client;
import it.polimi.ingsw.PSP034.client.RequestManager;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoCloseAnswer;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.*;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestNameColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import it.polimi.ingsw.PSP034.view.CLI.AnswerComposer;
import it.polimi.ingsw.PSP034.view.CLI.scenes.playPhase.Table;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Is a singleton that handles all the messages and information between the server and the GUI controllers.
 */
public class GUIRequestHub extends RequestManager {
    private GUIController currentController;
    private static GUIRequestHub instance;
    private static boolean canHandleRequest;

    private GUIRequestHub(){
    }

    public static GUIRequestHub getInstance(){
        if (instance == null){
            instance = new GUIRequestHub();
            canHandleRequest = true;
        }
        return instance;
    }

    public void setCurrentController(GUIController currentController) {
        this.currentController = currentController;
    }

    public GUIController getCurrentController() {
        return currentController;
    }

    /** Handles a RequestIP message, showing the corresponding scene to the user, in order to establish
     * the connection
     * @param request is the message received
     */
    private void craftRequestIP(RequestIP request){
        Platform.runLater(()->{
            ScenePath.setNextScene(currentController.getPane().getScene(), ScenePath.SERVER_LOGIN);
        });
    }//TODO--gestione errore

    /** Handles a RequestServerConfig message, showing the corresponding scene to the user, in order to
     * register the player
     * @param request is the message received
     */
    private void craftRequestServer(RequestServerConfig request){
        Scene scene = currentController.getPane().getScene();
        switch (request.getInfo()){

            case REQUEST_NAME_COLOR:
                RequestNameColor requestNameColor = (RequestNameColor) request;
                Platform.runLater(()->{
                    ScenePath.setNextScene(scene, ScenePath.NAME_COLOR);
                    ((NameColorController) currentController).update(requestNameColor.getAlreadyChosenNames(), requestNameColor.getAvailableColors());
                });
                break;

            case REQUEST_PLAYER_NUMBER:
                Platform.runLater(()->{ScenePath.setNextScene(scene, ScenePath.NUMBER_OF_PLAYERS);});
                break;

            case LOBBY:
                Platform.runLater(()->{
                    ScenePath.setNextScene(scene, ScenePath.WAITING);
                    ((WaitingController) currentController).setMyTitle("You are in the Lobby now");
                    ((WaitingController) currentController).setMyMessage("Wait for the other players to connect...");
                });
                break;

            case WELCOME_WAIT:
                Platform.runLater(()->{
                    ScenePath.setNextScene(scene, ScenePath.WAITING);
                    ((WaitingController) currentController).setMyTitle("Welcome in a new game");
                    ((WaitingController) currentController).setMyMessage("Wait for the other player to finish registration...");
                    });
                break;

            case SUCCESSFULLY_ADDED:
                Platform.runLater(()->{
                    ScenePath.setNextScene(scene, ScenePath.WAITING);
                    ((WaitingController) currentController).setMyTitle("Successfully Added!");
                    ((WaitingController) currentController).setMyMessage("You have been successfully added to the game. Wait!");
                });
                break;

            case CARDS_CHOICE_WAIT:
                Platform.runLater(()->{
                    ScenePath.setNextScene(scene, ScenePath.WAITING);
                    ((WaitingController) currentController).setMyTitle("Your choice has been registered.");
                    ((WaitingController) currentController).setMyMessage("Wait for the other players to make their choice");
                });
                break;

            case ALREADY_STARTED:
                setCanHandleRequest(false);
                Platform.runLater(()->{ScenePath.setDialog((Stage)scene.getWindow(),"Game Started",
                        "Oops, seems like the game has already started without you, sorry! :(");});
                break;
        }
    }

    /** Handles a SetupRequest message, showing the corresponding scene to the user, order to choose the gods
     * and position the workers on the Board.
     * @param request is the message received
     */
    private void craftRequestSetup(SetupRequest request){
        Scene scene = currentController.getPane().getScene();
        if (request instanceof GodLikeInfo){
            setCanHandleRequest(false);
            String godLikePlayer = ((GodLikeInfo) request).getGodLikePlayer();
            Platform.runLater(()->
            {
                ScenePath.setNextScene(scene, ScenePath.WAITING);
                ((WaitingController) currentController).setMyTitle("Wait!");
                ((WaitingController) currentController).setMyMessage("Wait for the other players to make their choice");
                GUIController previousController = currentController;
                ScenePath.setDialog((Stage)scene.getWindow(),"God like Player",
                    godLikePlayer + " has been chosen as the most god-like player. Such a lucky player!");
                currentController = previousController;
            });
        }

        else if(request instanceof RequestCardsChoice) {
            RequestCardsChoice requestCardsChoice = (RequestCardsChoice) request;
            Platform.runLater(()->{
                ScenePath.setNextScene(scene, ScenePath.CHOOSE_GODS);
                ((ChooseGodsController) currentController).update(requestCardsChoice.getPlayerNumber());
            });
        }

        else if (request instanceof RequestPersonalGod) {
            RequestPersonalGod requestPersonalGod = (RequestPersonalGod) request;
            Platform.runLater(()->{
                ScenePath.setNextScene(scene, ScenePath.PERSONAL_GOD);
                ((PersonalGodController) currentController).update(requestPersonalGod.getPossibleGods(), requestPersonalGod.getAlreadyChosenGods());
            });
        }

        else if (request instanceof RequestFirstPlayer) {
            RequestFirstPlayer requestFP = (RequestFirstPlayer) request;
            Platform.runLater(()->{
                ScenePath.setNextScene(scene, ScenePath.FIRST_PLAYER);
                ((FirstPlayerController) currentController).update(requestFP.getPlayers());
            });
        }

        else if (request instanceof RequestPlaceWorker) {
            Platform.runLater(()->{
                String workerSex = ((RequestPlaceWorker) request).getSex() == Sex.MALE ? "male" : "female";
                ((TableController) currentController).setMyTitle("Your turn");
                ((TableController) currentController).setMyDescription("Place your " + workerSex +  " worker");
                ((TableController) currentController).updatePlaceWorker(((RequestPlaceWorker) request).getSex(), ((RequestPlaceWorker) request).getSlimBoard().getSex());
            });
        }

        else if (request instanceof  ReceivedWorkerChoice){
        }

        else if(request instanceof InitializeBoard){
            Platform.runLater(()->{
                SlimBoard slim = ((InitializeBoard) request).getSlimBoard();
                ScenePath.setNextScene(scene, ScenePath.TABLE);
                ((TableController) currentController).updateCards(slim.getGodsList(), slim.getPlayersList(), slim.getColorsList());
                ((TableController) currentController).updateAndSaveBoard(slim);
            });
        }

        else if (request instanceof InfoIsPlacing){
            Platform.runLater(()->{
                ((TableController) currentController).setMyTitle(((InfoIsPlacing) request).getPlayer() + "'s turn");
                ((TableController) currentController).setMyDescription(((InfoIsPlacing) request).getPlayer() + " is placing workers");
            });
        }
        //TODO -- decidere se va bene
    }

    /** Handles a PlayRequest message, showing the corresponding scene to the user, depending on the
     * actual phase of the game
     * @param request is the message received
     */
    private void craftPlayRequest(PlayRequest request){
        Scene scene = currentController.getPane().getScene();
        if (request instanceof InfoIsStarting){
            Platform.runLater(()->{
                ((TableController) currentController).setMyTitle(((InfoIsStarting) request).getPlayer() + "'s turn");
                ((TableController) currentController).setMyDescription("");
                ((TableController) currentController).setVisibleBox(false);
            });
        }

        else if (request instanceof RequestStart){
            Platform.runLater(()->{
                ((TableController) currentController).setMyTitle("Your turn");
                ((TableController) currentController).setMyDescription("");
                ((TableController) currentController).setVisibleBox(true);
                GUIRequestHub.getInstance().sendAnswer(new AnswerStart());
            });
        }

        else if (request instanceof RequestAction){
            Platform.runLater(()->{
                switch (request.getNextPhase()) {
                    case MOVE:
                        ((TableController) currentController).setMyDescription("Choose a worker to move");
                        ((TableController) currentController).updatePlayAction((RequestAction) request);
                        break;
                    case BUILD:
                        ((TableController) currentController).setMyDescription("Choose a worker to build with");
                        ((TableController) currentController).updatePlayAction((RequestAction) request);
                        break;
                }
            });
        }

        else if (request instanceof RequestBooleanChoice){
            RequiredActions[] actions = request.getRequiredActions();
            Platform.runLater(()->{
                for(RequiredActions action : actions){
                    if (action == RequiredActions.REQUEST_POWER) {
                        ((TableController) currentController).setMyDescription("Do you want to use your power?");
                        ((TableController) currentController).updatePower();
                    }
                }
            });
        }

    }

    /** Handles a GameOverRequest message, showing the corresponding scene to the user, in order to close
     * the game with the right messages to the player
     * @param request is the message received
     */
    public void craftGameOverRequest(GameOverRequest request){
        Scene scene = currentController.getPane().getScene();
        if(request instanceof PersonalDefeatRequest){
            Platform.runLater(()->{
                ((TableController) currentController).setMyTitle("");
                ((TableController) currentController).setMyDescription("");
                ((TableController) currentController).setVisibleBox(false);
                ((TableController) currentController).updateLose(((PersonalDefeatRequest) request).getWinner(), ((PersonalDefeatRequest) request).getLosers());
            });
        }

        else if(request instanceof SingleLoserInfo) {
            setCanHandleRequest(false);
            GUIController previousController = currentController;
            Platform.runLater(()->{
                ((TableController) currentController).setCardOpacity(((SingleLoserInfo) request).getLoser(), 0.4);
                ScenePath.setDialog((Stage)scene.getWindow(),((SingleLoserInfo) request).getLoser()+ " lost!",
                        "You are one step closer to victory!");
                currentController = previousController;
                ((TableController) currentController).setMyTitle("");
                ((TableController) currentController).setMyDescription("");
                ((TableController) currentController).setVisibleBox(false);
            });
        }

        else if(request instanceof WinnerRequest){
            Platform.runLater(()->{
                ((TableController) currentController).setMyTitle("");
                ((TableController) currentController).setMyDescription("");
                ((TableController) currentController).setVisibleBox(false);
                ((TableController) currentController).updateWin(((WinnerRequest) request).getWinner(), ((WinnerRequest) request).getLoser());
            });
        }

        else if(request instanceof EndByDisconnection){
            setCanHandleRequest(false);
            Platform.runLater(()->{
                ScenePath.setDialog((Stage)scene.getWindow(),"Error",
                    "Oops, seems like a player has disconnected. The game has ended");
            });
        }
    }

    //TODO -- unificare con cli?

    /**Creates the connection between the client and the server
     * @param answer
     */
    void createConnection(AnswerIP answer){
        new Thread(() -> {
            Client client = new Client(this, answer.getIp(), answer.getPort());
            client.startConnection(); // TODO -- assicurarsi che la connessione sia stata creata
            Thread runClient = new Thread(client);
            runClient.start();
        }
        ).start();
    }

    /** Handles the message received depending on its type
     * @param message Request to be handled
     */
    @Override
    public void handleRequest(Request message) {
        if (message instanceof RequestIP)
            craftRequestIP((RequestIP) message);
        else if (message instanceof RequestServerConfig)
            craftRequestServer((RequestServerConfig) message);
        else if (message instanceof SetupRequest)
            craftRequestSetup((SetupRequest) message);
        else if (message instanceof PlayRequest)
            craftPlayRequest((PlayRequest) message);
        else if (message instanceof SlimBoard){
            Platform.runLater(()->{
                ((TableController) currentController).updateAndSaveBoard((SlimBoard) message);
            });
        }
        else if (message instanceof GameOverRequest)
            craftGameOverRequest((GameOverRequest) message);
    }

    @Override
    public void showError(ErrorMessage error) {
        //TODO
    }

    /**
     * Returns message handling availability.
     *
     * @return {@code true} if {@link this#handleRequest(Request)} can manage a new request.
     */
    @Override
    public synchronized boolean canHandleRequest() {
        return canHandleRequest;
    }

    public synchronized void setCanHandleRequest(boolean bool){
        canHandleRequest = bool;
    }
}
