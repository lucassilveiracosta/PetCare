package business.model.invoice;

import business.model.animal.Animal;
import business.model.appointment.VitalParameters;
import business.model.person.Veterinarian;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Hospitalization extends Procedure{

    private Veterinarian responsibleVeterinarian;
    private LocalDateTime entryDateTime;
    private LocalDateTime dischargeDateTime;
    private ArrayList<VitalParameters> vitalParametersHistory;

    public Hospitalization(Double price, Animal patient, LocalDateTime dateHour, String description, Animal animal, Veterinarian responsibleVeterinarian, LocalDateTime entryDateTime, LocalDateTime dischargeDateTime, ArrayList<VitalParameters> vitalParametersHistory) {
        super(price, patient, dateHour, description);
        setResponsibleVeterinarian(responsibleVeterinarian);
        setEntryDateTime(entryDateTime);
        setDischargeDateTime(dischargeDateTime);
        setVitalParametersHistory(vitalParametersHistory);
    }

    // Allows "now"/past dates (used when admitting or loading a hospitalization).
    public Hospitalization(Boolean bool, Double price, Animal patient, LocalDateTime dateHour, String description,
                           Veterinarian responsibleVeterinarian, LocalDateTime entryDateTime,
                           LocalDateTime dischargeDateTime, ArrayList<VitalParameters> vitalParametersHistory) {
        super(bool, price, patient, dateHour, description);
        setResponsibleVeterinarian(responsibleVeterinarian);
        setEntryDateTime(entryDateTime);
        setDischargeDateTime(dischargeDateTime);
        setVitalParametersHistory(vitalParametersHistory);
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

    /** Discharge may be null while the animal is still hospitalized (active). */
    public void setDischargeDateTime(LocalDateTime dischargeDateTime) {
        if (dischargeDateTime != null && entryDateTime != null && dischargeDateTime.isBefore(entryDateTime)) {
            throw new IllegalArgumentException("400 - discharge date must be after the entry date");
        }
        this.dischargeDateTime = dischargeDateTime;
    }

    /** True while the animal is still admitted (no discharge recorded yet). */
    public boolean isActive() {
        return dischargeDateTime == null;
    }

    public ArrayList<VitalParameters> getVitalParametersHistory() {
        return vitalParametersHistory;
    }

    public void setVitalParametersHistory(ArrayList<VitalParameters> vitalParametersHistory) {
        if (vitalParametersHistory == null) throw new IllegalArgumentException("400 - Invalid vital parameters");
        this.vitalParametersHistory = vitalParametersHistory;
    }
}
