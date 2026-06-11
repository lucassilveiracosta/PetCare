package business.model.invoice;


import enums.MedicineType;

public class Product {

    private static int count_id = 1;
    private int id = count_id++;
    private String name;
    private Integer quantity;
    private final boolean isVet; // Se é do petshop ou do veterinário
    private MedicineType medicineType;
    private String description; // e boolean para ver se o remédio é controlado
    private boolean isControlled;
    private Double price;

    public Product(String name, Integer quantity, String description, Double price, boolean isVet, MedicineType medicineType){
        setName(name);
        setQuantity(quantity);
        setDescription(description);
        this.price = price;
        this.isVet = isVet;
        if (isVet) {
            this.medicineType = medicineType;
            this.isControlled = isControlled;
        }
        else{
            this.isControlled = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("400 - Invalid name");
        this.name = name;
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

    public boolean isVet() {
        return isVet;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public boolean isControlled() {
        return isControlled;
    }
}
