package it.polimi.ingsw.PSP034.view.CLI.scenes;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;

/**
 * This class is the prototype that has to be extended by avery scene the composes the game. The variables that contain the dimensions of the frame are defined here.
 */
public abstract class Scene {
    private final int FRAME_WIDTH = Frame.FRAME_WIDTH;
    private final int FRAME_HEIGHT = Frame.FRAME_HEIGHT;
    private final int FRAME_START_LINE = Frame.FRAME_START_LINE;
    private final int FRAME_START_COLUMN = Frame.FRAME_START_COLUMN;

    public int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    public int getFrameStartLine() {
        return FRAME_START_LINE;
    }

    public int getFrameStartColumn() {
        return FRAME_START_COLUMN;
    }

    /**
     * Clears the frame erasing every object that is shown.
     */
    public void clearFrame(){
        ANSI.clearArea(FRAME_START_LINE, FRAME_START_COLUMN, FRAME_HEIGHT+FRAME_START_LINE, FRAME_WIDTH+FRAME_START_COLUMN);
    }

    /**
     * Organizes all the objects given in input in the mainVA vertical arrangement. To do so, two spacers are created to center vertically the objects.
     * @param objects Objects to be organized.
     */
    protected void printMain(PrintableObject...objects) throws NegativeArraySizeException{
        int totalHeight = 0;
        for(PrintableObject object : objects){
            totalHeight += object.getHeight();
        }
        VerticalArrangement mainVA = new VerticalArrangement();
        if(getFrameHeight()-totalHeight >= 0) {
            Spacer spacerUP = new Spacer(getFrameWidth(), (getFrameHeight() - totalHeight) / 2);
            Spacer spacerDOWN = new Spacer(getFrameWidth(), getFrameHeight() - totalHeight - spacerUP.getHeight());
            mainVA.addObjects(spacerUP);
            for(PrintableObject object : objects){
                mainVA.addObjects(object);
            }
            mainVA.addObjects(spacerDOWN);
            mainVA.setBorder(0);

            mainVA.print(getFrameStartLine(), getFrameStartColumn());
        }else
            throw new NegativeArraySizeException("The scene is bigger than the frame.");
    }

    /**
     * Prints an error prompt in the middle of the screen.
     * @param code          The code that determines the kind of error.
     * @param description   Error description.
     */
    public void printError(String code, String description){
        ErrorPrompt errorPrompt = new ErrorPrompt(code, description);
        errorPrompt.print(FRAME_START_LINE + (FRAME_HEIGHT-errorPrompt.getHeight())/2, FRAME_START_COLUMN + (FRAME_WIDTH-errorPrompt.getWidth())/2);
        moveToEndScreen();
    }

    public void moveToEndScreen(){
        ANSI.moveTo(Frame.SCREEN_HEIGHT+1, 1);
    }

    /**
     * Initializes and organizes the elements of the scene and then prints the whole scene using the printMain method. If the scene needs an input from the user, the regex that check the input are defined in this method.
     * @return The string of the answer of the user. If the scene requires no answer, the method is forced to return null;
     */
    public abstract String show();
}