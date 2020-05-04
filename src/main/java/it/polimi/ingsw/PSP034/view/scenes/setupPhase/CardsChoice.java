package it.polimi.ingsw.PSP034.view.scenes.setupPhase;


import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.printables.godcards.GodDescription;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

public class CardsChoice extends Scene {
    private int playersNumber;

    public CardsChoice(int playersNumber){
        this.playersNumber = playersNumber;
    }

    @Override
    public String show() {
        super.clearFrame();
        Font title = new Font("gods choice");

        VerticalArrangement godsVA = new VerticalArrangement();
        for (GodDescription god : GodDescription.values()){
            Message godMessage = new Message(god.getName() + " - " + god.getPower(), -1);
            godsVA.addObjects(godMessage);
        }
        godsVA.setBorder(0);

        String[] options = new String[GodDescription.values().length];
        for(int i = 0; i < options.length; i++){
            options[i] = GodDescription.values()[i].getName();
        }
        Dialog chooseGod = new Dialog("Choose the " + playersNumber + " you want to play with (eg: 1,10,5", -1, 2, options);
        Message yourChoice = new Message("Your choice :", -1);
        TextBox choice = new TextBox(10);
        HorizontalArrangement ha1 = new HorizontalArrangement();
        ha1.addObjects(chooseGod, yourChoice, choice);
        ha1.setBottomAlignment();

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(godsVA, ha1);
        va1.setLeftAlignment();

        VerticalArrangement va2 = new VerticalArrangement();
        va2.addObjects(title, va1);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va2.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va2.getHeight()- spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        //TODO -- valutare se fare più regex per i diversi errori
        RegexCondition regex;
        if(playersNumber == 2)
            regex = new RegexCondition("^(([1-9]|1[0-4]),)((?!\\2)([1-9]|1[0-4]))$", "Invalid selection");
        else
            regex = new RegexCondition("^(([1-9]|1[0-4]),)(((?!\\2)([1-9]|1[0-4])),)((?!\\2)(?!\\5)([1-9]|1[0-4]))$", "Invalid selection");
        return choice.waitAnswer(regex);
    }
}