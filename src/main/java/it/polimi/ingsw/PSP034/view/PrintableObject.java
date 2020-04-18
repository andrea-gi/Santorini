package it.polimi.ingsw.PSP034.view;

public class PrintableObject {
    private int line, column;
    private String[] object;

    public void setObjectSize(int size){
        object = new String[size];
    }

    public void setObjectLine(int line, String text) {
        object[line] = text;
    }

    public void print(int line, int column){
        this.line = line;
        this.column = column;
        for(int i = line; i-line < object.length; i++){
            ANSI.moveTo(i, column);
            System.out.print(object[i-line]);
        }
    }
}
