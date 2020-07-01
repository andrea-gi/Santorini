package it.polimi.ingsw.PSP034.view.CLI.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.Spacer;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user is waiting for another player to complete an action.
 */
public class CardsChoiceWait extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final Message choiceWait;

    /**
     * Creates the scene and organizes the objects.
     */
    public CardsChoiceWait(){
        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("wait");
        choiceWait = new Message("Your choice has been registered. Wait for the other players to make their choice", 2*title.getWidth());
        va1.addObjects(title, choiceWait);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        return null;
    }
}
