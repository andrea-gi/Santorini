package it.polimi.ingsw.PSP034.view.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/** It contains all the paths of the fxml files that create all the different scenes.
 */
public class ScenePath {
    public static final String TITLE = "/fxml/title.fxml";
    public static final String SERVER_LOGIN = "/fxml/serverLogin.fxml";
    public static final String NAME_COLOR = "/fxml/namecolor.fxml";
    public static final String WAITING = "/fxml/waiting.fxml";
    public static final String CHOOSE_GODS = "/fxml/chooseGods.fxml";
    public static final String PERSONAL_GOD = "/fxml/personalGod.fxml";
    public static final String NUMBER_OF_PLAYERS = "/fxml/numberOfPlayers.fxml";
    public static final String FIRST_PLAYER = "/fxml/firstPlayer.fxml";

    public static final String TABLE = "/fxml/table.fxml";

    /** Loads the next scene on the same stage
     * @param scene is the scene where the new one is loaded
     * @param path is the path to the right fxml file
     */
    public static void setNextScene(Scene scene, String path) {
        FXMLLoader loader = new FXMLLoader((ScenePath.class.getResource(path)));
        Pane pane;
        try {
            pane = loader.load();
            scene.setRoot(pane);
            scene.getStylesheets().add("/style.css");
        } catch (IOException ignored) {
            //TODO -- schermata di errore
        }
    }

    /** Loads a dialog on top of the previous scene
     * @param stage is the stage where the dialog is created
     * @param info is the title of the dialog
     * @param message is the description of the dialog
     */
    public static void setDialog(Stage stage, String info, String message){
        FXMLLoader loader = new FXMLLoader((ScenePath.class.getResource("/fxml/dialog.fxml")));
        Scene dialogScene;
        try {
            dialogScene = new Scene(loader.load(), 420, 430);
            dialogScene.getStylesheets().add("/style.css");
        } catch (IOException e) {
            return;
        }

        Stage dialog = new Stage();
        dialogScene.setFill(null);
        dialog.setScene(dialogScene);
        dialog.initOwner(stage);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setAlwaysOnTop(true);
        dialog.initStyle(StageStyle.TRANSPARENT);

        ((DialogController)GUIRequestHub.getInstance().getCurrentController()).setMyTitle(info);
        ((DialogController)GUIRequestHub.getInstance().getCurrentController()).setLabel(message);

        dialog.showAndWait();
    }

    public static void dismissDialog(Stage dialog){
        if (dialog != null)
            dialog.close();
    }
}
