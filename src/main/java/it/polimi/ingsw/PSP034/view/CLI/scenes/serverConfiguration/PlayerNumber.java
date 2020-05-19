package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class PlayerNumber extends Scene {
    @Override
    public String show() {
        super.clearFrame();
        Font title = new Font("new game");
        Message playerNumber = new Message("You are the first connected player. Insert the number of players that will take part in this game (2 or 3).", title.getWidth());
        TextBox choice = new TextBox(5);
        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(playerNumber, choice);
        va1.setLeftAlignment();
        VerticalArrangement va2 = new VerticalArrangement();
        va2.addObjects(title, va1);
        va2.setCentreAlignment();
        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va2.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va2.getHeight()-spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va2, spacerDOWN);
        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return choice.waitAnswer(new RegexCondition("^[2-3]$", "Invalid selection. Insert 2 or 3."));
    }
}
