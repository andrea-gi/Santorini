package it.polimi.ingsw.PSP034.view.CLI.printables;

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
            System.out.print("\033[" + negLines + "A");
        }else {
            System.out.print("\033[" + lines + "B");
        }
    }

    /**
     * Moves the terminal cursor horizontally
     * @param columns number of columns to move the cursor. Positive numbers will move the cursor rightwards, negative numbers wil move the cursor leftwards.
     */
    public static void moveHorizontal(int columns){
        if(columns < 0) {
            int negLines = -1*columns;
            System.out.print("\033[" + negLines + "D");
        }else {
            System.out.print("\033[" + columns + "C");
        }
    }


    /**
     * Moves the terminal cursor to the given line and column. The values are 1-based.
     * @param line Line to move to
     * @param column Column to move to
     */
    public static void moveTo(int line, int column){
        System.out.print("\033[" + line + ";" + column + "H");
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
        StringBuilder spaces = new StringBuilder();
        for(int x = startColumn; x <= endColumn; x++){
            spaces.append(" ");
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
        StringBuilder spaces = new StringBuilder();
        for(int x = startColumn; x <= endColumn; x++){
            spaces.append(" ");
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
    /**
     * ANSI escape sequence to restore the default options for console text
     */
    public static final String reset = "\033[0m";
    //This line can be used instead of the actual reset ANSI escape to change the background of the CLI.
    //In thi case the default background color would be white and the default foreground color would be black.
    //public static final String reset = "\033[38;2;0;0;0;48;2;255;255;255m";

    ///////////////////////////////////////////////////////////
    //4-BIT COLORS                                           //
    ///////////////////////////////////////////////////////////

    //FOREGROUND
    /**
     * ANSI escape sequence for 3/4-bit foreground color Black
     */
    public static final String FG_black = "\033[30m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Red
     */
    public static final String FG_red = "\033[31m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Green
     */
    public static final String FG_green = "\033[32m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Yellow
     */
    public static final String FG_yellow = "\033[33m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Blue
     */
    public static final String FG_blue = "\033[34m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Magenta
     */
    public static final String FG_magenta = "\033[35m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Cyan
     */
    public static final String FG_cyan = "\033[36m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color White
     */
    public static final String FG_white = "\033[37m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Black
     */
    public static final String FG_bright_black = "\033[90m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Red
     */
    public static final String FG_bright_red = "\033[91m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Green
     */
    public static final String FG_bright_green = "\033[92m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Yellow
     */
    public static final String FG_bright_yellow = "\033[93m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Blue
     */
    public static final String FG_bright_blue = "\033[94m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Magenta
     */
    public static final String FG_bright_magenta = "\033[95m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Cyan
     */
    public static final String FG_bright_cyan = "\033[96m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright White
     */
    public static final String FG_bright_white = "\033[97m";

    //BACKGROUND
    /**
     * ANSI escape sequence for 3/4-bit foreground color Black
     */
    public static final String BG_black = "\033[40m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Red
     */
    public static final String BG_red = "\033[41m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Green
     */
    public static final String BG_green = "\033[42m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Yellow
     */
    public static final String BG_yellow = "\033[43m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Blue
     */
    public static final String BG_blue = "\033[44m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Magenta
     */
    public static final String BG_magenta = "\033[45m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Cyan
     */
    public static final String BG_cyan = "\033[46m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color White
     */
    public static final String BG_white = "\033[47m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Black
     */
    public static final String BG_bright_black = "\033[100m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Red
     */
    public static final String BG_bright_red = "\033[101m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Green
     */
    public static final String BG_bright_green = "\033[102m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Yellow
     */
    public static final String BG_bright_yellow = "\033[103m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Blue
     */
    public static final String BG_bright_blue = "\033[104m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Magenta
     */
    public static final String BG_bright_magenta = "\033[105m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright Cyan
     */
    public static final String BG_bright_cyan = "\033[106m";

    /**
     * ANSI escape sequence for 3/4-bit foreground color Bright White
     */
    public static final String BG_bright_white = "\033[107m";




    ///////////////////////////////////////////////////////////
    //8-BIT COLORS                                           //
    ///////////////////////////////////////////////////////////

    /**
     * Creates the correct ANSI escape sequence for a 8-bit foreground color.
     * @param colorCode Code of the 8-bit color.
     * @return ANSI escape sequence of the wanted 8-bit color.
     */
    public static String FG_color(int colorCode){
        return "\033[38;5;"+colorCode+"m";
    }

    /**
     * Creates the correct ANSI escape sequence for a 8-bit background color.
     * @param colorCode Code of the 8-bit color.
     * @return ANSI escape sequence of the wanted 8-bit color.
     */
    public static String BG_color(int colorCode){
        return "\033[48;5;"+colorCode+"m";
    }




    ///////////////////////////////////////////////////////////
    //24-BIT COLORS                                           //
    ///////////////////////////////////////////////////////////

    /**
     * Creates the correct ANSI escape sequence for a 24-bit foreground color based on its rgb value.
     * @param r r value of the color.
     * @param g g value of the color.
     * @param b b value of the color.
     * @return ANSI escape sequence of the wanted 24-bit color.
     */
    public static String FG_rgbColor(int r, int g, int b){
        return "\033[38;2;" + r + ";" + g + ";" + b + "m";
    }

    /**
     * Creates the correct ANSI escape sequence for a 24-bit background color based on its rgb value.
     * @param r r value of the color.
     * @param g g value of the color.
     * @param b b value of the color.
     * @return ANSI escape sequence of the wanted 24-bit color.
     */
    public static String BG_rgbColor(int r, int g, int b){
        return "\033[48;2;" + r + ";" + g + ";" + b + "m";
    }




    ///////////////////////////////////////////////////////////
    //FONT OPTIONS                                           //
    ///////////////////////////////////////////////////////////
    /**
     * ANSI escape sequence to set the font to bold.
     */
    public static final String bold = "\033[1m";

    /**
     * ANSI escape sequence to set the font to italic.
     */
    public static final String italic = "\033[3m";

    /**
     * ANSI escape sequence to set the font to underline.
     */
    public static final String underline = "\033[4m";

    /**
     * ANSI escape sequence to set the font to crossed out.
     */
    public static final String crossedOut = "\033[9m";

    /**
     * ANSI escape sequence to set the font to not bold.
     */
    public static final String boldOff = "\033[21m";

    /**
     * ANSI escape sequence to set the font to not italic.
     */
    public static final String italicOff = "\033[23m";

    /**
     * ANSI escape sequence to set the font to not underline.
     */
    public static final String underlineOff = "\033[24m";

    /**
     * ANSI escape sequence to set the font to not crossed out.
     */
    public static final String crossedOutOff = "\033[29";
}
