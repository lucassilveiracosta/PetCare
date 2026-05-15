package business.model.notaFiscal;


public class Product {

    private static int count_id = 1;
    private int id = count_id++;
    private Integer quantity;
    private String description;
    private Double price;

    public Product(Integer quantity, String description, Double price){
        setQuantity(quantity);
        setDescription(description);
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity < 0) throw new IllegalArgumentException("400 - The quantity must be positive");
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("400 - A descrição do produto não pode ser nula");
        }
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if(price == null || price < 0){
            throw new IllegalArgumentException("400 - O preço do produto não pode ser nulo ou negativo");
        }
        this.price = price;
    }

    public int getId() {
        return id;
    }
}
