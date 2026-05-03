package model.notaFiscal;

import model.animal.Animal;

import java.time.LocalDateTime;

public class ServicoPetShop extends Procedimento {
    private String tipoServico;
    private String pelagem;

    public ServicoPetShop(Double preco, Animal paciente, LocalDateTime dataHora, String descricao, String tipoServico, String pelagem){
        super(preco, paciente, dataHora, descricao);
        setPelagem(pelagem);
        setTipoServico(tipoServico);
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
}
