package business.model.animal;

import java.time.LocalDate;

public class Vaccine {
    private String vaccineName;
    private LocalDate vaccineDate;
    private String description;
    private boolean isRabbiesVaccine;

    public Vaccine(String vaccineName, LocalDate vaccineDate, String description, boolean isRabbiesVaccine){
        setVaccineName(vaccineName);
        setVaccineDate(vaccineDate);
        this.description = description;
        setRabbiesVaccine(isRabbiesVaccine);

    }

    public boolean isRabbiesVaccine() {
        return isRabbiesVaccine;
    }

    public void setRabbiesVaccine(boolean rabbiesVaccine) {
        isRabbiesVaccine = rabbiesVaccine;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        if(vaccineName == null || vaccineName.isBlank()){
            throw new IllegalArgumentException("400 - Invalid vaccine name");
        }
        this.vaccineName = vaccineName;
    }

    public LocalDate getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(LocalDate vaccineDate) {
        if(vaccineDate == null){
            throw new IllegalArgumentException("400 - Invalid vaccine date");
        }
        this.vaccineDate = vaccineDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("400 - Invalid description");
        }
        this.description = description;
    }
}
