package it.polimi.ingsw.PSP034.view.CLI.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;
import it.polimi.ingsw.PSP034.view.CLI.printables.Frame;
import it.polimi.ingsw.PSP034.view.CLI.printables.Message;
import it.polimi.ingsw.PSP034.view.CLI.printables.TextBox;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class ScreenTest extends Scene {
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

        Message message = new Message("Please, make sure you can see the four corners forming a rectangle. If you don't, please try reducing your terminal font size and make sure that your terminal supports ANSI escape sequences and UNICODE characters. Press 'Enter' once you are ready.", Frame.SCREEN_WIDTH-4);
        message.print(3, 4);

        TextBox textBox = new TextBox(1);
        textBox.print(message.getStartLine()+message.getHeight(), message.getStartColumn());
        textBox.waitAnswer();
        return null;
    }
}
