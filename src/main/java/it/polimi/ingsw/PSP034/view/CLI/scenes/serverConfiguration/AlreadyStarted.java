package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class AlreadyStarted extends Scene {
    @Override
    public String show() {
        super.clearFrame();
        Font title = new Font("game started");
        Message sorry = new Message("Oops, seems like the game has already started without you, sorry! :(", -1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, sorry);

        super.printMain(va1);

        return null;
    }
}
