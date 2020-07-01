package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has connected to the server but a game has already started.
 */
public class AlreadyStarted extends Scene {
    private final VerticalArrangement va1;
    private final Font title;
    private final Message sorry;

    /**
     * Creates the scene and organizes the objects.
     */
    public AlreadyStarted(){
        va1 = new VerticalArrangement();

        title = new Font("game started");
        sorry = new Message("Oops, seems like the game has already started without you, sorry! :(", -1);
        va1.addObjects(title, sorry);
        va1.setCentreAlignment();
        va1.setBorder(1);
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
