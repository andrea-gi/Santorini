package it.polimi.ingsw.PSP034.view.CLI.printables.arrangements;

import it.polimi.ingsw.PSP034.view.CLI.printables.PrintableObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class creates an arrangement. An arrangement is used to organize a list of object so that they are correctly shown on the screen.
 */
public class Arrangement extends PrintableObject {
    private final ArrayList<PrintableObject> objects;
    private int alignment;
    private int border;

    public Arrangement(){
        super();
        this.objects = new ArrayList<>();
        alignment = 0;
        border = 1;
        super.setObjectSize(0);
    }

    /**
     * Adds any number of objects to the list of objects that the arrangement contains. The object are added at the end of the arrangement.
     * @param objects List of objects to be added.
     */
    public void addObjects(PrintableObject...objects){
        this.objects.addAll(Arrays.asList(objects));
        for(PrintableObject object : objects){
            object.setParent(this);
        }
        updateAlignment();
    }

    /**
     * Insert an object in a specific position in the arrangement
     * @param index Index of the position where the object must be inserted. The value is 0-based.
     * @param object Object to be inserted.
     */
    public void insertObject(int index, PrintableObject object){
        this.objects.add(index, object);
        updateAlignment();
    }

    /**
     * <pre>
     * Remove any number of objects from the arrangement.
     * NOTE: the functions compare the addresses of the objects, not the values.
     * </pre>
     *
     * @param objects List of objects to be removed.
     */
    public void removeObjects(PrintableObject...objects){
        this.objects.removeAll(Arrays.asList(objects));
        updateAlignment();
    }

    /**
     * Set the spacing between to adjacent objects in the arrangement.
     * @param border Value of the spacing.
     */
    public void setBorder(int border){
        this.border = border;
        updateAlignment();
    }

    /**
     * Updates the arrangement recalculating the position of the objects inside the arrangement.
     */
    public void updateAlignment(){
        if(super.getParent() != null)
            super.getParent().updateAlignment();
    }

    public ArrayList<PrintableObject> getObjects() {
        return objects;
    }

    public int getAlignment() {
        return alignment;
    }

    public int getBorder() {
        return border;
    }


    protected void setAlignment(int alignment) {
        this.alignment = alignment;
    }
}
