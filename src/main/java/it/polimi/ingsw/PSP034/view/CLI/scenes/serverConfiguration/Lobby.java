package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has connected to the server and has to wait for the other players to connect.
 */
public class Lobby extends Scene {
    private final VerticalArrangement va1;
    private final Font title;
    private final Message pleaseWait;

    /**
     * Creates the scene and organizes the objects.
     */
    public Lobby(){
        va1 = new VerticalArrangement();

        title = new Font("lobby");
        pleaseWait = new Message("A new game is being created. Wait until connection.", -1);
        va1.addObjects(title, pleaseWait);
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
