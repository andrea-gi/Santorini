package PSP034.model;

public class Board {
    private Tile[][] tiles;

    public Board(){
        tiles  = new Tile[5][5];
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }
}
