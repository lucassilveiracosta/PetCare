package model;

import java.util.ArrayList;

public class Prontuario {

    private ArrayList<IdaAoVeterinario> prontuario;
    private Animal paciente;
    private String descricao;

    public Prontuario(ArrayList<IdaAoVeterinario> prontuario, Animal paciente, String descricao) {
        this.prontuario = prontuario;
        this.paciente = paciente;
        this.descricao = descricao;
    }

    public ArrayList<IdaAoVeterinario> getProntuario() {
        return prontuario;
    }

    public void setProntuario(ArrayList<IdaAoVeterinario> prontuario) {
        this.prontuario = prontuario;
    }

    public Animal getPaciente() {
        return paciente;
    }

    public void setPaciente(Animal paciente) {
        this.paciente = paciente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
