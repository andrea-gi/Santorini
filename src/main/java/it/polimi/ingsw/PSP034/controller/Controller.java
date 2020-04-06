package it.polimi.ingsw.PSP034.controller;
import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.model.*;

/**It controls the unfolding of the game, checking the GamePhase, giving control of the TurnPhase to the TurnHandler
 * and decorating the Gods in the right order, choosing their right moves*/
public class Controller {
    private Game currentGame;
    private TurnHandler turnHandler;
    private SetupPhase setup;
    private GameOverPhase gameOver;
    private IRules rules;

    /**Creates the controller associated to a Game. It builds itself the SetupPhase, the TurnHandler and the GameOverPhase
     * It creates also the DefaultRules in order to have ready all the Gods cards
     * @param currentGame is the link to the Game*/
    public Controller(Game currentGame) {
        this.currentGame = currentGame;
        this.turnHandler = new TurnHandler();
        this.setup = new SetupPhase(this);
        this.gameOver = new GameOverPhase(this);
        this.rules = new DefaultRules();
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public IRules getRules(){
        return rules;
    }

    /**Sets the next game phase, in order*/
    public void nextGamePhase(){
        switch (currentGame.getGamePhase()) {
            case SETUP:
                currentGame.setGamePhase(GamePhase.PLAY);
            case PLAY:
                currentGame.setGamePhase(GamePhase.GAMEOVER);
            case GAMEOVER:
                //chiama chi gestisce la partita
        }
    }

    /**Executes the right game phase*/
    public boolean startGamePhase(){
        boolean validPhase = false;
        switch (currentGame.getGamePhase()) {
            case SETUP:
                validPhase = setup.startSetup();
            case PLAY:
                //turnHandler alla prima mossa della prima persona
                if(!turnHandler.gotWinner())
                    validPhase = turnHandler.startTurnHandler();
            case GAMEOVER:
                validPhase = gameOver.startGameOver();
        }

        if (validPhase)
            nextGamePhase();
        return validPhase;
    }

    /**Decorates the turn with the gods, already in order
     * @param name is the name of the god*/
    public void addGod(String name){
        switch (name){
            case "Apollo":
                rules = new Apollo(rules);
            case "Artemis":
                rules = new Artemis(rules);
            case "Athena":
                rules = new Athena(rules);
            case "Atlas":
                rules = new Atlas(rules);
            case "Demeter":
                rules = new Demeter(rules);
            case "Ephaestus":
                rules = new Ephaestus(rules);
            case "Minotaur":
                rules = new Minotaur(rules);
            case "Pan":
                rules = new Pan(rules);
            case "Prometheus":
                rules = new Prometheus(rules);
        }
    }


}
