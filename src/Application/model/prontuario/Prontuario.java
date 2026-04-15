package model.prontuario;

import java.util.ArrayList;

public class Prontuario {

    private ArrayList<IdaAoVeterinario> prontuario;
    private Animal animal;
    private String descricao;

    public Prontuario(ArrayList<IdaAoVeterinario> prontuario, String descricao, Animal animal) {
        this.prontuario = prontuario;
        this.animal = animal;
        this.descricao = descricao;
    }

    public ArrayList<IdaAoVeterinario> getProntuario() {
        return prontuario;
    }

    public void setProntuario(ArrayList<IdaAoVeterinario> prontuario) {
        this.prontuario = prontuario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
