package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import org.jetbrains.annotations.NotNull;

/**
 * Message from the client to the server that communicates the action chosen by the current player, in response to a RequestAction.
 */
public class AnswerAction extends PlayAnswer{
    static final long serialVersionUID = 491908580L;

    private final Sex workerSex;
    private final Directions direction;

    /**
     * Initializes the message.
     * @param workerSex Sex of the Worker that is executing the action.
     * @param direction Direction ot where to execute the action (tile to move to or to build over).
     */
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
