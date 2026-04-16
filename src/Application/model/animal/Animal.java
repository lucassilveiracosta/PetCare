package model.animal;

import enums.Porte;
import enums.Sexo;

import java.time.LocalDate;

public  class Animal {
    protected int IdAnimal;
    protected String Nome;
    protected String especie;
    protected String raca;
    protected LocalDate DataNascimento;
    protected double peso;
    protected Porte porte;
    protected Sexo sexo;

    public Animal(int idAnimal, String nome, String especie, String raca, LocalDate dataNascimento, double peso, Porte porte, Sexo sexo) {
        IdAnimal = idAnimal;
        Nome = nome;
        this.especie = especie;
        this.raca = raca;
        DataNascimento = dataNascimento;
        this.peso = peso;
        this.porte = porte;
        this.sexo = sexo;
    }

    public int getIdAnimal() {
        return IdAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        IdAnimal = idAnimal;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome do Animal inválido!");
        }
        Nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        if(raca == null || raca.isBlank()){
            throw new IllegalArgumentException("Nome da raça do animal inválido!");
        }
        this.raca = raca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        if(especie == null || especie.isBlank()){
            throw new IllegalArgumentException("Nome da especie do animal inválida!");
        }
        this.especie = especie;
    }

    public LocalDate getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {

        if(dataNascimento == null ){
            throw new IllegalArgumentException("Data de nascimento inválida!");
        } DataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if(peso <= 0 ){
            throw new IllegalArgumentException("peso do animal inválido!");
        }
        this.peso = peso;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        if(porte == null ){
            throw new IllegalArgumentException("Porte do animal inválido!");
        }
        this.porte = porte;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        if(sexo == null ){
            throw new IllegalArgumentException("Sexo do animal inválido!");
        }
        this.sexo = sexo;
    }
}
