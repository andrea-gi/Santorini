package it.polimi.ingsw.PSP034.view.CLI.printables;


import it.polimi.ingsw.PSP034.constants.Colors;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;

import java.util.ArrayList;

public class ViewBoard extends PrintableObject {
    private static final String BG_Sea = Colors.SEA_BG.get();
    private static final String FG_Sea_light = Colors.LIGHT_WAVE_FG.get();
    private static final String FG_Sea_dark = Colors.DARK_WAVE_FG.get();
    private static final String FG_Coordinates = Colors.SEA_COORDINATES_FG.get();
    private static final String BG_Grass = Colors.GRASS_BG.get();
    private static final String BG_Building = Colors.BUILDING_BG.get();

    private static ViewTile[][] viewTiles;

    private final int fragmentLength = 5;

    public ViewBoard(){
        super();
        viewTiles = new ViewTile[Constant.DIM][Constant.DIM];
        for(int y = 0; y<Constant.DIM; y++){
            for(int x = 0; x<Constant.DIM; x++){
                viewTiles[x][y] = new ViewTile();
            }
        }

        ArrayList<String> constructionArray = new ArrayList<>();

        super.setObjectSize(25);

        StringBuilder bottomSeaLine = new StringBuilder(BG_Sea);
        for(int x = 0; x < 60; x+=2){
            if(x%4 == 0)
                bottomSeaLine.append(FG_Sea_light);
            else
                bottomSeaLine.append(FG_Sea_dark);
            bottomSeaLine.append("~ ");
        }
        bottomSeaLine.append(FG_Sea_light).append("~").append(ANSI.reset);
        String bottomInvertedSeaLine = bottomSeaLine.toString().replace(" ", "#").replace("~", " ").replace("#", "~");


        //Coordinates numbers and letters are added in this section
        StringBuilder topSeaLine = new StringBuilder(bottomSeaLine + "\033[" + (fragmentLength + 10*Constant.DIM + 1) + "D" + BG_Sea + FG_Coordinates);
        StringBuilder topInvertedSeaLine = new StringBuilder(bottomInvertedSeaLine + "\033[" + (fragmentLength + 10*Constant.DIM + 1) + "D" + BG_Sea + FG_Coordinates);
        for(int num = 1; num <= Constant.DIM; num++){
            topSeaLine.append("\033[5C \033[4C");
            topInvertedSeaLine.append("\033[4C ").append(num).append(" \033[3C");
        }


        int completeFragmentLength = fragmentLength + BG_Sea.length() + 2 * FG_Sea_light.length() + FG_Sea_dark.length();
        String seaLineFragment = bottomSeaLine.substring(0, completeFragmentLength);
        String invertedSeaLineFragment = bottomInvertedSeaLine.substring(0, completeFragmentLength);


        constructionArray.add(topSeaLine.toString());
        constructionArray.add(topInvertedSeaLine.toString());

        constructionArray.add(seaLineFragment + BG_Grass + ANSI.FG_white + "╔═════════╤═════════╤═════════╤═════════╤═════════╗" + seaLineFragment + ANSI.reset);
        int coordinateNumber = 0;
        for(int y = 1; y < 20; y++){
            String line = "";
            if(y % 4 == 0){
                line = line + seaLineFragment;
            }else if(y % 2 == 0){
                coordinateNumber = y/2 - coordinateNumber;
                line = line + seaLineFragment + "\033[3D " + FG_Coordinates + (char)(coordinateNumber + 64) + " ";
            }else
                line = line + invertedSeaLineFragment + "\033[3D   ";

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

        constructionArray.add(bottomInvertedSeaLine);
        constructionArray.add(bottomSeaLine.toString());


        for(int i = 0; i < constructionArray.size(); i++){
            super.setObjectLine(i, constructionArray.get(i));
        }
    }

    @Override
    public void print(int line, int column) {
        super.print(line, column);

        if (super.getVisibility()) {
            int tileStarLine = super.getStartLine() + 3;
            int tileStarColumn = super.getStartColumn() + 6;

            for (int y = 0; y < Constant.DIM; y++) {
                for (int x = 0; x < Constant.DIM; x++){
                    viewTiles[x][y].print(tileStarLine, tileStarColumn);
                    tileStarColumn += 10;
                }
                tileStarColumn = super.getStartColumn() + 6;
                tileStarLine += 4;
            }
        }
    }

    public void updateTile(int x, int y, int building, boolean hasDome, PlayerColor color, Sex sex){
        viewTiles[x][y].update(building, hasDome, color, sex);
    }

    public void showNumbers(){
        int num = 1;
        for (int y = 0; y < Constant.DIM; y++) {
            for (int x = 0; x < Constant.DIM; x++){
                if(viewTiles[x][y].showNumber(num))
                    num++;
            }
        }
    }

    public void hideNumbers(){
        for (int y = 0; y < Constant.DIM; y++) {
            for (int x = 0; x < Constant.DIM; x++){
                viewTiles[x][y].hideNumber();
            }
        }
    }


    
    private static class ViewTile extends PrintableObject {
        private int building;
        private boolean hasDome;
        private PlayerColor color;
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

        private void update(int building, boolean hasDome, PlayerColor color, Sex sex){
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
                constructionArray.set(0, constructionArray.get(0) + "\033[9D" + "\033[3C" + ANSI.BG_blue + "   " + ANSI.reset + "\033[3C");
                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + "\033[2C" + ANSI.BG_blue + "     " + ANSI.reset + "\033[2C");
                constructionArray.set(2, constructionArray.get(2) + "\033[9D" + "\033[3C" + ANSI.BG_blue + "   " + ANSI.reset + "\033[3C");
            }

            if(sex != null){
                constructionArray.set(1, constructionArray.get(1) + "\033[9D" + "\033[3C" + this.color.getBG_color() + " " + sex.toString() + " " + ANSI.reset + "\033[3C");
            }

            super.setObjectLine(0, constructionArray.get(0));
            super.setObjectLine(1, constructionArray.get(1));
            super.setObjectLine(2, constructionArray.get(2));
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
        }
    }
}



