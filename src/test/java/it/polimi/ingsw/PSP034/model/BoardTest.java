package PSP034.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;
    Tile[][] tiles;
    @Before
    public void setUp(){
        board = new Board();
    }


    /*TODO -- exception*/
    @Test
    public void getTile_invalidInput_throwsException() {
        board.getTile(-1, 2);
    }

}