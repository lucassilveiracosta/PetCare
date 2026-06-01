package business.interfaces;

import business.model.invoice.Product;
import enums.MedicineType;

import java.util.List;

public interface IControllerStock {
    Product getById(int id);
    List<Product> getAll();
    void patch(int id, Product partialData);
    void put(int id, Product newProduct);
    void delete(int id);
    void post(Product newProduct);

    List<Product> filterByMedicineType(MedicineType medicineType);
    List<Product> filterPetShopProducts();
    public List<Product> filterVeterinarianProducts();
}
