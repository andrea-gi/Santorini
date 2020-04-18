package it.polimi.ingsw.PSP034.messages;

import it.polimi.ingsw.PSP034.constants.Sex;

public enum RequiredActions {
    REQUIRED_MALE, // you can only use your MALE worker
    REQUIRED_FEMALE, // you can only use your FEMALE worker
    REQUEST_WORKER, // you get a choice between MALE and FEMALE worker
    REQUEST_MOVE,
    REQUEST_BUILD,
    REQUEST_POWER;

    public static RequiredActions getRequiredSex(Sex sex){
        if(sex == Sex.MALE)
            return REQUIRED_MALE;
        else if (sex == Sex.FEMALE)
            return REQUIRED_FEMALE;
        else
            throw new IllegalArgumentException("Sex must be MALE or FEMALE");
    };
}
