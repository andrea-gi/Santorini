package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Directions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TileTest {
    private Board board;
    private Tile myTile;
    private Tile destinationTile;
    private boolean verified;

    @Before
    public void setUp(){
        board = new Board();
    }

    @Test
    public void isNeighbouringTileTrue() {
        myTile = board.getTile(2,3);
        destinationTile = board.getTile(3,3);
        verified = myTile.isNeighbouringTile(destinationTile);
        assertTrue(verified);
    }

    @Test
    public void isNeighbouringTileFalse() {
        myTile = board.getTile(2,3);
        destinationTile = board.getTile(4,1);
        verified = myTile.isNeighbouringTile(destinationTile);
        assertFalse(verified);
    }

    @Test
    public void getCompleteNeighbouringTiles() {
        myTile = board.getTile(2,3);
        ArrayList<Tile> myNeighbors = new ArrayList<>();
        myNeighbors.add(board.getTile(2,4));
        myNeighbors.add(board.getTile(2,3));
        myNeighbors.add(board.getTile(2,2));
        myNeighbors.add(board.getTile(1,4));
        myNeighbors.add(board.getTile(1, 3));
        myNeighbors.add(board.getTile(1,2));
        myNeighbors.add(board.getTile(3,4));
        myNeighbors.add(board.getTile(3,3));
        myNeighbors.add(board.getTile(3,2));
        assertTrue(myTile.getNeighbouringTiles().containsAll(myNeighbors) && myNeighbors.containsAll(myTile.getNeighbouringTiles()));
    }

    @Test
    public void getBorderNeighbouringTiles() {
        myTile = board.getTile(0,0);
        ArrayList<Tile> myNeighbors = new ArrayList<>();
        myNeighbors.add(board.getTile(0,1));
        myNeighbors.add(board.getTile(1,1));
        myNeighbors.add(board.getTile(1,0));
        myNeighbors.add(board.getTile(0,0));
        assertTrue(myTile.getNeighbouringTiles().containsAll(myNeighbors) && myNeighbors.containsAll(myTile.getNeighbouringTiles()));
    }

    @Test
    public void getNextTileSameDirection() {
        myTile = board.getTile(0,0);
        destinationTile = board.getTile(1,1);
        assertEquals(myTile.getNextTileSameDirection(destinationTile), board.getTile(2,2));
    }

    @Test
    public void existsTileSameDirectionTrue() {
        myTile = board.getTile(0,0);
        destinationTile = board.getTile(1,1);
        assertTrue(myTile.existsTileSameDirection(destinationTile));
    }

    @Test
    public void existsTileSameDirectionFalse() {
        myTile = board.getTile(0,1);
        destinationTile = board.getTile(0,0);
        assertFalse(myTile.existsTileSameDirection(destinationTile));
    }

    @Test
    public void validCoordinatesTrue() {
        assertTrue(Tile.validCoordinates(3,0));
    }

    @Test
    public void validCoordinatesFalse() {
        assertFalse(Tile.validCoordinates(5,0));
    }

    @Test
    public void isPerimeterTrue() {
        myTile = board.getTile(0,3);
        assertTrue(myTile.isPerimeter());
    }

    @Test
    public void isPerimeterFalse() {
        myTile = board.getTile(2,3);
        assertFalse(myTile.isPerimeter());
    }

    @Test
    public void directionCalculator() {
        myTile = board.getTile(2,2);
        destinationTile = board.getTile(2,3);
        Directions direction = myTile.directionCalculator(destinationTile);
        assertEquals(direction, Directions.N);
    }

    @Test
    public void neighbouringTileByDirection() {
        myTile = board.getTile(2,2);
        destinationTile = myTile.neighbouringTileByDirection(Directions.E);
        assertEquals(destinationTile, board.getTile(3,2));
    }
}