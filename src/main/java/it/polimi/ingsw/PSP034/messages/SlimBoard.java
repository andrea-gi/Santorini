package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.model.Board;

public class SlimBoard extends ModelUpdate {
    private final boolean[][] dome;
    private final int[][] building;
    private final Color[][] color;
    private final Sex[][] sex;

    public SlimBoard(Board board){
        dome = new boolean[Constant.DIM][Constant.DIM];
        building = new int[Constant.DIM][Constant.DIM];
        color = new Color[Constant.DIM][Constant.DIM];
        sex = new Sex[Constant.DIM][Constant.DIM];

        for(int i = 0; i < Constant.DIM; i++){
            for(int j = 0; j < Constant.DIM; j++){
                building[i][j] = board.getTile(i,j).getBuilding();
                dome[i][j] = board.getTile(i,j).hasDome();
                if(board.getTile(i,j).getWorker() == null) {
                    color[i][j] = null;
                    sex[i][j] = null;
                }
                else{
                    color[i][j] = board.getTile(i,j).getWorker().getColor();
                    sex[i][j] = board.getTile(i,j).getWorker().getSex();
                }
            }
        }
    }

    public boolean[][] getDome() {
        return dome;
    }

    public Color[][] getColor() {
        return color;
    }

    public int[][] getBuilding() {
        return building;
    }

    public Sex[][] getSex() {
        return sex;
    }
}
