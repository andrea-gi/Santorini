package it.polimi.ingsw.PSP034.view.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class WelcomeWait extends Scene {
    @Override
    public String show() {
        Font title = new Font("welcome");
        Message welcomeWait = new Message("Welcome to this new game. Another player is selecting the name and the color he will use during the game. Please wait for him to finish.", title.getWidth());

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, welcomeWait);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va1.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va1.getHeight()- spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return null;
    }
}
