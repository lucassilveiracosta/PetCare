package model.Pessoas;
import model.Especialidade.RepositorioEspecialidade;
import model.Pessoas.Pessoa;

import java.time.LocalDate;

public class Veterinario extends Pessoa {
    private RepositorioEspecialidade especialidade = new RepositorioEspecialidade();
    private String crmv;
    public Veterinario(String nome, LocalDate dataNascimento, String cpf, String telefone, RepositorioEspecialidade especialidade, String crmv){
        super(nome, dataNascimento, cpf, telefone);

        setEspecialidade(especialidade);
        setCrmv(crmv);
    }

    public RepositorioEspecialidade getEspecialidade(){
        return especialidade;
    }
    private void setEspecialidade(RepositorioEspecialidade especialidade){
        if(especialidade == null){
            throw new IllegalArgumentException("As especialidades não podem ser nulas!");
        }
        this.especialidade = especialidade;
    }

    public String getCrmv(){
        return crmv;
    }

    private void setCrmv(String crmv){
        if(crmv == null || crmv.isBlank()){
            throw new IllegalArgumentException("CRMV inválido!");
        }
        this.crmv = crmv;
    }
}
