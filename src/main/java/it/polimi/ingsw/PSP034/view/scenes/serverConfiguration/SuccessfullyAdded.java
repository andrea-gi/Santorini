package it.polimi.ingsw.PSP034.view.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class SuccessfullyAdded extends Scene {
    @Override
    public String show() {
        Font title = new Font("successfully added");
        Message correct = new Message("You have been successfully added to the game.", -1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, correct);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va1.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va1.getHeight()-spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return null;
    }
}
