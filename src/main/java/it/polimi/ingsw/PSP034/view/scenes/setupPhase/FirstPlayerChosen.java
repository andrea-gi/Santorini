package it.polimi.ingsw.PSP034.view.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class FirstPlayerChosen extends Scene {
    private final String firstPlayerChosen;

    public FirstPlayerChosen(String player){
        this.firstPlayerChosen = player;
    }

    @Override
    public String show() {
        super.clearFrame();

        Font title = new Font("first player");
        Message firstPlayerMessage = new Message(firstPlayerChosen + " has been chosen as first player! Is this a good strategy? We'll see...",
                title.getWidth());

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, firstPlayerMessage);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight() - va1.getHeight()) / 2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight() - va1.getHeight() - spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return null;
    }
}
