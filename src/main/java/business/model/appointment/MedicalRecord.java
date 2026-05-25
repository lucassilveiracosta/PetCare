package business.model.appointment;

import business.model.animal.Animal;

import java.util.ArrayList;

public class MedicalRecord {

    private ArrayList<Appointment> appointments; // avaliar se se enquadra em consulta
    private Animal animal;
    private String description;

    public MedicalRecord(ArrayList<Appointment> idasAoVeterinarios, String description, Animal animal) {
        this.appointments = idasAoVeterinarios;
        setAnimal(animal);
        this.description = description;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> idasAoVeterinarios) {
        this.appointments = idasAoVeterinarios;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("400 - Invalid animal");
        this.animal = animal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Appointment> getIdasAoVeterinario() {
        return appointments;
    }
}
