package main.java.com.example.testejavafx;

import gui.MockDataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MockDataLoader.load();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/fxml/Consulta.fxml"));
        SplitPane root = new SplitPane();
        fxmlLoader.setRoot(root);
        fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Veterinary Consultation");
        stage.setScene(scene);
        stage.show();
    }
}
