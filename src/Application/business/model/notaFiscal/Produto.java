package business.model.notaFiscal;


import java.time.LocalDateTime;

public class Produto {

    private static int contador_id = 1;
    private int id = contador_id++;
    private Integer quantity;
    private String description;
    private Double preco;

    public Produto(Integer quantity, String description, Double preco){
        setQuantity(quantity);
        setDescricao(description);
        this.preco = preco;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity < 0) throw new IllegalArgumentException("The quantity must be positive");
        this.quantity = quantity;
    }

    public String getDescricao() {
        return description;
    }

    public void setDescricao(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("A descrição do produto não pode ser nula");
        }
        this.description = description;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        if(preco == null || preco < 0){
            throw new IllegalArgumentException("O preço do produto não pode ser nulo ou negativo");
        }
        this.preco = preco;
    }

    public int getId() {
        return id;
    }
}
