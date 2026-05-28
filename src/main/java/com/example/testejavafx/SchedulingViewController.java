package com.example.testejavafx;

import business.interfaces.IControllerAppointment;
import business.interfaces.IControllerPessoa;
import business.interfaces.IControllerAnimal;
import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Veterinarian;
import enums.PetShopServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

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

    @FXML
    private ChoiceBox<String> nameVeterianarianScheduling;

    @FXML
    private ChoiceBox<String> appointmentScheduling;

    @FXML
    private Text professionalText;

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
        appointmentScheduling.getItems().clear();
        nameVeterianarianScheduling.getItems().clear();

        List<Person> AllPerson = controllerPessoa.getAll();

        for (Person person : AllPerson) {
            if (person instanceof Owner) {
                nameTutorScheduling.getItems().add(person.getName());
            }
        }
        appointmentScheduling.getItems().add("MEDICAL CONSULTATION");
        for(PetShopServices service : PetShopServices.values()){
            appointmentScheduling.getItems().add(service.name());
        }

        nameTutorScheduling.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue != null) {
                updateAnimals(newValue);
            }
        });


        // Arrow functionzinha com if e else para alternar o texto e sempre atualizando de acordo com o que o usuário seleciona.
        appointmentScheduling.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                if(newValue.equals("MEDICAL CONSULTATION")){
                    professionalText.setText("Select the Veterinarian:");
                    updateProfessionalList(true);
                } else {
                    professionalText.setText("Select the Employee");
                    updateProfessionalList(false);
                }
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

    private void updateProfessionalList(boolean isConsulta) {
        nameVeterianarianScheduling.getItems().clear();

        List<Person> AllPerson = controllerPessoa.getAll();

        for (Person person : AllPerson) {
            if (isConsulta && person instanceof Veterinarian) {
                nameVeterianarianScheduling.getItems().add(person.getName());
            }
            else if (!isConsulta && person instanceof Employee) {
                nameVeterianarianScheduling.getItems().add(person.getName());
            }
        }
    }
    }
