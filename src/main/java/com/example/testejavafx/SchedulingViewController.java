package com.example.testejavafx;

import business.interfaces.IControllerAppointment;
import business.interfaces.IControllerPessoa;
import business.interfaces.IControllerAnimal;
import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.person.Owner;
import business.model.person.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
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

    private IControllerPessoa controllerPessoa;
    private IControllerAnimal controllerAnimal;
    private IControllerAppointment controllerAppointment;

    @FXML
    protected void onScheduleClick(ActionEvent event) {
        String nameTutor = nameTutorScheduling.getValue();
        String nameAnimal = nameAnimalScheduling.getValue();
        System.out.println("Agendando para: " + nameTutor + " - Pet: " + nameAnimal);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controllerPessoa = ControllerPetCareServer.getInstance().getPerson();
        controllerAnimal = ControllerPetCareServer.getInstance().getAnimal();

        nameAnimalScheduling.getItems().clear();
        nameTutorScheduling.getItems().clear();

        List<Person> AllPerson = controllerPessoa.getAll();

        for (Person person : AllPerson) {
            if (person instanceof Owner) {
                nameTutorScheduling.getItems().add(person.getName());
            }
        }
        nameTutorScheduling.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue != null) {
                updateAnimals(newValue);
            }
        });
    }
        private void updateAnimals(String nameTutorSelected){
            nameAnimalScheduling.getItems().clear();

            List<Animal> allAnimals = controllerAnimal.getAll();

            for(Animal animal : allAnimals){
                if(animal instanceof DomesticAnimal){
                    DomesticAnimal domesticAnimal = (DomesticAnimal) animal;

                    if(domesticAnimal.getOwner().getName().equals(nameTutorSelected)){
                        nameAnimalScheduling.getItems().add(domesticAnimal.getName() + "(" + domesticAnimal.getSpecies() +")");
                    }
                }
            }
            if(!nameAnimalScheduling.getItems().isEmpty()){
                nameAnimalScheduling.getSelectionModel().selectFirst();
            }
        }
    }
