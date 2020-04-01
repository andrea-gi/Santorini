package PSP034.model;

public class Tile {
    private Worker worker;
    private int building;
    private boolean dome;
    private final int x, y;

    public Tile(int x, int y){
        worker = null;
        building = 0;
        dome = false;
        this.x = x;
        this.y = y;
    }

    public Worker getWorker(){
        return worker;
    }

    public void setWorker(Worker newWorker){
        worker = newWorker;
    }
    /*TODO -- Chiedere*/
    public void removeWorker(){
        worker = null;
    }

    public int getBuilding(){
        return building;
    }

    public void setBuilding(int height){
        building = height;
    }

    public boolean hasDome(){
        return dome;
    }

    public void setDome(boolean hasDome){
        dome = hasDome;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
