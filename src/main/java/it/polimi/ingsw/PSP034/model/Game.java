package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.ModelUpdate;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.model.gods.*;
import it.polimi.ingsw.PSP034.observer.ModelObservable;
import it.polimi.ingsw.PSP034.observer.ModelObserver;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game extends ModelObservable {
    private final Board board;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private GamePhase gamePhase;
    private IRules rules;
    private ArrayList<String> remainingGods;


    /**
     * Creates a new Game class, instantiating a new Board and a structure for Players. 
     * At the time of instantiation, there is no currentPlayer, which has to bet set using {@link Game#setCurrentPlayer(Player)}
     */
    public Game(){
        this.players = new ArrayList<>();
        this.board = new Board();
        this.currentPlayer = null;
        this.gamePhase = GamePhase.SETUP;
        this.rules = new DefaultRules(this);
        this.remainingGods = new ArrayList<>();
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

    public void removeRemainingGod(String god) {
        this.remainingGods.remove(god);
    }

    public void addRemainingGod(String god){
        this.remainingGods.add(god);
    }

    public IRules getRules() {
        return rules;
    }

    public void setRules(IRules rules) {
        this.rules = rules;
    }

    /**
     * @return Number of players currently in the game
     */
    public int getPlayerNumber(){
        return players.size();
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
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

    /**
     * @param name Name of the player to search
     * @return Reference to the player with that given name
     */
    public Player getPlayerByName(String name){
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
    }

    public void removePlayer(Player player){
        if(player == currentPlayer)
            setNextPlayer();
        player.getWorker(Sex.MALE).getMyTile().setWorker(null);
        player.getWorker(Sex.FEMALE).getMyTile().setWorker(null);
        //TODO -- chiudere la connessione e cose
        players.remove(player);
        notifyObservers(new SlimBoard(board)); // Notify all model observers
    }


    public Board getBoard(){
        return board;
    }

    public void setGamePhase(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase(){
        return gamePhase;
    }

    /**Decorates the turn with the gods, already in order
     * @param name is the name of the god*/
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
            case "Ephaestus":
                rules = new Ephaestus(rules, player);
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
    }

    public void removeGod(String playerName){
        if(getPlayerByName(playerName) == null)
            return;

        GodsRules current = (GodsRules) rules;
        GodsRules previous = current;
        int godNumber = 0;

        while(godNumber < players.size() - 1) {
            if (current.getPlayer().getName().equals(playerName))
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
