package model.Especialidade;

public class Especialidade {
    private String nome;
    private String descricao;
    public Especialidade(String nome, String descricao){
        setNome(nome);
        setDescricao(descricao);
    }
    public String getNome(){
        return nome;
    }
    private void setNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome da especialidade inválida!");
        }
        this.nome = nome;
    }
    public String getDescricao(){
        return descricao;
    }
    private void setDescricao(String descricao){
        if(descricao == null || nome.isBlank()){
            throw new IllegalArgumentException("Descrição da especialidade inválida!");
        }
        this.descricao = descricao;
    }


}
