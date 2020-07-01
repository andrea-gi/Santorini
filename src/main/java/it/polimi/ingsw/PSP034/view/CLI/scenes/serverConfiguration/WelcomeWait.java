package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.Spacer;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has been added to the game after having been in the lobby.
 */
public class WelcomeWait extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final Message welcomeWait;

    /**
     * Creates the scene and organizes the objects.
     */
    public WelcomeWait(){
        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("welcome");
        welcomeWait = new Message("Welcome to this new game. Another player is selecting the name and the color he will use during the game. Please wait for him to finish.", title.getWidth());
        va1.addObjects(title, welcomeWait);
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
