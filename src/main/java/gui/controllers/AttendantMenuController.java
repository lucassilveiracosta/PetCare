package gui.controllers;

import gui.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AttendantMenuController {

    @FXML
    public void openPetShop(ActionEvent event) {
        Navigator.navigate("Pet Shop", "/view/fxml/RegisterProductList.fxml");
    }

    @FXML
    public void openRegisterOwner(ActionEvent event) {
        Navigator.navigate("Register Owner", "/view/fxml/RegisterOwner.fxml");
    }

    @FXML
    public void openRegisterPet(ActionEvent event) {
        Navigator.navigate("Register Pet", "/view/fxml/RegisterPetQuestion.fxml");
    }

    @FXML
    public void openPetShopScheduling(ActionEvent event) {
        Navigator.navigate("Pet Shop Scheduling", "/view/fxml/Attendant.fxml");
    }

    @FXML
    public void openVeterinarianScheduling(ActionEvent event) {
        Navigator.navigate("Scheduling", "/view/fxml/Scheduling.fxml");
    }

    @FXML
    public void backButton(ActionEvent event) {
        Navigator.navigate("Home", "/view/fxml/MenuPrincipal.fxml");
    }
}
