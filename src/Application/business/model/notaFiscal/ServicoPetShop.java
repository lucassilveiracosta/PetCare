package business.model.notaFiscal;

import business.model.Pessoas.Employee;
import business.model.animal.Animal;

import java.time.LocalDateTime;

public class ServicoPetShop extends Procedure {
    private String tipoServico;
    private String pelagem;
    private Employee funcionarioResponsavel;

    public ServicoPetShop(Double preco, Animal paciente, LocalDateTime dataHora, String descricao, String tipoServico, String pelagem, Employee funcionarioResponsavel){
        super(preco, paciente, dataHora, descricao);
        setPelagem(pelagem);
        setTipoServico(tipoServico);
        setFuncionarioResponsavel(funcionarioResponsavel);
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem)
    {
        if(pelagem == null || pelagem.isBlank()){
            throw new IllegalArgumentException("A pelagem não pode ser nula!");
        }
        this.pelagem = pelagem;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        if(tipoServico == null || tipoServico.isBlank()){
            throw new IllegalArgumentException("O tipo do serviço não pode ser nulo!");
        }
        this.tipoServico = tipoServico;
    }

    public Employee getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Employee funcionarioResponsavel) {
        if(funcionarioResponsavel == null){
            throw new IllegalArgumentException("Funcionário responsável não pode ser nulo!");
        }
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
}
