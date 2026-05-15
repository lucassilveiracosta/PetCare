package business.model.animal;

import enums.Porte;
import enums.Sexo;
import enums.Temperamento;
import enums.FaseDaVida;
import business.model.Pessoas.Dono;

import java.time.LocalDate;
import java.util.ArrayList;

public class AnimalDomestico extends Animal {
    private Dono owner;
    private boolean castrated;
    private ArrayList<Vacina> vaccines;
    private Temperamento temperament;

    public AnimalDomestico(String name, String species, String race, LocalDate birthDate, FaseDaVida stageOfLife, Double weight, Porte size, Sexo sex, Dono owner, ArrayList<Vacina> vaccines, Temperamento temperament, boolean castrated) {
        super(name,  species,  race,  birthDate, stageOfLife,  weight,  size,  sex);
        setOwner(owner);
        setVaccines(vaccines);
        setTemperament(temperament);
        setCastrated(castrated);
    }

    public Dono getOwner() {
        return owner;
    }

    public void setOwner(Dono owner) {
        if(owner == null ){
            throw new IllegalArgumentException("400 - Invalid owner");
        }
        this.owner = owner;
    }

    public Temperamento getTemperament() {
        return temperament;
    }

    public void setTemperament(Temperamento temperament) {
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


    public ArrayList<Vacina> getVaccines() {
        return vaccines;
    }

    public void setVaccines(ArrayList<Vacina> vaccines) {
        this.vaccines = vaccines;
    }
}
