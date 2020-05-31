package it.polimi.ingsw.PSP034.view.GUI;

public enum GodPath {
    APOLLO("/images/gods/Apollo.png", "/images/powers/Apollo.png"),
    ARTEMIS("/images/gods/Artemis.png", "/images/powers/Artemis.png"),
    ATHENA("/images/gods/Athena.png", "/images/powers/Athena.png"),
    ATLAS("/images/gods/Atlas.png", "/images/powers/Atlas.png"),
    DEMETER("/images/gods/Demeter.png", "/images/powers/Demeter.png"),
    HEPHAESTUS("/images/gods/Hephaestus.png", "/images/powers/Hephaestus.png"),
    HERA("/images/gods/Hera.png", "/images/powers/Hera.png"),
    HESTIA("/images/gods/Hestia.png", "/images/powers/Hestia.png"),
    LIMUS("/images/gods/Limus.png", "/images/powers/Limus.png"),
    MINOTAUR("/images/gods/Minotaur.png", "/images/powers/Minotaur.png"),
    PAN("/images/gods/Pan.png", "/images/powers/Pan.png"),
    PROMETHEUS("/images/gods/Prometheus.png", "/images/powers/Prometheus.png"),
    TRITON("/images/gods/Triton.png", "/images/powers/Triton.png"),
    ZEUS("/images/gods/Zeus.png", "/images/powers/Zeus.png");

    private final String normalPath;
    private final String power;

    GodPath(String normalPath, String power){
        this.normalPath = normalPath;
        this.power = power;
    }

    public static final GodPath[] paths = new GodPath[]{APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS,
            HERA, HESTIA, LIMUS, MINOTAUR, PAN, PROMETHEUS, TRITON, ZEUS};

    public static String getPath(String string){
        for(GodPath god:paths){ //TODO -- sostituire con values
            if (("/images/gods/" + string + ".png").equalsIgnoreCase(god.normalPath))
                return god.normalPath;
        }
        return "";
    }

    public static String getGoldPath(String string){
        String normalPath = getPath(string);
        if (!normalPath.equals("")) {
            return normalPath.substring(0, normalPath.length() - 4) + "_gold.png";
        }
        return "";
    }

    public static String getPower(String string){
        for(GodPath god:paths){
            if (("/images/powers/" + string + ".png").equalsIgnoreCase(god.power))
                return god.power;
        }
        return "";
    }

}