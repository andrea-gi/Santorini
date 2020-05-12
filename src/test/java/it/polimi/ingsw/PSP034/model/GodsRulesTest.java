package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Color;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GodsRulesTest {
    private Game myGame;
    private Board myBoard;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;

    @Before
    public void setUp() {
        myBoard = new Board();
        myGame = new Game(myBoard);
        firstPlayer = new Player("Veronica", Color.WHITE);
        secondPlayer = new Player("Andrea", Color.BLUE);
        thirdPlayer = new Player("Lorenzo", Color.GREY);
        myGame.addPlayer(firstPlayer);
        myGame.addPlayer(secondPlayer);
        myGame.addPlayer(thirdPlayer);
        myGame.setCurrentPlayerByName("Veronica");
        myGame.addWorker(firstPlayer, Sex.MALE, 0,0);
        myGame.addWorker(firstPlayer, Sex.FEMALE, 1,1);
        myGame.addWorker(secondPlayer, Sex.MALE, 2,2);
        myGame.addWorker(secondPlayer, Sex.FEMALE, 3,3);
        myGame.addWorker(thirdPlayer, Sex.MALE, 4,4);
        myGame.addWorker(thirdPlayer, Sex.FEMALE, 3,2);
    }

    //Metodo ausiliario
    private void setGods(String firstGod, String secondGod, String thirdGod){
        myGame.addGod(firstGod, firstPlayer);
        myGame.addGod(secondGod, secondPlayer);
        myGame.addGod(thirdGod, thirdPlayer);
    }

    private void setBoardBuildings(int[][] buildings){
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                myBoard.getTile(i, j).setBuilding(buildings[i][j]);
            }
        }
    }

    @Test
    public void checkBuildLost() {
    }

    @After
    public void tearDown() {
        myBoard = null;
        myGame = null;
        firstPlayer = null;
        secondPlayer = null;
        thirdPlayer = null;
    }
}