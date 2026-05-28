package com.example.testejavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.tests.MockDataLoader;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        MockDataLoader.load();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scheduling.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Animal Scheduling");
        stage.setScene(scene);
        stage.show();
    }
}
