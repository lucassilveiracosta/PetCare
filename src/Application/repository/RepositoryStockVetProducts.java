package repository;

import model.notaFiscal.Produto;
import repository.Interface.IRepositoryStockVetProducts;

import java.util.ArrayList;

public class RepositoryStockVetProducts implements IRepositoryStockVetProducts {

    private ArrayList<Produto> produtos;
    public RepositoryStockVetProducts(ArrayList<Produto> produtos) {
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
        produtos.set(id, p);
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
