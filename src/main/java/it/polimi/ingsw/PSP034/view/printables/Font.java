package it.polimi.ingsw.PSP034.view.printables;

import java.util.ArrayList;

/**
 * A Font object represents a string written with a special "font" created using the UNICODE box drawing characters.
 */
public class Font extends PrintableObject{
    private final ArrayList<String[]> formattedString;

    /**
     *Initializes a Font object
     * @param string The string to be converted in the new font.
     */
    public Font(String string){
        super();
        formattedString = new ArrayList<>();
        for(int i = 0; i < string.length(); i++){
            formatCharacter(string.charAt(i));
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
                formattedString.add(formattedCharacter);
                break;
            case 'B':
            case 'b':
                formattedCharacter = new String[]{"╔═╗ ","╠═╩╗","╚══╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'C':
            case 'c':
                formattedCharacter = new String[]{"╔══╗","║   ","╚══╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'D':
            case 'd':
                formattedCharacter = new String[]{"╔═ ","║ ║","╚═ "};
                formattedString.add(formattedCharacter);
                break;
            case 'E':
            case 'e':
                formattedCharacter = new String[]{"╔══","╠═ ","╚══"};
                formattedString.add(formattedCharacter);
                break;
            case 'F':
            case 'f':
                formattedCharacter = new String[]{"╔══","╠═ ","╩  "};
                formattedString.add(formattedCharacter);
                break;
            case 'G':
            case 'g':
                formattedCharacter = new String[]{"╔══╗","║  ╦","╚══╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'H':
            case 'h':
                formattedCharacter = new String[]{"╦  ╦","╠══╣","╩  ╩"};
                formattedString.add(formattedCharacter);
                break;
            case 'I':
            case 'i':
                formattedCharacter = new String[]{"╦","║","╩"};
                formattedString.add(formattedCharacter);
                break;
            case 'J':
            case 'j':
                formattedCharacter = new String[]{" ═╦","╦ ║","╚═╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'K':
            case 'k':
                formattedCharacter = new String[]{"╦ ╔","╠╣ ","╩ ╚"};
                formattedString.add(formattedCharacter);
                break;
            case 'L':
            case 'l':
                formattedCharacter = new String[]{"╦  ","║  ","╚══"};
                formattedString.add(formattedCharacter);
                break;
            case 'M':
            case 'm':
                formattedCharacter = new String[]{"╔╗╔╗","║╚╝║","╩  ╩"};
                formattedString.add(formattedCharacter);
                break;
            case 'N':
            case 'n':
                formattedCharacter = new String[]{"╔╗╦","║║║","╩╚╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'O':
            case 'o':
                formattedCharacter = new String[]{"╔══╗","║  ║","╚══╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'P':
            case 'p':
                formattedCharacter = new String[]{"╔══╗","╠══╝","╩   "};
                formattedString.add(formattedCharacter);
                break;
            case 'Q':
            case 'q':
                formattedCharacter = new String[]{"╔══╗","║  ║","╚══╣"};
                formattedString.add(formattedCharacter);
                break;
            case 'R':
            case 'r':
                formattedCharacter = new String[]{"╔══╗","╠═╦╝","╩ ╩ "};
                formattedString.add(formattedCharacter);
                break;
            case 'S':
            case 's':
                formattedCharacter = new String[]{"╔═══","╚══╗","═══╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'T':
            case 't':
                formattedCharacter = new String[]{"═╦═"," ║ "," ╩ "};
                formattedString.add(formattedCharacter);
                break;
            case 'U':
            case 'u':
                formattedCharacter = new String[]{"╦  ╦","║  ║","╚══╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'V':
            case 'v':
                formattedCharacter = new String[]{"╦  ╦","╚╗╔╝"," ╚╝ "};
                formattedString.add(formattedCharacter);
                break;
            case 'W':
            case 'w':
                formattedCharacter = new String[]{"╦   ╦","║ ╦ ║","╚═╩═╝"};
                formattedString.add(formattedCharacter);
                break;
            case 'Y':
            case 'y':
                formattedCharacter = new String[]{"╦ ╦","╚╦╝"," ╩ "};
                formattedString.add(formattedCharacter);
                break;
            case 'X':
            case 'x':
                formattedCharacter = new String[]{"╗ ╔"," ╬ ","╝ ╚"};
                formattedString.add(formattedCharacter);
                break;
            case 'Z':
            case 'z':
                formattedCharacter = new String[]{"═══╗","╔══╝","╚═══"};
                formattedString.add(formattedCharacter);
                break;
            case '0':
                formattedCharacter = new String[]{"╔═╗","║ ║","╚═╝"};
                formattedString.add(formattedCharacter);
                break;
            case '1':
                formattedCharacter = new String[]{"╔╗ "," ║ ","═╩═"};
                formattedString.add(formattedCharacter);
                break;
            case '2':
                formattedCharacter = new String[]{"╔═╗","╔═╝","╚══"};
                formattedString.add(formattedCharacter);
                break;
            case '3':
                formattedCharacter = new String[]{"══╗"," ═╣","══╝"};
                formattedString.add(formattedCharacter);
                break;
            case '4':
                formattedCharacter = new String[]{"╦ ╦","╚═╣","  ╩"};
                formattedString.add(formattedCharacter);
                break;
            case '5':
                formattedCharacter = new String[]{"╔═╗","╚═╗","══╝"};
                formattedString.add(formattedCharacter);
                break;
            case '6':
                formattedCharacter = new String[]{"╔═╗","╠═╗","╚═╝"};
                formattedString.add(formattedCharacter);
                break;
            case '7':
                formattedCharacter = new String[]{"╔═╗","  ║","  ╩"};
                formattedString.add(formattedCharacter);
                break;
            case '8':
                formattedCharacter = new String[]{"╔═╗","╠═╣","╚═╝"};
                formattedString.add(formattedCharacter);
                break;
            case '9':
                formattedCharacter = new String[]{"╔═╗","╚═╣","══╝"};
                formattedString.add(formattedCharacter);
                break;
            case '!':
                formattedCharacter = new String[]{"║","║","╦"};
                formattedString.add(formattedCharacter);
                break;
            case '?':
                formattedCharacter = new String[]{"╔═╗"," ╔╝"," ╦ "};
                formattedString.add(formattedCharacter);
                break;
            case '\'':
                formattedCharacter = new String[]{"╝"," "," "};
                formattedString.add(formattedCharacter);
                break;
            case ',':
                formattedCharacter = new String[]{" "," ","╗"};
                formattedString.add(formattedCharacter);
                break;
            case '.':
                formattedCharacter = new String[]{" "," ","╦"};
                formattedString.add(formattedCharacter);
                break;
            case '_':
                formattedCharacter = new String[]{"  ","  ","══"};
                formattedString.add(formattedCharacter);
                break;
            case ' ':
                formattedCharacter = new String[]{"  ","  ","  "};
                formattedString.add(formattedCharacter);
                break;
            default:
                formattedCharacter = new String[]{"####","####","####"};
                formattedString.add(formattedCharacter);
                break;
        }
    }
}
