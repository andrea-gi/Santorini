package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.model.Board;

/**
 * Main model update. Contains a model snapshot at a given moment.
 */
public class SlimBoard extends ModelUpdate {
    static final long serialVersionUID = 357212198304L;
    
    private final String currentPlayer;
    private final String[] godsList;
    private final String[] playersList;
    private final PlayerColor[] colorsList;
    private final boolean[][] dome;
    private final int[][] building;
    private final PlayerColor[][] color;
    private final Sex[][] sex;

    /**
     * Fully constructs a new SlimBoard.
     * @param board         Reference to the real board.
     * @param currentPlayer Current player name.
     * @param godsList      List of active gods.
     * @param playersList   List of active players.
     * @param colorsList    List of active players' colors.
     */
    public SlimBoard(Board board, String currentPlayer, String[] godsList, String[] playersList, PlayerColor[] colorsList){
        this.currentPlayer = currentPlayer;
        this.colorsList = colorsList;
        this.godsList = godsList;
        this.playersList = playersList;
        dome = new boolean[Constant.DIM][Constant.DIM];
        building = new int[Constant.DIM][Constant.DIM];
        color = new PlayerColor[Constant.DIM][Constant.DIM];
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

    /**
     * Returns boolean copy of domes on the Board.
     *
     * @return Two-dimensional array representing domes on the Board.
     */
    public boolean[][] getDome() {
        return dome;
    }

    /**
     * Returns PlayerColor copy of domes on the Board.
     *
     * @return Two-dimensional array representing PlayerColor on the Board.
     * Single element is {@code null} if no worker is present on the corresponding tile.
     */
    public PlayerColor[][] getColor() {
        return color;
    }

    /**
     * Returns integer copy of domes on the Board.
     *
     * @return Two-dimensional array representing building levels on the Board.
     */
    public int[][] getBuilding() {
        return building;
    }

    /**
     * Returns Sex copy of domes on the Board.
     *
     * @return Two-dimensional array representing Sex on the Board.
     * Single element is {@code null} if no worker is present on the corresponding tile.
     */
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

    public PlayerColor[] getColorsList() {
        return colorsList;
    }
}
