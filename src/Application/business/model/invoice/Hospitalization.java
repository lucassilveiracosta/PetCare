package business.model.invoice;

import business.model.animal.Animal;
import business.model.appointment.VitalParameters;
import business.model.person.Veterinarian;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Hospitalization extends Procedure{

    private Animal animal;
    private Veterinarian responsibleVeterinarian;
    private LocalDateTime entryDateTime;
    private LocalDateTime dischargeDateTime;
    private ArrayList<VitalParameters> vitalParametersHistory;

    public Hospitalization(Double price, Animal patient, LocalDateTime dateHour, String description, Animal animal, Veterinarian responsibleVeterinarian, LocalDateTime entryDateTime, LocalDateTime dischargeDateTime, ArrayList<VitalParameters> vitalParametersHistory) {
        super(price, patient, dateHour, description);
        this.animal = animal;
        this.responsibleVeterinarian = responsibleVeterinarian;
        this.entryDateTime = entryDateTime;
        this.dischargeDateTime = dischargeDateTime;
        this.vitalParametersHistory = vitalParametersHistory;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("400 - Invalid animal");
        this.animal = animal;
    }

    public Veterinarian getResponsibleVeterinarian() {
        return responsibleVeterinarian;
    }

    public void setResponsibleVeterinarian(Veterinarian responsibleVeterinarian) {
        if (responsibleVeterinarian == null) throw new IllegalArgumentException("400 - Invalid veterinarian");
        this.responsibleVeterinarian = responsibleVeterinarian;
    }

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        if (entryDateTime == null) throw new IllegalArgumentException("400 - Invalid entry date");
        this.entryDateTime = entryDateTime;
    }

    public LocalDateTime getDischargeDateTime() {
        return dischargeDateTime;
    }

    public void setDischargeDateTime(LocalDateTime dischargeDateTime) {
        if (dischargeDateTime == null) throw new IllegalArgumentException("400 - Invalid discharge date");
        this.dischargeDateTime = dischargeDateTime;
    }

    public ArrayList<VitalParameters> getVitalParametersHistory() {
        return vitalParametersHistory;
    }

    public void setVitalParametersHistory(ArrayList<VitalParameters> vitalParametersHistory) {
        if (vitalParametersHistory == null) throw new IllegalArgumentException("400 - Invalid vital parameters");
        this.vitalParametersHistory = vitalParametersHistory;
    }
}
