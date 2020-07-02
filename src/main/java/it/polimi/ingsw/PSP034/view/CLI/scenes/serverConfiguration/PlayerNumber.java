package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has to choose how many players the game will have.
 */
public class PlayerNumber extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final VerticalArrangement va2;

    private Message playerNumber;
    private final HorizontalArrangement ha1;

    private final Message players;
    private final TextBox choice;

    /**
     * Creates the scene and organizes the objects.
     */
    public PlayerNumber(){
        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("new game");
        va2 = new VerticalArrangement();
        va2.setLeftAlignment();
        va2.setBorder(1);
        va1.addObjects(title, va2);

        playerNumber = new Message("You are the first connected player. Insert the number of players that will take part in this game (2 or 3).", title.getWidth());
        ha1 = new HorizontalArrangement();
        ha1.setBottomAlignment();
        ha1.setBorder(1);
        va2.addObjects(playerNumber, ha1);

        players = new Message("Players :", -1);
        choice = new TextBox(title.getWidth()-players.getWidth()-ha1.getBorder());
        ha1.addObjects(players, choice);
    }

    /**
     * {@inheritDoc}
     * @return The number of players chosen by the user.
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        RegexCondition regexCondition = new RegexCondition("^[2-3]$", "Invalid selection. Insert 2 or 3.");

        return choice.waitAnswer(regexCondition);
    }
}
