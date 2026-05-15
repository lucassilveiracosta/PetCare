package business.model.animal;

import java.time.LocalDate;

public class Vaccine {
    private String vaccineName;
    private LocalDate vaccineDate;
    private String description;

    public Vaccine(String vaccineName, LocalDate vaccineDate, String description) {
        this.vaccineName = vaccineName;
        this.vaccineDate = vaccineDate;
        this.description = description;
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
