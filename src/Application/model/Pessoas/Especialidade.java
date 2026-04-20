package model.Pessoas;

public class Especialidade {
    private String nome;
    private String descricao;

    public Especialidade(String nome, String descricao) {
        setNome(nome);
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("O nome da especialidade não pode ser em branco ou nulo");
        }
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
