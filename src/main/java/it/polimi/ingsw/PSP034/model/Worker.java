package PSP034.model;

public class Worker {
    private final char sex;
    private final String name;
    private Tile myTile;

    /**Builds a worker in a non-valid tile; the player has to set the correct tile in the first turn*/
    public Worker(char sex, String name) {
        this.sex = sex;
        this.name = name;
        myTile = null;
    }

    public Tile getMyTile(){
        return myTile;
    }

    public void setMyTile(Tile tile){   //exception ?? Null?
        myTile = tile;
    }

    public char getSex() {
        return sex;
    }

    public String getOwner() {
        return name;
    }

}
