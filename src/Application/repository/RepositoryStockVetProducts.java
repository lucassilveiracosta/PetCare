package repository;

import model.notaFiscal.Produto;
import repository.Interface.IRepositoryStockVetProducts;

import java.util.ArrayList;
import java.util.List;

public class RepositoryStockVetProducts implements IRepositoryStockVetProducts {

    private List<Produto> produtos = new ArrayList<>();

    public RepositoryStockVetProducts() {
    }

    @Override
    public Produto findById(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public List<Produto> listAll() {
        return new ArrayList<>(produtos);
    }

    @Override
    public void delete(int id) {
        produtos.removeIf(p -> p.getId() == id);
    }


}