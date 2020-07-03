package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.Sex;

/**
 * This enum represents al the possible action that can be required during a turn phase.
 */
public enum RequiredActions{
    REQUIRED_MALE, // you can only use your MALE worker
    REQUIRED_FEMALE, // you can only use your FEMALE worker
    REQUEST_WORKER, // you get a choice between MALE and FEMALE worker
    REQUEST_MOVE,
    REQUEST_BUILD,
    REQUEST_POWER;

    /**
     * Returns a required actions representation, base on the given sex.
     *
     * @param sex Worker sex given.
     * @return Certain required action.
     */
    public static RequiredActions getRequiredSex(Sex sex){
        if(sex == Sex.MALE)
            return REQUIRED_MALE;
        else if (sex == Sex.FEMALE)
            return REQUIRED_FEMALE;
        else
            throw new IllegalArgumentException("Sex must be MALE or FEMALE");
    }
}
