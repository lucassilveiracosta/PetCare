package business.model.animal;

import enums.Size;
import enums.Sex;
import enums.Temperament;
import enums.StageOfLife;
import business.model.Pessoas.Owner;

import java.time.LocalDate;
import java.util.ArrayList;

public class DomesticAnimal extends Animal {
    private Owner owner;
    private boolean castrated;
    private ArrayList<Vaccine> vaccines;
    private Temperament temperament;

    public DomesticAnimal(String name, String species, String race, LocalDate birthDate, StageOfLife stageOfLife, Double weight, Size size, Sex sex, Owner owner, ArrayList<Vaccine> vaccines, Temperament temperament, boolean castrated) {
        super(name,  species,  race,  birthDate, stageOfLife,  weight,  size,  sex);
        setOwner(owner);
        setVaccines(vaccines);
        setTemperament(temperament);
        setCastrated(castrated);
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        if(owner == null ){
            throw new IllegalArgumentException("400 - Invalid owner");
        }
        this.owner = owner;
    }

    public Temperament getTemperament() {
        return temperament;
    }

    public void setTemperament(Temperament temperament) {
        if(temperament == null ){
            throw new IllegalArgumentException("400 - Invalid temperament");
        }
        this.temperament = temperament;
    }

    public boolean isCastrated() {
        return castrated;
    }

    public void setCastrated(boolean castrated) {
        this.castrated = castrated;
    }


    public ArrayList<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(ArrayList<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }
}
