package it.polimi.ingsw.PSP034.view.printables;


import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Sex;

import java.util.ArrayList;

public class ViewBoard extends PrintableObject {
    private static final String BG_Sea = ANSI.BG_blue;
    private static final String FG_Sea_light = ANSI.FG_bright_white;
    private static final String FG_Sea_dark = ANSI.FG_cyan;

    private static ViewTile[][] viewTiles;


    private ArrayList<String> constructionArray;

    public ViewBoard(){
        super();
        viewTiles = new ViewTile[5][5];
        for(int y = 0; y<5; y++){
            for(int x = 0; x<5; x++){
                viewTiles[x][y] = new ViewTile();
            }
        }

        constructionArray = new ArrayList<>();

        super.setObjectSize(25);
        String seaLine = BG_Sea;
        for(int x = 0; x < 60; x+=2){
            if(x%4 == 0)
                seaLine = seaLine + FG_Sea_light;
            else
                seaLine = seaLine + FG_Sea_dark;
            seaLine = seaLine + "~ ";
        }
        seaLine = seaLine + FG_Sea_light + "~" + ANSI.reset;
        String invertedSeaLine = seaLine.replace(" ", "#").replace("~", " ").replace("#", "~");

        String seaLineFragment = seaLine.substring(0, 5+BG_Sea.length()+2*FG_Sea_light.length()+FG_Sea_dark.length());
        String invertedSeaLineFragment = invertedSeaLine.substring(0, 5+BG_Sea.length()+2*FG_Sea_light.length()+FG_Sea_dark.length());


        constructionArray.add(seaLine);
        constructionArray.add(invertedSeaLine);

        constructionArray.add(seaLineFragment + ANSI.BG_green + ANSI.FG_white + "╔═════════╤═════════╤═════════╤═════════╤═════════╗" + seaLineFragment + ANSI.reset);
        for(int y = 1; y < 20; y++){
            String line = "";
            if (y%2 == 0)
                line = line + seaLineFragment;
            else
                line = line + invertedSeaLineFragment;

            if(y%4 == 0)
                line = line + ANSI.BG_green + ANSI.FG_white + "╟─────────┼─────────┼─────────┼─────────┼─────────╢";
            else
                line = line + ANSI.BG_green + ANSI.FG_white + "║         │         │         │         │         ║";

            if (y%2 == 0)
                line = line + seaLineFragment + ANSI.reset;
            else
                line = line + invertedSeaLineFragment + ANSI.reset;

            constructionArray.add(line);
        }
        constructionArray.add(seaLineFragment + ANSI.BG_green + ANSI.FG_white + "╚═════════╧═════════╧═════════╧═════════╧═════════╝" + seaLineFragment + ANSI.reset);

        constructionArray.add(invertedSeaLine);
        constructionArray.add(seaLine);


        for(int i = 0; i < constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }

    @Override
    public void print(int line, int column) {
        super.print(line, column);

        int tileStarLine = super.getStartLine() + 3;
        int tileStarColumn = super.getStartColumn() + 6;

        for(ViewTile[] tileRow : viewTiles){
            for(ViewTile tile : tileRow){
                tile.print(tileStarLine, tileStarColumn);
                tileStarColumn += 10;
            }
            tileStarColumn = super.getStartColumn() + 6;
            tileStarLine += 4;
        }
    }

    public void updateTile(int x, int y, int building, boolean hasDome, Color color, Sex sex){
        viewTiles[x][y].update(building, hasDome, color, sex);
    }

    public void showNumbers(){
        int num = 1;
        for(ViewTile[] row : viewTiles) {
            for (ViewTile tile : row) {
                if(tile.showNumber(num))
                    num++;
            }
        }
    }

    public void hideNumbers(){
        for(ViewTile[] row : viewTiles) {
            for (ViewTile tile : row) {
                tile.hideNumber();
            }
        }
    }



    //TODO -- Ddecidere static o no
    private class ViewTile extends PrintableObject {
        private int building;
        private boolean hasDome;
        private Color color;
        private Sex sex;
        private final ArrayList<String> constructionArray;

        private ViewTile(){
            super();
            building = 0;
            hasDome = false;
            color = null;
            sex = null;
            constructionArray = new ArrayList<>();

            super.setObjectSize(3);
            String baseLine = ANSI.BG_green + ANSI.FG_white + "         " + ANSI.reset;

            constructionArray.add(baseLine);
            constructionArray.add(baseLine);
            constructionArray.add(baseLine);

            super.setObjectLine(0, baseLine);
            super.setObjectLine(1, baseLine);
            super.setObjectLine(2, baseLine);
        }

        private void update(int building, boolean hasDome, Color color, Sex sex){
            this.building = building;
            this.hasDome = hasDome;
            this.color = color;
            this.sex = sex;

            String baseLine = ANSI.BG_green + ANSI.FG_white + "         " + ANSI.reset;

            constructionArray.set(0, baseLine);
            constructionArray.set(1, baseLine);
            constructionArray.set(2, baseLine);

            if(building != 0){
                constructionArray.set(0, constructionArray.get(0) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + " "+ building + "     " + building +" " + ANSI.reset);
                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + "         " + ANSI.reset);
                constructionArray.set(2, constructionArray.get(2) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + " "+ building + "     " + building +" " + ANSI.reset);
            }

            if(hasDome){
                constructionArray.set(0, constructionArray.get(0) + "\033[9D" + "\033[3C" + ANSI.BG_blue + "   " + ANSI.reset);
                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + "\033[2C" + ANSI.BG_blue + "     " + ANSI.reset);
                constructionArray.set(2, constructionArray.get(2) + "\033[9D" + "\033[3C" + ANSI.BG_blue + "   " + ANSI.reset);
            }

            if(sex != null){
                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + "\033[4C" + color.getBG_color() + sex.toString() + ANSI.reset);
            }

            super.setObjectLine(0, constructionArray.get(0));
            super.setObjectLine(1, constructionArray.get(1));
            super.setObjectLine(2, constructionArray.get(2));

            //super.print(super.getStartLine(), super.getStartColumn());
        }

        private boolean showNumber(int number){
            if(sex == null  &&  !hasDome) {
                resetTile();

                if(this.building != 0){
                    constructionArray.set(0, constructionArray.get(0) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + " "+ this.building + "     " + this.building +" " + ANSI.reset);
                    constructionArray.set(1, constructionArray.get(1) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + "         " + ANSI.reset);
                    constructionArray.set(2, constructionArray.get(2) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + " "+ this.building + "     " + this.building +" " + ANSI.reset);
                }
                String color = building != 0 ? ANSI.BG_white : ANSI.BG_green;
                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + "\033[4C" + color + number + ANSI.reset + "\033[" + (4-Integer.toString(number).length()) + "C");

                super.setObjectLine(0, constructionArray.get(0));
                super.setObjectLine(1, constructionArray.get(1));
                super.setObjectLine(2, constructionArray.get(2));

                return true;
            }else
                return false;
        }

        private void hideNumber(){
            if(sex == null  &&  !hasDome) {
                resetTile();

                if(this.building != 0){
                    constructionArray.set(0, constructionArray.get(0) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + " "+ this.building + "     " + this.building +" " + ANSI.reset);
                    constructionArray.set(1, constructionArray.get(1) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + "         " + ANSI.reset);
                    constructionArray.set(2, constructionArray.get(2) + "\033[9D" + ANSI.BG_white + ANSI.FG_blue + " "+ this.building + "     " + this.building +" " + ANSI.reset);
                }
                String color = building != 0 ? ANSI.BG_white : ANSI.BG_green;

                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + "\033[4C" + color + "  " + ANSI.reset + "\033[3C");
                super.setObjectLine(0, constructionArray.get(0));
                super.setObjectLine(1, constructionArray.get(1));
                super.setObjectLine(2, constructionArray.get(2));
            }
        }

        private void resetTile(){
            String baseLine = ANSI.BG_green + ANSI.FG_white + "         " + ANSI.reset;

            constructionArray.set(0, baseLine);
            constructionArray.set(1, baseLine);
            constructionArray.set(2, baseLine);

            super.setObjectLine(0, constructionArray.get(0));
            super.setObjectLine(1, constructionArray.get(1));
            super.setObjectLine(2, constructionArray.get(2));

            //super.print(super.getStartLine(), super.getStartColumn());
        }
    }
}



