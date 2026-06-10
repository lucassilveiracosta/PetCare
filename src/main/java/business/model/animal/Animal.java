package business.model.animal;

import enums.StageOfLife;
import enums.Size;
import enums.Sex;
import exceptions.InvoiceConflictException;

import java.time.LocalDate;
import java.util.ArrayList;


public class Animal {
    protected static int contadorId = 1;

    protected int id = contadorId++;
    protected String name;
    protected String species;
    protected String race;
    protected LocalDate birthDate;
    protected StageOfLife stageOfLife;
    protected Double weight;
    protected Size size;
    protected Sex sex;
    private ArrayList<Vaccine> vaccines;

    public Animal(String name, String species, String race, LocalDate birthDate, StageOfLife stageOfLife, double weight, Size size, Sex sex, ArrayList<Vaccine> vaccines) {
        setName(name);
        setSpecies(species);
        setRace(race);
        setbirthDate(birthDate);
        setStageOfLife(stageOfLife);
        setWeight(weight);
        setSize(size);
        setSex(sex);
        setVaccines(vaccines);

    }


    public ArrayList<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(ArrayList<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }

    public StageOfLife getStageOfLife() {
        return stageOfLife;
    }

    public void setWeight(Double weight) {
        if(weight <= 0 ){
            throw new IllegalArgumentException("400 - Invalid weight");
        }
        this.weight = weight;
    }

    public void setStageOfLife(StageOfLife stageOfLife) {
        if(stageOfLife == null){
            throw new IllegalArgumentException("400 - Invalid stage of life");
        }
        this.stageOfLife = stageOfLife;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("400 - Invalid name");
        }
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        if(race == null || race.isBlank()){
            throw new IllegalArgumentException("400 - Invalid race");
        }
        this.race = race;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        if(species == null || species.isBlank()){
            throw new IllegalArgumentException("400 - Invalid specie");
        }
        this.species = species;
    }

    public LocalDate getbirthDate() {
        return birthDate;
    }

    public void setbirthDate(LocalDate birthDate) {
        if (birthDate == null) throw new InvoiceConflictException("400 - Invalid birthDate");
        this.birthDate = birthDate;
    }

    public double getWeight() {
        return weight;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        if(size == null ){
            throw new IllegalArgumentException("400 - Invalid size");
        }
        this.size = size;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        if(sex == null ){
            throw new IllegalArgumentException("400 - Invalid sex");
        }
        this.sex = sex;
    }

    public void setId(int id) {
        this.id = id;
        if (id >= contadorId) {
            contadorId = id + 1;
        }
    }

    public int getId() {
        return id;
    }

/*
            try {
                // formato que o usuário digita
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // converter string para data
                LocalDate data = LocalDate.parse(birthDate, formatoEntrada);

                // formato padrão desejado
                DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // exibir formatado
                String dataFormatada = data.format(formatoSaida);

                System.out.println("Data formatada: " + dataFormatada);

            } catch (DateTimeParseException e) {
                System.out.println("Erro: Data inválida! Use o formato dd/MM/yyyy.");
            }
       */
}

