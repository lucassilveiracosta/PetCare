package business.model.notaFiscal;


import java.time.LocalDateTime;

public class Produto {

    private static int contador_id = 1;
    private int id = contador_id++;
    private LocalDateTime dataHora;
    private String descricao;
    private Double preco;

    public Produto(LocalDateTime dataHora, String descricao, Double preco){
        this.dataHora = dataHora;
        setDescricao(descricao);
        this.preco = preco;
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
