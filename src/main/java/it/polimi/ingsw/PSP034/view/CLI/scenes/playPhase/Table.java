package it.polimi.ingsw.PSP034.view.CLI.scenes.playPhase;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Directions;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * This class creates the scene to be printed when the game table has to be shown.
 */
public class Table extends Scene{
    private final VerticalArrangement all;

    private Font title;
    private final HorizontalArrangement boardANDRight;

    private final ViewBoard board;
    private final VerticalArrangement rightSide;

    private final HorizontalArrangement alignedCards;
    private final VerticalArrangement request;

    private final PlayerBox[] cards;
    private final Spacer spaceThirdCard;

    private Drawing drawing;
    private Dialog question;
    private Message message;
    private final HorizontalArrangement textBoxANDAnswer;

    private  final Spacer answerIndentation;
    private  Message answer;
    private  TextBox textBox;
    private  ArrayList<RegexCondition> regex;
    private  boolean requiredAnswer;


    /**
     * Creates the scene and organizes the objects.
     * @param gods The gods that are being used in the game.
     * @param players The names of players that are playing.
     * @param colors The colors of the players. Note that the order of the colors must be the same as the names of the players.
     */
    public Table(String[] gods, String[] players, PlayerColor[] colors){
        all = new VerticalArrangement();

        title = new Font("   ");
        boardANDRight = new HorizontalArrangement();
        all.addObjects(title, boardANDRight);
        all.setCentreAlignment();
        all.setBorder(1);

        board = new ViewBoard();
        rightSide = new VerticalArrangement();
        boardANDRight.addObjects(board, rightSide);
        boardANDRight.setTopAlignment();
        boardANDRight.setBorder(5);

        alignedCards = new HorizontalArrangement();
        request = new VerticalArrangement();
        rightSide.addObjects(alignedCards, request);
        rightSide.setCentreAlignment();
        rightSide.setBorder(3);

        cards = new PlayerBox[gods.length];
        for(int i = 0; i < gods.length; i++){
            cards[i] = new PlayerBox(players[i], gods[i],colors[i]);
        }
        spaceThirdCard = new Spacer(cards[0].getWidth()/2, 1);
        if(gods.length == 3)
            alignedCards.addObjects(cards);
        else
            alignedCards.addObjects(spaceThirdCard, cards[0], cards[1], spaceThirdCard);
        alignedCards.setTopAlignment();
        alignedCards.setBorder(1);

        message = new Message("", -1);
        message.setVisible(false);
        question = new Dialog("", -1,1, "");
        question.setVisible(false);
        textBoxANDAnswer = new HorizontalArrangement();
        textBoxANDAnswer.setVisible(false);
        request.addObjects(message, question, textBoxANDAnswer);
        request.setCentreAlignment();
        request.setBorder(1);

        answerIndentation = new Spacer(5, 1);
        answer = new Message("", -1);
        textBox = new TextBox(1);
        textBoxANDAnswer.addObjects(answerIndentation, answer, textBox);
        textBoxANDAnswer.setBottomAlignment();
        textBoxANDAnswer.setBorder(1);

        regex = null;

        requiredAnswer = false;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(all);

        if(!requiredAnswer){
            return null;
        }else{
            if(regex == null){
                textBox.waitAnswer();
                return null;
            }else{
                return textBox.waitAnswer(regex.toArray(new RegexCondition[0]));
            }
        }
    }

    /**
     * Updates the board.
     * @param dome Matrix that indicates whether a tile has a dome or not.
     * @param building Matrix that indicates the height of the building on each tile.
     * @param color Matrix that indicates the color of the worker on each tile. If a tile has no worker, the value for that tile must be {@code NULL}.
     * @param sex Matrix that indicates the sex of the worker on each tile. If a tile has no worker, the value for that tile must be {@code NULL}.
     * @param showNumbers Whether the number of each tile that has no worker nor dome must be shown or not.
     */
    public void updateBoard(boolean[][] dome, int[][] building, PlayerColor[][] color, Sex[][] sex, boolean showNumbers){
        for(int y = 0; y < Constant.DIM; y++){
            for(int x = 0; x < Constant.DIM; x++){
                board.updateTile(x, y, building[x][y], dome[x][y], color[x][y], sex[x][y]);
            }
        }

        if(showNumbers)
            board.showNumbers();
        else
            board.hideNumbers();

        requiredAnswer = false;

    }

    /**
     * Updates the scene asking the user where he/she wants to place a worker.
     * @param dome Matrix that indicates whether a tile has a dome or not.
     * @param building Matrix that indicates the height of the building on each tile.
     * @param color Matrix that indicates the color of the worker on each tile. If a tile has no worker, the value for that tile is {@code NULL}.
     * @param sex Matrix that indicates the sex of the worker on each tile. If a tile has no worker, the value for that tile is {@code NULL}.
     * @param worker The sex of the worker to be placed.
     */
    public void updatePlaceWorker(boolean[][] dome, int[][] building, PlayerColor[][] color, Sex[][] sex, Sex worker){
        setTitle("workers setup");

        updateBoard(dome, building, color, sex, true);

        int freeTiles = 0;
        for(int y = 0; y < Constant.DIM; y++){
            for(int x = 0; x < Constant.DIM; x++){
                if(!dome[x][y] &&  color[x][y]==null)
                    freeTiles++;
            }
        }

        String[] options = new String[freeTiles];
        for (int n = 1; n<=freeTiles; n++){
            options[n-1] = Integer.toString(n);
        }


        setQuestion(new Dialog("Select the tiles your worker will start from:", -1,5, options));

        String workerSex = worker.name().substring(0,1).toUpperCase() + worker.name().substring(1).toLowerCase();
        setAnswer(new Message( workerSex + " Worker :" , -1));

        regex = new ArrayList<>();
        StringBuilder rule;
        if(freeTiles <= 9)
            rule = new StringBuilder("^[1-" + freeTiles + "]$");
        else{
            rule = new StringBuilder("^([1-9]");
            int x;
            for(x = 19; x < freeTiles; x+=10){
                rule.append("|").append(x / 10).append("[0-9]");
            }
            rule.append("|").append(x / 10).append("[0-").append(freeTiles % 10).append("])$");
        }


        regex.add(new RegexCondition(rule.toString(), "Invalid selection."));
    }

    /**
     * Updates the scene communicating to the user that another player is placing workers.
     * @param playerName Name of the player that is placing workers.
     */
    public void updateOtherPlacing(String playerName){
        setTitle("Workers setup");
        setMessage(new Message(playerName + " is placing Workers", -1));
    }

    /**
     * Updates the scene asking to the user which worker he/she wants to select for the next action.
     * @param action The name of the next action.
     */
    public void updateSelectWorker(String action) {
        setQuestion(new Dialog("Select which Worker you want to " + action + ":", -1, 1, "Male (" + Sex.MALE.toString() + ")", "Female (" + Sex.FEMALE.toString() + ")"));

        setAnswer(new Message("Your choice :", -1));

        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-2]$", "Invalid selection."));
    }

    /**
     * Updates the scene asking the user where he/she wants to move the selected worker.
     * @param sex The sex of the worker to be moved.
     * @param workerX x coordinates of the starting tile.
     * @param workerY y coordinates of the starting tile.
     * @param possibleDirections List of the possible direction for the movement.
     * @param hasChoice Whether the user can change the selected worker or not.
     */
    public void updateMove(@NotNull Sex sex, int workerX, int workerY, Directions[] possibleDirections, boolean hasChoice){
        String[] options = directionsToOptions(workerX, workerY,possibleDirections, hasChoice);

        setQuestion(new Dialog("Where do you want to move your " + sex.name().toLowerCase() + " (" + sex.toString() + ") Worker?", -1, 3, options));

        setAnswer(new Message("Your move :", -1));

        regex = new ArrayList<>();
        if(options.length <= 9)
            regex.add(new RegexCondition("^[1-" + (options.length) + "]$", "Invalid selection."));
        else
            regex.add(new RegexCondition("^(([1-9])|(1(" + (options.length)%10 + ")))$", "Invalid selection."));
    }

    /**
     * Updates the scene asking the user where he/she wants to build with the selected worker.
     * @param sex The sex of the worker to build with.
     * @param workerX x coordinates of the tile of the worker.
     * @param workerY y coordinates of the tile of the worker.
     * @param possibleDirections List of the possible direction for the build.
     * @param hasChoice Whether the user can change the selected worker or not.
     */
    public void updateBuild(@NotNull Sex sex, int workerX, int workerY, Directions[] possibleDirections, boolean hasChoice){
        String[] options = directionsToOptions(workerX, workerY, possibleDirections, hasChoice);

        setQuestion(new Dialog("Where do you want to build with your " + sex.name().toLowerCase() + " (" + sex.toString() + ") Worker?", -1, 3, options));

        setAnswer(new Message("Your build :", -1));

        regex = new ArrayList<>();
        if(options.length <= 9)
            regex.add(new RegexCondition("^[1-" + (options.length) + "]$", "Invalid selection."));
        else
            regex.add(new RegexCondition("^(([1-9])|(1(" + (options.length)%10 + ")))$", "Invalid selection."));
    }

    /**
     * Updates the scene asking the user whether he/she wants to use a god power.
     */
    public void updatePower(){
        setQuestion(new Dialog("Do you want to use your god's power?", -1, 1, "Yes", "No"));
        setAnswer(new Message("Your choice :", -1));

        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-2]$", "Invalid selection."));
    }

    /**
     * Updates the scene changing the title to "your turn" and clearing any request shown.
     */
    public void updateStart(){
        setTitle("your turn");

        setEmptyRequest();
    }

    /**
     * Updates the scene communicating to the user that another player's turn is starting.
     * @param playerName Name of the player whose turn is starting.
     */
    public void updateOtherStarting(String playerName){
        PlayerColor playerColor;
        for(PlayerBox card : cards){
            if(card.getPlayerName().equals(playerName)){
                playerColor = card.getColor();
                setTitle(playerColor.getFG_color() + "$" + ANSI.reset + playerName + "'s turn" + playerColor.getFG_color() + "&" + ANSI.reset);
            }
        }
        setEmptyRequest();
    }

    /**
     * Updates the scene changing the title to "your turn ended" and clearing any request shown.
     */
    public void updateEnd(){
        setTitle("Your turn ended");
        setEmptyRequest();
    }

    /**
     * Updates the scene communicating to the user that he/she lost. If another player won the winning player's name is communicate too.
     * @param winnerName Name of the winning player. If there is no winner this value must be {@code NULL}.
     * @param losersNames Name of the losing players.
     * @throws NullPointerException If the losing player or the winning player cannot be found an exception is thrown.
     */
    public void updateDefeat(@NotNull String winnerName, @NotNull String[] losersNames) throws NullPointerException{
        if(winnerName.equals("")){
            PlayerColor loserColor = null;
            for(PlayerBox card : cards){
                if(card.getPlayerName().equals(losersNames[0])){
                    loserColor = card.getColor();
                    PlayerBox newCard = new PlayerBox(card.getPlayerName(), card.getGodName(), null);
                    changeCard(card, newCard);
                }
            }

            LoserDrawing loserDrawing;
            if(loserColor != null)
                loserDrawing = new LoserDrawing(loserColor);
            else
                throw new NullPointerException(losersNames[0] + "can't loose as there's no such player still playing");

            setDrawing(loserDrawing);
            setMessage(new Message("Oh, no! You lost...", -1));
            setQuestion(new Dialog("Do you want to exit the game or keep watching?", -1, 1, "Exit", "Keep watching"));

        }else{
            PlayerColor[] loserColors = new PlayerColor[losersNames.length];
            PlayerColor winnerColor = null;
            for(PlayerBox card : cards){
                if(card.getPlayerName().equals(winnerName))
                    winnerColor = card.getColor();
                else{
                    for(int i = 0; i < losersNames.length; i++){
                        if(card.getPlayerName().equals(losersNames[i]))
                            loserColors[i] = card.getColor();
                    }
                }
            }

            for(int i = 0; i < loserColors.length; i++){
                if(loserColors[i] == null)
                    throw new NullPointerException(losersNames[i] + "can't loose as there's no such player still playing");
            }
            if(winnerColor == null)
                throw new NullPointerException(winnerName + "can't win as there's no such player still playing");


            setDrawing(new OtherWinnerDrawing(winnerColor, loserColors));
            setMessage(new Message("Oh, no! " + winnerName + " won...", -1));
            setQuestion(new Dialog("Do you want to exit the game or play again?", -1, 1, "Exit", "Play again"));

        }
        message.setVisible(true);
        setAnswer(new Message("Your choice", -1));
        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-2]$", "Invalid selection"));
    }

    /**
     * Updates the scene communicating to the user that another player lost and removing the color from the losing player card.
     * @param loser Name of the losing player.
     * @throws NullPointerException If the losing player cannot be found an exception is thrown.
     */
    public void updateRemovePlayer(String loser) throws NullPointerException{
        boolean loserExists = false;
        for(PlayerBox card : cards){
            if(card.getPlayerName().equals(loser)){
                PlayerBox newCard = new PlayerBox(card.getPlayerName(), card.getGodName(), null);
                changeCard(card, newCard);
                loserExists = true;
                break;
            }
        }
        if(!loserExists)
            throw new NullPointerException(loser + " can't loose as there is no such player still playing");

        setMessage(new Message(loser + " lost! You are one step closer to victory!", -1));

        setAnswer(new Message("Enter to continue", -1));
        regex = null;
    }

    /**
     * Updates the scene communicating to the user that he/she won.
     * @param winnerName Name of the winning player.
     * @throws NullPointerException If the winning player cannot be found an exception is thrown.
     */
    public void updateWin(String winnerName) throws NullPointerException{
        PlayerColor winnerColor = null;
        for(PlayerBox card : cards){
            if(card.getPlayerName().equals(winnerName)){
                winnerColor = card.getColor();
            }
        }
        if(winnerColor == null)
            throw new NullPointerException(winnerName + " can't win as there is no such player still playing");


        setDrawing(new WinnerDrawing(winnerColor));
        setMessage(new Message("YAY! YOU WIN!", -1));
        setQuestion(new Dialog("Do you want to exit the game or play again?", -1, 1, "Exit", "Play again"));
        message.setVisible(true);

        setAnswer(new Message("Your choice", -1));
        regex = new ArrayList<>();
        regex.add(new RegexCondition("^[1-2]$", "Invalid selection"));
    }

    /**
     * Updates the scene clearing any request shown.
     */
    public void updateClearRequest(){
        setEmptyRequest();
    }



    private void setTitle(String newTitle){
        int pos = all.getObjects().indexOf(title);
        all.removeObjects(title);
        title = new Font(newTitle);
        all.insertObject(pos, title);
    }

    private void setQuestion(Dialog newQuestion){
        int pos = request.getObjects().indexOf(question);
        request.removeObjects(question);
        question = newQuestion;
        question.setVisible(true);
        request.insertObject(pos, question);
        message.setVisible(false);

        textBoxANDAnswer.setVisible(false);
        requiredAnswer = false;
    }

    private void setMessage(Message newMessage){
        int pos = request.getObjects().indexOf(message);
        request.removeObjects(message);
        message = newMessage;
        message.setVisible(true);
        request.insertObject(pos, message);
        question.setVisible(false);

        textBoxANDAnswer.setVisible(false);
        requiredAnswer = false;
    }

    private void setEmptyRequest(){
        message.setVisible(false);
        question.setVisible(false);

        textBoxANDAnswer.setVisible(false);

        requiredAnswer = false;
    }

    private void setAnswer(Message newAnswer){
        int pos = textBoxANDAnswer.getObjects().indexOf(answer);
        textBoxANDAnswer.removeObjects(answer);
        answer = newAnswer;
        textBoxANDAnswer.insertObject(pos, answer);

        pos = textBoxANDAnswer.getObjects().indexOf(textBox);
        textBoxANDAnswer.removeObjects(textBox);
        textBox = new TextBox(question.getWidth()-answerIndentation.getWidth()-answer.getWidth());
        textBoxANDAnswer.insertObject(pos, textBox);
        textBoxANDAnswer.setVisible(true);

        requiredAnswer = true;
    }

    private void setDrawing(Drawing newDrawing){
        int pos;
        if(drawing == null)
            pos = request.getObjects().indexOf(message);
        else {
            pos = request.getObjects().indexOf(drawing);
            request.removeObjects(drawing);
        }

        drawing = newDrawing;
        drawing.setVisible(true);
        request.insertObject(pos, drawing);
    }

    private String @NotNull [] directionsToOptions( int workerX, int workerY, Directions[] possibleDirections, boolean hasChoice) {
        String[] options;
        if(hasChoice) {
            options = new String[possibleDirections.length + 1];
            for (int i = 0; i < possibleDirections.length; i++) {
                String option = "(" + (char)(workerY + Directions.directionToYOffset(possibleDirections[i]) + 65);
                option += "," + (workerX + Directions.directionToXOffset(possibleDirections[i]) + 1) + ")";

                options[i] = option;
            }
            options[options.length - 1] = "Back";
        }else{
            options = new String[possibleDirections.length];
            for (int i = 0; i < possibleDirections.length; i++) {
                String option = "(" + (char)(workerY + Directions.directionToYOffset(possibleDirections[i]) + 65);
                option += "," + (workerX + Directions.directionToXOffset(possibleDirections[i]) + 1) + ")";

                options[i] = option;
            }
        }
        return options;
    }

    private void changeCard(PlayerBox oldCard, PlayerBox newCard){
        int pos = alignedCards.getObjects().indexOf(oldCard);
        alignedCards.removeObjects(oldCard);
        oldCard = newCard;
        alignedCards.insertObject(pos, oldCard);
    }
}
