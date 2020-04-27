package it.polimi.ingsw.PSP034.view;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;

public class View {
    private Screen screen;

    public View(){
        screen = new Screen();
        screen.start();
    }

    public Answer produceOnScreen(Request request){
        return screen.action(request);
    }

    public void updateBoard(){}
}
