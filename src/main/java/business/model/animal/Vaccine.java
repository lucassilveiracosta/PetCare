package business.model.animal;

import exceptions.VaccineException;

import java.time.LocalDate;

public class Vaccine {
    private String vaccineName;
    private LocalDate vaccineDate;
    private LocalDate expireVaccineDate;
    private String description;
    private boolean isRabiesVaccine;

    public Vaccine(String vaccineName, LocalDate vaccineDate, String description, boolean isRabiesVaccine, LocalDate expireVaccineDate){
        setVaccineName(vaccineName);
        setVaccineDate(vaccineDate);
        this.description = description;
        setRabiesVaccine(isRabiesVaccine);
        setExpireVaccineDate(expireVaccineDate);

    }

    public LocalDate getExpireVaccineDate() {
        return expireVaccineDate;
    }

    public void setExpireVaccineDate(LocalDate expireVaccineDate) {
        if (expireVaccineDate == null) throw new IllegalArgumentException("400 - Invalid expire date");
        if (expireVaccineDate.isBefore(vaccineDate)) throw new VaccineException("400 - expire date must be after the vaccine date");
        this.expireVaccineDate = expireVaccineDate;
    }

    public boolean isRabiesVaccine() {
        return isRabiesVaccine;
    }

    public void setRabiesVaccine(boolean rabiesVaccine) {
        isRabiesVaccine = rabiesVaccine;
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
