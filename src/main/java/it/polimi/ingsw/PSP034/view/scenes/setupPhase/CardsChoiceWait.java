package it.polimi.ingsw.PSP034.view.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class CardsChoiceWait extends Scene {
    @Override
    public String show() {
        super.clearFrame();

        Font title = new Font("wait");
        Message waitCardsChoice = new Message( "Your choice has been registered. Wait for the other players to make their choice",
                -1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, waitCardsChoice);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight() - va1.getHeight()) / 2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight() - va1.getHeight() - spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return null;
    }
}
