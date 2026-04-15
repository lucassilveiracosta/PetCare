package model.Pessoas;
import  model.Pessoas.Pessoa;

import java.time.LocalDate;

public class Dono extends Pessoa {
    private String profissao;
    private String descricao;

    public Dono(String nome, LocalDate dataNascimento, String cpf, String telefone, String profissao, String descricao){
        super(nome, dataNascimento, cpf, telefone);
        setProfissao(profissao);
        setDescricao(descricao);
    }
    public String getProfissao(){
        return profissao;
    }
    private void setProfissao(String profissao){
        if(profissao == null || profissao.isBlank()){
            throw new IllegalArgumentException("Profissão inválida!");
        }
        this.profissao = profissao;
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
