package business.model.Pessoas;

import java.time.LocalDate;
import java.util.ArrayList;

public class Veterinario extends Pessoa {
    private String crmv;
    private ArrayList<Especialidade> especialidades;


    public Veterinario(String nome, String email, String password, LocalDate dataNascimento, String cpf, String telefone, String crmv, ArrayList<Especialidade> especialidades){
        super(nome, email, password, dataNascimento, cpf, telefone);
        setCrmv(crmv);
        setEspecialidades(especialidades);
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

    public ArrayList<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(ArrayList<Especialidade> especialidades) {
        if(especialidades == null){
            throw new IllegalArgumentException("A lista não pode ser nula! Se não houver especialidades, passe uma lista vazia.");

        }
        else {
            this.especialidades = especialidades;
        }
    }
}
