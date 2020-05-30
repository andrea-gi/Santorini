package it.polimi.ingsw.PSP034.view.GUI;

public class GodPath {
    public static final String APOLLO = "/images/gods/Apollo.png";
    public static final String ARTEMIS = "/images/gods/Artemis.png";
    public static final String ATHENA = "/images/gods/Athena.png";
    public static final String ATLAS = "/images/gods/Atlas.png";
    public static final String DEMETER = "/images/gods/Demeter.png";
    public static final String HEPHAESTUS = "/images/gods/Hephaestus.png";
    public static final String HERA = "/images/gods/Hera.png";
    public static final String HESTIA = "/images/gods/Hestia.png";
    public static final String LIMUS = "/images/gods/Limus.png";
    public static final String MINOTAUR = "/images/gods/Minotaur.png";
    public static final String PAN = "/images/gods/Pan.png";
    public static final String PROMETHEUS = "/images/gods/Prometheus.png";
    public static final String TRITON = "/images/gods/Triton.png";
    public static final String ZEUS = "/images/gods/Zeus.png";

    public static final String[] paths = new String[]{APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS,
            HERA, HESTIA, LIMUS, MINOTAUR, PAN, PROMETHEUS, TRITON, ZEUS};

    public static String getPath(String string){
        for(String god:paths){
            if (("/images/gods/" + string + ".png").equalsIgnoreCase(god))
                return god;
        }
        return "";
    }
}