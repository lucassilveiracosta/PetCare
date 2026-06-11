package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    public void openConsultas(ActionEvent event) {
        Navigator.navigate("Consultations", "/view/fxml/Consultation.fxml");
    }

    @FXML
    public void openDashboard(ActionEvent event) {
        Navigator.navigate("Appointment Dashboard", "/view/fxml/SchedulingDashboard.fxml");
    }

    @FXML
    public void openAttendant(ActionEvent event) {
        Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }

    @FXML
    public void openAdmin(ActionEvent event) {
        Navigator.navigate("Admin", "/view/fxml/AdminMenu.fxml");
    }
}