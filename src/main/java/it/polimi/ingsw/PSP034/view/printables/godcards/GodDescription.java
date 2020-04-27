package it.polimi.ingsw.PSP034.view.printables.godcards;

public enum GodDescription{
    APOLLO("Apollo", "Your Move: Your Worker may move into an opponent Worker's space (using normale movement rules) and force their Worker to the space yours just vacated (swappping their positions)."),
    ARTEMIS("Artemis", "Your Move: Your Worker may move one additional time, but not back to the space it started on."),
    ATHENA("Athena", "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");

    private String name;
    private String power;

    GodDescription(String name, String power){
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }
}
