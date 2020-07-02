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
    private final VerticalArrangement va1;

    private final Font title;

    private final VerticalArrangement godsVA;
    private Message[] godMessage;

    private final HorizontalArrangement ha1;
    private final Dialog chooseGod;

    private final HorizontalArrangement haAnswer;
    private final Message yourChoice;
    private final TextBox choice;

    /**
     * Creates the scene and organizes the objects.
     * @param playersNumber The number of player that take part to the game.
     */
    public CardsChoice(int playersNumber){
        this.playersNumber = playersNumber;

        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("gods choice");
        godsVA = new VerticalArrangement();
        godsVA.setLeftAlignment();
        godsVA.setBorder(0);
        ha1 = new HorizontalArrangement();
        ha1.setBottomAlignment();
        ha1.setBorder(5);
        va1.addObjects(title,godsVA,ha1);

        godMessage = new Message[GodDescription.values().length];
        for (int i = 0; i<GodDescription.values().length; i++){
            GodDescription god = GodDescription.values()[i];
            godMessage[i] = new Message(ANSI.FG_red + god.getName() + ANSI.reset + " - " + god.getPower(), super.getFrameWidth());
        }
        godsVA.addObjects(godMessage);

        String[] options = new String[GodDescription.values().length];
        for(int i = 0; i < options.length; i++){
            options[i] = GodDescription.values()[i].getName();
        }
        chooseGod = new Dialog("Choose the " + playersNumber + " you want to play with (eg: 1,10,5)", -1, 2, options);

        haAnswer = new HorizontalArrangement();
        haAnswer.setBorder(1);
        haAnswer.setBottomAlignment();
        ha1.addObjects(chooseGod, haAnswer);

        yourChoice = new Message("Your choice :", -1);
        choice = new TextBox(10);
        haAnswer.addObjects(yourChoice, choice);

    }

    /**
     * {@inheritDoc}
     * @return Two or three numbers (based on how many players are playing) divided by a comma, representing the positions in tha array of the chosen gods. The user input is 1-based.
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        RegexCondition regexCondition;
        if(playersNumber == 2)
            regexCondition = new RegexCondition("^(([1-9])|1([0-4])),((?!\\2)[1-9]|1((?!\\3)[0-4]))$", "Invalid selection");
        else
            regexCondition = new RegexCondition("^(([1-9])|1([0-4])),(((?!\\2)[1-9])|(1((?!\\3)[0-4]))),((?!\\2)(?!\\5)[1-9]|1((?!\\3)(?!\\7)[0-4]))$", "Invalid selection");
        return choice.waitAnswer(regexCondition);
    }
}
