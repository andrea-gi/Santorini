package it.polimi.ingsw.PSP034.view.CLI.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class TitleScene extends Scene {

    @Override
    public String show(){
        ANSI.clearScreen();

        Frame frame = new Frame();

        Title title = new Title();
        Message pressEnter = new Message("Press enter to continue", -1);
        TextBox textBox = new TextBox(1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(pressEnter, textBox);
        va1.setBorder(0);

        VerticalArrangement va2 = new VerticalArrangement();
        va2.addObjects(title, va1);
        va2.setBorder(3);

        frame.print(1,1);
        super.printMain(va2);

        textBox.waitAnswer();
        return null;
    }
}
