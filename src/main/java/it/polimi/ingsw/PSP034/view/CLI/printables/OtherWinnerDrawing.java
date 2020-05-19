package it.polimi.ingsw.PSP034.view.CLI.printables;

import it.polimi.ingsw.PSP034.constants.Color;

public class OtherWinnerDrawing extends PrintableObject {
    public OtherWinnerDrawing(Color winnerColor, Color[] losersColors){
        super();

        if(losersColors == null)
            throw new NullPointerException("Can't use this drawing with no loser. Consider using WinnerDrawing");
        if(losersColors.length == 0)
            throw new IllegalArgumentException("Can't use this drawing with no loser. Consider using WinnerDrawing");
        if(winnerColor == null)
            throw new NullPointerException("Can't use this drawing with no winner. Consider using LoserDrawing");

        super.setObjectSize(9);

        String buildColor = ANSI.FG_white;
        String winner = winnerColor.getFG_color();
        StringBuilder line = new StringBuilder();

        line.append("   ").append(winner).append("\\o/             ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append("          ");
        }
        super.setObjectLine(0,line.toString());

        line = new StringBuilder();
        line.append("    ").append(winner).append("█              ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append("          ");
        }
        super.setObjectLine(1,line.toString());

        line = new StringBuilder();
        line.append("   ").append(winner).append("/ \\        ").append(losersColors[0].getFG_color()).append("o       ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(losersColors[i].getFG_color()).append("     o    ").append(ANSI.reset);
        }
        super.setObjectLine(2,line.toString());

        line = new StringBuilder();
        line.append("  ").append(buildColor).append("█████      ").append(losersColors[0].getFG_color()).append("/█\\      ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(losersColors[i].getFG_color()).append("    /█\\   ").append(ANSI.reset);
        }
        super.setObjectLine(3,line.toString());

        line = new StringBuilder();
        line.append("  ").append(buildColor).append("█████      ").append(losersColors[0].getFG_color()).append("/ \\      ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(losersColors[i].getFG_color()).append("    / \\   ").append(ANSI.reset);
        }
        super.setObjectLine(4,line.toString());

        line = new StringBuilder();
        line.append(" ").append(buildColor).append("███████   ███████ ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(buildColor).append("  ███████ ").append(ANSI.reset);
        }
        super.setObjectLine(5,line.toString());

        line = new StringBuilder();
        line.append(" ").append(buildColor).append("███████   ███████ ").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(buildColor).append("  ███████ ").append(ANSI.reset);
        }
        super.setObjectLine(6,line.toString());

        line = new StringBuilder();
        line.append(buildColor).append("█████████ █████████").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(buildColor).append(" █████████").append(ANSI.reset);
        }
        super.setObjectLine(7,line.toString());

        line = new StringBuilder();
        line.append(buildColor).append("█████████ █████████").append(ANSI.reset);
        for(int i = 1; i < losersColors.length; i++){
            line.append(buildColor).append(" █████████").append(ANSI.reset);
        }
        super.setObjectLine(8,line.toString());
    }
}
/*
0    \o/
1     █
2    / \        o         o
3   █████      /█\       /█\
4   █████      / \       / \
5  ███████   ███████   ███████
6  ███████   ███████   ███████
7 █████████ █████████ █████████
8 █████████ █████████ █████████
*/
