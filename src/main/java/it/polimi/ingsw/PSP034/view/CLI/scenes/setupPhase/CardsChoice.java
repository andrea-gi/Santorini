package it.polimi.ingsw.PSP034.view.CLI.scenes.setupPhase;


import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.GodDescription;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has to choose the gods that will be used in the game.
 */
public class CardsChoice extends Scene {
    private final int playersNumber;

    /**
     * Creates the scene and organizes the objects.
     * @param playersNumber The number of player that take part to the game.
     */
    public CardsChoice(int playersNumber){
        this.playersNumber = playersNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String show() {
        super.clearFrame();
        Font title = new Font("gods choice");

        VerticalArrangement godsVA = new VerticalArrangement();
        for (GodDescription god : GodDescription.values()){
            Message godMessage = new Message(ANSI.FG_red + god.getName() + ANSI.reset + " - " + god.getPower(), super.getFrameWidth());
            godsVA.addObjects(godMessage);
        }
        godsVA.setBorder(0);
        godsVA.setLeftAlignment();

        String[] options = new String[GodDescription.values().length];
        for(int i = 0; i < options.length; i++){
            options[i] = GodDescription.values()[i].getName();
        }
        Dialog chooseGod = new Dialog("Choose the " + playersNumber + " you want to play with (eg: 1,10,5)", -1, 2, options);

        Message yourChoice = new Message("Your choice :", -1);
        Spacer spacer = new Spacer(5, 1);
        TextBox choice = new TextBox(10);
        HorizontalArrangement ha1 = new HorizontalArrangement();
        ha1.addObjects(chooseGod, spacer, yourChoice, choice);
        ha1.setBottomAlignment();

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(godsVA, ha1);
        va1.setBorder(2);
        va1.setLeftAlignment();

        VerticalArrangement va2 = new VerticalArrangement();
        va2.addObjects(title, va1);

        super.printMain(va2);

        //TODO -- valutare se fare più regex per i diversi errori
        RegexCondition regexCondition;
        if(playersNumber == 2)
            regexCondition = new RegexCondition("^(([1-9])|1([0-4])),((?!\\2)[1-9]|1((?!\\3)[0-4]))$", "Invalid selection");
        else
            regexCondition = new RegexCondition("^(([1-9])|1([0-4])),(((?!\\2)[1-9])|(1((?!\\3)[0-4]))),((?!\\2)(?!\\5)[1-9]|1((?!\\3)(?!\\7)[0-4]))$", "Invalid selection");
        return choice.waitAnswer(regexCondition);
    }
}
