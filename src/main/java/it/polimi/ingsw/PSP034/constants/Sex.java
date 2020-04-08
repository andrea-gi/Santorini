package it.polimi.ingsw.PSP034.constants;

public enum Sex {
    MALE,
    FEMALE;

    public Sex getOppositeSex(){
        if (this == MALE)
            return FEMALE;
        else
            return MALE;
    }
}
