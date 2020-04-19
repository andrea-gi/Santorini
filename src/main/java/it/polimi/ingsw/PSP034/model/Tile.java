package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static it.polimi.ingsw.PSP034.constants.Constant.*;

public class Tile {
    private Worker worker;
    private int building;
    private boolean dome;
    private final int x, y;
    private static Board board;

    /**
     * Initializes a new tile with (x,y) coordinates.
     * At the moment of creation the tile has no worker (set to null), building (set to 0) nor dome (set to false).
     *
     * @param x x coordinate of the new tile
     * @param y y coordinate of the new tile
     */
    public Tile(int x, int y) {
        worker = null;
        building = Constant.GROUND;
        dome = false;
        this.x = x;
        this.y = y;
    }

    protected static void setBoard(Board board) {
        Tile.board = board;
    }

    public Worker getWorker() {
        return worker;
    }

    /**
     * Set the worker standing in this tile to newWorker.
     *
     * @param newWorker Reference to the worker to be positioned in the tile
     * @throws IllegalArgumentException If newWorker is null an exception is thrown. removeWorker should be used instead.
     */
    public void setWorker(Worker newWorker) {
        worker = newWorker;
    }

    public int getBuilding() {
        return building;
    }

    /**
     * Sets the height of the building on this tile.
     *
     * @param height the height of the building to set. height must be between 0 and 3
     * @throws IllegalArgumentException If height is not between 0 and 3 the function throws an exception
     */
    public void setBuilding(int height) throws IllegalArgumentException {
        if (Constant.GROUND <= height && height <= Constant.LEVEL_THREE)
            building = height;
        else
            throw new IllegalArgumentException(height + " is not an acceptable height.");
    }

    public boolean hasDome() {
        return dome;
    }

    public void setDome(boolean hasDome) {
        dome = hasDome;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Checks if two tiles are neighbours (but not the same).
     *
     * @param tile Reference to the tile to compare to.
     * @return true only if the given tile is neighbour.
     */
    public boolean isNeighbouringTile(Tile tile) {
        int xDistance = Math.abs(x - tile.getX());
        int yDistance = Math.abs(y - tile.getY());
        if (xDistance == 0 && yDistance == 0)
            return false;
        else
            return ((xDistance <= 1) && (yDistance <= 1));
    }

    /**
     * Finds neighbouring tiles
     *
     * @return reference to every neighbouring tile
     */
    public ArrayList<Tile> getNeighbouringTiles() {
        ArrayList<Tile> neighbouringTiles = new ArrayList<Tile>();
        for (int x = 0; x < DIM; x++) {
            for (int y = 0; y < DIM; y++) {
                if (isNeighbouringTile(board.getTile(x, y))) {
                    neighbouringTiles.add(this);
                }
            }
        }
        return neighbouringTiles;
    }

    /**
     * Using this method requires that each needed check regarding the existence of the next tile has already been performed.
     * Only finds next tile in the same direction of the given one.
     *
     * @param tile Reference to the tile to be checked. Must be a neighbouring tile.
     * @return Reference to the next tile in same direction
     * @throws IllegalArgumentException if the pre-condition is not met.
     */
    public Tile getNextTileSameDirection(Tile tile) {
        if (!existsTileSameDirection(tile))
            throw new IllegalArgumentException("There is no tile in same direction.");
        else {
            int x = sameDirectionCoordinate(tile.getX() - this.x, this.x);
            int y = sameDirectionCoordinate(tile.getY() - this.y, this.y);
            return board.getTile(x, y);
        }
    }

    /**
     * Checks if there is a valid tile next to the given one, in the same direction.
     *
     * @param tile Reference to the tile to be checked.
     * @return true if it exists a neighbouring tile in the same direction
     */
    public boolean existsTileSameDirection(Tile tile) {
        if (!this.isNeighbouringTile(tile))
            return false;

        int xDistance = tile.getX() - this.x;
        int yDistance = tile.getY() - this.y;

        int resultX = sameDirectionCoordinate(xDistance, this.x);
        int resultY = sameDirectionCoordinate(yDistance, this.y);

        return validCoordinates(resultX, resultY);

    }

    /**
     * Finds the theoretical coordinate (one-dimensional) given a tile coordinate and an offset.
     *
     * @param distance Integer representing the one-dimensional direction (offset)
     * @param base     One-dimension coordinate of a tile
     * @return Theoretical coordinate of the next tile in the direction represented by the offset, starting from the base.
     */
    private int sameDirectionCoordinate(int distance, int base) {
        switch (distance) {
            case 1:
                base = base + 2;
            case -1:
                base = base - 2;
            case 0:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + distance);
        }
        return base;
    }


    /**
     * Checks if the given coordinates represent an existing tile
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the coordinates are valid
     */
    protected static boolean validCoordinates(int x, int y) {
        return x >= 0 && x < DIM && y >= 0 && y < DIM;
    }

    public boolean isPerimeter() {
        return x == 0 || x == DIM - 1 || y == 0 || y == DIM - 1;
    }

    public Directions directionCalculator(@NotNull Tile tile) {
        int xOffset = tile.getX() - this.x;
        int yOffset = tile.getY() - this.y;

        return Directions.offsetToDirection(xOffset, yOffset);
    }

    public Tile neighbouringTileByDirection(Directions direction){
        int xOffset = Directions.directionToXOffset(direction);
        int yOffset = Directions.directionToYOffset(direction);

        return board.getTile(x + xOffset, y + yOffset );
    }
}
