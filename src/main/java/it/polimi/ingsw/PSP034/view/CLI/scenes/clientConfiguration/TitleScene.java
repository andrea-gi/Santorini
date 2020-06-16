package it.polimi.ingsw.PSP034.view.CLI.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class TitleScene extends Scene {

    @Override
    public String show(){
        ANSI.clearScreen();

        ANSI.moveTo(1,1);
        System.out.print("╔══");
        ANSI.moveTo(2,1);
        System.out.print("║");

        ANSI.moveTo(1, Frame.SCREEN_WIDTH-2);
        System.out.print("══╗");
        ANSI.moveTo(2, Frame.SCREEN_WIDTH);
        System.out.print("║");

        ANSI.moveTo(Frame.SCREEN_HEIGHT-1, 1);
        System.out.print("║");
        ANSI.moveTo(Frame.SCREEN_HEIGHT, 1);
        System.out.print("╚══");

        ANSI.moveTo(Frame.SCREEN_HEIGHT-1, Frame.SCREEN_WIDTH);
        System.out.print("║");
        ANSI.moveTo(Frame.SCREEN_HEIGHT, Frame.SCREEN_WIDTH-2);
        System.out.print("══╝");

        String warning = "Please, make sure you can see the four corners forming a rectangle. If you don't, please try reducing your terminal font size and make sure that your terminal supports ANSI escape sequences and UNICODE characters and restart the application (check the readme file to know more). Press 'Enter' once you are ready.";
        int start = 0;
        int end = Frame.SCREEN_WIDTH;
        ANSI.moveTo(3, 1);
        do{
            System.out.println(warning.substring(start, end));
            start = end;
            end += Frame.SCREEN_WIDTH;
        }while(end <= warning.length());
        if(start < warning.length()) {
            System.out.print(warning.substring(start));
        }

        TextBox textBox = new TextBox(1);
        textBox.print(4 + warning.length()/Frame.SCREEN_WIDTH, 1);
        textBox.waitAnswer();



        ANSI.clearScreen();

        Frame frame = new Frame();

        Title title = new Title();
        Message pressEnter = new Message("Press enter to continue", -1);
        textBox = new TextBox(1);

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(pressEnter, textBox);
        va1.setBorder(0);

        VerticalArrangement va2 = new VerticalArrangement();
        va2.addObjects(title, va1);
        va2.setBorder(3);

        frame.print(1,1);
        super.printMain(va2);

        textBox.waitAnswer();
        return null;
    }
}
