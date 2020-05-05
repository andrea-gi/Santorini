package it.polimi.ingsw.PSP034.view.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class FirstPlayer extends Scene {
    private String[] players;

    public FirstPlayer(String[] players){
        this.players = players;
    }

    @Override
    public String show() {
        Font title = new Font("first player");
        Dialog selectFirst = new Dialog("Select who will be the first player.", -1, 1, players);
        TextBox choice = new TextBox(1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, selectFirst, choice);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va1.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va1.getHeight()- spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return choice.waitAnswer(new RegexCondition("^[1-" + players.length + "]$", "Invalid selection"));
    }
}
