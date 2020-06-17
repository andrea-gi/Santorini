package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.Font;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.Spacer;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class SuccessfullyAdded extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final Message correct;

    public SuccessfullyAdded(){
        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("successfully added");
        correct = new Message("You have been successfully added to the game.", -1);
        va1.addObjects(title, correct);
    }

    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        return null;
    }
}
