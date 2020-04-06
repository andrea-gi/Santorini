package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;

/**Handles the first phase of the game, initialising the number of Players, their names, their colors and their Gods*/
public class SetupPhase {
    private Controller controller;

    /**It creates the class, already associated with the controller
     * @param controller is the controller that handles this game*/
    public SetupPhase(Controller controller){
        this.controller = controller;
    }

    /**It handles the communication with the Client, asking to the players their number, name and color*/
   /*
    public boolean startSetup(){
        //how many players
        int number = ;
        //your name
        int i;
        for (i = 0; i < number; i++) {
            //prendo il player che mi danno e lo aggiungo
            //controllo che il suo nome sia diverso dagli altri
            controller.getCurrentGame().addPlayer(player);
            color = getmycolor;
            tile = getmytile;
            controller.getCurrentGame().getCurrentPlayer().setMyWorkers(Sex.MALE, Color.color, tile);
            tile = getmytile;
            controller.getCurrentGame().getCurrentPlayer().setMyWorkers(Sex.FEMALE, Color.color, tile);
            god = getmygod;
            controller.getCurrentGame().getCurrentPlayer().setMyGod(god);
        }

        //il primo player sceglie le carte
        player = primo player;
        if(controller.getCurrentGame().getPlayers().indexOf(player) == 0){
            for (i = 0; i < controller.getCurrentGame().getPlayerNumber(); i++){
                chooseGods();
                controller.getCurrentGame().addGod();
                controller.addGod();
            }

        }
        //gli altri a turno le pescano tra quelle scelte
        //ok! Ora sei pronto

        //ritorna true se tutto ok
        return true;
    }

    */
}
