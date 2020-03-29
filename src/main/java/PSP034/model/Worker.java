package PSP034.model;

public class Worker {
    private char sex, team;     //team necessario??
    private int x, y;

    /**Builds a worker in a non-valid tile; the player has to set the correct tile in the first turn*/
    public Worker(char sex, char team) {
        this.sex = sex;
        this.team = team;
        x = 9;      //x e y non validi, prima del posizionamento da parte del player
        y = 9;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;     //exception ??? per valore sbagliato
    }
    public void setY(int y) {
        this.y = y;
    }

    public char getSex() {
        return sex;
    }

    public char getTeam() {
        return team;
    }

}
