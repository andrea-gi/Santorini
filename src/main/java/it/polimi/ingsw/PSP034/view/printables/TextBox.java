package it.polimi.ingsw.PSP034.view.printables;

import java.util.Scanner;

public class TextBox extends PrintableObject{
    public TextBox(){
        super();
        super.setObjectSize(1);
        super.setObjectLine(0, " ");
    }

    @Override
    public void print(int line, int column) {
        ANSI.moveTo(line, column);
    }

    public String waitAnswer(String regex){
        String answer = null;
        Scanner scan = new Scanner(System.in);
        if (regex != null){
            do{
                ANSI.moveTo(super.getStartLine(), super.getStartColumn());
                answer = scan.nextLine();
            }while(!answer.matches(regex));
        }else{
            try{
                System.in.read();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return answer;
    }
}
