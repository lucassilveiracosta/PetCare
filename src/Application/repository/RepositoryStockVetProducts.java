package repository;

import model.notaFiscal.Produto;
import repository.interfaces.IRepositoryStockVetProducts;

import java.util.ArrayList;

public class RepositoryStockVetProducts implements IRepositoryStockVetProducts {

    private final ArrayList<Produto> produtos;
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
    public void update(int index, Produto p) {
        produtos.set(index, p);
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
