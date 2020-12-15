package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage primaryStage;

    /**
     * Opens the login window for the GUI
     * @param primaryStage  the stage to be displayed
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("CS4013 Project - Login");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }

    /**
     * Launches the Graphical User Interface
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
