package it.polimi.ingsw.PSP034.view.scenes;

import it.polimi.ingsw.PSP034.view.printables.ANSI;
import it.polimi.ingsw.PSP034.view.printables.PrintableObject;
import it.polimi.ingsw.PSP034.view.printables.Spacer;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;

/**
 * This class is the prototype that has to be extended by avery scene the composes the game. The variables that contain the dimensions of the frame are defined here.
 */
public abstract class Scene {
    private final int FRAME_WIDTH = 192;
    private final int FRAME_HEIGHT = 31;
    private final int FRAME_START_LINE = 4;
    private final int FRAME_START_COLUMN = 18;
    private VerticalArrangement mainVA;

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
        mainVA = new VerticalArrangement();
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
    };

    /**
     * Abstract method that has to be implemented in every scene. Initializes and organizes the elements of the scene and then prints the whole scene using the printMain method.
     * @return The string of the answer of the user. If the scene requires no answer, the method is forced to return null;
     */
    public abstract String show();
}







/*
__________________________________________________________________________________________________________________________________________________________________________________________________________________________________
 (@^,^,^,^,^,@)                                                                                                                                                                                                    (@^,^,^,^,^,@)
   )`){o}(`(    ##################################################################################################################################################################################################   )`){o}(`(
   ,`,`,`,`,`   #                                                                                                                                                                                                #   ,`,`,`,`,`
   ==========   #                                                                                                                                                                                                #   ==========
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                     Wait for a game to be created                                                                              #    ||||||||
    ||||||||    #                                                                                                  ▄▄                                                                                            #    ||||||||
    ||||||||    #                                                                                                  ▀▀                                                                                            #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
    ||||||||    #                                                                                                                                                                                                #    ||||||||
   ,________,   #                                                                                                                                                                                                #   ,________,
     )    (     #                                                                                                                                                                                                #     )    (
   ,       `    ##################################################################################################################################################################################################   ,       `
 _/__________\_                                                                                                                                                                                                    _/__________\_
|______________|__________________________________________________________________________________________________________________________________________________________________________________________________|______________|


__________________________________________________________________________________________________________________________________________________________________________________________________________________________________
 (@^,^,^,^,^,@)                                                                                                                                                                                                    (@^,^,^,^,^,@)
   )`){o}(`(                                                                                                                                                                                                         )`){o}(`(
   ,`,`,`,`,`                                                                                                                                                                                                        ,`,`,`,`,`
   ==========                                                                                                                                                                                                        ==========
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                           Wait for a game to be created                                                                                  ||||||||
    ||||||||                                                                                                        ▄▄                                                                                                ||||||||
    ||||||||                                                                                                        ▀▀                                                                                                ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
    ||||||||                                                                                                                                                                                                          ||||||||
   ,________,                                                                                                                                                                                                        ,________,
     )    (                                                                                                                                                                                                            )    (
   ,       `                                                                                                                                                                                                         ,       `
 _/__________\_                                                                                                                                                                                                    _/__________\_
|______________|__________________________________________________________________________________________________________________________________________________________________________________________________|______________|



 */