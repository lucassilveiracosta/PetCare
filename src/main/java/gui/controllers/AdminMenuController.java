package gui.controllers;
import gui.Navigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminMenuController {

    @FXML
    public void openVeterinarians(ActionEvent event) {
        Navigator.navigate("Manage Veterinarians", "/view/fxml/AdminVeterinarians.fxml");
    }

    @FXML
    public void openStock(ActionEvent event) {
        Navigator.navigate("Manage Stock", "/view/fxml/AdminStock.fxml");
    }

    @FXML
    public void openAppointments(ActionEvent event) {
        Navigator.navigate("Manage Appointments", "/view/fxml/AdminAppointments.fxml");
    }

    @FXML
    public void openClinicalHistory(ActionEvent event) {
        Navigator.navigate("Pet Clinical History", "/view/fxml/AdminClinicalHistory.fxml");
    }

    @FXML
    public void openDashboard(ActionEvent event) {
        Navigator.navigate("System & Financial Dashboard", "/view/fxml/AdminDashboard.fxml");
    }
}
