package main.java.business.interfaces;


import main.java.business.model.invoice.Product;

import java.util.List;

public interface IControllerStock {
    Product getById(int id);
    List<Product> getAll();
    void patch(int id, Product partialData);
    void put(int id, Product newProduct);
    void delete(int id);
    void post(Product newProduct);
}
