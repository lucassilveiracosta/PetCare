package business.model.medicine;

import enums.MedicineType;
import business.model.invoice.Product;

public class Medicine extends Product {

    private MedicineType type;
    private String batch;

    public Medicine(String name, Integer quantity, String description, Double price, MedicineType type, String batch) {
        super(name, quantity, description, price);

        this.type = type;
        this.batch = batch;
    }

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        if(type == null){
            throw new IllegalArgumentException("400 - Invalid type");
        }
        this.type = type;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        if(batch == null || batch.isBlank()){
            throw new IllegalArgumentException("400 - Invalid batch");
        }
        this.batch = batch;
    }
}