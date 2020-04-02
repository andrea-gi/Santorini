package it.polimi.ingsw.PSP034.model;

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

    @Test
    public void getTile_invalidXInput_nullOutput(){
        assertNull(board.getTile(-1, 2));
    }

    @Test
    public void getTile_invalidYInput_nullOutput(){
        assertNull(board.getTile(1, -2));
    }
}