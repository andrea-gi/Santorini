package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.SetupPhase.*;
import it.polimi.ingsw.PSP034.model.Player;

/**Handles the first phase of the game, initialising the number of Players, their names, their colors and their Gods*/
public class SetupHandler {
    private final Controller controller;
    private SetupPhase currentSetupPhase;
    private boolean firstWorker = false;
    private int playerNumber = 0;

    /**It creates the class, already associated with the controller
     * @param controller is the controller that handles this game*/
    public SetupHandler(Controller controller){
        this.controller = controller;
        currentSetupPhase = SetupPhase.CARDS_CHOICE;
    }

    public void executeSelectedState(SetupAnswer message){
        boolean validMessage = true;
        switch(currentSetupPhase){
            case CARDS_CHOICE:
                if (message instanceof AnswerCardsChoice){
                    String[] choice = ((AnswerCardsChoice) message).getChoice();
                    for (String s : choice) {
                        controller.addRemainingGod(s);
                    }
                    controller.setNextPlayer();
                }
                break;

            case PERSONAL_GOD_CHOICE:
                if (message instanceof AnswerPersonalGod){
                    controller.addGod(((AnswerPersonalGod) message).getMyGod(), controller.getCurrentPlayer());
                    controller.removeRemainingGod(((AnswerPersonalGod) message).getMyGod());
                    if (controller.getRemainingGods().size() > 0)
                        controller.setNextPlayer();
                }
                break;

            case CHOOSE_FIRST_PLAYER:
                if (message instanceof AnswerFirstPlayer){
                    controller.firstPlayerSetUp(((AnswerFirstPlayer) message).getFirstPlayer());
                }
                break;

            case PLACE_WORKERS:
                if (message instanceof AnswerPlaceWorker){
                    int x = ((AnswerPlaceWorker) message).getX();
                    int y = ((AnswerPlaceWorker) message).getY();
                    Sex sex = ((AnswerPlaceWorker) message).getSex();
                    controller.getCurrentPlayer().addWorker(sex, controller.getTile(x, y));
                    if (!firstWorker){
                        controller.setNextPlayer();
                        playerNumber++;
                        if (playerNumber < controller.getPlayerNumber())
                            currentSetupPhase = SetupPhase.CHOOSE_FIRST_PLAYER;
                    }
                }
                break;
        }
        manageNextState();
    }

    public void manageNextState(){
        Player player = controller.getCurrentPlayer();
        switch(currentSetupPhase){
            case CARDS_CHOICE:
                currentSetupPhase = SetupPhase.PERSONAL_GOD_CHOICE;
                controller.sendToPlayer(player, new RequestPersonalGod(controller.getRemainingGods())); //new sendto per setup??
                break;

            case PERSONAL_GOD_CHOICE:
                if (controller.getRemainingGods().size() > 0){
                    controller.sendToPlayer(player, new RequestPersonalGod(controller.getRemainingGods()));
                }
                else {
                    currentSetupPhase = SetupPhase.CHOOSE_FIRST_PLAYER;
                    controller.sendToPlayer(player, new RequestFirstPlayer(controller.getPlayersName()));
                }
                break; //TODO -- invia notifica agli altri giocatori

            case CHOOSE_FIRST_PLAYER:
                currentSetupPhase = SetupPhase.PLACE_WORKERS;
                controller.sendToPlayer(player, new RequestPlaceWorker(Sex.MALE));
                firstWorker = true;
                break;

            case PLACE_WORKERS:
                if (firstWorker){
                    controller.sendToPlayer(player, new RequestPlaceWorker(Sex.FEMALE));
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
