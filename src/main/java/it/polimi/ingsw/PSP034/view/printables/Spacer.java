package it.polimi.ingsw.PSP034.view.printables;

public class Spacer extends PrintableObject{
    public Spacer(int width, int height){
        super();
        super.setObjectSize(height);
        for(int line = 0; line < height; line++) {
            super.setObjectLine(line, new String(new char[width]).replace('\u0000', ' '));
        }
    }
}
