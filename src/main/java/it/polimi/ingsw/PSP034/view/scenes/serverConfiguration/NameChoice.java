package it.polimi.ingsw.PSP034.view.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class NameChoice extends Scene {
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
        TextBox namePicker = new TextBox(title.getWidth());
        va1.addObjects(title, selectName, namePicker);
        VerticalArrangement va2 = new VerticalArrangement();
        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()- va1.getHeight())/2);
        Spacer spacerDown = new Spacer(super.getFrameWidth(), super.getFrameHeight()- va1.getHeight()-spacerUP.getHeight());
        va2.addObjects(spacerUP, va1, spacerDown);
        va2.print(super.getFrameStartLine(), super.getFrameStartColumn());

        RegexCondition[] regex = new RegexCondition[2+alreadyChosenNames.length];
        regex[0] = new RegexCondition("^[a-zA-Z0-9_]+$", "Your name can only contain letters, numbers and underscores.");
        regex[1] = new RegexCondition("^{1,20}", "Your name must contain 1-20 characters.");
        for(int i = 0; i < alreadyChosenNames.length; i++){
            regex[i+2] = new RegexCondition("^(?!" + alreadyChosenNames[i] + ")", "This name is already been taken.");
        }
        return namePicker.waitAnswer(regex);
    }
}
