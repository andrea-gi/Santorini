package it.polimi.ingsw.PSP034.view.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class Lobby extends Scene {
    @Override
    public String show() {
        super.clearFrame();

        Font title = new Font("lobby");
        Message pleaseWait = new Message("A new game is being created. Wait until connection.", -1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, pleaseWait);

        super.printMain(va1);

        return null;
    }
}
