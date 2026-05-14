package data.repository;

import business.model.notaFiscal.Produto;
import data.interfaces.IRepositoryStockGeneralProducts;

import java.util.ArrayList;

public class RepositoryStockGeneralProducts implements IRepositoryStockGeneralProducts {

    private final ArrayList<Produto> produtos;
    public RepositoryStockGeneralProducts(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }


    @Override
    public Produto findById(int id) {
        Produto produto = null;
        for (Produto p : produtos) {
            if (p.getId() == id) {
                produto = p;
            }
        }
        return produto;
    }

    @Override
    public ArrayList<Produto> findAll() {
        return produtos;
    }

    @Override
    public void update(int id, Produto p) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == id) {
                produtos.set(i, p);
                return;
            }
        }
    }

    @Override
    public void create(Produto p) {
        produtos.add(p);
    }

    @Override
    public void remove(Produto p) {
        if (p != null) {
            produtos.remove(p);
        }
    }
}
