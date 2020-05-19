package it.polimi.ingsw.PSP034.view.CLI.scenes.setupPhase;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.godcards.GodCard;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class PersonalGodChoice extends Scene {
    private final String[] possibleGods;
    private final String[] alreadyChosenGods;

    public PersonalGodChoice(String[] possibleGods, String[] alreadyChosenGods){
        this.possibleGods = possibleGods;
        this.alreadyChosenGods = alreadyChosenGods;
    }

    @Override
    public String show() {
        super.clearFrame();

        Font title = new Font("god choice");

        GodCard[] godCards = new GodCard[possibleGods.length];
        for(int i = 0; i < possibleGods.length; i++){
            godCards[i] = new GodCard(possibleGods[i]);
        }

        GodCard[] takenCards = new GodCard[alreadyChosenGods.length];
        for(int i = 0; i < alreadyChosenGods.length; i++){
            takenCards[i] = new GodCard(alreadyChosenGods[i], ANSI.FG_red);
        }
        HorizontalArrangement godsHA = new HorizontalArrangement();
        godsHA.addObjects(takenCards);
        godsHA.addObjects(godCards);
        godsHA.setTopAlignment();

        Dialog chooseGod = new Dialog("Choose the god you want to play with:", -1, 1, possibleGods);

        TextBox godPicker = new TextBox(chooseGod.getWidth());

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, godsHA, chooseGod, godPicker);

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va1.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va1.getHeight()- spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        return godPicker.waitAnswer(new RegexCondition("^[1-" + possibleGods.length + "]$", "Invalid selection"));

    }
}
