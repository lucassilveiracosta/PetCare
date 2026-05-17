package data.interfaces;

import business.model.invoice.Product;

import java.util.ArrayList;

public interface IRepositoryStock {

    Product findById(int id);
    ArrayList<Product> findAll();
    void update(int index, Product p);
    void create(Product p);
    void remove(Product p);
}
