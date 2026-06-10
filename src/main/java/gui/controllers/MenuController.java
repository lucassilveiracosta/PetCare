package gui.controllers;

import gui.MockDataLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public void openAttendantMenu(ActionEvent event) throws IOException {
        MockDataLoader mdl = new MockDataLoader();
        mdl.load();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/AttendantMenu.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("AttendantMenu");
        stage.show();
    }

    @FXML
    public void openConsultas(ActionEvent event) throws IOException {
        MockDataLoader mdl = new MockDataLoader();
        mdl.load();
        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/Consultation.fxml"));
        SplitPane root = new SplitPane();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Consultations");
        stage.show();
    }

    @FXML
    public void openDashboard(ActionEvent event) throws IOException {
        MockDataLoader mdl = new MockDataLoader();
        mdl.load();
        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/SchedulingDashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.setTitle("Scheduling Dashboard");
        stage.show();
    }
}
