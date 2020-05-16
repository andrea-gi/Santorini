package it.polimi.ingsw.PSP034.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/title.fxml"));

        primaryStage.setTitle("Santorini");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add("/style.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
