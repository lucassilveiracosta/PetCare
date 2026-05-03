package model.Pessoas;

import java.time.LocalDate;

public class Funcionario extends Pessoa{
    private String cargo;
    private String turnoTrabalho;

    public Funcionario(String nome, LocalDate dataNascimento, String cpf, String telefone, String cargo, String turnoTrabalho) {
        super(nome, dataNascimento, cpf, telefone);
        setCargo(cargo);
        setTurnoTrabalho(turnoTrabalho);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTurnoTrabalho() {
        return turnoTrabalho;
    }

    public void setTurnoTrabalho(String turnoTrabalho) {
        this.turnoTrabalho = turnoTrabalho;
    }
}
