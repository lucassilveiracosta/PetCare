package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AppShellController implements Initializable {

    @FXML private HBox breadcrumbBar;
    @FXML private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new MockDataLoader().load();
        Navigator.init(breadcrumbBar, contentArea);
        Navigator.reset("Home", "/view/fxml/MenuPrincipal.fxml");
    }
}
