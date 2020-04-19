package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.SetupPhase.SetupAnswer;

/**Handles the first phase of the game, initialising the number of Players, their names, their colors and their Gods*/
public class SetupHandler {
    private Controller controller;
    private SetupPhase currentSetupPhase;

    /**It creates the class, already associated with the controller
     * @param controller is the controller that handles this game*/
    public SetupHandler(Controller controller){
        this.controller = controller;
        currentSetupPhase = SetupPhase.CARDS_CHOICE;
    }


    public void executeSelectedState(SetupAnswer message){
        switch(currentSetupPhase){
            case CARDS_CHOICE:

            case PERSONAL_GOD_CHOICE:

            case PLACE_WORKERS:
        }
        manageNextState();
    }

    public void manageNextState(){}
}
