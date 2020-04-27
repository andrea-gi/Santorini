package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPersonalGod;
import it.polimi.ingsw.PSP034.messages.setupPhase.RequestPersonalGod;
import it.polimi.ingsw.PSP034.messages.setupPhase.RequestPlaceWorker;
import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.Dialog;
import it.polimi.ingsw.PSP034.view.printables.Font;
import it.polimi.ingsw.PSP034.view.printables.godcards.GodCard;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

public class Screen {
    private GamePhase gamePhase;
    private ArrayList<String> playerNames;
    private ArrayList<PlayerBox> playerBoxes;
    private ViewBoard board;


    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 30;
    private static final int FRAME_START_LINE = 3;
    private static final int FRAME_START_COLUMN = 18;

    public Screen(){
        gamePhase = GamePhase.SETUP;
        board = new ViewBoard();
    }

    private void cleareFrame(){
        ANSI.clearArea(FRAME_START_LINE, FRAME_START_COLUMN, FRAME_START_LINE + FRAME_HEIGHT, FRAME_START_COLUMN + FRAME_WIDTH);
    }

    public void start(){
        printTitle();
        int mode = printMenu();
        return ???
    }

    public Answer action(Request request){
        switch(gamePhase){
            case SETUP:
                return setupAction(request);
            case PLAY:
                return playAction(request);
            default: //GAMEOVER
                return gameoverAction(request);
        }
    }

    private Answer setupAction(Request request){
        switch (request.getClass().getName()){
            case "RequestPersonalGod":
                return ChoosePersonalGod((RequestPersonalGod) request);
            case "":
        }
    }

    private Answer ChoosePersonalGod(RequestPersonalGod request){
        cleareFrame();
        String[] possibleGods = (request).getPossibleGods();
        GodCard[] possibleCards = new GodCard[possibleGods.length];
        for(int i = 0; i < possibleCards.length; i++){
            possibleCards[i] = new GodCard(possibleGods[i]);
        }
        String question = "Choose one between the remaining gods";
        Dialog chooseGod = new Dialog(question, question.length(), possibleGods.length, possibleGods);

        int maxGodHeight = 0;
        for(GodCard god : possibleCards){
            if(maxGodHeight < god.getHeight())
                maxGodHeight = god.getHeight();
        }

        for(int i = 0; i < possibleCards.length; i++){
            int line = FRAME_START_LINE + (FRAME_HEIGHT - maxGodHeight - chooseGod.getHeight())/2;
            int column = FRAME_START_COLUMN + (FRAME_WIDTH-i*(2 + possibleCards[0].getWidth()))/2;
            possibleCards[i].print(line, column);
        }
        chooseGod.print(possibleCards[0].getStartLine()+maxGodHeight+5, FRAME_START_COLUMN + (FRAME_WIDTH-chooseGod.getWidth())/2);
        ANSI.moveTo(chooseGod.getStartLine()+chooseGod.getHeight()+2, FRAME_START_COLUMN+FRAME_WIDTH/2);


        Scanner scan = new Scanner(System.in);
        int choice;
        do{
            ANSI.clearLineInterval(chooseGod.getStartLine()+chooseGod.getHeight()+2, FRAME_START_COLUMN+FRAME_WIDTH/2, 5+ FRAME_START_COLUMN+FRAME_WIDTH/2);
            choice = scan.nextInt();
        }while((1 <= choice  &&  choice <= possibleGods.length));



        return new AnswerPersonalGod(possibleGods[choice-1]);
    }

    private static void printTitle(){
        ANSI.clearScreen();
        System.out.print("__________________________________________________________________________________________________________________________________________________________________________________________________________________________________\n" +
                " (@^,^,^,^,^,@)                                                                                                                                                                                                    (@^,^,^,^,^,@)\n" +
                "   )`){o}(`(                                                                                                                                                                                                         )`){o}(`(\n" +
                "   ,`,`,`,`,`                                                                                                                                                                                                        ,`,`,`,`,`\n" +
                "   ==========                                                                                                                                                                                                        ==========\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                    ,&%.                                                                                                                                                                  ||||||||\n" +
                "    ||||||||                                @@@@@@@@@@@@.                                                                           %%*                                              @@@@                             ||||||||\n" +
                "    ||||||||                              #@@@@@@@  @@@@@&                                    @@@@@@@&#/                          ,@@@@@*#@@@@@@@@   ,@@@@&                         %@@@@@                            ||||||||\n" +
                "    ||||||||                              @@@@@@@   @@@@@@        ,@@@%             %@@@@@@/   %@@@@@@@@@@@@@@@@@,              %@@@@@@@@  .@@@@@@@% .@@@@@              %@@&,       %@@@                             ||||||||\n" +
                "    ||||||||                              &@@@@@@@.              /@@@@@@&   @@@@@( @@@ @@@@@@       &@@@@@           .#%%@@@@.    @@@@@@(   @@@@@@@@   /%@/  .@@@@@   ,@@@@@@@@/     @@@@@%                           ||||||||\n" +
                "    ||||||||                               @@@@@@@@@            ,@@@@@@@@,   @@@@@@@@  (@@@@@(      @@@@@@      ,@@@@@*    %@@@   @@@@@@&   @@@@@@@   /@@@@@  /@@@@@,@@@, %@@@@@*    @@@@@@                           ||||||||\n" +
                "    ||||||||                                &@@@@@@@@&          &@@@@@@@@@   *@@@@@@   (@@@@@@     (@@@@@@    &@@@@@@@@@@@, #@@@, &@@@@@&  @@@@@@,    /@@@@@   @@@@@@@%   #@@@@@@    @@@@@@                           ||||||||\n" +
                "    ||||||||                                 .@@@@@@@@@#       .@@@@/@@@@@%   @@@@@#   (@@@@@@.    @@@@@@@   @@@@@@@/  #@@@# @@@@ @@@@@@&.@@@@@       (@@@@@   @@@@@@@    @@@@@@@    @@@@@@                           ||||||||\n" +
                "    ||||||||                                   @@@@@@@@@@#     &@@@@ @@@@@@   %@@@@@   /@@@@@@#    @@@@@@@  @@@@@@@,    %@@@ @@@@,@@@@@@@@@@@         &@@@@@   @@@@@@.    @@@@@@@   .@@@@@#                           ||||||||\n" +
                "    ||||||||                             @@@(    @@@@@@@@@@.   @@@@@*%@@@@@@  *@@@@@(   @@@@@@@    @@@@@@/ @@@@@@@*      @@. @@@@,@@@@@@@@@@@@@       &@@@@@   @@@@@@,    @@@@@@@    @@@@@/                           ||||||||\n" +
                "    ||||||||                           @@@@@@@    @@@@@@@@@@& @@@@@@* @@@@@@/  @@@@@@   @@@@@@@   #@@@@@@*/@@@@@@@ %@@. @@@ @@@@@.@@@@@@/  @@@@@@     @@@@@@   @@@@@@(   (@@@@@@@   #@@@@@/                           ||||||||\n" +
                "    ||||||||                          @@@@@@@      ,@@@@@@@@@&%@@@@#  (@@@@@@  @@@@@@   &@@@@@@/  @@@@@@@ #@@@@@@@ @@@@@@% (@@@@*&@@@@@@(   @@@@@@    @@@@@@   @@@@@@(   %@@@@@@@   %@@@@@(                           ||||||||\n" +
                "    ||||||||                          .@@@@@@@      /@@@@@@@@@@@@@@,   @@@@@@& *@@@@@.   @@@@@@@( @@@@@@@  @@@@@@@        *@@@@@ @@@@@@@%   .@@@@@@   @@@@@@(  @@@@@@(   @@@@@@@@   @@@@@@@                           ||||||||\n" +
                "    ||||||||                           @@@@@@@@@@@@@@@@@@@@@@&@@@@@,    @@@@@@( %@@@@      #@@@@& @@@@@@@   &@@@@@@.     &@@@@(  &@@@@@@&    &@@@@@@  @@@@@@@% @@@@@@(   %@@@@@@@.  @@@@@@@(                          ||||||||\n" +
                "    ||||||||                            @@@@@@@@@@@@@@@@@@@@@ @@@@@      @@@@@@&,                 &@@@@@@     ,@@@@@@/ %@@@@,     @@@@@@@     @@@@@@@  .@@@*   %@@@@@/   *@@@@@@@(  #@@@@@@                           ||||||||\n" +
                "    ||||||||                               @@@@@@@@@@@@@@@#     *//        ,@@@@/                 @@@@@@@/         ,#@@@%.                    ,@@@@@@@%          (&%/      *@@@@@@@                                   ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "    ||||||||                                                                                         PRESS ENTER TO CONTINUE                                                                                          ||||||||\n" +
                "    ||||||||                                                                                                                                                                                                          ||||||||\n" +
                "   ,________,                                                                                                                                                                                                        ,________,\n" +
                "     )    (                                                                                                                                                                                                            )    (\n" +
                "   ,       `                                                                                                                                                                                                         ,       `\n" +
                " _/__________\\_                                                                                                                                                                                                    _/__________\\_\n" +
                "|______________|__________________________________________________________________________________________________________________________________________________________________________________________________|______________|");


        ANSI.moveTo(FRAME_HEIGHT/2 + FRAME_START_LINE, FRAME_WIDTH/2 + FRAME_START_COLUMN);
        Scanner scan = new Scanner(System.in);
        scan.next();
    }

    private int printMenu(){
        cleareFrame();

        Font menuTitle = new Font("main menu");
        Dialog menu = new Dialog("", 19, 4, "Rules", "Two players", "Three players", "Exit");

        menuTitle.print((FRAME_HEIGHT-menuTitle.getHeight()-menu.getHeight()-3)/2 + FRAME_START_LINE, (FRAME_WIDTH-menuTitle.getWidth())/2 + FRAME_START_COLUMN);
        menu.print(menuTitle.getStartLine()+menuTitle.getHeight()+3, (FRAME_WIDTH-menu.getWidth())/2 + FRAME_START_COLUMN);

        Scanner in = new Scanner(System.in);
        int choice;
        ANSI.moveTo(menu.getHeight()+menu.getStartLine()+2, FRAME_WIDTH/2 + FRAME_START_COLUMN);
        do {
            do {
                choice = in.nextInt();
                ANSI.clearLineInterval(menu.getHeight()+menu.getStartLine()+2, FRAME_WIDTH/2 + FRAME_START_COLUMN, FRAME_WIDTH/2 + FRAME_START_COLUMN +3);
            } while (!(choice == 1 || choice == 2 || choice == 3 || choice == 4));

            switch (choice) {
                case 1:
                    printRules();
                case 2:
                    //TODO -- setta le variabile per passare alla lobby
                case 3:
                    //TODO -- setta le variabile per passare alla lobby
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        }while(choice != 2  &&  choice != 3);
        return choice;
    }

    private void printRules(){
        cleareFrame();
        Font rulesTitle = new Font("rules");
        Message rules = new Message("rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules rules ", FRAME_WIDTH);
        Message pressEnter = new Message("Press enter to go back to main menu", 35);

        rulesTitle.print((FRAME_HEIGHT-rulesTitle.getHeight()-3-rules.getHeight()-3-pressEnter.getHeight())/2 + FRAME_START_LINE, (FRAME_WIDTH-rulesTitle.getWidth())/2 + FRAME_START_COLUMN);
        rules.print(rulesTitle.getStartLine()+rulesTitle.getHeight()+3, (FRAME_WIDTH-rules.getWidth())/2 + FRAME_START_COLUMN);
        pressEnter.print(rules.getStartLine()+rules.getHeight()+3, (FRAME_WIDTH-pressEnter.getWidth())/2 + FRAME_START_COLUMN);

        ANSI.moveTo(pressEnter.getStartLine()+2, FRAME_WIDTH/2 + FRAME_START_COLUMN);

        Scanner scan = new Scanner(System.in);
        scan.next();
    }

    private void updateBoard(SlimBoard update){
        for(int i = 0; i < Constant.DIM; i++){
            for(int j = 0; j < Constant.DIM; j++){
                board.updateTile(i, j, update.getBuilding()[i][j], update.getDome()[i][j], update.getColor()[i][j], update.getSex()[i][j]);
            }
        }
    }


    ////////////////////////////////////////
    //             Play phase             //
    ////////////////////////////////////////



}
