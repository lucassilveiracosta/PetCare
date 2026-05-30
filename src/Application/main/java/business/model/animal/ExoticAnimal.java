package main.java.business.model.animal;

import enums.Origin;
import enums.Size;
import enums.Sex;

import enums.StageOfLife;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExoticAnimal extends Animal {
    private boolean requiresControlEnviroment;
    private String dietDescription;
    private Origin origin;
    private String microChipId;
    private String registrationNumber;

    public ExoticAnimal(String name, String species, String race, LocalDate birthDate, StageOfLife stageOfLife, double weight, Size size, Sex sex, String registrationNumber, String microChipId, boolean requiresControlEnviroment, String dietDescription, Origin origin, ArrayList<Vaccine> vaccines) {
        super(name,  species,  race,  birthDate, stageOfLife, weight,  size,  sex, vaccines);
        setRegistrationNumber(registrationNumber);
        setMicroChipId(microChipId);
        setRequiresControlEnviroment(requiresControlEnviroment);
        setDescricaoDieta(dietDescription);
        setOrigin(origin);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if(registrationNumber == null || registrationNumber.isBlank()){
            throw new IllegalArgumentException("400 - Invalid registration number");
        }
        this.registrationNumber = ExoticAnimal.this.registrationNumber;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        if(origin == null){
            throw new IllegalArgumentException("400 - Invalid origin");
        }
        this.origin = origin;
    }

    public String getDietDescription() {
        return dietDescription;
    }

    public void setDescricaoDieta(String dietDescription) {
        if(dietDescription == null || dietDescription.isBlank()){
            throw new IllegalArgumentException("400 - Invalid diet description");
        }
        this.dietDescription = dietDescription;
    }

    public String getMicroChipId() {
        return microChipId;
    }

    public void setMicroChipId(String microChipId) {
        if(microChipId == null || microChipId.isBlank()){
            throw new IllegalArgumentException("Microchip inválido!");
        }
        this.microChipId = microChipId;
    }

    public boolean isRequiresControlEnviroment() {
        return requiresControlEnviroment;
    }

    public void setRequiresControlEnviroment(boolean requiresControlEnviroment) {

        this.requiresControlEnviroment = requiresControlEnviroment;
    }
}
