package it.polimi.ingsw.PSP034.view;

import java.util.ArrayList;

public class Font extends PrintableObject{
    private ArrayList<String[]> formattedString;

    public Font(String string){
        super();
        formattedString = new ArrayList<>();
        for(int i = 0; i < string.length(); i++){
            formatCharacter(string.charAt(i));
        }
        super.setObjectSize(3);
        for(int line = 0; line < 3; line++){
            String text = "";
            for(String[] formattedLetter : formattedString){
                text = text + " " + formattedLetter[line];
            }
            super.setObjectLine(line, text);
        }
    }

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
            case 'Z':
            case 'z':
                formattedCharacter = new String[]{"═══╗","╔══╝","╚═══"};
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
