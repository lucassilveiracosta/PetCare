package business.model.Pessoas;

import java.time.LocalDate;

public class Employee extends Person {
    private String cargo;
    private String turnoTrabalho;

    public Employee(String nome, String email, String password, LocalDate dataNascimento, String cpf, String telefone, String cargo, String turnoTrabalho) {
        super(nome, email, password, dataNascimento, cpf, telefone);
        setCargo(cargo);
        setTurnoTrabalho(turnoTrabalho);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo)
    {
        if(cargo == null || cargo.isBlank()){
            throw new IllegalArgumentException("Cargo não pode ser nulo");
        }
        this.cargo = cargo;
    }

    public String getTurnoTrabalho() {
        return turnoTrabalho;
    }

    public void setTurnoTrabalho(String turnoTrabalho) {
        if(turnoTrabalho == null || turnoTrabalho.isBlank()){
            throw new IllegalArgumentException("O turno do trabalho não pode ser nulo!");
        }
        this.turnoTrabalho = turnoTrabalho;
    }
}
