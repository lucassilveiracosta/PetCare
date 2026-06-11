package business.model.animal;

import enums.StageOfLife;
import exceptions.VaccineException;

import java.time.LocalDate;

public class Vaccine {
    private String vaccineName;
    private LocalDate vaccineDate;
    private LocalDate expireVaccineDate;
    private String description;
    private boolean isRabbiesVaccine;
    private StageOfLife minAge;

    public Vaccine(String vaccineName, LocalDate vaccineDate, String description, boolean isRabbiesVaccine, LocalDate expireVaccineDate, StageOfLife minAge) {
        setVaccineName(vaccineName);
        setVaccineDate(vaccineDate);
        this.description = description;
        setRabbiesVaccine(isRabbiesVaccine);
        setExpireVaccineDate(expireVaccineDate);
        setMinAge(minAge);
    }

    public StageOfLife getMinAge() {
        return minAge;
    }

    public void setMinAge(StageOfLife minAge) {
        if (minAge == null) throw new IllegalArgumentException("minAge cannot be null");
        if (minAge == StageOfLife.RECEMNASCIDO) throw new IllegalArgumentException("minAge cannot be RECEMNASCIDO"); //aqui vai dar conflito
        this.minAge = minAge;
    }

    public LocalDate getExpireVaccineDate() {
        return expireVaccineDate;
    }

    public void setExpireVaccineDate(LocalDate expireVaccineDate) {
        if (expireVaccineDate == null) throw new IllegalArgumentException("400 - Invalid expire date");
        if (expireVaccineDate.isBefore(vaccineDate)) throw new VaccineException("400 - expire data must be after the vaccine date");
        this.expireVaccineDate = expireVaccineDate;
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
