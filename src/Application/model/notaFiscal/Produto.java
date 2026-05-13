package model.notaFiscal;


import java.time.LocalDateTime;

public class Produto {

    private LocalDateTime dataHora;
    private String descricao;
    private Double preco;
    private int id;

    public Produto(LocalDateTime dataHora, String descricao, Double preco, int id){
    this.dataHora = dataHora;
    setDescricao(descricao);
    this.preco = preco;
    this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do produto não pode ser nula");
        }
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getId() {
        return id;
    }
}
