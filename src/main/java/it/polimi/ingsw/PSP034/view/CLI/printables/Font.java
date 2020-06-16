package it.polimi.ingsw.PSP034.view.CLI.printables;

import java.util.ArrayList;

/**
 * A Font object represents a string written with a special "font" created using the UNICODE box drawing characters.
 */
public class Font extends PrintableObject{
    private final ArrayList<String[]> formattedString;
    private final String text;
    private String color;

    /**
     *Initializes a Font object
     * @param string The string to be converted in the new font.
     */
    public Font(String string){
        super();
        text = string;
        color = ANSI.reset;
        formattedString = new ArrayList<>();
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == '\033'){
                int start = i;
                while(string.charAt(i) != 'm') i++;
                color = text.substring(start, i+1);
            }else{
                formatCharacter(string.charAt(i));
            }
        }
        super.setObjectSize(3);
        for(int line = 0; line < 3; line++){
            StringBuilder text = new StringBuilder();
            for(String[] formattedLetter : formattedString){
                text.append(" ").append(formattedLetter[line]);
            }
            super.setObjectLine(line, text.toString());
        }
    }

    public String getText(){
        return text;
    }

    /**
     * This function generates the Font symbol for the symbol given in input. If there is no Font symbol for a given symbol a 3x4 rectangle of '#' will be created instead.
     * @param letter the symbol to be converted.
     */
    private void formatCharacter(char letter){
        String[] formattedCharacter;
        switch(letter){
            case 'A':
            case 'a':
                formattedCharacter = new String[]{"╔══╗","╠══╣","╩  ╩"};
                break;
            case 'B':
            case 'b':
                formattedCharacter = new String[]{"╔═╗ ","╠═╩╗","╚══╝"};
                break;
            case 'C':
            case 'c':
                formattedCharacter = new String[]{"╔══╗","║   ","╚══╝"};
                break;
            case 'D':
            case 'd':
                formattedCharacter = new String[]{"╔═ ","║ ║","╚═ "};
                break;
            case 'E':
            case 'e':
                formattedCharacter = new String[]{"╔══","╠═ ","╚══"};
                break;
            case 'F':
            case 'f':
                formattedCharacter = new String[]{"╔══","╠═ ","╩  "};
                break;
            case 'G':
            case 'g':
                formattedCharacter = new String[]{"╔══╗","║  ╦","╚══╝"};
                break;
            case 'H':
            case 'h':
                formattedCharacter = new String[]{"╦  ╦","╠══╣","╩  ╩"};
                break;
            case 'I':
            case 'i':
                formattedCharacter = new String[]{"╦","║","╩"};
                break;
            case 'J':
            case 'j':
                formattedCharacter = new String[]{" ═╦","╦ ║","╚═╝"};
                break;
            case 'K':
            case 'k':
                formattedCharacter = new String[]{"╦ ╔","╠╣ ","╩ ╚"};
                break;
            case 'L':
            case 'l':
                formattedCharacter = new String[]{"╦  ","║  ","╚══"};
                break;
            case 'M':
            case 'm':
                formattedCharacter = new String[]{"╔╗╔╗","║╚╝║","╩  ╩"};
                break;
            case 'N':
            case 'n':
                formattedCharacter = new String[]{"╔╗╦","║║║","╩╚╝"};
                break;
            case 'O':
            case 'o':
                formattedCharacter = new String[]{"╔══╗","║  ║","╚══╝"};
                break;
            case 'P':
            case 'p':
                formattedCharacter = new String[]{"╔══╗","╠══╝","╩   "};
                break;
            case 'Q':
            case 'q':
                formattedCharacter = new String[]{"╔══╗","║  ║","╚══╣"};
                break;
            case 'R':
            case 'r':
                formattedCharacter = new String[]{"╔══╗","╠═╦╝","╩ ╩ "};
                break;
            case 'S':
            case 's':
                formattedCharacter = new String[]{"╔═══","╚══╗","═══╝"};
                break;
            case 'T':
            case 't':
                formattedCharacter = new String[]{"═╦═"," ║ "," ╩ "};
                break;
            case 'U':
            case 'u':
                formattedCharacter = new String[]{"╦  ╦","║  ║","╚══╝"};
                break;
            case 'V':
            case 'v':
                formattedCharacter = new String[]{"╦  ╦","╚╗╔╝"," ╚╝ "};
                break;
            case 'W':
            case 'w':
                formattedCharacter = new String[]{"╦   ╦","║ ╦ ║","╚═╩═╝"};
                break;
            case 'Y':
            case 'y':
                formattedCharacter = new String[]{"╦ ╦","╚╦╝"," ╩ "};
                break;
            case 'X':
            case 'x':
                formattedCharacter = new String[]{"╗ ╔"," ╬ ","╝ ╚"};
                break;
            case 'Z':
            case 'z':
                formattedCharacter = new String[]{"═══╗","╔══╝","╚═══"};
                break;
            case '0':
                formattedCharacter = new String[]{"╔═╗","║ ║","╚═╝"};
                break;
            case '1':
                formattedCharacter = new String[]{"╔╗ "," ║ ","═╩═"};
                break;
            case '2':
                formattedCharacter = new String[]{"╔═╗","╔═╝","╚══"};
                break;
            case '3':
                formattedCharacter = new String[]{"══╗"," ═╣","══╝"};
                break;
            case '4':
                formattedCharacter = new String[]{"╦ ╦","╚═╣","  ╩"};
                break;
            case '5':
                formattedCharacter = new String[]{"╔═╗","╚═╗","══╝"};
                break;
            case '6':
                formattedCharacter = new String[]{"╔═╗","╠═╗","╚═╝"};
                break;
            case '7':
                formattedCharacter = new String[]{"╔═╗","  ║","  ╩"};
                break;
            case '8':
                formattedCharacter = new String[]{"╔═╗","╠═╣","╚═╝"};
                break;
            case '9':
                formattedCharacter = new String[]{"╔═╗","╚═╣","══╝"};
                break;
            case '!':
                formattedCharacter = new String[]{"║","║","╦"};
                break;
            case '?':
                formattedCharacter = new String[]{"╔═╗"," ╔╝"," ╦ "};
                break;
            case '\'':
                formattedCharacter = new String[]{"╝"," "," "};
                break;
            case ',':
                formattedCharacter = new String[]{" "," ","╗"};
                break;
            case '.':
                formattedCharacter = new String[]{" "," ","╦"};
                break;
            case '_':
                formattedCharacter = new String[]{"  ","  ","══"};
                break;
            case ' ':
                formattedCharacter = new String[]{"  ","  ","  "};
                break;
            case '$':
                formattedCharacter = new String[]{"       ","╔═╗╔══╗","╚══╝╚═╝"};
                break;
            case '&':
                formattedCharacter = new String[]{"       ","╔══╗╔═╗","╚═╝╚══╝"};
                break;
            default:
                formattedCharacter = new String[]{"####","####","####"};
                break;
        }
        formattedCharacter[0] = color + formattedCharacter[0];
        formattedCharacter[1] = color + formattedCharacter[1];
        formattedCharacter[2] = color + formattedCharacter[2];
        formattedString.add(formattedCharacter);
    }
}
