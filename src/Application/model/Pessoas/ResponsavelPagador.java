package model.Pessoas;
import model.Pessoas.Pessoa;
import java.time.LocalDate;
public class ResponsavelPagador extends Pessoa {
    private String pagador;
    private String descricao;
    public ResponsavelPagador(String nome, LocalDate dataNascimento, String cpf, String telefone, String profissao, String descricao){
        super(nome, dataNascimento, cpf, telefone);
        setPagador(profissao);
        setDescricao(descricao);
    }
    public String getPagador(){
        return pagador;
    }
    private void setPagador(String pagador){
        if(pagador == null || pagador.isBlank()){
            throw new IllegalArgumentException("Profissão inválida!");
        }
        this.pagador = pagador;
    }

    public String getDescricao(){
        return descricao;
    }
    private void setDescricao(String descricao){
        if(descricao == null || descricao.isBlank()){
            throw new IllegalArgumentException("Descrição inválida!");
        }
        this.descricao = descricao;
    }
}
