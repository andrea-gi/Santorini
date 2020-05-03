package it.polimi.ingsw.PSP034.view.scenes;

import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.Message;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.TextBox;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;

public class NameChoice extends Scene{
    private String[] alreadyChosenNames;

    public NameChoice(String[] alreadyChosenNames){
        this.alreadyChosenNames = alreadyChosenNames;
    }

    @Override
    public String show(){
        super.clearFrame();
        VerticalArrangement va1 = new VerticalArrangement();
        Font title = new Font("name choice");
        Message selectName = new Message("Insert the name that will be shown to the other players.", title.getWidth());
        TextBox namePicker = new TextBox();
        va1.addObjects(title, selectName, namePicker);
        VerticalArrangement va2 = new VerticalArrangement();
        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()- va1.getHeight())/2);
        Spacer spacerDown = new Spacer(super.getFrameWidth(), super.getFrameHeight()- va1.getHeight()-spacerUP.getHeight());
        va2.addObjects(spacerUP, va1, spacerDown);
        va2.print(super.getFrameStartLine(), super.getFrameStartColumn());

        String regex = "^";
        for(String name : alreadyChosenNames){
            regex += "(?!" + name + ")";
        }
        regex += "[a-zA-Z0-9_]+$";
        return namePicker.waitAnswer(regex);
    }
}
