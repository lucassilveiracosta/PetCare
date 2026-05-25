package com.example.testejavafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class AgendamentoViewController implements Initializable {
    @FXML
    private ChoiceBox<String> nomeTutorAgendamento;

    @FXML
    private ChoiceBox<String> nomeAnimalAgendamento;

    @FXML
    private DatePicker dataAgendamento;

    @FXML
    private Button agendarAgendamento;


    @FXML
    protected void onAgendarClick(ActionEvent event) {
        String nomeTutor = nomeTutorAgendamento.getValue();
        System.out.println("O botão foi clicado! Nome digitado: " + nomeTutor);
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeTutorAgendamento.getItems().addAll(
                "João da Silva",
                "Maria Oliveira",
                "Carlos Souza"
        );

        nomeAnimalAgendamento.getItems().addAll(
                "Rex (Cachorro)",
                "Mimi (Gato)"
        );

    }
}
