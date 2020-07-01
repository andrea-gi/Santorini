package it.polimi.ingsw.PSP034.view.CLI.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.GodCard;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

/**
 * This class creates the scene to be printed when the user has to choose the god he/she wants to play with.
 */
public class PersonalGodChoice extends Scene {
    private final String[] possibleGods;
    private final String[] alreadyChosenGods;

    private final VerticalArrangement va1;

    private final Font title;
    private final HorizontalArrangement godsHA;
    private final Dialog chooseGod;

    private final HorizontalArrangement ha1;
    private final Message yourChoice;
    private final TextBox godPicker;

    /**
     * Creates the scene and organizes the objects.
     * @param possibleGods Gods that the user can choose.
     * @param alreadyChosenGods Gods that another player already chose.
     */
    public PersonalGodChoice(String[] possibleGods, String[] alreadyChosenGods){
        this.possibleGods = possibleGods;
        this.alreadyChosenGods = alreadyChosenGods;

        va1 = new VerticalArrangement();
        va1.setBorder(1);
        va1.setCentreAlignment();

        title = new Font("god choice");
        godsHA = new HorizontalArrangement();
        godsHA.setBorder(1);
        godsHA.setTopAlignment();

        GodCard[] godCards = new GodCard[possibleGods.length];
        for(int i = 0; i < this.possibleGods.length; i++){
            godCards[i] = new GodCard(this.possibleGods[i]);
        }
        GodCard[] takenCards = new GodCard[alreadyChosenGods.length];
        for(int i = 0; i < this.alreadyChosenGods.length; i++){
            takenCards[i] = new GodCard(this.alreadyChosenGods[i], ANSI.FG_red);
        }
        godsHA.addObjects(takenCards);
        godsHA.addObjects(godCards);

        chooseGod = new Dialog("Choose the god you want to play with:", -1, 1, possibleGods);

        ha1 = new HorizontalArrangement();
        ha1.setBorder(1);
        ha1.setBottomAlignment();

        va1.addObjects(title, godsHA, chooseGod, ha1);

        yourChoice = new Message("Your choice:", -1);
        godPicker = new TextBox(chooseGod.getWidth()-yourChoice.getWidth()-ha1.getBorder());
        ha1.addObjects(yourChoice, godPicker);
    }

    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        return godPicker.waitAnswer(new RegexCondition("^[1-" + possibleGods.length + "]$", "Invalid selection"));

    }
}
