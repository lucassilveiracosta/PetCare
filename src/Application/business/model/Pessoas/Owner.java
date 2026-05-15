package business.model.Pessoas;

import java.time.LocalDate;

public class Owner extends Person {
    private String profissao;
    private String descricao;

    public Owner(String nome, String email, String password, LocalDate dataNascimento, String cpf, String telefone, String profissao, String descricao){
        super(nome, email, password, dataNascimento, cpf, telefone);
        setProfissao(profissao);
        setDescricao(descricao);
    }
    public String getProfissao(){
        return profissao;
    }
    public void setProfissao(String profissao){
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
