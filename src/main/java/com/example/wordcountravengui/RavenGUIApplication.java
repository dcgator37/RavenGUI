package com.example.wordcountravengui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main class for the Java FX application.
 */
public class RavenGUIApplication extends Application {
    /**
     * This is the start function that loads the fxml file, creates a scene, sets the title, and shows the stage.
     * @param stage the start function accepts a blank stage to place the Java FX scene on
     * @throws IOException the function will throw an IOException if the fxml file can not be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RavenGUIApplication.class.getResource("raven-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 1200);
        stage.setTitle("Word Count of Edgar Allen Poe's Raven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main function that launches the Java FX app.
     * @param args Strings passed into the main method
     */
    public static void main(String[] args) {
        launch();
    }
}
