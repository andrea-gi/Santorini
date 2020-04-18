package it.polimi.ingsw.PSP034.view;

/**
 * This class makes UNICODE escape characters available through functions and variables that have easy to understand names.
 */
public class ANSI {
    ///////////////////////////////////////////////////////////
    //TERMINAL MANAGEMENT FUNCTIONS                          //
    ///////////////////////////////////////////////////////////

    /**
     * Moves the terminal cursor vertically.
     * @param lines number of line to move the cursor. Positive numbers will move the cursor downwards, negative numbers wil move the cursor upwards.
     */
    public static void moveVertical(int lines){
        if(lines < 0) {
            int negLines = -1*lines;
            System.out.print("\033[<" + negLines + ">A");
        }else {
            System.out.print("\033[<" + lines + ">B");
        }
    }

    /**
     * Moves the terminal cursor horizontally
     * @param columns number of columns to move the cursor. Positive numbers will move the cursor rightwards, negative numbers wil move the cursor leftwards.
     */
    public static void moveHorizontal(int columns){
        if(columns < 0) {
            int negLines = -1*columns;
            System.out.print("\033[<" + negLines + ">D");
        }else {
            System.out.print("\033[<" + columns + ">C");
        }
    }


    /**
     * Moves the terminal cursor to the given line and column. The values are 1-based.
     * @param line Line to move to
     * @param column Column to move to
     */
    public static void moveTo(int line, int column){
        System.out.print("\033[<" + line + ">;<" + column + ">H");
    }

    /**
     * Clears the screen and moves the terminal cursor to the top left angle (1,1).
     */
    public static void clearScreen(){
        System.out.print("\033[2J");
    }

    /**
     * Hides the terminal cursor.
     */
    public static void hideCursor(){
        System.out.print("\033[?5l");
    }

    /**
     * Shows the terminal cursor.
     */
    public static void showCursor(){
        System.out.print("\033[?5h");
    }

    /**
     * Clears the entire line at which the cursor is. Cursor position doesn't change.
     */
    public static void clearLine(){
        System.out.print("\033[2K");
    }

    /**
     * Clears characters from column startColumn to column endColumn at given line. After execution the cursor is in position (line, startColumn).
     * @param line line to be erased.
     * @param startColumn column to startColumn clearing from.
     * @param endColumn last column to be cleared.
     */
    public static void clearLineInterval(int line, int startColumn, int endColumn){
        ANSI.moveTo(line, startColumn);
        String spaces = "";
        for(int x = startColumn; x <= endColumn; x++){
            spaces = spaces + " ";
        }
        System.out.print(spaces);
        ANSI.moveTo(line, startColumn);
    }

    /**
     * Clears characters from line startLine to line endLine at given column. After execution the cursor is in position (startLine, column).
     * @param column column to be erased.
     * @param startLine line to startLine clearing from.
     * @param endLine last line to be cleared.
     */
    public static void clearColumnInterval(int column, int startLine, int endLine){
        for(int y = startLine; y <= endLine; y++){
            ANSI.moveTo(y, column);
            System.out.print(" ");
        }
        ANSI.moveTo(startLine, column);
    }

    /**
     * Clears the area of th console defined by given coordinates. After execution the cursor is in position (startLine, startColumn).
     * @param startLine Line of the top left character of the area.
     * @param startColumn Column of the top left character of the area.
     * @param endLine Line of the bottom right character of the area.
     * @param endColumn Column of the bottom right character of the area.
     */
    public static void clearArea(int startLine, int startColumn, int endLine, int endColumn){
        String spaces = "";
        for(int x = startColumn; x <= endColumn; x++){
            spaces = spaces + " ";
        }
        for(int y = startLine; y <= endLine; y++){
            ANSI.moveTo(y, startColumn);
            System.out.print(spaces);
        }
        ANSI.moveTo(startLine, startColumn);
    }


    ///////////////////////////////////////////////////////////
    //RESET                                                  //
    ///////////////////////////////////////////////////////////
    public static final String reset = "\033[0m";
    public static final String underlineOff = "\033[24";

    ///////////////////////////////////////////////////////////
    //4-BIT COLORS                                           //
    ///////////////////////////////////////////////////////////

    //FOREGROUND
    public static final String FG_black = "\033[30m";
    public static final String FG_red = "\033[31m";
    public static final String FG_green = "\033[32m";
    public static final String FG_yellow = "\033[33m";
    public static final String FG_blue = "\033[34m";
    public static final String FG_magenta = "\033[35m";
    public static final String FG_cyan = "\033[36m";
    public static final String FG_white = "\033[37m";
    public static final String FG_bright_black = "\033[90m";
    public static final String FG_bright_red = "\033[91m";
    public static final String FG_bright_green = "\033[92m";
    public static final String FG_bright_yellow = "\033[93m";
    public static final String FG_bright_blue = "\033[94m";
    public static final String FG_bright_magenta = "\033[95m";
    public static final String FG_bright_cyan = "\033[96m";
    public static final String FG_bright_white = "\033[97m";

    //BACKGROUND
    public static final String BG_black = "\033[40m";
    public static final String BG_red = "\033[41m";
    public static final String BG_green = "\033[42m";
    public static final String BG_yellow = "\033[43m";
    public static final String BG_blue = "\033[44m";
    public static final String BG_magenta = "\033[45m";
    public static final String BG_cyan = "\033[46m";
    public static final String BG_white = "\033[47m";
    public static final String BG_bright_black = "\033[100m";
    public static final String BG_bright_red = "\033[101m";
    public static final String BG_bright_green = "\033[102m";
    public static final String BG_bright_yellow = "\033[103m";
    public static final String BG_bright_blue = "\033[104m";
    public static final String BG_bright_magenta = "\033[105m";
    public static final String BG_bright_cyan = "\033[106m";
    public static final String BG_bright_white = "\033[107m";




    ///////////////////////////////////////////////////////////
    //8-BIT COLORS                                           //
    ///////////////////////////////////////////////////////////
    public static String FG_color(int colorCode){
        return "\033[38;5;"+colorCode+"m";
    }

    public static String BG_color(int colorCode){
        return "\033[48;5;"+colorCode+"m";
    }




    ///////////////////////////////////////////////////////////
    //FONT OPTIONS                                           //
    ///////////////////////////////////////////////////////////

}
