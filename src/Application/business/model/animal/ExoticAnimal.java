package business.model.animal;

import enums.Origem;
import enums.Porte;
import enums.Sexo;

import enums.FaseDaVida;
import java.time.LocalDate;

public class ExoticAnimal extends Animal {
    private boolean requiresControlEnviroment;
    private String dietDescription;
    private Origem origin;
    private String microChipId;
    private String registrationNumber;

    public ExoticAnimal(String name, String species, String race, LocalDate birthDate, FaseDaVida stageOfLife, double weight, Porte size, Sexo sex, String registrationNumber, String microChipId, boolean requiresControlEnviroment, String dietDescription, Origem origin) {
        super(name,  species,  race,  birthDate, stageOfLife, weight,  size,  sex);
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

    public Origem getOrigin() {
        return origin;
    }

    public void setOrigin(Origem origin) {
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
