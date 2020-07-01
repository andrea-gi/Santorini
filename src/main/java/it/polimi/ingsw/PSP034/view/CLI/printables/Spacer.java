package it.polimi.ingsw.PSP034.view.CLI.printables;

/**
 * Represents an empty object used for spacing.
 */
public class Spacer extends PrintableObject{
    /**
     * Creates the spacer.
     * @param width Number of columns of the spacer.
     * @param height Number of rows of the spacer.
     */
    public Spacer(int width, int height){
        super();
        super.setObjectSize(height);
        for(int line = 0; line < height; line++) {
            super.setObjectLine(line, new String(new char[width]).replace('\u0000', ' '));
        }
    }
}
