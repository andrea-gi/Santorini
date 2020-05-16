package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.model.Board;

public class SlimBoard extends ModelUpdate {
    static final long serialVersionUID = 357212198304L;
    
    private final String currentPlayer;
    private final String[] godsList;
    private final String[] playersList;
    private final Color[] colorsList;
    private final boolean[][] dome;
    private final int[][] building;
    private final Color[][] color;
    private final Sex[][] sex;

    public SlimBoard(Board board, String currentPlayer, String[] godsList, String[] playersList, Color[] colorsList){
        this.currentPlayer = currentPlayer;
        this.colorsList = colorsList;
        this.godsList = godsList;
        this.playersList = playersList;
        dome = new boolean[Constant.DIM][Constant.DIM];
        building = new int[Constant.DIM][Constant.DIM];
        color = new Color[Constant.DIM][Constant.DIM];
        sex = new Sex[Constant.DIM][Constant.DIM];

        for(int i = 0; i < Constant.DIM; i++){
            for(int j = 0; j < Constant.DIM; j++){
                building[i][Constant.DIM - 1 - j] = board.getTile(i,j).getBuilding();
                dome[i][Constant.DIM - 1 - j] = board.getTile(i,j).hasDome();
                if(board.getTile(i,j).getWorker() == null) {
                    color[i][Constant.DIM - 1 - j] = null;
                    sex[i][Constant.DIM - 1 - j] = null;
                }
                else{
                    color[i][Constant.DIM - 1 - j] = board.getTile(i,j).getWorker().getColor();
                    sex[i][Constant.DIM - 1 - j] = board.getTile(i,j).getWorker().getSex();
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

    public String[] getGodsList() {
        return godsList;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String[] getPlayersList() {
        return playersList;
    }

    public Color[] getColorsList() {
        return colorsList;
    }
}
