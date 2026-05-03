package model.notaFiscal;

import model.Pessoas.Funcionario;
import model.animal.Animal;

import java.time.LocalDateTime;

public class ServicoPetShop extends Procedimento {
    private String tipoServico;
    private String pelagem;
    private Funcionario funcionarioResponsavel;

    public ServicoPetShop(Double preco, Animal paciente, LocalDateTime dataHora, String descricao, String tipoServico, String pelagem, Funcionario funcionarioResponsavel){
        super(preco, paciente, dataHora, descricao);
        setPelagem(pelagem);
        setTipoServico(tipoServico);
        setFuncionarioResponsavel(funcionarioResponsavel);
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        pelagem = pelagem;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
}
