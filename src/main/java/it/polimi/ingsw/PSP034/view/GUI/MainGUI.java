package it.polimi.ingsw.PSP034.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Starting GUI class. Creates a stage and loads the Title scene.
 */
public class MainGUI extends Application {
    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(ScenePath.TITLE));

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setTitle("Santorini");
        primaryStage.setResizable(false);

        InputStream icon = MainGUI.class.getClassLoader().getResourceAsStream("images/icon.png");
        if (icon != null) {
            primaryStage.getIcons().add(new Image(icon));
        }

        Scene myScene = new Scene(root, 1280, 720);
        primaryStage.setScene(myScene);
        root.getStylesheets().add("/style.css");
        primaryStage.show();

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
}