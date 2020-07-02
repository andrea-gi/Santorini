package it.polimi.ingsw.PSP034.constants;

/**
 * Stores all the directions and the correlated operations to convert the directions into offset or vice versa.
 */
public enum Directions {
    N,
    S,
    E,
    W,
    NE,
    NW,
    SE,
    SW,
    O; //ORIGIN

    /**
     * Calculates the direction corresponding to a given offset.
     *
     * @param x x offset.
     * @param y y offset.
     * @return Direction corresponding to a given offset.
     *
     * @throws IllegalArgumentException if either one between x and y is not equal to -1, 0 or 1.
     */
    public static Directions offsetToDirection(int x, int y){
        switch (x){
            case -1:
                switch (y){
                    case -1:
                        return NW;
                    case 0:
                        return W;
                    case 1:
                        return SW;
                }

            case 0:
                switch (y){
                    case -1:
                        return N;
                    case 0:
                        return O;
                    case 1:
                        return S;
                }

            case 1:
                switch (y){
                    case -1:
                        return NE;
                    case 0:
                        return E;
                    case 1:
                        return SE;
                }

        }
        throw new IllegalArgumentException("Invalid offset. Both x and y must be -1 or 0 or 1");
    }

    /**
     * Calculates the x offset corresponding to a given direction.
     *
     * @param direction Direction used to calculate x offset.
     * @return x offset.
     *
     */
    public static int directionToXOffset(Directions direction){
        switch (direction){
            case NW:
            case W:
            case SW:
                return -1;

            case N:
            case O:
            case S:
                return 0;

            case NE:
            case E:
            case SE:
                return 1;
        }

        return 9;
    }

    /**
     * Calculates the y offset corresponding to a given direction.
     *
     * @param direction Direction used to calculate y offset.
     * @return y offset.
     *
     */
    public static int directionToYOffset(Directions direction){
        switch (direction){
            case SW:
            case S:
            case SE:
                return 1;

            case W:
            case O:
            case E:
                return 0;

            case NW:
            case N:
            case NE:
                return -1;
        }

        return 9;
    }
}