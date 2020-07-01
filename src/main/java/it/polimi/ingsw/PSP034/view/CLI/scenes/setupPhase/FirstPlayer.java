package it.polimi.ingsw.PSP034.view.CLI.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has to choose who will be the first player to play.
 */
public class FirstPlayer extends Scene {
    private final String[] players;

    private final VerticalArrangement va1;

    private final Font title;
    private final Dialog selectFirst;
    private final HorizontalArrangement ha1;

    private final Message yourChoice;
    private final TextBox choice;

    /**
     * Creates the scene and organizes the objects.
     * @param players All the players to choose between.
     */
    public FirstPlayer(String[] players){
        this.players = players;

        va1 = new VerticalArrangement();
        va1.setBorder(1);
        va1.setCentreAlignment();

        title = new Font("first player");
        selectFirst = new Dialog("Select who will be the first player.", -1, 1, players);
        ha1 = new HorizontalArrangement();
        ha1.setBorder(1);
        ha1.setBottomAlignment();
        va1.addObjects(title, selectFirst, ha1);

        yourChoice = new Message("Your choice:", -1);
        choice = new TextBox(selectFirst.getWidth()-yourChoice.getWidth()-ha1.getBorder());
        ha1.addObjects(yourChoice, choice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        return choice.waitAnswer(new RegexCondition("^[1-" + players.length + "]$", "Invalid selection"));
    }
}
