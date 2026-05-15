package data.repository;

import business.model.notaFiscal.Product;
import data.interfaces.IRepositoryStock;

import java.util.ArrayList;

public class RepositoryStock implements IRepositoryStock {

    private final ArrayList<Product> produtos;
    public RepositoryStock(ArrayList<Product> produtos) {
        this.produtos = produtos;
    }


    @Override
    public Product findById(int id) {
        Product produto = null;
        for (Product p : produtos) {
            if (p.getId() == id) {
                produto = p;
            }
        }
        return produto;
    }

    @Override
    public ArrayList<Product> findAll() {
        return produtos;
    }

    @Override
    public void update(int id, Product p) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == id) {
                produtos.set(i, p);
                return;
            }
        }
    }

    @Override
    public void create(Product p) {
        produtos.add(p);
    }

    @Override
    public void remove(Product p) {
        if (p != null) {
            produtos.remove(p);
        }
    }
}
