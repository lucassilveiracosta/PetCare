package com.example.testejavafx;

import business.controller.ControllerAnimal;
import business.controller.ControllerAppointment;
import business.controller.ControllerPessoa;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class SchedulingViewController implements Initializable {
    @FXML
    private ChoiceBox<String> nameTutorScheduling;

    @FXML
    private ChoiceBox<String> nameAnimalScheduling;

    @FXML
    private DatePicker schedulingDate;

    @FXML
    private Button scheduleScheduling;

    private ControllerPessoa controllerPessoa;
    private ControllerAnimal controllerAnimal;
    private ControllerAppointment controllerAppointment;

    @FXML
    protected void onAgendarClick(ActionEvent event) {
        String nameTutor = nameTutorScheduling.getValue();
        System.out.println("O botão foi clicado! Nome digitado: " + nameTutor);
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameTutorScheduling.getItems().addAll(
                "João da Silva",
                "Maria Oliveira",
                "Carlos Souza"
        );

        nameAnimalScheduling.getItems().addAll(
                "Rex (Cachorro)",
                "Mimi (Gato)"
        );

    }
}
