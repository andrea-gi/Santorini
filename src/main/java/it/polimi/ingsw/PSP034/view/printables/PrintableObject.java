package it.polimi.ingsw.PSP034.view.printables;

/**
 * The PrintableObject class creates and handles objects that can be printed in a console. A printable object
 * is made of a certain number o lines each represented by a string. The line share the same starting column but can be of different
 * length. the printable object has the same width as it's longest line length.
 */
public class PrintableObject {
    private int startLine, startColumn;
    private int width, height;
    private String[] object;

    /**
     * A PrintableObject is initialized with invalid values for it's coordinates (set to -1) and with no width nor height (set to 0).
     */
    public PrintableObject(){
        startLine = -1;
        startColumn = -1;
        width = 0;
        height = 0;
    }

    /**
     * Sets the number of lines the object is made of. The function must be invoked every time the number of line changes.
     * @param size Number of lines the object is made of.
     */
    public void setObjectSize(int size){
        object = new String[size];
        height = size;
    }

    /**
     * Sets a given line of the object to a given string.
     * @param line Number of the line to be set. The value is 0-based.
     * @param text Text to put in the given line.
     */
    public void setObjectLine(int line, String text) {
        //regex to only consider the effective length of the string without the ANSI escape sequences.
        //the first regex eliminates the extra length due to the use of \033[<n>D sequence followed by the text to be put inside a previous text.
        //the second regex eliminates all the ANSI escape sequences such as colors and font setting.
        int newWidth = text.replaceAll("\033\\[[0-9;]+D.*", "").replaceAll("\033\\[[0-9;]+[a-zA-Z]", "").length();
        object[line] = text;
        if(width < newWidth)
            width = newWidth;
    }


    /**
     * Prints the object at the given coordinates.
     * @param line line to start printing from. The value is 1-based.
     * @param column line to start printing from.  The value is 1-based.
     */
    public void print(int line, int column){
        this.startLine = line;
        this.startColumn = column;
        for(int i = line; i-line < object.length; i++){
            ANSI.moveTo(i, column);
            System.out.print(object[i-line]);
        }
    }

    /**
     * Returns the line of the top left character of the object.
     * @return startLine if the object if it has already be printed, -1 otherwise.
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * Returns the column of the top left character of the object.
     * @return startColumn if the object if it has already be printed, -1 otherwise.
     */
    public int getStartColumn() {
        return startColumn;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Returns the the object in its matrix representation.
     * @return the object field.
     */
    public String[] getObject(){
        return object.clone();
    }
}
