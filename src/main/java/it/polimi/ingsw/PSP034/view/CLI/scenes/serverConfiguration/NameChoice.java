package it.polimi.ingsw.PSP034.view.CLI.scenes.serverConfiguration;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class NameChoice extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final VerticalArrangement va2;

    private final String[] alreadyChosenNames;
    private final Message selectName;
    private final HorizontalArrangement ha1;

    private final Message yourName;
    private final TextBox namePicker;

    public NameChoice(String[] alreadyChosenNames){
        this.alreadyChosenNames = alreadyChosenNames;

        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("name choice");
        va2 = new VerticalArrangement();
        va2.setLeftAlignment();
        va2.setBorder(1);
        va1.addObjects(title, va2);

        selectName = new Message("Insert the name that will be shown to the other players.", title.getWidth());
        ha1 = new HorizontalArrangement();
        va2.addObjects(selectName, ha1);

        yourName = new Message("Your name :", -1);
        namePicker = new TextBox(title.getWidth());
        ha1.addObjects(yourName, namePicker);
        ha1.setBottomAlignment();
        ha1.setBorder(1);
    }

    @Override
    public String show(){
        super.clearFrame();

        super.printMain(va1);

        int maxLength = Constant.MAX_NAME_LENGTH;

        RegexCondition[] regex = new RegexCondition[2+alreadyChosenNames.length];
        regex[0] = new RegexCondition("^[a-zA-Z0-9_]+$", "Your name can only contain letters, numbers and underscores.");
        regex[1] = new RegexCondition("^([a-zA-Z0-9_]){1,"+maxLength+"}$", "Your name must contain 1-"+maxLength+" characters.");
        for(int i = 0; i < alreadyChosenNames.length; i++){
            regex[i+2] = new RegexCondition("^(?!" + alreadyChosenNames[i] + ")([a-zA-Z0-9_]){1,"+maxLength+"}$", "This name has already been taken.");
        }

        return namePicker.waitAnswer(regex);
    }
}
