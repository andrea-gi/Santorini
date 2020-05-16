package it.polimi.ingsw.PSP034.view.printables.godcards;

public enum GodDescription{
    APOLLO("Apollo", "Your Move: Your Worker may move into an opponent Worker's space (using normale movement rules) and force their Worker to the space yours just vacated (swappping their positions)."),
    ARTEMIS("Artemis", "Your Move: Your Worker may move one additional time, but not back to the space it started on."),
    ATHENA("Athena", "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn."),
    ATLAS("Atlas", "Your Build: Your Worker may build a dome at any level including the ground."),
    DEMETER("Demeter", "Your Build: Your Worker may build one additional time, but not on the same space."),
    HEPHAESTUS("Hephaestus", "Your Build: Your Worker may build one additional block (not dome) on top of your first block."),
    MINOTAUR("Minotaur", "Your Move: Your Worker may move into an opponent's space (using normal movement rules), if the next space in the same direction is unoccupied. Their Worker is forced into that space (regardles of its level)."),
    PAN("Pan", "Win Condition: You also win if your Worker moves down two or more levels."),
    PROMETHEUS("Prometheus", "Your Turn: If your Worker does not move up, it may build both before and after moving."),
    HESTIA("Hestia", "Your Build: Your Worker may build one additional time. The additional build cannot be on a perimeter space."),
    HERA("Hera", "Opponent's Turn: An opponent cannot win by moving on to a perimeter space."),
    LIMUS("Limus", "Opponent's Turn: Opponent Workers cannot build on spaces neighboring your Workers, unless building a dome to create a Complete Tower."),
    TRITON("Triton", "Your Move: Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again."),
    ZEUS("Zeus", "Your Build: Your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up to the third level.");


    private final String name;
    private final String power;

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
