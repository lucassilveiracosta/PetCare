package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/view/fxml/AppShell.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 740);
        stage.setTitle("PetCare");
        stage.setScene(scene);
        stage.show();
    }
}
