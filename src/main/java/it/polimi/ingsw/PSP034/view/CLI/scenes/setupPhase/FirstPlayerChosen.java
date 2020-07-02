package it.polimi.ingsw.PSP034.view.CLI.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user is waiting for another player to choose the gods to play with.
 */
public class FirstPlayerChosen extends Scene {
    private final String firstPlayerChosen;

    private final VerticalArrangement va1;

    private final Font title;
    private final Message playerChosen;

    /**
     * Creates the scene and organizes the objects.
     */
    public FirstPlayerChosen(String player){
        this.firstPlayerChosen = player;

        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("first player");
        playerChosen = new Message(firstPlayerChosen + " has been chosen as first player! Is this a good strategy? We'll see...", title.getWidth());
        va1.addObjects(title, playerChosen);
    }

    /**
     * {@inheritDoc}
     * @return {@code null}
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        return null;
    }
}
