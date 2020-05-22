package it.polimi.ingsw.PSP034.view.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class ScenePath {
    public static final String TITLE = "/fxml/title.fxml";
    public static final String SERVER_LOGIN = "/fxml/serverLogin.fxml";
    public static final String LOGIN = "/fxml/login.fxml";
    public static final String WAITING = "/fxml/waiting.fxml";
    public static final String CHOOSE_GODS = "/fxml/chooseGods.fxml";
    public static final String PERSONAL_GOD = "/fxml/personalGod.fxml";
    public static final String NUMBER_OF_PLAYERS = "/fxml/numberOfPlayers.fxml";

    public static void setNextScene(Scene scene, String path) {
        FXMLLoader loader = new FXMLLoader((ScenePath.class.getResource(path)));
        Pane pane;
        try {
            pane = loader.load();
            scene.setRoot(pane);
            scene.getStylesheets().add("/style.css");
        } catch (IOException ignored) {
        }
    }
}
