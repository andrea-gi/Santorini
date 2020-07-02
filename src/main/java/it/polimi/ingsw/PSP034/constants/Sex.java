package it.polimi.ingsw.PSP034.constants;

/**
 * Stores the possible sexes of the workers.
 */
public enum Sex {
    MALE,
    FEMALE;

    /**
     * Gets the opposite sex, in order to be able to pick the other worker of a player.
     * @return Opposite sex.
     */
    public Sex getOppositeSex(){
        if (this == MALE)
            return FEMALE;
        else
            return MALE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        if (this == MALE)
            return "M";
        else
            return "F";
    }
}
