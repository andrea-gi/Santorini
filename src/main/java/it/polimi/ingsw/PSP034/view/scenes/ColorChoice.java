package it.polimi.ingsw.PSP034.view.scenes;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;

public class ColorChoice extends Scene{
    private Color[] availableColors;

    public ColorChoice(Color[] availableColors){
        this.availableColors = availableColors;
    }

    @Override
    public String show(){
        super.clearFrame();
        VerticalArrangement va1 = new VerticalArrangement();
        Font title = new Font("Color choice");
        String[] options = new String[availableColors.length];
        for(int i = 0; i < availableColors.length; i++){
            options[i] = availableColors[i].getBG_color()+availableColors[i].name();
        }
        Dialog selectColor = new Dialog("Select the color you want to play with:", title.getWidth(), 1, options);
        TextBox colorPicker = new TextBox();
        va1.addObjects(title, selectColor, colorPicker);
        VerticalArrangement va2 = new VerticalArrangement();
        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()- va1.getHeight())/2);
        Spacer spacerDown = new Spacer(super.getFrameWidth(), super.getFrameHeight()- va1.getHeight()-spacerUP.getHeight());
        va2.addObjects(spacerUP, va1, spacerDown);
        va2.print(super.getFrameStartLine(), super.getFrameStartColumn());


        String regex = "^[1-" + availableColors.length + "]+$";
        return colorPicker.waitAnswer(regex);
    }
}