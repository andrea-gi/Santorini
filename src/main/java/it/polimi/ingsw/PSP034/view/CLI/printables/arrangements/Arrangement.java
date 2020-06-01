package it.polimi.ingsw.PSP034.view.CLI.printables.arrangements;

import it.polimi.ingsw.PSP034.view.CLI.printables.PrintableObject;

import java.util.ArrayList;
import java.util.Arrays;

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

    public void addObjects(PrintableObject...objects){
        this.objects.addAll(Arrays.asList(objects));
        for(PrintableObject object : objects){
            object.setParent(this);
        }
        updateAlignment();
    }

    public void insertObject(int index, PrintableObject object){
        this.objects.add(index, object);
        updateAlignment();
    }

    public void removeObjects(PrintableObject...objects){
        this.objects.removeAll(Arrays.asList(objects));
        updateAlignment();
    }

    public void setBorder(int border){
        this.border = border;
        updateAlignment();
    }

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
