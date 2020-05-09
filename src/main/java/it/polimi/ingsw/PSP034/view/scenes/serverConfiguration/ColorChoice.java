package it.polimi.ingsw.PSP034.view.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class ColorChoice extends Scene {
    private final Color[] availableColors;

    public ColorChoice(Color[] availableColors){
        this.availableColors = availableColors;
    }

    @Override
    public String show(){
        super.clearFrame();

        Font title = new Font("Color choice");
        String[] options = new String[availableColors.length];
        for(int i = 0; i < availableColors.length; i++){
            options[i] = availableColors[i].getBG_color()+availableColors[i].name()+ANSI.reset;
        }
        Dialog selectColor = new Dialog("Select the color you want to play with:", title.getWidth(), 1, options);
        TextBox colorPicker = new TextBox(title.getWidth());

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, selectColor, colorPicker);

        super.printMain(va1);

        return colorPicker.waitAnswer(new RegexCondition("^[1-" + availableColors.length + "]$", "Invalid input."));
    }
}
