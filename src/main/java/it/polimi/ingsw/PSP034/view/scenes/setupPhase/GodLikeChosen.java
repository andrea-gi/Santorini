package it.polimi.ingsw.PSP034.view.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class GodLikeChosen extends Scene {
    private final String godLikePlayer;

    public GodLikeChosen(String player){
        this.godLikePlayer = player;
    }

    @Override
    public String show() {
        Font title = new Font("god like player");
        Message godLikeMessage = new Message(godLikePlayer+" has been chosen as the most god-like player! Such a lucky player, but don't worry, you will have better luck next time!",
                title.getWidth());

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, godLikeMessage);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va1.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va1.getHeight()- spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return null;
    }
}
