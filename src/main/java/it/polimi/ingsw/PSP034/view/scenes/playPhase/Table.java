package it.polimi.ingsw.PSP034.view.scenes.playPhase;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.printables.godcards.GodCard;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

import java.util.ArrayList;
import java.util.Arrays;

public class Table extends Scene{
    Font title;
    ViewBoard board;
    GodCard[] cards;
    Spacer cardsQuestionDistance;
    Dialog question;
    Message message;
    Message answer;
    TextBox textBox;
    ArrayList<RegexCondition> regex;
    Spacer answerIndentation;
    HorizontalArrangement textBoxANDAnswer;
    HorizontalArrangement alignedCards;
    VerticalArrangement rightSide;
    HorizontalArrangement boardANDRight;
    VerticalArrangement all;

    public Table(String[] gods){
        board = new ViewBoard();

        cards = new GodCard[gods.length];
        for(int i = 0; i < gods.length; i++){
            cards[i] = new GodCard(gods[i]);
        }

        cardsQuestionDistance = new Spacer(1, 4);
        answerIndentation = new Spacer(4, 1);
    }

    @Override
    public String show() {
        PrintableObject[] objectsToAdd;

        textBoxANDAnswer = new HorizontalArrangement();
        if(textBox != null)
            textBoxANDAnswer.addObjects(answerIndentation, answer, textBox);
        textBoxANDAnswer.setBottomAlignment();

        alignedCards = new HorizontalArrangement();
        alignedCards.addObjects(cards);
        alignedCards.setTopAlignment();
        alignedCards.setBorder(3);

        rightSide = new VerticalArrangement();
        if(message != null)
            objectsToAdd = new PrintableObject[] {alignedCards, cardsQuestionDistance, message, textBoxANDAnswer};
        else if(question != null)
            objectsToAdd = new PrintableObject[] {alignedCards, cardsQuestionDistance, question, textBoxANDAnswer};
        else
            objectsToAdd = new PrintableObject[] {alignedCards, cardsQuestionDistance};
        rightSide.addObjects(objectsToAdd);
        rightSide.setCentreAlignment();

        boardANDRight = new HorizontalArrangement();
        boardANDRight.addObjects(board, rightSide);
        boardANDRight.setBorder(5);
        boardANDRight.setTopAlignment();

        all = new VerticalArrangement();
        all.addObjects(title, boardANDRight);
        all.setBorder(2);
        all.setCentreAlignment();

        super.printMain(all);

        if(textBox == null){
            return null;
        }else{
            if(regex == null){
                textBox.waitAnswer();
                return null;
            }else{
                return textBox.waitAnswer(regex.toArray(new RegexCondition[0]));
            }
        }
    }

    public void updateBoard(boolean[][] dome, int[][] building, Color[][] color, Sex[][] sex, boolean showNumbers){
        for(int y = 0; y < Constant.DIM; y++){
            for(int x = 0; x < Constant.DIM; x++){
                board.updateTile(x, y, building[x][y], dome[x][y], color[x][y], sex[x][y]);
            }
        }

        if(showNumbers)
            board.showNumbers();
        else
            board.hideNumbers();

        textBox = null;
    }

    public void updatePlaceWorker(boolean[][] dome, int[][] building, Color[][] color, Sex[][] sex){
        title = new Font("workers setup");
        updateBoard(dome, building, color, sex, true);

        int freeTiles = 0;
        for(int y = 0; y < Constant.DIM; y++){
            for(int x = 0; x < Constant.DIM; x++){
                if(!dome[x][y] &&  color[x][y]==null)
                    freeTiles++;
            }
        }

        message = null;
        String[] options = new String[freeTiles];
        for (int n = 1; n<=freeTiles; n++){
            options[n-1] = Integer.toString(n);
        }
        question = new Dialog("Select the tiles your worker will start from:", -1,5, options);

        answer = new Message("Male Worker :", -1);
        textBox = new TextBox(question.getWidth()-answer.getWidth());

        regex = new ArrayList<>();
        String rule;
        if(freeTiles <= 9)
            rule = "^[1-" + freeTiles + "]$";
        else{
            rule = "^([1-9]";
            int x;
            for(x = 19; x < freeTiles; x+=10){
                rule += "|" + x/10 + "[0-9]";
            }
            rule += "|" + x/10 + "[0-" + freeTiles%10 + "])$";
        }

        regex.add(new RegexCondition(rule, "Invalid selection."));
    }

    public void updateSelectWorker() {
        message = null;
        question = new Dialog("Select which Worker you want to move:", -1, 1, "Male (" + Sex.MALE.toString() + ")", "Female (" + Sex.FEMALE.toString() + ")");
        answer = new Message("Your choice :", -1);
        textBox = new TextBox(question.getWidth() - answer.getWidth());

        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-2]$", "Invalid selection."));
    }

    public void updateMove(Sex sex, Directions[] possibleDirections, boolean hasChoice){
        message = null;
        String[] options;
        if(hasChoice) {
            options = new String[possibleDirections.length];
            for (int i = 0; i < possibleDirections.length; i++) {
                options[i] = possibleDirections[i].name();
            }
            options[possibleDirections.length - 1] = "Back";
        }else{
            options = new String[possibleDirections.length];
            for (int i = 0; i < possibleDirections.length; i++) {
                options[i] = possibleDirections[i].name();
            }
        }
        question = new Dialog("Where do you want to move your " + sex.name().toLowerCase() + " (" + sex.toString() + ") Worker?", -1, 3, options);
        answer = new Message("Your move :", -1);
        textBox = new TextBox(question.getWidth() - answer.getWidth());

        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-" + (possibleDirections.length+1) + "]$", "Invalid selection."));
    }

    public void updateBuild(Sex sex, Directions[] possibleDirections, boolean hasChoice){
        message = null;
        String[] options;
        if(hasChoice) {
            options = new String[possibleDirections.length];
            for (int i = 0; i < possibleDirections.length; i++) {
                options[i] = possibleDirections[i].name();
            }
            options[possibleDirections.length - 1] = "Back";
        }else{
            options = new String[possibleDirections.length];
            for (int i = 0; i < possibleDirections.length; i++) {
                options[i] = possibleDirections[i].name();
            }
        }
        question = new Dialog("Where do you want to build with your " + sex.name().toLowerCase() + " (" + sex.toString() + ") Worker?", -1, 3, options);
        answer = new Message("Your build :", -1);
        textBox = new TextBox(question.getWidth() - answer.getWidth());

        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-" + (possibleDirections.length+1) + "]$", "Invalid selection."));
    }

    public void updatePower(){
        message = null;
        question = new Dialog("Do you want to use your god's power?", -1, 1, "Yes", "No");
        answer = new Message("Your choice :", -1);
        textBox = new TextBox(question.getWidth() - answer.getWidth());

        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-2]$", "Invalid selection."));
    }

    public void updateStart(){
        title = new Font("your turn");

        question = null;
        message = new Message("Press Enter to start your turn.", -1);
        textBox = new TextBox(1);
        regex = null;
    }
}
