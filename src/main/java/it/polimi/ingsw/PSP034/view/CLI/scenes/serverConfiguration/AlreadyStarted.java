package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class AlreadyStarted extends Scene {
    private final VerticalArrangement va1;
    private final Font title;
    private final Message sorry;

    public AlreadyStarted(){
        va1 = new VerticalArrangement();

        title = new Font("game started");
        sorry = new Message("Oops, seems like the game has already started without you, sorry! :(", -1);
        va1.addObjects(title, sorry);
        va1.setCentreAlignment();
        va1.setBorder(1);
    }

    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        return null;
    }
}
