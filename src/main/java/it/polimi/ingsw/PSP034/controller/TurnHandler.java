package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.playPhase.*;
import it.polimi.ingsw.PSP034.model.IStateManager;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

/**Handles the turn phases for each Player */
public class TurnHandler {
    private IStateManager currentGod;
    private final Controller controller;
    private TurnPhase myTurnPhase; //DA SALVARE NEL MODEL ??
    private TurnPhase previousTurnPhase;

    public TurnHandler(Controller controller){
        this.controller = controller;
        this.myTurnPhase = TurnPhase.START;
        this.previousTurnPhase = TurnPhase.START;
    }

    /**Sets the current God associated to the Player who is playing right now
     * @param currentGod is the God associated to the Player*/
    public void setCurrentGod(IStateManager currentGod) {
        this.currentGod = currentGod;
        myTurnPhase = TurnPhase.START;
        previousTurnPhase = TurnPhase.START;
    }

    /**Sets the right turn phase
     * @param turnPhase is the turn phase that needs to be executed at the moment*/
    public void setMyTurnPhase(TurnPhase turnPhase){
        this.myTurnPhase = turnPhase;
    }

    public void setPreviousTurnPhase(TurnPhase previousTurnPhase) {
        this.previousTurnPhase = previousTurnPhase;
    }

    public TurnPhase getMyTurnPhase(){
        return myTurnPhase;
    }

    /**Makes the state of the turn change in order, depending on the God associated to the Player */
    private void manageNextState(){
        //myTurnPhase = currentGod.nextState();
        Player player = controller.getCurrentPlayer();
        NextStateInfo nextStateInfo = currentGod.nextState(myTurnPhase);
        setPreviousTurnPhase(myTurnPhase); // Saving previous phase
        setMyTurnPhase(nextStateInfo.getNextPhase()); // Saving new phase
        switch (nextStateInfo.getNextPhase()){
            case BUILD:
            case MOVE:
                controller.sendToPlayer(player, new RequestAction(nextStateInfo, player));
                break;
            case POWER:
                controller.sendToPlayer(player, new RequestBooleanChoice(nextStateInfo));
                break;
            case END:
                setMyTurnPhase(TurnPhase.START);
                setPreviousTurnPhase(TurnPhase.START);
                controller.setNextPlayer();
                setCurrentGod(controller.getCurrentGod());
                controller.sendToPlayer(controller.getCurrentPlayer(), new RequestStart(new NextStateInfo(TurnPhase.START)));
                break;
                //TODO--WIN & GAMEOVER
            case WIN:
                player.setHasWon(true);
                // TODO -- GESTIONE AL CONTROLLER
                break;
            case GAMEOVER:
                player.setHasLost(true);
                boolean isGameOver = controller.isGameOver();
                if ( !isGameOver ){
                    setMyTurnPhase(TurnPhase.START);
                    setPreviousTurnPhase(TurnPhase.START);
                    //Next player already set by controller
                    setCurrentGod(controller.getCurrentGod());
                    controller.sendToPlayer(controller.getCurrentPlayer(), new RequestStart(new NextStateInfo(TurnPhase.START)));
                }
                else{
                    controller.setNextGamePhase();
                    controller.handleGamePhase();
                }
                break;

        // TODO -- Invia a tutti il turno corrente (chi sta giocando ecc)
        //sendToPlayer(player, Messaggio)
        }
    }




    /**Executes the actions in the actual turn phase*/
    public void executeSelectedState(PlayAnswer message){
        boolean validMessage = true;
        switch(myTurnPhase){
            case START:
                if(message instanceof AnswerStart) {
                    validMessage = currentGod.executeState(TurnPhase.START, null, null, false);
                }
                else
                    validMessage = false;
                break;
            case MOVE:
            case BUILD:
                if(message instanceof AnswerAction) {
                    AnswerAction action = (AnswerAction) message;
                    Worker myWorker = controller.getCurrentPlayer().getWorker(action.getWorkerSex());
                    Tile myTile = myWorker.getMyTile().neighbouringTileByDirection(action.getDirection());
                    validMessage = currentGod.executeState(myTurnPhase, myWorker, myTile, false); // if error, not valid
                }
                else
                    validMessage = false;
                break;
            case POWER:
                if(message instanceof AnswerBooleanChoice){
                    validMessage = currentGod.executeState(TurnPhase.POWER, null, null, ((AnswerBooleanChoice) message).getChoice());
                }
                else
                    validMessage = false;
                break;
        }
        if( !validMessage ) {
            setMyTurnPhase(previousTurnPhase);
        }

        manageNextState();
    }
}