package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.jetbrains.annotations.NotNull;

public class AnswerAction extends PlayAnswer{
    private final Sex workerSex;
    private final Directions direction;

    public AnswerAction(@NotNull Sex workerSex, @NotNull Directions direction){
        this.workerSex = workerSex;
        this.direction = direction;
    }

    public Directions getDirection() {
        return direction;
    }

    public Sex getWorkerSex() {
        return workerSex;
    }
}
