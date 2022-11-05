package com.example.wordcountravengui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RavenGUIApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RavenGUIApplication.class.getResource("raven-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 1200);
        stage.setTitle("Word Count of Edgar Allen Poe's Raven");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
