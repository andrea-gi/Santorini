package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.model.gods.*;
import it.polimi.ingsw.PSP034.observer.ModelObservable;
import it.polimi.ingsw.PSP034.observer.ModelObserver;
import it.polimi.ingsw.PSP034.server.ServerLogger;
import it.polimi.ingsw.PSP034.view.CLI.printables.ANSI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Contains the needed information on the state of the game. Works as the main entry point for the {@link it.polimi.ingsw.PSP034.controller.Controller}.
 */
public class Game extends ModelObservable {
    private final Board board;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private GamePhase gamePhase;
    private IRules rules;
    private String[] godsList = {""};
    private final ArrayList<String> remainingGods;
    private final ArrayList<String> alreadyChosenGods;
    private String eliminatedPlayerName = "";

    private final String[] godsNames = {"Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur",
        "Pan", "Prometheus", "Hera", "Hestia", "Zeus", "Triton", "Limus"};

    /**
     * Creates a new Game class, instantiating a new Board and a structure for Players. 
     * At the time of instantiation, there is no currentPlayer, which has to bet set using {@link Game#setCurrentPlayer(Player)}
     *
     * @param board Reference to the board to be used throughout the game.
     */
    public Game(Board board){
        super();
        this.players = new ArrayList<>();
        this.board = board;
        this.currentPlayer = null;
        this.gamePhase = GamePhase.SETUP;
        this.rules = new DefaultRules(this);
        this.remainingGods = new ArrayList<>();
        this.alreadyChosenGods = new ArrayList<>();
    }

    @Override
    public void addObserver(@NotNull ModelObserver observer) {
        super.addObserver(observer);
    }

    @Override
    public void notifyObservers(ModelUpdate message) {
        super.notifyObservers(message);
    }

    @Override
    public void removeObserver(@NotNull ModelObserver observer) {
        super.removeObserver(observer);
    }

    public ArrayList<String> getRemainingGods() {
        return new ArrayList<>(this.remainingGods);
    }

    public ArrayList<String> getAlreadyChosenGods() {
        return new ArrayList<>(this.alreadyChosenGods);
    }

    public void addRemainingGod(String god){
        if (god == null || god.length() == 0){
            return;
        }
        ArrayList<String> names = new ArrayList<>(Arrays.asList(godsNames));
        if (names.contains(god)) {
            this.remainingGods.add(god);
            if (remainingGods.size() == players.size()) {
                godsList = remainingGods.toArray(new String[0]);
            }
        }
    }

    public Player loser(){
        for(Player player : players){
            if(player.hasLost())
                return player;
        }
        return null;
    }

    public SlimBoard generateSlimBoard(){
        int playersSize = players.size();
        String[] finalGodsList = new String[playersSize];
        String[] playersList = new String[playersSize];
        PlayerColor[] colorsList = new PlayerColor[playersSize];
        for (int i = 0; i < playersSize; i++) {
            playersList[i] = players.get(i).getName();
            colorsList[i] = players.get(i).getColor();
            if (remainingGods.size() == 0){
                if (players.get(i).getMyGod() != null) //Gods must be assigned
                    finalGodsList[i] = players.get(i).getMyGod().getClass().getSimpleName();
            }
        }
        if (remainingGods.size() > 0)
        {
            finalGodsList = godsList.clone();
        }
        return new SlimBoard(board, currentPlayer.getName(), finalGodsList, playersList, colorsList);
    }

    /**
     * @return Number of players currently in the game
     */
    public int getPlayerNumber(){
        return players.size();
    }

    private void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public void setCurrentPlayerByName(String name){
        Player player = getPlayerByName(name);
        if (player != null)
            setCurrentPlayer(player);
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public ArrayList<String> getPlayersName(){
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }
        return names;
    }

    /**
     * Adds a Player to the structure of active players.
     * @param player - Existing player to be added to the game.
     */
    public void addPlayer(Player player){
        if(player == null)
            throw new IllegalArgumentException("Added player cannot be null!");
        players.add(player);
        if (players.size() == 1){
            setCurrentPlayer(player);
        }
    }

    public void addWorker(Player player, Sex sex, int x, int y){
        player.addWorker(sex, board.getTile(x,y));
        notifyObservers(generateSlimBoard());
    }

    /**
     * @param name Name of the player to search
     * @return Reference to the player with that given name
     */
    private Player getPlayerByName(String name){
        for (Player player : players){
            if(player.getName().equals(name))
                return player;
        }
        return null;
    }

    /**
     * Sets the next player among the data structure of players, which is already in the right turn order.
     * */
    public void setNextPlayer(){
        int index = players.indexOf(currentPlayer);
        int nextIndex = ((index + 1) % players.size());
        setCurrentPlayer(players.get(nextIndex));
        ServerLogger.getInstance().printString(ANSI.underline + "Changed current player to: " + currentPlayer.getColor().getFG_color()+
                currentPlayer.getName() + ANSI.reset);
    }

    public void setRandomPlayer(){
        Random randomGen = new Random();
        int index = randomGen.nextInt(getPlayerNumber());
        setCurrentPlayer(players.get(index));
    }

    public void removePlayer(Player player){
        if(player == currentPlayer)
            setNextPlayer();
        eliminatedPlayerName = player.getName();
        player.getWorker(Sex.MALE).getMyTile().setWorker(null);
        player.getWorker(Sex.FEMALE).getMyTile().setWorker(null);
        removeGod(player);
        players.remove(player);
        notifyObservers(generateSlimBoard()); // Notify all model observers
    }

    public String getEliminatedPlayerName() {
        return eliminatedPlayerName;
    }

    public void setGamePhase(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase(){
        return gamePhase;
    }

    /**Decorates the turn with the gods, already in order
     * @param name      Name of the god
     * @param player    Reference to the player the god must be attached to.
     */
    public void addGod(String name, Player player){
        switch (name){
            //SIMPLE GODS
            case "Apollo":
                rules = new Apollo(rules, player);
                break;
            case "Artemis":
                rules = new Artemis(rules, player);
                break;
            case "Athena":
                rules = new Athena(rules, player);
                break;
            case "Atlas":
                rules = new Atlas(rules, player);
                break;
            case "Demeter":
                rules = new Demeter(rules, player);
                break;
            case "Hephaestus":
                rules = new Hephaestus(rules, player);
                break;
            case "Minotaur":
                rules = new Minotaur(rules, player);
                break;
            case "Pan":
                rules = new Pan(rules, player);
                break;
            case "Prometheus":
                rules = new Prometheus(rules, player);
                break;

            //ADVANCED GODS
            case "Hera":
                rules = new Hera(rules, player);
                break;
            case "Hestia":
                rules = new Hestia(rules, player);
                break;
            case "Zeus":
                rules = new Zeus(rules, player);
                break;
            case "Triton":
                rules = new Triton(rules, player);
                break;
            case "Limus":
                rules = new Limus(rules, player);
                break;
        }
        player.setMyGod((GodsRules) rules);
        int index;
        for(index = 0; index < remainingGods.size(); index++) {
            if (name.equals(remainingGods.get(index))){
                break;
            }
        }
        this.remainingGods.remove(index);
        this.alreadyChosenGods.add(name);
    }

    private void removeGod(Player player){
        GodsRules current = (GodsRules) rules;
        GodsRules previous = current;
        int godNumber = 0;

        while(godNumber < players.size() - 1) {
            if (current.getPlayer() == player)
                break;
            else {
                previous = current;
                current = (GodsRules) current.getDecoratedRules();
                godNumber++;
            }
        }

        if(godNumber == 0){
            rules = ((GodsRules) rules).getDecoratedRules();
            GodsRules.setCompleteRules((GodsRules) rules);
        }
        else {
            previous.setDecoratedRules(current.getDecoratedRules());
        }
    }
}
