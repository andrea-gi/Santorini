package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.ServerInfo;
import it.polimi.ingsw.PSP034.messages.setupPhase.*;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;

/**
 * Handles the first phase of the game, initialising the number of Players, their names, their colors and their Gods.
 */
public class SetupHandler {
    private final Controller controller;
    private SetupPhase currentSetupPhase;
    private SetupPhase previousSetupPhase;
    private boolean firstWorker = false;
    private int playerNumber = 0;

    /**
     * Creates the class, already associated with the controller.
     * @param controller Controller that handles this game.
     */
    public SetupHandler(Controller controller){
        this.controller = controller;
        currentSetupPhase = SetupPhase.CARDS_CHOICE;
        previousSetupPhase = currentSetupPhase;
    }

    /**
     * Executes a setup action based on the message received, setting the next phase.
     * @param message Message received.
     */
    public void executeSelectedState(SetupAnswer message){
        boolean validMessage = false;
        switch(currentSetupPhase){
            case CARDS_CHOICE:
                if (message instanceof AnswerCardsChoice){
                    String[] choice = ((AnswerCardsChoice) message).getChoice();
                    for (String s : choice) {
                        controller.addRemainingGod(s);
                    }
                    if (choice.length == controller.getRemainingGods().size()) {
                        controller.sendToPlayer(controller.getCurrentPlayer().getName(), new RequestServerConfig(ServerInfo.CARDS_CHOICE_WAIT));
                        controller.setNextPlayer();
                        validMessage = true;
                    }
                }
                break;

            case PERSONAL_GOD_CHOICE:
                if (message instanceof AnswerPersonalGod){
                    int before = controller.getRemainingGods().size();
                    controller.addGod(((AnswerPersonalGod) message).getMyGod());
                    int after = controller.getRemainingGods().size();
                    if (controller.getRemainingGods().size() > 0 && before != after) {
                        controller.sendToPlayer(controller.getCurrentPlayer().getName(), new RequestServerConfig(ServerInfo.CARDS_CHOICE_WAIT));
                        controller.setNextPlayer();
                        validMessage = true;
                    }
                }
                break;

            case CHOOSE_FIRST_PLAYER:
                if (message instanceof AnswerFirstPlayer){
                    if(controller.getPlayersName().contains(((AnswerFirstPlayer) message).getFirstPlayer())) {
                        controller.firstPlayerSetUp(((AnswerFirstPlayer) message).getFirstPlayer());
                        controller.sendToAll(new InitializeBoard(controller.getSlimBoard()), false);
                        validMessage = true;
                    }
                }
                break;

            case PLACE_WORKERS:
                if (message instanceof AnswerPlaceWorker){
                    int x = ((AnswerPlaceWorker) message).getX();
                    int y = ((AnswerPlaceWorker) message).getY();
                    Sex sex = ((AnswerPlaceWorker) message).getSex();
                    if (Tile.validCoordinates(x,y)) {
                        controller.addWorker(sex, x, y);
                        if (!firstWorker) {
                            controller.sendToPlayer(controller.getCurrentPlayer().getName(), new ReceivedWorkerChoice());
                            controller.setNextPlayer();
                            playerNumber++;
                            if (playerNumber < controller.getPlayerNumber())
                                currentSetupPhase = SetupPhase.CHOOSE_FIRST_PLAYER;
                        }
                        validMessage = true;
                    }
                }
                break;
        }

        if (!validMessage){
            if (currentSetupPhase == previousSetupPhase && currentSetupPhase == SetupPhase.CARDS_CHOICE){
                controller.sendToPlayer(controller.getCurrentPlayer().getName(), new RequestCardsChoice(controller.getPlayerNumber()));
                return;
            }
            currentSetupPhase = previousSetupPhase;
        }
        manageNextState();
    }

    /**
     * Sets the next phase, in order, and sends the right message.
     */
    private void manageNextState(){
        Player player = controller.getCurrentPlayer();
        previousSetupPhase = currentSetupPhase;
        switch(currentSetupPhase){
            case CARDS_CHOICE:
                currentSetupPhase = SetupPhase.PERSONAL_GOD_CHOICE;
                controller.sendToPlayer(player.getName(), new RequestPersonalGod(controller.getRemainingGods(), controller.getAlreadyChosenGods())); //new sendto per setup??
                break;

            case PERSONAL_GOD_CHOICE:
                if (controller.getRemainingGods().size() > 0){
                    controller.sendToPlayer(player.getName(), new RequestPersonalGod(controller.getRemainingGods(), controller.getAlreadyChosenGods()));
                }
                else {
                    currentSetupPhase = SetupPhase.CHOOSE_FIRST_PLAYER;
                    controller.sendToPlayer(player.getName(), new RequestFirstPlayer(controller.getPlayersName()));
                }
                break;

            case CHOOSE_FIRST_PLAYER:
                currentSetupPhase = SetupPhase.PLACE_WORKERS;
                controller.sendToPlayer(player.getName(), new RequestPlaceWorker(Sex.MALE, controller.getSlimBoard()));
                controller.sendToAllExcept(player.getName(), new InfoIsPlacing(player.getName()), false);
                firstWorker = true;
                break;

            case PLACE_WORKERS:
                if (firstWorker){
                    controller.sendToPlayer(player.getName(), new RequestPlaceWorker(Sex.FEMALE, controller.getSlimBoard()));
                    firstWorker = false;
                }
                else {
                    controller.setNextGamePhase();
                    controller.handleGamePhase();
                }
                break;
        }

    }
}
