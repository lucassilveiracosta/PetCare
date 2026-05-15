package business.model.prontuario;

import business.model.animal.Animal;

import java.util.ArrayList;

public class MedicalRecord {

    private ArrayList<IdaAoVeterinario> idasAoVeterinario;
    private Animal animal;
    private String descricao;

    public MedicalRecord(ArrayList<IdaAoVeterinario> idasAoVeterinarios, String descricao, Animal animal) {
        this.idasAoVeterinario = idasAoVeterinarios;
        this.animal = animal;
        this.descricao = descricao;
    }

    public ArrayList<IdaAoVeterinario> getProntuario() {
        return idasAoVeterinario;
    }

    public void setProntuario(ArrayList<IdaAoVeterinario> idasAoVeterinarios) {
        this.idasAoVeterinario = idasAoVeterinarios;
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

    public ArrayList<IdaAoVeterinario> getIdasAoVeterinario() {
        return idasAoVeterinario;
    }
}
