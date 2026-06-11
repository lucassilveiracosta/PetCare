package business.model.appointment;

import business.model.animal.Animal;

import java.util.ArrayList;

public class ClinicalHistory {

    private ArrayList<Appointment> appointments; // avaliar se se enquadra em consulta
    private String description;

    public ClinicalHistory(ArrayList<Appointment> idasAoVeterinarios, String description) {
        this.appointments = idasAoVeterinarios;
        this.description = description;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> idasAoVeterinarios) {
        this.appointments = idasAoVeterinarios;
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
